package com.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;

public class KafkaConector {

	public static KafkaSource<String> create() {
		return KafkaSource.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setTopics("test_topic")
                .setGroupId("flink-group")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();
	}
}
