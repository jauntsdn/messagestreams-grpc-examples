// Generated by jauntsdn.com rpc compiler (version 1.3.2)
// source: service.proto

package example;

public interface StreamService {
  String SERVICE = "example.StreamService";
  Class<?> SERVICE_TYPE = example.StreamService.class;

  String METHOD_REPLY = "reply";
  boolean METHOD_REPLY_IDEMPOTENT = false;
  int METHOD_REPLY_RANK = 0;

  String METHOD_SERVER_STREAM = "serverStream";
  boolean METHOD_SERVER_STREAM_IDEMPOTENT = false;
  int METHOD_SERVER_STREAM_RANK = 0;

  String METHOD_BIDI_STREAM = "bidiStream";
  boolean METHOD_BIDI_STREAM_IDEMPOTENT = false;
  int METHOD_BIDI_STREAM_RANK = 0;

  void reply(example.Request message, io.netty.buffer.ByteBuf metadata, io.grpc.stub.StreamObserver<example.Response> observer);

  void serverStream(example.Request message, io.netty.buffer.ByteBuf metadata, io.grpc.stub.StreamObserver<example.Response> observer);

  io.grpc.stub.StreamObserver<example.Request> bidiStream(io.netty.buffer.ByteBuf metadata, io.grpc.stub.StreamObserver<example.Response> observer);

  default void reply(example.Request message, io.grpc.stub.StreamObserver<example.Response> observer) {
    reply(message, io.netty.buffer.Unpooled.EMPTY_BUFFER, observer);
  }

  default void serverStream(example.Request message, io.grpc.stub.StreamObserver<example.Response> observer) {
    serverStream(message, io.netty.buffer.Unpooled.EMPTY_BUFFER, observer);
  }

  default io.grpc.stub.StreamObserver<example.Request> bidiStream(io.grpc.stub.StreamObserver<example.Response> observer) {
    return bidiStream(io.netty.buffer.Unpooled.EMPTY_BUFFER, observer);
  }
}
