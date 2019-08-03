package io.vertx.grpc.examples.server;

import com.google.protobuf.Empty;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.streams.ReadStream;
import io.vertx.grpc.examples.ChatProto;
import io.vertx.grpc.examples.VertxChatGrpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatImpl extends VertxChatGrpc.ChatImplBase {

  private final List<ChatProto.ChatMessage> db = Collections.synchronizedList(new ArrayList<>());

  private final Vertx vertx;

  public ChatImpl(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public Future<Empty> postMessage(Future<ChatProto.ChatMessage> request) {
    return request.compose(chatMessage -> {
      db.add(chatMessage);

      return Future.succeededFuture();
    });
  }

  @Override
  public ReadStream<ChatProto.ChatMessage> getMessages(Future<Empty> request) {
    return new IterableReadStream<>(vertx.getOrCreateContext(), db);
  }

}
