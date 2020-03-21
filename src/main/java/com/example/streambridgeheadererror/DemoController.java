package com.example.streambridgeheadererror;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.ResponseEntity.accepted;

@Controller
class DemoController {

    static final String CUSTOM_HEADER_KEY = "custom-header";
    static final String CUSTOM_HEADER_VALUE = "val";

    private final StreamBridge streamBridge;

    public DemoController(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @PostMapping
    ResponseEntity<Void> sendMessage(@RequestBody String payload) {
        Message<String> message = MessageBuilder.withPayload(payload)
                .setHeader(CUSTOM_HEADER_KEY, CUSTOM_HEADER_VALUE)
                .build();
        streamBridge.send("channel-out-0", message);
        return accepted().build();
    }
}