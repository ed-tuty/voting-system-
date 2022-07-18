package com.sicredi.test.kafka.producer;

import com.google.gson.Gson;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultProducer {

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private static final String TOPIC = "RESULT_TOPIC";

  @Autowired
  private KafkaTemplate<String, CloudEvent> kafkaTemplate;

  public ListenableFuture<SendResult<String, CloudEvent>> sendMessage(Object message) {

    final CloudEvent event = CloudEventBuilder.v1()
        .withId("000")
        .withType(message.getClass().toString())
        .withData(toBytes(message))
        .withSource(URI.create("localhost:8080"))
        .build();

    return this.kafkaTemplate.send(TOPIC, event);
  }

  private byte[] toBytes(Object object) {
    Gson mapper = new Gson();
    return mapper.toJson(object).getBytes();
  }

}
