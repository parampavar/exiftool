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

package com.thebuzzmedia.exiftool.commons.lang;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class ToStringBuilderTest {

	@Test
	void it_should_serialize_to_string() {
		String value = ToStringBuilder.create(getClass())
				.append("str", "given str")
				.append("nb", 10L)
				.append("time", TimeUnit.MILLISECONDS)
				.build();

		assertThat(value).isEqualTo(
				"ToStringBuilderTest{" +
					"str: \"given str\", " +
					"nb: 10, " +
					"time: MILLISECONDS" +
				"}"
		);
	}
}
