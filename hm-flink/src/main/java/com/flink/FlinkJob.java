package com.flink;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flink.config.FlinkConfig;

public class FlinkJob {
	
	private static final Logger LOG = LoggerFactory.getLogger(FlinkJob.class);

	public static void main(String[] args) throws Exception {
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		
		FlinkConfig.envConfig(env);
		
		DataStream<String> eventStream = env.fromSource(KafkaConector.create(), WatermarkStrategy.noWatermarks(),
				"Kafka Source");

		eventStream.map(new MapFunction<String, String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String map(String value) throws Exception {
				LOG.info(value);
				return value;
			}
			
		});

		env.execute("Kafka Reader");
	}

}
