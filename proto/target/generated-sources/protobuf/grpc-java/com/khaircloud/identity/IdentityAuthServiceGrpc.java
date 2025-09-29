package com.khaircloud.identity;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.54.0)",
    comments = "Source: identity.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class IdentityAuthServiceGrpc {

  private IdentityAuthServiceGrpc() {}

  public static final String SERVICE_NAME = "com.khaircloud.identity.IdentityAuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.khaircloud.identity.IntrospectRequest,
      com.khaircloud.identity.IntrospectResponse> getIntrospectMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "introspect",
      requestType = com.khaircloud.identity.IntrospectRequest.class,
      responseType = com.khaircloud.identity.IntrospectResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.khaircloud.identity.IntrospectRequest,
      com.khaircloud.identity.IntrospectResponse> getIntrospectMethod() {
    io.grpc.MethodDescriptor<com.khaircloud.identity.IntrospectRequest, com.khaircloud.identity.IntrospectResponse> getIntrospectMethod;
    if ((getIntrospectMethod = IdentityAuthServiceGrpc.getIntrospectMethod) == null) {
      synchronized (IdentityAuthServiceGrpc.class) {
        if ((getIntrospectMethod = IdentityAuthServiceGrpc.getIntrospectMethod) == null) {
          IdentityAuthServiceGrpc.getIntrospectMethod = getIntrospectMethod =
              io.grpc.MethodDescriptor.<com.khaircloud.identity.IntrospectRequest, com.khaircloud.identity.IntrospectResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "introspect"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.khaircloud.identity.IntrospectRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.khaircloud.identity.IntrospectResponse.getDefaultInstance()))
              .setSchemaDescriptor(new IdentityAuthServiceMethodDescriptorSupplier("introspect"))
              .build();
        }
      }
    }
    return getIntrospectMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static IdentityAuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceStub>() {
        @java.lang.Override
        public IdentityAuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IdentityAuthServiceStub(channel, callOptions);
        }
      };
    return IdentityAuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static IdentityAuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceBlockingStub>() {
        @java.lang.Override
        public IdentityAuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IdentityAuthServiceBlockingStub(channel, callOptions);
        }
      };
    return IdentityAuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static IdentityAuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<IdentityAuthServiceFutureStub>() {
        @java.lang.Override
        public IdentityAuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new IdentityAuthServiceFutureStub(channel, callOptions);
        }
      };
    return IdentityAuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void introspect(com.khaircloud.identity.IntrospectRequest request,
        io.grpc.stub.StreamObserver<com.khaircloud.identity.IntrospectResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getIntrospectMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service IdentityAuthService.
   */
  public static abstract class IdentityAuthServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return IdentityAuthServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service IdentityAuthService.
   */
  public static final class IdentityAuthServiceStub
      extends io.grpc.stub.AbstractAsyncStub<IdentityAuthServiceStub> {
    private IdentityAuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdentityAuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IdentityAuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void introspect(com.khaircloud.identity.IntrospectRequest request,
        io.grpc.stub.StreamObserver<com.khaircloud.identity.IntrospectResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getIntrospectMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service IdentityAuthService.
   */
  public static final class IdentityAuthServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<IdentityAuthServiceBlockingStub> {
    private IdentityAuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdentityAuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IdentityAuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.khaircloud.identity.IntrospectResponse introspect(com.khaircloud.identity.IntrospectRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getIntrospectMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service IdentityAuthService.
   */
  public static final class IdentityAuthServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<IdentityAuthServiceFutureStub> {
    private IdentityAuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected IdentityAuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new IdentityAuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.khaircloud.identity.IntrospectResponse> introspect(
        com.khaircloud.identity.IntrospectRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getIntrospectMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_INTROSPECT = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_INTROSPECT:
          serviceImpl.introspect((com.khaircloud.identity.IntrospectRequest) request,
              (io.grpc.stub.StreamObserver<com.khaircloud.identity.IntrospectResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getIntrospectMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.khaircloud.identity.IntrospectRequest,
              com.khaircloud.identity.IntrospectResponse>(
                service, METHODID_INTROSPECT)))
        .build();
  }

  private static abstract class IdentityAuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    IdentityAuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.khaircloud.identity.Identity.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("IdentityAuthService");
    }
  }

  private static final class IdentityAuthServiceFileDescriptorSupplier
      extends IdentityAuthServiceBaseDescriptorSupplier {
    IdentityAuthServiceFileDescriptorSupplier() {}
  }

  private static final class IdentityAuthServiceMethodDescriptorSupplier
      extends IdentityAuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    IdentityAuthServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (IdentityAuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new IdentityAuthServiceFileDescriptorSupplier())
              .addMethod(getIntrospectMethod())
              .build();
        }
      }
    }
    return result;
  }
}
