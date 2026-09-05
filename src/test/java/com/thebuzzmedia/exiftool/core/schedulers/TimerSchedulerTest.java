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

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import java.util.Timer;
import java.util.TimerTask;

import static com.thebuzzmedia.exiftool.tests.ReflectionTestUtils.writePrivateField;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class TimerSchedulerTest {

	@Test
	void it_should_not_create_scheduler_for_invalid_delay() {
		assertThatThrownBy(() -> new TimerScheduler("foo", -1))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Delay must be strictly positive");
	}

	@Test
	void it_should_create_scheduler() {
		String name = "foo";
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(name, delay);

		assertThat(scheduler).extracting("name").isEqualTo(name);
		assertThat(scheduler).extracting("delay").isEqualTo(delay);
		assertThat(scheduler).extracting("timer").isNotNull();
	}

	@Test
	void it_should_with_default_name() {
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(null, delay);

		assertThat(scheduler).extracting("name").isEqualTo("ExifTool Cleanup Timer");
		assertThat(scheduler).extracting("delay").isEqualTo(delay);
		assertThat(scheduler).extracting("timer").isNotNull();
	}

	@Test
	void it_should_start_scheduler() {
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(null, delay);

		Runnable runnable = mock(Runnable.class);
		Timer timer = mock(Timer.class);
		writePrivateField(scheduler, "timer", timer);

		scheduler.start(runnable);

		ArgumentCaptor<TimerTask> taskCaptor = ArgumentCaptor.forClass(TimerTask.class);
		verify(timer).schedule(taskCaptor.capture(), eq(delay));
		verify(runnable, never()).run();

		TimerTask task = taskCaptor.getValue();
		assertThat(task).isNotNull();

		assertThat(scheduler).extracting("pendingTask").isSameAs(task);

		task.run();
		verify(runnable).run();
	}

	@Test
	void it_should_stop_scheduler() {
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(null, delay);

		Timer timer = mock(Timer.class);
		writePrivateField(scheduler, "timer", timer);

		scheduler.stop();

		verify(timer, never()).purge();

		TimerTask timerTask = mock(TimerTask.class);
		writePrivateField(scheduler, "pendingTask", timerTask);

		scheduler.stop();

		InOrder inOrder = inOrder(timer, timerTask);
		inOrder.verify(timerTask).cancel();
		inOrder.verify(timer).purge();

		assertThat(scheduler).extracting("pendingTask").isNull();
	}

	@Test
	void it_should_shutdown_scheduler_without_pending_task() {
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(null, delay);

		Timer timer = mock(Timer.class);
		writePrivateField(scheduler, "timer", timer);

		scheduler.shutdown();

		verify(timer, never()).purge();
		verify(timer).cancel();
		assertThat(scheduler).extracting("pendingTask").isNull();
	}

	@Test
	void it_should_shutdown_scheduler_with_pending_task() {
		long delay = 10000;
		TimerScheduler scheduler = new TimerScheduler(null, delay);

		Timer timer = mock(Timer.class);
		TimerTask timerTask = mock(TimerTask.class);
		writePrivateField(scheduler, "timer", timer);
		writePrivateField(scheduler, "pendingTask", timerTask);

		scheduler.shutdown();

		verify(timer).purge();
		verify(timer).cancel();
		assertThat(scheduler).extracting("pendingTask").isNull();
	}
}
