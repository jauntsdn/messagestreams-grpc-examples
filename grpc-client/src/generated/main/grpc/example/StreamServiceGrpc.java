package example;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.53.0)",
    comments = "Source: service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class StreamServiceGrpc {

  private StreamServiceGrpc() {}

  public static final String SERVICE_NAME = "example.StreamService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<example.Request,
      example.Response> getReplyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "reply",
      requestType = example.Request.class,
      responseType = example.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<example.Request,
      example.Response> getReplyMethod() {
    io.grpc.MethodDescriptor<example.Request, example.Response> getReplyMethod;
    if ((getReplyMethod = StreamServiceGrpc.getReplyMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getReplyMethod = StreamServiceGrpc.getReplyMethod) == null) {
          StreamServiceGrpc.getReplyMethod = getReplyMethod =
              io.grpc.MethodDescriptor.<example.Request, example.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "reply"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Response.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("reply"))
              .build();
        }
      }
    }
    return getReplyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<example.Request,
      example.Response> getServerStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "serverStream",
      requestType = example.Request.class,
      responseType = example.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<example.Request,
      example.Response> getServerStreamMethod() {
    io.grpc.MethodDescriptor<example.Request, example.Response> getServerStreamMethod;
    if ((getServerStreamMethod = StreamServiceGrpc.getServerStreamMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getServerStreamMethod = StreamServiceGrpc.getServerStreamMethod) == null) {
          StreamServiceGrpc.getServerStreamMethod = getServerStreamMethod =
              io.grpc.MethodDescriptor.<example.Request, example.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "serverStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Response.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("serverStream"))
              .build();
        }
      }
    }
    return getServerStreamMethod;
  }

  private static volatile io.grpc.MethodDescriptor<example.Request,
      example.Response> getBidiStreamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bidiStream",
      requestType = example.Request.class,
      responseType = example.Response.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<example.Request,
      example.Response> getBidiStreamMethod() {
    io.grpc.MethodDescriptor<example.Request, example.Response> getBidiStreamMethod;
    if ((getBidiStreamMethod = StreamServiceGrpc.getBidiStreamMethod) == null) {
      synchronized (StreamServiceGrpc.class) {
        if ((getBidiStreamMethod = StreamServiceGrpc.getBidiStreamMethod) == null) {
          StreamServiceGrpc.getBidiStreamMethod = getBidiStreamMethod =
              io.grpc.MethodDescriptor.<example.Request, example.Response>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "bidiStream"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  example.Response.getDefaultInstance()))
              .setSchemaDescriptor(new StreamServiceMethodDescriptorSupplier("bidiStream"))
              .build();
        }
      }
    }
    return getBidiStreamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static StreamServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceStub>() {
        @java.lang.Override
        public StreamServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceStub(channel, callOptions);
        }
      };
    return StreamServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static StreamServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceBlockingStub>() {
        @java.lang.Override
        public StreamServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceBlockingStub(channel, callOptions);
        }
      };
    return StreamServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static StreamServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<StreamServiceFutureStub>() {
        @java.lang.Override
        public StreamServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new StreamServiceFutureStub(channel, callOptions);
        }
      };
    return StreamServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class StreamServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void reply(example.Request request,
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getReplyMethod(), responseObserver);
    }

    /**
     */
    public void serverStream(example.Request request,
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getServerStreamMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<example.Request> bidiStream(
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getBidiStreamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getReplyMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                example.Request,
                example.Response>(
                  this, METHODID_REPLY)))
          .addMethod(
            getServerStreamMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                example.Request,
                example.Response>(
                  this, METHODID_SERVER_STREAM)))
          .addMethod(
            getBidiStreamMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                example.Request,
                example.Response>(
                  this, METHODID_BIDI_STREAM)))
          .build();
    }
  }

  /**
   */
  public static final class StreamServiceStub extends io.grpc.stub.AbstractAsyncStub<StreamServiceStub> {
    private StreamServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceStub(channel, callOptions);
    }

    /**
     */
    public void reply(example.Request request,
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getReplyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void serverStream(example.Request request,
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getServerStreamMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<example.Request> bidiStream(
        io.grpc.stub.StreamObserver<example.Response> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getBidiStreamMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class StreamServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<StreamServiceBlockingStub> {
    private StreamServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public example.Response reply(example.Request request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getReplyMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<example.Response> serverStream(
        example.Request request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getServerStreamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class StreamServiceFutureStub extends io.grpc.stub.AbstractFutureStub<StreamServiceFutureStub> {
    private StreamServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected StreamServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new StreamServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<example.Response> reply(
        example.Request request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getReplyMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REPLY = 0;
  private static final int METHODID_SERVER_STREAM = 1;
  private static final int METHODID_BIDI_STREAM = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final StreamServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(StreamServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REPLY:
          serviceImpl.reply((example.Request) request,
              (io.grpc.stub.StreamObserver<example.Response>) responseObserver);
          break;
        case METHODID_SERVER_STREAM:
          serviceImpl.serverStream((example.Request) request,
              (io.grpc.stub.StreamObserver<example.Response>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BIDI_STREAM:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bidiStream(
              (io.grpc.stub.StreamObserver<example.Response>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    StreamServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return example.StreamServiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("StreamService");
    }
  }

  private static final class StreamServiceFileDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier {
    StreamServiceFileDescriptorSupplier() {}
  }

  private static final class StreamServiceMethodDescriptorSupplier
      extends StreamServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    StreamServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (StreamServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new StreamServiceFileDescriptorSupplier())
              .addMethod(getReplyMethod())
              .addMethod(getServerStreamMethod())
              .addMethod(getBidiStreamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
