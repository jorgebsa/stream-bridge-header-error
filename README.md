Stream Bridge Header Error
============================

https://github.com/spring-cloud/spring-cloud-stream/issues/1931

This project contains a sample controller that sends a message with a custom header with StreamBridge, as recommended in the [official documentation](https://cloud.spring.io/spring-cloud-static/spring-cloud-stream/3.0.3.RELEASE/reference/html/spring-cloud-stream.html#_using_streambridge).

> Also, note that streamBridge.send(..) method takes an Object for data. This means you can send POJO or Message to it and it will go through the same routine when sending output as if it was from any Function or Supplier providing the same level of consistency as with functions. This means the output type conversion, partitioning etc are honored as if it was from the output produced by functions.

The quote from the documentation leads a developer to believe that by building his own `Message<?>` and sending it through `StreamBridge.send(..)` that all the messages configuration, such as content and headers, will be preserved.
However, the included test case fails because it verifies if the message that was received through the test binder had the custom header set, which turns out to be false. This is due to the fact that
the send method implementation ignores that the data object is of type `Message<?>`, and applies a function to transform the data into a new `GenericMessage<?>` instance, therefore all of the existing headers are not forwarded with the content

### How to run the test

Simply execute:

```bash
./gradlew test
```
