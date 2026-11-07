package com.flink.config;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FlinkConfig {

	@SuppressWarnings("deprecation")
	public static void envConfig(StreamExecutionEnvironment env) {
		// start a checkpoint every 5 min
		env.enableCheckpointing(300000);

		// advanced options:

		// set mode to exactly-once (this is the default)
		env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);

		// make sure 500 ms of progress happen between checkpoints
		env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);

		// checkpoints have to complete within one minute, or are discarded
		env.getCheckpointConfig().setCheckpointTimeout(10 * 60 * 1000);

		// only two consecutive checkpoint failures are tolerated
		env.getCheckpointConfig().setTolerableCheckpointFailureNumber(3);

		// allow only one checkpoint to be in progress at the same time
		env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);

		// enables the unaligned checkpoints
		env.getCheckpointConfig().enableUnalignedCheckpoints();
		env.getCheckpointConfig().setCheckpointStorage("file:///data/flink/checkpoints");

		// 7️⃣ Enable externalized checkpoints
		env.getCheckpointConfig().enableExternalizedCheckpoints(
				org.apache.flink.streaming.api.environment.CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

		env.setRestartStrategy(RestartStrategies.failureRateRestart(3, // max failures
				Time.minutes(10), // failure interval
				Time.seconds(30) // delay between restarts
		));

	}

}
