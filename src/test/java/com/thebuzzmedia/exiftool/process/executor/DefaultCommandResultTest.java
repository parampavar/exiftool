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

package com.thebuzzmedia.exiftool.process.executor;

import com.thebuzzmedia.exiftool.process.CommandResult;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultCommandResultTest {

	@Test
	void it_should_create_result() {
		String output = "foo";
		CommandResult result = new DefaultCommandResult(0, output);
		assertThat(result.getExitStatus()).isZero();
		assertThat(result.getOutput()).isEqualTo(output);
	}

	@Test
	void it_should_mark_result_as_success() {
		CommandResult result = new DefaultCommandResult(0, "foo");
		assertThat(result.isSuccess()).isTrue();
		assertThat(result.isFailure()).isFalse();
	}

	@Test
	void it_should_mark_result_as_failure() {
		CommandResult result = new DefaultCommandResult(1, "foo");
		assertThat(result.isSuccess()).isFalse();
		assertThat(result.isFailure()).isTrue();
	}

	@Test
	void it_should_implement_equals_and_hashCode() {
		EqualsVerifier.forClass(DefaultCommandResult.class).verify();
	}

	@Test
	void it_should_implement_toString() {
		assertThat(new DefaultCommandResult(0, "foo")).hasToString("[0] foo");
	}
}
