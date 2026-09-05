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

package com.thebuzzmedia.exiftool.logs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LogUtilsTest {

	@Test
	void it_should_translate_slf4j_string_to_string_format() {
		assertThat(LogUtils.fromSlf4jStyle("")).isEqualTo("");
		assertThat(LogUtils.fromSlf4jStyle("Test")).isEqualTo("Test");
		assertThat(LogUtils.fromSlf4jStyle("Test {}")).isEqualTo("Test %s");
		assertThat(LogUtils.fromSlf4jStyle("Test {} {}")).isEqualTo("Test %s %s");
	}

	@Test
	void it_should_serialize_stack_trace_to_string() {
		RuntimeException ex = new RuntimeException("message");
		String stackTrace = LogUtils.getStackTrace(ex);
		assertThat(stackTrace).isNotEmpty().startsWith("java.lang.RuntimeException: message");
	}
}
