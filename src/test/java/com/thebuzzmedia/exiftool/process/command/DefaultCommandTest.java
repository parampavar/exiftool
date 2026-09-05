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

package com.thebuzzmedia.exiftool.process.command;

import com.thebuzzmedia.exiftool.process.Command;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultCommandTest {

	@Test
	void it_should_create_command() {
		Command command = new DefaultCommand("exiftool", Collections.<String>emptyList());
		assertThat(command.toString()).isEqualTo("exiftool");
		assertThat(command.getArguments()).isEqualTo(singletonList("exiftool"));
	}

	@Test
	void it_should_create_command_and_add_argument() {
		Command command = new DefaultCommand("exiftool", asList("-ver", "-stay_open"));
		assertThat(command.toString()).isEqualTo("exiftool -ver -stay_open");
		assertThat(command.getArguments()).isEqualTo(asList("exiftool", "-ver", "-stay_open"));
	}

	@Test
	void it_should_implement_equals_hash_code() {
		EqualsVerifier.forClass(DefaultCommand.class).verify();
	}
}
