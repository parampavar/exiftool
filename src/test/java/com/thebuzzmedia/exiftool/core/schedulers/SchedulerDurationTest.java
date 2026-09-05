/*
 * Copyright 2011 The Buzz Media, LLC
 * Copyright 2015-2026 Mickael Jeanroy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thebuzzmedia.exiftool.core.schedulers;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static com.thebuzzmedia.exiftool.core.schedulers.SchedulerDuration.duration;
import static com.thebuzzmedia.exiftool.core.schedulers.SchedulerDuration.millis;
import static com.thebuzzmedia.exiftool.core.schedulers.SchedulerDuration.seconds;
import static org.assertj.core.api.Assertions.assertThat;

class SchedulerDurationTest {

	@Test
	void it_should_create_duration() {
		long delay = 1;
		TimeUnit timeUnit = TimeUnit.HOURS;
		SchedulerDuration duration = duration(delay, timeUnit);
		assertThat(duration.getDelay()).isEqualTo(delay);
		assertThat(duration.getTimeUnit()).isEqualTo(timeUnit);
	}

	@Test
	void it_should_create_seconds_duration() {
		long delay = 1;
		SchedulerDuration duration = seconds(delay);
		assertThat(duration.getDelay()).isEqualTo(delay);
		assertThat(duration.getTimeUnit()).isEqualTo(TimeUnit.SECONDS);
	}

	@Test
	void it_should_create_millis_duration() {
		long delay = 1;
		SchedulerDuration duration = millis(delay);
		assertThat(duration.getDelay()).isEqualTo(delay);
		assertThat(duration.getTimeUnit()).isEqualTo(TimeUnit.MILLISECONDS);
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(SchedulerDuration.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		SchedulerDuration duration = millis(1);
		assertThat(duration).hasToString(
				"SchedulerDuration{" +
					"delay: 1, " +
					"timeUnit: MILLISECONDS" +
				"}"
		);
	}
}
