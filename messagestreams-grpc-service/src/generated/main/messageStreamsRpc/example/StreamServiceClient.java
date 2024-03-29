// Generated by jauntsdn.com rpc compiler (version 1.5.4)
// source: service.proto

package example;

@com.jauntsdn.rsocket.Rpc.Generated(
    role = com.jauntsdn.rsocket.Rpc.Role.CLIENT,
    service = StreamService.class)
@SuppressWarnings("all")
public final class StreamServiceClient implements StreamService {
  private final com.jauntsdn.rsocket.MessageStreams streams;
  private final io.netty.buffer.ByteBufAllocator allocator;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<example.Response> replyInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<example.Response> serverStreamInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<example.Response> clientStreamInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<example.Response> bidiStreamInstrumentation;
  private final com.jauntsdn.rsocket.RpcInstrumentation.Factory<com.google.protobuf.Empty> fnfInstrumentation;
  private final com.jauntsdn.rsocket.Rpc.Codec rpcCodec;

  private StreamServiceClient(com.jauntsdn.rsocket.MessageStreams streams, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
    this.streams = streams;
    this.allocator = streams.allocator().orElse(io.netty.buffer.ByteBufAllocator.DEFAULT);
    com.jauntsdn.rsocket.RpcInstrumentation i = instrumentation == null
      ? streams.attributes().attr(com.jauntsdn.rsocket.Attributes.RPC_INSTRUMENTATION)
      : instrumentation.orElse(null);
    if (i == null) {
      this.replyInstrumentation = null;
      this.serverStreamInstrumentation = null;
      this.clientStreamInstrumentation = null;
      this.bidiStreamInstrumentation = null;
      this.fnfInstrumentation = null;
    } else {
      this.replyInstrumentation = i.instrument("client", StreamService.SERVICE, StreamService.METHOD_REPLY, false);
      this.serverStreamInstrumentation = i.instrument("client", StreamService.SERVICE, StreamService.METHOD_SERVER_STREAM, true);
      this.clientStreamInstrumentation = i.instrument("client", StreamService.SERVICE, StreamService.METHOD_CLIENT_STREAM, true);
      this.bidiStreamInstrumentation = i.instrument("client", StreamService.SERVICE, StreamService.METHOD_BIDI_STREAM, true);
      this.fnfInstrumentation = i.instrument("client", StreamService.SERVICE, StreamService.METHOD_FNF, false);
    }
    com.jauntsdn.rsocket.Rpc.Codec codec = streams.attributes().attr(com.jauntsdn.rsocket.Attributes.RPC_CODEC);
    if (codec != null) {
      rpcCodec = codec;
      if (codec.isDisposable()) {
        streams.onClose().thenAccept(ignored -> codec.dispose());
      }
      return;
    }
    throw new IllegalArgumentException("MessageStreams " + streams.getClass() + " does not provide RPC codec");
  }

  public static StreamServiceClient create(com.jauntsdn.rsocket.MessageStreams streams, java.util.Optional<com.jauntsdn.rsocket.RpcInstrumentation> instrumentation) {
    java.util.Objects.requireNonNull(streams, "streams");
    java.util.Objects.requireNonNull(instrumentation, "instrumentation");
    return new StreamServiceClient(streams, instrumentation);
  }

