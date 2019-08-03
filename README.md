# Vert.x gRPC - Protoc Plugin

This plugin is a set of gRPC bindings for reactive programming with [Vert.x](https://github.com/eclipse-vertx/vert.x).

## Installation

To use Reactor-gRPC with the protobuf-maven-plugin, add a [custom protoc plugin configuration section](https://www.xolstice.org/protobuf-maven-plugin/examples/protoc-plugin.html).

```
<protocPlugins>
    <protocPlugin>
        <id>vertx-grpc-protoc-plugin</id>
        <groupId>io.vertx</groupId>
        <artifactId>vertx-grpc-protoc-plugin</artifactId>
        <version>[VERSION]</version>
        <mainClass>io.vertx.grpc.protoc.plugin.VertxGrpcGenerator</mainClass>
    </protocPlugin>
</protocPlugins>
``` 

And add the [vertx-grpc](https://github.com/vert-x3/vertx-grpc) dependency:

```
<dependency>
  <groupId>io.vertx</groupId>
  <artifactId>vertx-grpc</artifactId>
  <version>[VERSION]</version>
</dependency>
```

## Usage

More info about how to use the generated service stubs can be found in the documentation: http://vertx.io/docs/vertx-grpc/java/
