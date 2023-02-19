package com.jauntsdn.messagestreams.grpc.service;

import com.jauntsdn.messagestreams.grpc.MessageStreamsFactory;
import com.jauntsdn.rsocket.Disposable;
import com.jauntsdn.rsocket.ServerStreamsAcceptor;
import example.Request;
import example.Response;
import example.StreamService;
import example.StreamServiceServer;
import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import io.netty.buffer.ByteBuf;
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
    logger.info("==> FUTURES SERVICE, TCP: {}", serviceTcpAddress);
    logger.info("==> FUTURES SERVICE, WEBSOCKET: {}", serviceWsHttp2Address);
    logger.info("==> FUTURES SERVICE, GRPC: {}", serviceGrpcAddress);

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
    public void reply(Request message, ByteBuf metadata, StreamObserver<Response> observer) {
      observer.onNext(Response.newBuilder().setMessage(message.getMessage()).build());
    }

    @Override
    public void serverStream(Request message, ByteBuf metadata, StreamObserver<Response> observer) {
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
        logger.info("==> FUTURES SERVER BOUND WITH ERROR: {}:{}", err.getClass(), err.getMessage());
      } else {
        logger.info("==> FUTURES SERVER BOUND SUCCESSFULLY");
      }
    };
  }
}
