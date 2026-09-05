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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.thebuzzmedia.exiftool.core.schedulers.SchedulerDuration.duration;
import static com.thebuzzmedia.exiftool.core.schedulers.SchedulerDuration.millis;
import static com.thebuzzmedia.exiftool.tests.ReflectionTestUtils.writePrivateField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultSchedulerTest {

	private ScheduledThreadPoolExecutor executor;

	@BeforeEach
	void setUp() {
		executor = mock(ScheduledThreadPoolExecutor.class);
	}

	@Test
	void it_should_not_create_default_scheduler_with_negative_delay() {
		assertThatThrownBy(() -> new DefaultScheduler(millis(-1)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Delay should be a strictly positive value");
	}

	@Test
	void it_should_not_create_default_scheduler_with_zero_delay() {
		assertThatThrownBy(() -> new DefaultScheduler(millis(0)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Delay should be a strictly positive value");
	}

	@Test
	void it_should_not_create_default_scheduler_with_null_time_unit() {
		assertThatThrownBy(() -> new DefaultScheduler(duration(1, null)))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Time Unit should not be null");
	}

	@Test
	void it_should_create_default_scheduler() {
		long delay = 10000;
		DefaultScheduler scheduler = new DefaultScheduler(millis(delay));

		SchedulerDuration expectedDelay = duration(delay, TimeUnit.MILLISECONDS);
		assertThat(scheduler).extracting("executionDelay").isEqualTo(expectedDelay);
		assertThat(scheduler).extracting("executor").isNotNull();
	}

	@Test
	void it_should_create_default_scheduler_with_specific_time_unit() {
		long delay = 1;
		TimeUnit timeUnit = TimeUnit.HOURS;

		SchedulerDuration executionDelay = duration(delay, timeUnit);
		DefaultScheduler scheduler = new DefaultScheduler(executionDelay);

		SchedulerDuration expectedDelay = duration(delay, timeUnit);
		assertThat(scheduler).extracting("executionDelay").isEqualTo(expectedDelay);
		assertThat(scheduler).extracting("executor").isNotNull();
	}

	@Test
	void it_should_start_scheduler() {
		int delay = 10000;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		SchedulerDuration executionDelay = duration(delay, timeUnit);
		DefaultScheduler scheduler = new DefaultScheduler(executionDelay);
		writePrivateField(scheduler, "executor", executor);

		RunnableFuture<?> runnable = mock(RunnableFuture.class);
		scheduler.start(runnable);

		verify(executor).schedule(runnable, delay, timeUnit);
	}

	@Test
	void it_should_stop_scheduler() {
		int delay = 10000;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		SchedulerDuration executionDelay = duration(delay, timeUnit);
		DefaultScheduler scheduler = new DefaultScheduler(executionDelay);
		writePrivateField(scheduler, "executor", executor);

		RunnableFuture<?> r1 = mock(RunnableFuture.class);
		RunnableFuture<?> r2 = mock(RunnableFuture.class);
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
		queue.add(r1);
		queue.add(r2);

		when(executor.getQueue()).thenReturn(queue);

		scheduler.stop();

		verify(r1).cancel(false);
		verify(r2).cancel(false);
		verify(executor).purge();
	}

	@Test
	void it_should_shutdown_scheduler() {
		int delay = 10000;
		TimeUnit timeUnit = TimeUnit.MILLISECONDS;
		SchedulerDuration executionDelay = duration(delay, timeUnit);
		DefaultScheduler scheduler = new DefaultScheduler(executionDelay);
		writePrivateField(scheduler, "executor", executor);

		RunnableFuture<?> r1 = mock(RunnableFuture.class);
		RunnableFuture<?> r2 = mock(RunnableFuture.class);
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
		queue.add(r1);
		queue.add(r2);

		when(executor.getQueue()).thenReturn(queue);

		scheduler.shutdown();

		verify(r1).cancel(false);
		verify(r2).cancel(false);
		verify(executor).purge();
		verify(executor).shutdownNow();
	}
}
