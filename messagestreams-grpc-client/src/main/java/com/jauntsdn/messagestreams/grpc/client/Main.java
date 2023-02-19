package com.jauntsdn.messagestreams.grpc.client;

import com.jauntsdn.messagestreams.grpc.MessageStreamsFactory;
import com.jauntsdn.rsocket.Closeable;
import com.jauntsdn.rsocket.MessageMetadata;
import com.jauntsdn.rsocket.MessageStreams;
import example.Request;
import example.Response;
import example.StreamService;
import example.StreamServiceClient;
import io.grpc.stub.StreamObserver;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ResourceLeakDetector;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(MessageStreamsFactory messageStreamsFactory) {
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

    String serviceAddress = System.getProperty("SERVICE", "localhost/stream");

    String service = service(serviceAddress);
    String call = call(serviceAddress);

    logger.info("==> MSTREAMS SERVICE: address {}", serviceAddress);
    logger.info("==> MSTREAMS SERVICE: service {}", service);
    logger.info("==> MSTREAMS SERVICE: call {}", call);

    CompletionStage<MessageStreams> streamServiceFuture =
        messageStreamsFactory.client("MSTREAMS", service).whenComplete(clientStartListener());

    streamServiceFuture
        .thenApply(
            messageStreams -> {
              switch (call) {
                case "reply":
                  Reply.start(messageStreams);
                  break;
                case "stream":
                  Stream.start(messageStreams);
                  break;
              }
              return messageStreams;
            })
        .thenCompose(Closeable::onClose)
        .toCompletableFuture()
        .join();
  }

  static final class Reply {

    public static void start(MessageStreams messageStreams) {

      StreamService streamService = StreamServiceClient.create(messageStreams);

      Request request = Request.newBuilder().setMessage("data").build();
      ByteBuf metadata =
          MessageMetadata.allocator(messageStreams.allocator().get()).defaultService(true).build();

      Counter counter = new Counter();

      ScheduledExecutorService executorService = messageStreams.scheduler().get();
      ScheduledFuture<?> scheduledFuture =
          executorService.scheduleAtFixedRate(
              () -> logger.info("client received messages: {}", counter.responses()),
              1,
              1,
              TimeUnit.SECONDS);
      messageStreams
          .onClose()
          .whenComplete(
              (ignored, err) -> {
                metadata.release();
                scheduledFuture.cancel(true);
              });

      counter.onRequest(
          requests -> {
            for (int i = 0; i < requests; i++) {
              streamService.reply(
                  request,
                  metadata,
                  new StreamObserver<>() {
                    @Override
                    public void onNext(Response value) {
                      counter.count();
                    }

                    @Override
                    public void onError(Throwable t) {
                      logger.error("rsocket-rpc request-response error", t);
                      messageStreams.dispose();
                    }

                    @Override
                    public void onCompleted() {}
                  });
            }
          });
    }

    static class Counter {
      private static final int REQUESTS = 7777;
      private int responses;
      private int requests = REQUESTS;
      private IntConsumer onRequest;

      public void onRequest(IntConsumer onRequest) {
        this.onRequest = onRequest;
        onRequest.accept(requests);
      }

      void count() {
        responses++;
        if (--requests == REQUESTS / 2) {
          requests += REQUESTS;
          onRequest.accept(REQUESTS);
        }
      }

      int responses() {
        int c = responses;
        responses = 0;
        return c;
      }
    }
  }

  static final class Stream {

    public static void start(MessageStreams messageStreams) {

      StreamServiceClient streamServiceClient =
          StreamServiceClient.create(messageStreams, Optional.empty());
      Request request = Request.newBuilder().setMessage("data").build();

      Counter counter = new Counter(messageStreams);

      streamServiceClient.serverStream(
          request,
          Unpooled.EMPTY_BUFFER,
          new StreamObserver<>() {

            @Override
            public void onNext(Response value) {
              counter.count();
            }

            @Override
            public void onError(Throwable t) {
              logger.error("rsocket-rpc request-stream example error", t);
              messageStreams.dispose();
            }

            @Override
            public void onCompleted() {}
          });
    }

    private static class Counter {
      int counter;

      Counter(MessageStreams messageStreams) {
        ScheduledFuture<?> future =
            messageStreams
                .scheduler()
                .get()
                .scheduleAtFixedRate(
                    () -> {
                      int c = counter;
                      counter = 0;
                      logger.info("client received messages: {}", c);
                    },
                    1,
                    1,
                    TimeUnit.SECONDS);
        messageStreams.onClose().thenAccept(v -> future.cancel(true));
      }

      public void count() {
        counter++;
      }
    }
  }

  static String call(String serviceAddress) {
    int idx = serviceAddress.lastIndexOf("/");
    if (idx == -1) {
      throw new IllegalArgumentException("Unexpected address format: " + serviceAddress);
    }
    String call = serviceAddress.substring(idx + 1);
    if ("reply".equals(call) || "stream".equals(call)) {
      return call;
    }
    throw new IllegalArgumentException("Unsupported interaction: " + call);
  }

  static String service(String serviceAddress) {
    int idx = serviceAddress.lastIndexOf("/");
    if (idx == -1) {
      throw new IllegalArgumentException("Unexpected address format: " + serviceAddress);
    }
    return serviceAddress.substring(0, idx);
  }

  private static BiConsumer<MessageStreams, Throwable> clientStartListener() {
    return (ignored, err) -> {
      if (err != null) {
        logger.info(
            "==> MSTREAMS-GRPC SERVICE CONNECTION ERROR: {}:{}", err.getClass(), err.getMessage());
      } else {
        logger.info("==> MSTREAMS-GRPC SERVICE CONNECTED SUCCESSFULLY");
      }
    };
  }
}
