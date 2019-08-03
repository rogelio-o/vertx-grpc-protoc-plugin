package io.vertx.grpc.examples.server;

import io.vertx.core.*;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;
import io.vertx.grpc.examples.VertxChatGrpc;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startFuture) throws Exception {
        VertxChatGrpc.ChatImplBase service = new ChatImpl(vertx);
        VertxServer rpcServer = VertxServerBuilder
                .forAddress(vertx, "localhost", 8080)
                .addService(service)
                .build();

        // Start is asynchronous
        rpcServer.start();

        startFuture.complete();
    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new MainVerticle());
    }

}
