package com.jauntsdn.messagestreams.grpc.grpcclient;

import example.Request;
import example.Response;
import example.StreamServiceGrpc;
import io.grpc.Codec;
import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ResourceLeakDetector;
import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws Exception {
    ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);

    String port = System.getProperty("SERVICE_PORT_GRPC", "7782");
    String service = System.getProperty("SERVICE", "localhost/reply");
    String host = service(service);
    String call = call(service);
    logger.info("==> MSTREAMS GRPC SERVICE: grpc-java client {}", service);
    logger.info("==> MSTREAMS GRPC SERVICE: address {}:{}", host, port);
    logger.info("==> MSTREAMS GRPC SERVICE: call {}", call);

    CompressorRegistry compressorRegistry = CompressorRegistry.newEmptyInstance();
    compressorRegistry.register(Codec.Identity.NONE);
    DecompressorRegistry decompressorRegistry =
        DecompressorRegistry.emptyInstance().with(Codec.Identity.NONE, true);

    NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);

    ManagedChannel channel =
        NettyChannelBuilder.forAddress(inetSocketAddress(host, port))
            .eventLoopGroup(eventLoopGroup)
            .channelType(NioSocketChannel.class)
            .compressorRegistry(compressorRegistry)
            .decompressorRegistry(decompressorRegistry)
            .usePlaintext()
            .flowControlWindow(1_000_000)
            .build();

    switch (call) {
      case "stream":
        Stream.start(channel, eventLoopGroup.next());
        break;
      case "reply":
        Reply.start(channel, eventLoopGroup.next());
        break;
      default:
        channel.shutdownNow();
        logger.info("Unsupported interaction: {}", call);
    }
    channel.awaitTermination(365, TimeUnit.DAYS);
  }

  static final class Reply {

    static void start(ManagedChannel channel, ScheduledExecutorService executor) {
      StreamServiceGrpc.StreamServiceStub streamService = StreamServiceGrpc.newStub(channel);

      Request request = Request.newBuilder().setMessage("data").build();

      Counter counter = new Counter();

      executor.scheduleAtFixedRate(
          () -> logger.info("client received messages: {}", counter.responses()),
          1,
          1,
          TimeUnit.SECONDS);

      counter.onRequest(
          requests -> {
            for (int i = 0; i < requests; i++) {
              streamService.reply(
                  request,
                  new StreamObserver<>() {
                    @Override
                    public void onNext(Response value) {
                      counter.count();
                    }

                    @Override
                    public void onError(Throwable t) {
                      logger.error("rsocket-rpc request-response error", t);
                      ((ManagedChannel) streamService.getChannel()).shutdownNow();
                    }

                    @Override
                    public void onCompleted() {}
                  });
            }
          });
    }

    static class Counter {
      private static final int REQUESTS = 77;
      private final AtomicInteger responses = new AtomicInteger();
      private final AtomicInteger requests = new AtomicInteger(REQUESTS);
      private volatile IntConsumer onRequest;

      public void onRequest(IntConsumer onRequest) {
        this.onRequest = onRequest;
        onRequest.accept(REQUESTS);
      }

      void count() {
        responses.incrementAndGet();
        if (requests.decrementAndGet() == REQUESTS / 2) {
          requests.addAndGet(REQUESTS);
          onRequest.accept(REQUESTS);
        }
      }

      int responses() {
        return responses.getAndSet(0);
      }
    }
  }

  static class Stream {

    public static void start(ManagedChannel channel, ScheduledExecutorService executor) {
      StreamServiceGrpc.StreamServiceStub streamService = StreamServiceGrpc.newStub(channel);

      Request request = Request.newBuilder().setMessage("data").build();

      Counter counter = new Counter(executor);

      streamService.serverStream(
          request,
          new StreamObserver<>() {

            @Override
            public void onNext(Response value) {
              counter.count();
            }

            @Override
            public void onError(Throwable t) {
              logger.error("rsocket-rpc request-stream example error", t);
              channel.shutdownNow();
            }

            @Override
            public void onCompleted() {}
          });
    }

    private static class Counter {
      final AtomicInteger counter = new AtomicInteger();

      Counter(ScheduledExecutorService executor) {
        executor.scheduleAtFixedRate(
            () -> logger.info("client received messages: {}", counter.getAndSet(0)),
            1,
            1,
            TimeUnit.SECONDS);
      }

      public void count() {
        counter.incrementAndGet();
      }
    }
  }

  static InetSocketAddress inetSocketAddress(String host, String port) {
    return new InetSocketAddress(host, Integer.parseInt(port));
  }

  static String call(String serviceAddress) {
    int idx = serviceAddress.lastIndexOf("/");
    if (idx == -1) {
      throw new IllegalArgumentException("Unexpected service format: " + serviceAddress);
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
      throw new IllegalArgumentException("Unexpected service format: " + serviceAddress);
    }
    return serviceAddress.substring(0, idx);
  }
}
