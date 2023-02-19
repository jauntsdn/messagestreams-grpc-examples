![Message-Streams](readme/mstreams.png)

### Message-Streams with GRPC-stub API

Demo for [Message streams](https://github.com/jauntsdn/rsocket-jvm) - very fast GRPC-like & GRPC-compatible services 
on JVM with rich streaming models - using familiar, widespread streaming API - grpc-java-stub `StreamObserver`.

It complements [1 million streams](https://jauntsdn.com/post/rsocket-million-streams-2/) stress test report and 
[message-streams interop](https://github.com/jauntsdn/rsocket-jvm-interop-examples) example with  
common request-reply, request-stream models.

Example showcases how to access `StreamObserver` based services over TCP, WebSockets or consume with GRPC (http2) clients.

Also It outlines some properties of Message-Streams based applications:

* (Services, APIs) / (networking, runtime) decoupling
* Tiny service jar & app binary distribution size
* Fast startup / first request time
* Instant startup / tiny memory footprint with graalvm native image