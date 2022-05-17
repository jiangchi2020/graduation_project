package com.scarike.gp.web.common.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.4.0)",
    comments = "Source: nlp.proto")
public final class RpcNlpServiceGrpc {

  private RpcNlpServiceGrpc() {}

  public static final String SERVICE_NAME = "com.scarike.gp.web.common.grpc.RpcNlpService";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<RpcNlpRequest,
      RpcNlpResponse> METHOD_ANALYSIS =
      io.grpc.MethodDescriptor.<RpcNlpRequest, RpcNlpResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.scarike.gp.web.common.grpc.RpcNlpService", "analysis"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              RpcNlpRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              RpcNlpResponse.getDefaultInstance()))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RpcNlpServiceStub newStub(io.grpc.Channel channel) {
    return new RpcNlpServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RpcNlpServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new RpcNlpServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RpcNlpServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new RpcNlpServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class RpcNlpServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void analysis(RpcNlpRequest request,
                         io.grpc.stub.StreamObserver<RpcNlpResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ANALYSIS, responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ANALYSIS,
            asyncUnaryCall(
              new MethodHandlers<
                RpcNlpRequest,
                RpcNlpResponse>(
                  this, METHODID_ANALYSIS)))
          .build();
    }
  }

  /**
   */
  public static final class RpcNlpServiceStub extends io.grpc.stub.AbstractStub<RpcNlpServiceStub> {
    private RpcNlpServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcNlpServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcNlpServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcNlpServiceStub(channel, callOptions);
    }

    /**
     */
    public void analysis(RpcNlpRequest request,
                         io.grpc.stub.StreamObserver<RpcNlpResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ANALYSIS, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class RpcNlpServiceBlockingStub extends io.grpc.stub.AbstractStub<RpcNlpServiceBlockingStub> {
    private RpcNlpServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcNlpServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcNlpServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcNlpServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public RpcNlpResponse analysis(RpcNlpRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ANALYSIS, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class RpcNlpServiceFutureStub extends io.grpc.stub.AbstractStub<RpcNlpServiceFutureStub> {
    private RpcNlpServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private RpcNlpServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected RpcNlpServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new RpcNlpServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<RpcNlpResponse> analysis(
        RpcNlpRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ANALYSIS, getCallOptions()), request);
    }
  }

  private static final int METHODID_ANALYSIS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final RpcNlpServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(RpcNlpServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ANALYSIS:
          serviceImpl.analysis((RpcNlpRequest) request,
              (io.grpc.stub.StreamObserver<RpcNlpResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static final class RpcNlpServiceDescriptorSupplier implements io.grpc.protobuf.ProtoFileDescriptorSupplier {
    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return RpcNlpServiceApi.getDescriptor();
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RpcNlpServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RpcNlpServiceDescriptorSupplier())
              .addMethod(METHOD_ANALYSIS)
              .build();
        }
      }
    }
    return result;
  }
}
