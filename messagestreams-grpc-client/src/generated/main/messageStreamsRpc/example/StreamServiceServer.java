// Generated by jauntsdn.com rpc compiler (version 1.5.1)
// source: service.proto

package example;

@com.jauntsdn.rsocket.Rpc.Generated(
    role = com.jauntsdn.rsocket.Rpc.Role.SERVICE,
    service = StreamService.class)
@SuppressWarnings("all")
public final class StreamServiceServer implements com.jauntsdn.rsocket.RpcService {
  private final java.util.concurrent.CompletableFuture<Void> onClose = new java.util.concurrent.CompletableFuture<>();
  private final StreamService service;
  private final java.util.function.Function<com.google.protobuf.MessageLite, com.jauntsdn.rsocket.Message> messageEncoder;
  private final com.jauntsdn.rsocket.Rpc.Codec rpcCodec;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<com.jauntsdn.rsocket.Message> replyInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<com.jauntsdn.rsocket.Message> serverStreamInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<com.jauntsdn.rsocket.Message> bidiStreamInstrumentation;

  private StreamServiceServer(StreamService service, com.jauntsdn.rsocket.RpcInstrumentation instrumentation, io.netty.buffer.ByteBufAllocator allocator, com.jauntsdn.rsocket.Rpc.Codec rpcCodec) {
    this.messageEncoder = com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceServer", allocator, rpcCodec);
    this.service = service;
    this.rpcCodec = rpcCodec;
    if (instrumentation == null) {
      this.replyInstrumentation = null;
      this.serverStreamInstrumentation = null;
      this.bidiStreamInstrumentation = null;
    } else {
      this.replyInstrumentation = instrumentation.instrument("service", StreamService.SERVICE, StreamService.METHOD_REPLY, false);
      this.serverStreamInstrumentation = instrumentation.instrument("service", StreamService.SERVICE, StreamService.METHOD_SERVER_STREAM, true);
      this.bidiStreamInstrumentation = instrumentation.instrument("service", StreamService.SERVICE, StreamService.METHOD_BIDI_STREAM, true);
    }
  }

  public static StreamServiceServer.Factory create(StreamService service, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
    return new StreamServiceServer.Factory(service, instrumentation);
  }

  public static StreamServiceServer.Factory create(StreamService service) {
    return new StreamServiceServer.Factory(service);
  }

  @Override
  public String service() {
    return StreamService.SERVICE;
  }

  @Override
  public Class<?> serviceType() {
    return StreamService.SERVICE_TYPE;
  }

  @Override
  public void fireAndForget(com.jauntsdn.rsocket.Message message, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) {
    message.release();
    observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: fireAndForget not implemented"));
  }

  @Override
  public void requestResponse(com.jauntsdn.rsocket.Message message, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) {
    try {
      io.netty.buffer.ByteBuf metadata = message.metadata();
      long header = com.jauntsdn.rsocket.Rpc.RpcMetadata.header(metadata);
      int flags = com.jauntsdn.rsocket.Rpc.RpcMetadata.flags(header);
      String method = rpcCodec.decodeMessageMethod(metadata, header, flags);

      if (requestResponseHandler(flags, method, message.data(), metadata, observer)) {
        return;
      }
      observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: requestResponse not implemented"));
    } catch (Throwable t) {
      observer.onError(t);
    } finally {
      message.release();
    }
  }

  @Override
  public void requestStream(com.jauntsdn.rsocket.Message message, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) {
    try {
      io.netty.buffer.ByteBuf metadata = message.metadata();
      long header = com.jauntsdn.rsocket.Rpc.RpcMetadata.header(metadata);
      int flags = com.jauntsdn.rsocket.Rpc.RpcMetadata.flags(header);
      String method = rpcCodec.decodeMessageMethod(metadata, header, flags);

      if (requestStreamHandler(flags, method, message.data(), metadata, observer)) {
        return;
      }
      observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: requestStream unknown method: " + method));
    } catch (Throwable t) {
      observer.onError(t);
    } finally {
      message.release();
    }
  }

  @Override
  public io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> requestChannel(io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) {
    observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: unsupported method: requestChannel(Observer<Message>)"));
    return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
  }

