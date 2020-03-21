package com.example.streambridgeheadererror;

import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TestChannelBinderConfiguration.class)
class TestConfig {

}