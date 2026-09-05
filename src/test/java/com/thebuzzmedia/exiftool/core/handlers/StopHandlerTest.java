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

package com.thebuzzmedia.exiftool.core.handlers;

import org.junit.jupiter.api.Test;

import static com.thebuzzmedia.exiftool.core.handlers.StopHandler.stopHandler;
import static org.assertj.core.api.Assertions.assertThat;

class StopHandlerTest {

	@Test
	void it_should_detect_end_of_stream() {
		assertThat(stopHandler().readLine(null)).isFalse();
		assertThat(stopHandler().readLine("{ready}")).isFalse();
		assertThat(stopHandler().readLine("foo")).isTrue();
		assertThat(stopHandler().readLine("")).isTrue();
	}
}
