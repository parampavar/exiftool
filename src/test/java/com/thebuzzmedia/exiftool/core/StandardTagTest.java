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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StandardTagTest {

	@Test
	void it_should_extract_IPTC_KEYWORD_with_a_single_value() {
		String value = "foo";
		String[] result = StandardTag.IPTC_KEYWORDS.parse(value);
		assertThat(result).hasSize(1).contains(value);
	}

	@Test
	void it_should_extract_IPTC_KEYWORD_with_multiple_values() {
		String value = "foo|>☃bar";
		String[] result = StandardTag.IPTC_KEYWORDS.parse(value);
		assertThat(result).hasSize(2).contains(
				"foo", "bar"
		);
	}

	@Test
	void it_should_not_contains_duplicate() {
		StandardTag[] tags = StandardTag.values();
		List<String> tagNames = new ArrayList<>(tags.length);
		for (StandardTag tag : tags) {
			tagNames.add(tag.getDisplayName());
		}

		assertThat(tagNames).doesNotHaveDuplicates();
	}
}
