package com.jauntsdn.messagestreams.grpc;

import com.jauntsdn.rsocket.Disposable;
import com.jauntsdn.rsocket.MessageStreams;
import com.jauntsdn.rsocket.ServerStreamsAcceptor;
import java.util.concurrent.CompletionStage;

public interface MessageStreamsFactory {

  Server server(String name, String address);

  CompletionStage<MessageStreams> client(String name, String address);

  interface Server {

    CompletionStage<Disposable> start(ServerStreamsAcceptor serverAcceptor);
  }
}
