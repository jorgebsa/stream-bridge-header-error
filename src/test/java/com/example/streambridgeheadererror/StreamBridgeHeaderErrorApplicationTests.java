package com.example.streambridgeheadererror;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import static com.example.streambridgeheadererror.DemoController.CUSTOM_HEADER_KEY;
import static com.example.streambridgeheadererror.DemoController.CUSTOM_HEADER_VALUE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StreamBridgeHeaderErrorApplicationTests {

    @Autowired
    private OutputDestination outputDestination;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void checkIfCustomHeaderIsPresent() {
        String body = "test string";
        ResponseEntity<Void> responseEntity = testRestTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>(body), Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Message<byte[]> message = outputDestination.receive(1000, "channel-out-0");
        String payload = new String(message.getPayload());
        assertThat(payload).isEqualTo(body);

        MessageHeaders headers = message.getHeaders();

        assertThat(headers.containsKey(CUSTOM_HEADER_KEY))
                .as("The custom header should have been included in the message sent by StreamBridge")
                .isTrue();

        assertThat(headers.get(CUSTOM_HEADER_KEY))
                .as("The custom header value should match the hard coded value")
                .isEqualTo(CUSTOM_HEADER_VALUE);
    }

}