  @Override
  public io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> requestChannel(com.jauntsdn.rsocket.Message message, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) {
    try {
      io.netty.buffer.ByteBuf metadata = message.metadata();
      long header = com.jauntsdn.rsocket.Rpc.RpcMetadata.header(metadata);
      int flags = com.jauntsdn.rsocket.Rpc.RpcMetadata.flags(header);
      String method = rpcCodec.decodeMessageMethod(metadata, header, flags);

      switch (method) {
        case StreamService.METHOD_BIDI_STREAM: {
          if (!StreamService.METHOD_BIDI_STREAM_IDEMPOTENT && com.jauntsdn.rsocket.Rpc.RpcMetadata.flagIdempotentCall(flags)) {
            observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: idempotent call to non-idempotent method: " + method));
            return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
          }
          com.jauntsdn.rsocket.RpcInstrumentation.Listener<com.jauntsdn.rsocket.Message> instrumentationListener = null;
          if (bidiStreamInstrumentation != null) {
            instrumentationListener = bidiStreamInstrumentation.create();
          }
          com.jauntsdn.rsocket.Headers bidiStreamHeaders = com.jauntsdn.rsocket.generated.ProtobufCodec.decodeHeaders(metadata);
          io.grpc.stub.StreamObserver<example.Request> bidiStream = service.bidiStream(bidiStreamHeaders, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Server.encode(observer, messageEncoder, instrumentationListener));
          io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> request = com.jauntsdn.rsocket.generated.ProtobufCodec.decode("StreamServiceServer", example.Request.parser(), bidiStream, observer);
          request.onNext(message);
          return request;
        }
      }
      if (com.jauntsdn.rsocket.Rpc.RpcMetadata.flagForeignCall(flags)) {
        if (requestStreamHandler(flags, method, message.data(), metadata, observer)) {
          return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
        }
        if (requestResponseHandler(flags, method, message.data(), metadata, observer)) {
          return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
        }
      }
      observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: requestChannel unknown method: " + method));
      return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
    } catch (Throwable t) {
      observer.onError(t);
      return com.jauntsdn.rsocket.MessageStreamsHandler.noopServerObserver();
    } finally {
      message.release();
    }
  }

  @Override
  public void dispose() {
    onClose.complete(null);
  }

  @Override
  public boolean isDisposed() {
    return onClose.isDone();
  }

  @Override
  public java.util.concurrent.CompletionStage<Void> onClose() {
    return onClose;
  }

  private boolean requestResponseHandler(int flags, String method, io.netty.buffer.ByteBuf data, io.netty.buffer.ByteBuf metadata, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) throws java.io.IOException {
    switch (method) {
      case StreamService.METHOD_REPLY: {
        if (!StreamService.METHOD_REPLY_IDEMPOTENT && com.jauntsdn.rsocket.Rpc.RpcMetadata.flagIdempotentCall(flags)) {
          observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: idempotent call to non-idempotent method: " + method));
          return true;
        }
        com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(data.internalNioBuffer(0, data.readableBytes()));
        com.jauntsdn.rsocket.RpcInstrumentation.Listener<com.jauntsdn.rsocket.Message> instrumentationListener = null;
        if (replyInstrumentation != null) {
          instrumentationListener = replyInstrumentation.create();
        }
        com.jauntsdn.rsocket.Headers replyHeaders = com.jauntsdn.rsocket.generated.ProtobufCodec.decodeHeaders(metadata);
        service.reply(example.Request.parseFrom(is), replyHeaders, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Server.encode(observer, messageEncoder, instrumentationListener));
        return true;
      }
      default: {
        return false;
      }
    }
  }

  private boolean requestStreamHandler(int flags, String method, io.netty.buffer.ByteBuf data, io.netty.buffer.ByteBuf metadata, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> observer) throws java.io.IOException {
    switch (method) {
      case StreamService.METHOD_SERVER_STREAM: {
        if (!StreamService.METHOD_SERVER_STREAM_IDEMPOTENT && com.jauntsdn.rsocket.Rpc.RpcMetadata.flagIdempotentCall(flags)) {
          observer.onError(new com.jauntsdn.rsocket.exceptions.RpcException("StreamServiceServer: idempotent call to non-idempotent method: " + method));
          return true;
        }
        com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(data.internalNioBuffer(0, data.readableBytes()));
        com.jauntsdn.rsocket.RpcInstrumentation.Listener<com.jauntsdn.rsocket.Message> instrumentationListener = null;
        if (serverStreamInstrumentation != null) {
          instrumentationListener = serverStreamInstrumentation.create();
        }
        com.jauntsdn.rsocket.Headers serverStreamHeaders = com.jauntsdn.rsocket.generated.ProtobufCodec.decodeHeaders(metadata);
        service.serverStream(example.Request.parseFrom(is), serverStreamHeaders, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Server.encode(observer, messageEncoder, instrumentationListener));
        return true;
      }
      default: {
        return false;
      }
    }
  }

  public static final class Factory extends com.jauntsdn.rsocket.RpcService.ServerFactory<StreamServiceServer> {

    public Factory(StreamService service, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
      super(service, instrumentation);
    }

    public Factory(StreamService service) {
      super(service);
    }

    @Override
    public StreamServiceServer create(com.jauntsdn.rsocket.RpcInstrumentation rpcInstrumentation, io.netty.buffer.ByteBufAllocator allocator, com.jauntsdn.rsocket.Rpc.Codec rpcCodec) {
      return new StreamServiceServer(service(), rpcInstrumentation, allocator, rpcCodec);
    }
  }
}