  public static StreamServiceClient create(com.jauntsdn.rsocket.MessageStreams streams) {
    java.util.Objects.requireNonNull(streams, "streams");
    return new StreamServiceClient(streams, null);
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = example.Response.class)
  public void reply(example.Request message, com.jauntsdn.rsocket.Headers headersMetadata, io.grpc.stub.StreamObserver<example.Response> observer) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    boolean isDefaultService = headersMetadata.isDefaultService();
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : StreamService.SERVICE;
    io.netty.buffer.ByteBuf metadata = com.jauntsdn.rsocket.generated.ProtobufCodec.encodeHeaders(headersMetadata);
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, service, StreamService.METHOD_REPLY, false, StreamService.METHOD_REPLY_IDEMPOTENT, dataSize, externalMetadataSize);
    com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, StreamService.METHOD_REPLY_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<example.Response> instrumentationListener = null;
    if (replyInstrumentation != null) {
      instrumentationListener = replyInstrumentation.create();
    }
    streams.requestResponse(msg, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Client.decode(observer, com.jauntsdn.rsocket.generated.ProtobufCodec.decode("StreamServiceClient", example.Response.parser()), instrumentationListener));
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = example.Response.class)
  public void serverStream(example.Request message, com.jauntsdn.rsocket.Headers headersMetadata, io.grpc.stub.StreamObserver<example.Response> observer) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    boolean isDefaultService = headersMetadata.isDefaultService();
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : StreamService.SERVICE;
    io.netty.buffer.ByteBuf metadata = com.jauntsdn.rsocket.generated.ProtobufCodec.encodeHeaders(headersMetadata);
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, service, StreamService.METHOD_SERVER_STREAM, true, StreamService.METHOD_SERVER_STREAM_IDEMPOTENT, dataSize, externalMetadataSize);
    com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, StreamService.METHOD_SERVER_STREAM_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<example.Response> instrumentationListener = null;
    if (serverStreamInstrumentation != null) {
      instrumentationListener = serverStreamInstrumentation.create();
    }
    streams.requestStream(msg, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Client.decode(observer, com.jauntsdn.rsocket.generated.ProtobufCodec.decode("StreamServiceClient", example.Response.parser()), instrumentationListener));
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = example.Response.class)
  public void clientStream(example.Request message, com.jauntsdn.rsocket.Headers headersMetadata, io.grpc.stub.StreamObserver<example.Response> observer) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    boolean isDefaultService = headersMetadata.isDefaultService();
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : StreamService.SERVICE;
    io.netty.buffer.ByteBuf metadata = com.jauntsdn.rsocket.generated.ProtobufCodec.encodeHeaders(headersMetadata);
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, service, StreamService.METHOD_CLIENT_STREAM, true, StreamService.METHOD_CLIENT_STREAM_IDEMPOTENT, dataSize, externalMetadataSize);
    com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, StreamService.METHOD_CLIENT_STREAM_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<example.Response> instrumentationListener = null;
    if (clientStreamInstrumentation != null) {
      instrumentationListener = clientStreamInstrumentation.create();
    }
    streams.requestStream(msg, com.jauntsdn.rsocket.RpcMessageCodec.Stream.Client.decode(observer, com.jauntsdn.rsocket.generated.ProtobufCodec.decode("StreamServiceClient", example.Response.parser()), instrumentationListener));
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = example.Response.class)
  public io.grpc.stub.StreamObserver<example.Request> bidiStream(com.jauntsdn.rsocket.Headers headersMetadata, io.grpc.stub.StreamObserver<example.Response> observer) {
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<example.Response> instrumentationListener = null;
    if (bidiStreamInstrumentation != null) {
      instrumentationListener = bidiStreamInstrumentation.create();
    }
    com.jauntsdn.rsocket.RpcMessageCodec.Channel.Client.Encoder<example.Request> bidiStreamEncoder = 
      new com.jauntsdn.rsocket.RpcMessageCodec.Channel.Client.Encoder<example.Request>(instrumentationListener) {
        private boolean started;

        @Override
        public com.jauntsdn.rsocket.Message onNext(example.Request message) {
          int dataSize = message.getSerializedSize();
          com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
          if (!started) {
            started = true;
            int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
            boolean isDefaultService = headersMetadata.isDefaultService();
            String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : StreamService.SERVICE;
            io.netty.buffer.ByteBuf metadata = com.jauntsdn.rsocket.generated.ProtobufCodec.encodeHeaders(headersMetadata);
            io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, service, StreamService.METHOD_BIDI_STREAM, true, StreamService.METHOD_BIDI_STREAM_IDEMPOTENT, dataSize, externalMetadataSize);
            com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
            return codec.encodeMessage(content, StreamService.METHOD_BIDI_STREAM_RANK);
          } else {
            io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, dataSize);
            com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
            return codec.encodeMessage(content);
          }
        }
    };
    io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> bidiStreamRequest = streams.requestChannel(
      com.jauntsdn.rsocket.RpcMessageCodec.Channel.Client.decode(observer, bidiStreamEncoder, com.jauntsdn.rsocket.generated.ProtobufCodec.decode("StreamServiceClient", example.Response.parser()), instrumentationListener));
    return bidiStreamEncoder.encodeStream(bidiStreamRequest);
  }

  @Override
  @com.jauntsdn.rsocket.Rpc.GeneratedMethod(returnType = com.google.protobuf.Empty.class)
  public void fnf(example.Request message, com.jauntsdn.rsocket.Headers headersMetadata, io.grpc.stub.StreamObserver<com.google.protobuf.Empty> observer) {
    int externalMetadataSize = streams.attributes().intAttr(com.jauntsdn.rsocket.Attributes.EXTERNAL_METADATA_SIZE);
    int dataSize = message.getSerializedSize();
    boolean isDefaultService = headersMetadata.isDefaultService();
    String service = isDefaultService ? com.jauntsdn.rsocket.Rpc.RpcMetadata.defaultService() : StreamService.SERVICE;
    io.netty.buffer.ByteBuf metadata = com.jauntsdn.rsocket.generated.ProtobufCodec.encodeHeaders(headersMetadata);
    com.jauntsdn.rsocket.Rpc.Codec codec = rpcCodec;
    io.netty.buffer.ByteBuf content = codec.encodeContent(allocator, metadata, service, StreamService.METHOD_FNF, false, StreamService.METHOD_FNF_IDEMPOTENT, dataSize, externalMetadataSize);
    com.jauntsdn.rsocket.generated.ProtobufCodec.encode("StreamServiceClient", content, message);
    com.jauntsdn.rsocket.Message msg = codec.encodeMessage(content, StreamService.METHOD_FNF_RANK);
    com.jauntsdn.rsocket.RpcInstrumentation.Listener<com.google.protobuf.Empty> instrumentationListener = null;
    if (fnfInstrumentation != null) {
      instrumentationListener = fnfInstrumentation.create();
    }
    streams.fireAndForget(msg, com.jauntsdn.rsocket.RpcMessageCodec.FireAndForget.Client.decode(observer, com.google.protobuf.Empty.getDefaultInstance(), instrumentationListener));
  }
}
