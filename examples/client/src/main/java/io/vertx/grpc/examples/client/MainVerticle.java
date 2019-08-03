package io.vertx.grpc.examples.client;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.grpc.VertxChannelBuilder;
import io.vertx.grpc.examples.ChatProto;
import io.vertx.grpc.examples.VertxChatGrpc;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Rogelio Orts
 */
public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startFuture) throws Exception {
    ManagedChannel channel = VertxChannelBuilder
      .forAddress(vertx, "localhost", 8080)
      .usePlaintext(true)
      .build();

    VertxChatGrpc.VertxChatStub stub = VertxChatGrpc.newVertxStub(channel);

    ChatProto.ChatMessage chatMessage = ChatProto.ChatMessage.newBuilder()
      .setMessage("Test " + UUID.randomUUID())
      .setAuthor("tester")
      .setWhen(Timestamp.newBuilder().setSeconds(Instant.now().getEpochSecond()))
      .build();
    stub.postMessage(chatMessage).setHandler(res -> {
      if (res.succeeded()) {
        stub.getMessages(Empty.newBuilder().build())
          .endHandler(v -> {
            System.out.println("CHAT MESSAGES PROCESSED, BYE.");
            vertx.close();
          })
          .handler(chatMessage1 -> {
            System.out.println("CHAT MESSAGE: " + chatMessage1.getMessage() + " (" + chatMessage.getAuthor() + ")");
          });
      } else {
        System.out.println("FAILED: " + res.cause());
        vertx.close();
      }
    });

    startFuture.complete();
  }

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(new MainVerticle());
  }

}
