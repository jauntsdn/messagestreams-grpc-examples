// Generated by jauntsdn.com rsocket-rpc compiler (version 1.5.4)
// Should be referenced by generated code only

package com.jauntsdn.rsocket.generated;

public final class ProtobufCodec {
  private ProtobufCodec() {
  }

  public static java.util.function.Function<com.google.protobuf.MessageLite, com.jauntsdn.rsocket.Message> encode(
    String stubName, io.netty.buffer.ByteBufAllocator allocator, com.jauntsdn.rsocket.Rpc.Codec codec) {
      return new java.util.function.Function<com.google.protobuf.MessageLite, com.jauntsdn.rsocket.Message>() {
        @Override
        public com.jauntsdn.rsocket.Message apply(com.google.protobuf.MessageLite message) {
          int length = message.getSerializedSize();
          io.netty.buffer.ByteBuf byteBuf = codec.encodeContent(allocator, length);
          try {
            int writerIndex = byteBuf.writerIndex();
            message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(byteBuf.internalNioBuffer(writerIndex, length)));
            byteBuf.writerIndex(writerIndex + length);
            return codec.encodeMessage(byteBuf);
          } catch (Throwable t) {
            byteBuf.release();
            com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
            throw new com.jauntsdn.rsocket.exceptions.SerializationException(stubName + ": message serialization error", t);
          }
        }
      };
  }

  public static <T> io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> decode(String stubName, final com.google.protobuf.Parser<T> parser,
    io.grpc.stub.StreamObserver<T> request, io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message> response) {
    return new io.grpc.stub.StreamObserver<com.jauntsdn.rsocket.Message>() {
      boolean first = true;

      @Override
      public void onNext(com.jauntsdn.rsocket.Message message) {
        try {
          io.netty.buffer.ByteBuf messageData = message.data();
          com.google.protobuf.CodedInputStream is = com.google.protobuf.CodedInputStream.newInstance(messageData.internalNioBuffer(0, messageData.readableBytes()));
          request.onNext(parser.parseFrom(is));
        } catch (Throwable t) {
          com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
          response.onError(new com.jauntsdn.rsocket.exceptions.SerializationException(stubName + ": message deserialization error", t));
        } finally {
          if (!first) {
            message.release();
            return;
          }
          first = false;
        }
      }

      @Override
      public void onError(Throwable t) {
        request.onError(t);
      }

      @Override
      public void onCompleted() {
        request.onCompleted();
      }
    };
  }

  public static io.netty.buffer.ByteBuf encode(String stubName, io.netty.buffer.ByteBuf content, com.google.protobuf.MessageLite message) {
    int length = message.getSerializedSize();
    try {
      int writerIndex = content.writerIndex();
      message.writeTo(com.google.protobuf.CodedOutputStream.newInstance(content.internalNioBuffer(writerIndex, length)));
      content.writerIndex(writerIndex + length);
      return content;
    } catch (Throwable t) {
      content.release();
      com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
      throw new com.jauntsdn.rsocket.exceptions.SerializationException(stubName + ": message serialization error", t);
    }
  }

  public static <T> java.util.function.Function<com.jauntsdn.rsocket.Message, T> decode(String stubName, final com.google.protobuf.Parser<T> parser) {
    return new java.util.function.Function<com.jauntsdn.rsocket.Message, T>() {
      @Override
      public T apply(com.jauntsdn.rsocket.Message message) {
        try {
          io.netty.buffer.ByteBuf messageData = message.data();
          com.google.protobuf.CodedInputStream is =
            com.google.protobuf.CodedInputStream.newInstance(messageData.internalNioBuffer(0, messageData.readableBytes()));
          return parser.parseFrom(is);
        } catch (Throwable t) {
          com.jauntsdn.rsocket.exceptions.Exceptions.throwIfJvmFatal(t);
          throw new com.jauntsdn.rsocket.exceptions.SerializationException(stubName + ": message deserialization error", t);
        } finally {
          message.release();
        }
      }
    };
  }
  public static io.netty.buffer.ByteBuf encodeHeaders(com.jauntsdn.rsocket.Headers headers) {
    return com.jauntsdn.rsocket.Rpc.ProtoMetadata.encodeHeaders(headers);
  }

  public static com.jauntsdn.rsocket.Headers decodeHeaders(io.netty.buffer.ByteBuf metadata) throws java.io.IOException {
    return com.jauntsdn.rsocket.Rpc.ProtoMetadata.decodeHeaders(metadata);
  }
}
