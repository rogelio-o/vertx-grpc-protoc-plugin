package io.vertx.grpc.examples.server;

import io.vertx.core.Context;
import io.vertx.core.Handler;
import io.vertx.core.streams.ReadStream;
import io.vertx.core.streams.impl.InboundBuffer;

import java.util.List;

/**
 * @author Rogelio Orts
 */
public class IterableReadStream<T> implements ReadStream<T> {

  private final InboundBuffer<T> queue;

  private final List<T> data;

  private Handler<Throwable> exceptionHandler;

  private Handler<Void> endHandler;

  public IterableReadStream(Context context, List<T> data) {
    this.queue = new InboundBuffer<>(context);
    this.data = data;
  }

  @Override
  public ReadStream<T> exceptionHandler(Handler<Throwable> handler) {
    this.exceptionHandler = handler;
    queue.exceptionHandler(handler);

    return this;
  }

  private void handleException(Throwable cause) {
    if (exceptionHandler != null) {
      exceptionHandler.handle(cause);
    }
  }

  @Override
  public ReadStream<T> handler(Handler<T> handler) {
    queue.handler(handler);
    doRead();

    return this;
  }

  private void doRead() {
    if(queue.write(data)) {
      handleEnd();
    }
  }

  @Override
  public ReadStream<T> pause() {
    queue.pause();

    return this;
  }

  @Override
  public ReadStream<T> resume() {
    queue.resume();

    return this;
  }

  @Override
  public ReadStream<T> fetch(long l) {
    queue.fetch(l);

    return this;
  }

  @Override
  public ReadStream<T> endHandler(Handler<Void> handler) {
    this.endHandler = handler;

    return this;
  }

  private void handleEnd() {
    if (endHandler != null) {
      endHandler.handle(null);
    }
  }

}
