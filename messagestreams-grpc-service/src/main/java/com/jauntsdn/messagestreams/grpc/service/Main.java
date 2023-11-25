package com.jauntsdn.messagestreams.grpc.service;

import com.google.protobuf.Empty;
import com.jauntsdn.messagestreams.grpc.MessageStreamsFactory;
import com.jauntsdn.rsocket.Disposable;
import com.jauntsdn.rsocket.Headers;
import com.jauntsdn.rsocket.ServerStreamsAcceptor;
import example.Request;
import example.Response;
import example.StreamService;
import example.StreamServiceServer;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import io.netty.util.ResourceLeakDetector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  @SuppressWarnings("unchecked")
  public static void main(MessageStreamsFactory messageStreamsFactory) {
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

    String serviceTcpAddress = System.getProperty("SERVICE_ADDRESS_TCP", "tcp://localhost");
    String serviceWsHttp2Address = System.getProperty("SERVICE_ADDRESS_WS", "ws://localhost");
    String serviceGrpcAddress = System.getProperty("SERVICE_ADDRESS_GRPC", "grpc://localhost");
    logger.info("==> GRPC-API SERVICE, TCP: {}", serviceTcpAddress);
    logger.info("==> GRPC-API SERVICE, WEBSOCKET: {}", serviceWsHttp2Address);
    logger.info("==> GRPC-API SERVICE, GRPC: {}", serviceGrpcAddress);

    /*service are implemented in terms of grpc-stubs (StreamObserver API) and generated Protobuf messages */
    StreamService service = new GoodStreamService();

    ServerStreamsAcceptor acceptor =
        (setupMessage, messageStreams) ->
            CompletableFuture.completedFuture(
                /*Generated boilerplate to bind service with MessageStreams*/
                StreamServiceServer.create(service).withLifecycle(messageStreams));

    /*Runtime - including network transports, metrics, load estimator may be trivially
     * hidden from application developer*/

    /*TCP*/
    CompletionStage<Disposable> tcpServer =
        messageStreamsFactory.server("MSTREAMS_SERVICE", serviceTcpAddress).start(acceptor);

    /*WEBSOCKET*/
    CompletionStage<Disposable> wsServer =
        messageStreamsFactory.server("MSTREAMS_SERVICE", serviceWsHttp2Address).start(acceptor);

    /*GRPC*/
    CompletionStage<Disposable> grpcServer =
        messageStreamsFactory.server("MSTREAMS_SERVICE", serviceGrpcAddress).start(acceptor);

    awaitServersClose(tcpServer, wsServer, grpcServer);
  }

  static class GoodStreamService implements StreamService {

    @Override
    public void reply(Request message, Headers metadata, StreamObserver<Response> observer) {
      observer.onNext(Response.newBuilder().setMessage(message.getMessage()).build());
      observer.onCompleted();
    }

    @Override
    public void serverStream(Request message, Headers metadata, StreamObserver<Response> observer) {
      ServerCallStreamObserver<Response> callStreamObserver =
          (ServerCallStreamObserver<Response>) observer;
      Response response = Response.newBuilder().setMessage(message.getMessage()).build();
      callStreamObserver.setOnReadyHandler(
          () -> {
            while (callStreamObserver.isReady()) {
              observer.onNext(response);
            }
          });
    }

    @Override
    public void clientStream(
        Request message, Headers metadata, StreamObserver<Response> observer) {}

    @Override
    public StreamObserver<Request> bidiStream(Headers metadata, StreamObserver<Response> observer) {
      ServerCallStreamObserver<Response> callObserver =
          (ServerCallStreamObserver<Response>) observer;
      ResponseWriter responseWriter = new ResponseWriter(callObserver);
      callObserver.setOnReadyHandler(responseWriter);

      return new StreamObserver<>() {
        boolean responseStarted;

        @Override
        public void onNext(Request message) {
          if (!responseStarted) {
            responseStarted = true;
            responseWriter.write(Response.newBuilder().setMessage(message.getMessage()).build());
          }
        }

        @Override
        public void onError(Throwable t) {
          logger.info("rsocket-rpc bidiStream request error", t);
        }

        @Override
        public void onCompleted() {
          logger.info("rsocket-rpc bidiStream request complete");
          observer.onCompleted();
        }
      };
    }

    @Override
    public void fnf(Request message, Headers metadata, StreamObserver<Empty> observer) {}

    private static class ResponseWriter implements Runnable {
      private final ServerCallStreamObserver<Response> observer;
      private Response response;

      public ResponseWriter(ServerCallStreamObserver<Response> observer) {
        this.observer = observer;
      }

      public void write(Response r) {
        response = r;
        ServerCallStreamObserver<Response> o = observer;
        while (o.isReady()) {
          o.onNext(r);
        }
      }

      @Override
      public void run() {
        Response r = response;
        if (r == null) {
          return;
        }
        ServerCallStreamObserver<Response> o = observer;
        while (o.isReady()) {
          o.onNext(r);
        }
      }
    }
  }

  private static void awaitServersClose(CompletionStage<Disposable>... servers) {
    BiConsumer<Disposable, Throwable> serverStartListener = serverStartListener();
    List<Disposable> startedServers = new ArrayList<>(servers.length);
    for (CompletionStage<Disposable> server : servers) {
      Disposable startedServer =
          server
              .whenComplete(serverStartListener)
              .toCompletableFuture()
              .orTimeout(15, TimeUnit.SECONDS)
              .join();
      startedServers.add(startedServer);
    }
    for (Disposable startedServer : startedServers) {
      startedServer.onClose().awaitUninterruptibly();
    }
  }

  private static BiConsumer<Disposable, Throwable> serverStartListener() {
    return (disposable, err) -> {
      if (err != null) {
        logger.info(
            "==> GRPC-API SERVER BOUND WITH ERROR: {}:{}", err.getClass(), err.getMessage());
      } else {
        logger.info("==> GRPC-API SERVER BOUND SUCCESSFULLY");
      }
    };
  }
}
