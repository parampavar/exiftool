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

package com.thebuzzmedia.exiftool.commons.iterables;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class CollectionsTest {

	@Test
	void it_should_check_if_collection_is_empty() {
		assertThat(Collections.isEmpty(null)).isTrue();
		assertThat(Collections.isEmpty(emptyList())).isTrue();
		assertThat(Collections.isEmpty(asList(1, 2, 3))).isFalse();
	}

	@Test
	void it_should_check_if_collection_is_not_empty() {
		assertThat(Collections.isNotEmpty(null)).isFalse();
		assertThat(Collections.isNotEmpty(emptyList())).isFalse();
		assertThat(Collections.isNotEmpty(asList(1, 2, 3))).isTrue();
	}

	@Test
	void it_should_get_size_of_collection() {
		assertThat(Collections.size(null)).isZero();
		assertThat(Collections.size(emptyList())).isZero();
		assertThat(Collections.size(asList(1, 2, 3))).isEqualTo(3);
	}

	@Test
	void it_should_get_collection_from_iterable() {
		Iterable<Integer> iterables = () -> new Iterator<Integer>() {
			private int i = 0;

			@Override
			public boolean hasNext() {
				return i < 3;
			}

			@Override
			public Integer next() {
				i++;
				return i;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};

		assertThat(Collections.toCollection(null)).isNotNull().isEmpty();
		assertThat(Collections.toCollection(asList(1, 2, 3))).isEqualTo(asList(1, 2, 3));
		assertThat(Collections.toCollection(iterables)).isEqualTo(asList(1, 2, 3));
	}

	@Test
	void it_should_add_all_iterable_elements_in_a_null_safe_way_1() {
		Collection<String> collection = new ArrayList<>();
		Iterable<String> iterable = null;

		Collections.addAll(collection, iterable);

		assertThat(collection).isNotNull().isEmpty();
	}

	@Test
	void it_should_add_all_iterable_elements_in_a_null_safe_way_2() {
		Collection<String> collection = null;
		Iterable<String> iterable = new ArrayList<>();

		Collections.addAll(collection, iterable);

		assertThat(collection).isNull();
	}

	@Test
	void it_should_add_all_iterable_elements() {
		Collection<String> collection = new ArrayList<>();
		Iterable<String> iterable = asList("one", "two", "three");

		Collections.addAll(collection, iterable);

		assertThat(collection).hasSameSizeAs(iterable).containsExactlyElementsOf(
				iterable
		);
	}
}
