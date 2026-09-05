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

import com.thebuzzmedia.exiftool.exceptions.UnreadableFileException;
import com.thebuzzmedia.exiftool.exceptions.UnwritableFileException;
import com.thebuzzmedia.exiftool.tests.builders.FileBuilder;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PreConditionsTest {

	@Test
	void it_should_fail_with_null_string() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notBlank(null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_empty_string() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notBlank("", message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_blank_string() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notBlank("  ", message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_valid_string() {
		String message = "should not be empty";
		String val = "foo";

		String result = PreConditions.notBlank(val, message);

		assertThat(result).isEqualTo(val);
	}

	@Test
	void it_should_fail_with_null_array() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notBlank("  ", message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_empty_array() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notEmpty(new String[]{}, message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_valid_array() {
		String message = "should not be empty";
		String[] val = new String[]{"foo"};

		String[] results = PreConditions.notEmpty(val, message);

		assertThat(results).isSameAs(val);
	}

	@Test
	void it_should_fail_with_null_map() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notEmpty((Map<Object, Object>) null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_empty_map() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notEmpty(emptyMap(), message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_valid_map() {
		String message = "should not be empty";
		Map<String, String> val = new HashMap<>();
		val.put("foo", "bar");

		Map<String, String> results = PreConditions.notEmpty(val, message);

		assertThat(results).isSameAs(val);
	}

	@Test
	void it_should_fail_with_null_iterable() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notEmpty((Iterable<Object>) null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_empty_iterable() {
		String message = "should not be empty";
		assertThatThrownBy(() -> PreConditions.notEmpty(emptyList(), message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_valid_iterable() {
		String message = "should not be empty";
		Iterable<String> val = asList("foo", "bar");

		Iterable<String> results = PreConditions.notEmpty(val, message);

		assertThat(results)
				.isNotNull()
				.isNotEmpty()
				.isSameAs(val);
	}

	@Test
	void it_should_fail_with_null_number() {
		String message = "should be readable";
		assertThatThrownBy(() -> PreConditions.isPositive(null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_negative_number() {
		String message = "should be readable";
		assertThatThrownBy(() -> PreConditions.isPositive(-1, message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_zero() {
		String message = "should be readable";
		assertThatThrownBy(() -> PreConditions.isPositive(0, message))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_positive_number() {
		String message = "should be readable";

		int nb = PreConditions.isPositive(1, message);

		assertThat(nb).isEqualTo(1);
	}

	@Test
	void it_should_not_be_readable_with_null_file() {
		String message = "should be readable";
		assertThatThrownBy(() -> PreConditions.isReadable(null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_be_readable_with_file_that_does_not_exist() {
		String message = "should be readable";
		File file = new FileBuilder("foo.png").exists(false).build();
		assertThatThrownBy(() -> PreConditions.isReadable(file, message))
				.isInstanceOf(UnreadableFileException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_file_that_is_not_readable() {
		String message = "should be readable";
		File file = new FileBuilder("foo.png").canRead(false).build();
		assertThatThrownBy(() -> PreConditions.isReadable(file, message))
				.isInstanceOf(UnreadableFileException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_readable_file() {
		String message = "should be readable";
		File file = new FileBuilder("foo.png").build();

		File result = PreConditions.isReadable(file, message);

		assertThat(result).isSameAs(file);
	}

	@Test
	void it_should_not_be_writable_with_null_file() {
		String message = "should be writable";
		assertThatThrownBy(() -> PreConditions.isWritable(null, message))
				.isInstanceOf(NullPointerException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_be_writable_with_file_that_does_not_exist() {
		String message = "should be writable";
		File file = new FileBuilder("foo.png").exists(false).build();
		assertThatThrownBy(() -> PreConditions.isWritable(file, message))
				.isInstanceOf(UnwritableFileException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_fail_with_file_that_is_not_writable() {
		String message = "should be writable";
		File file = new FileBuilder("foo.png").canWrite(false).build();
		assertThatThrownBy(() -> PreConditions.isWritable(file, message))
				.isInstanceOf(UnwritableFileException.class)
				.hasMessage(message);
	}

	@Test
	void it_should_not_fail_with_writable_file() {
		String message = "should be writable";
		File file = new FileBuilder("foo.png").build();

		File result = PreConditions.isWritable(file, message);

		assertThat(result).isSameAs(file);
	}
}
