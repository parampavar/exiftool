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

package com.thebuzzmedia.exiftool.core;

import com.thebuzzmedia.exiftool.Tag;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static com.thebuzzmedia.exiftool.Constants.SEPARATOR;
import static org.assertj.core.api.Assertions.assertThat;

class UnspecifiedTagTest {

	@Test
	void it_should_pass_through_single_value_on_parse() {
		String value = "foo";
		Tag tag = new UnspecifiedTag("CustomTag");
		String[] result = tag.parse(value);
		assertThat(result).hasSize(1);
		assertThat(result[0]).isEqualTo(value);
	}

	@Test
	void it_should_pass_through_multiple_value_on_parse() {
		String value = "foo" + SEPARATOR + "bar";
		Tag tag = new UnspecifiedTag("CustomTag");
		String[] result = tag.parse(value);
		assertThat(result).hasSize(2).containsExactly("foo", "bar");
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(UnspecifiedTag.class).verify();
	}

	@Test
	void it_should_implement_to_string() {
		UnspecifiedTag tag = new UnspecifiedTag("a name");
		assertThat(tag).hasToString(
				"UnspecifiedTag{" +
					"name: \"a name\"" +
				"}"
		);
	}
}
