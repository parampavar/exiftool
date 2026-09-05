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
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommandBuilderTest {

	@Test
	void it_should_build_command() {
		Command cmd = CommandBuilder.builder("exiftool").build();
		assertThat(cmd.toString()).isEqualTo("exiftool");
	}

	@Test
	void it_should_build_command_with_expected_size_and_arguments() {
		Command cmd = CommandBuilder.builder("exiftool", 1).addArgument("-ver").build();
		assertThat(cmd.toString()).isEqualTo("exiftool -ver");
	}

	@Test
	void it_should_build_command_with_arguments() {
		Command cmd = CommandBuilder.builder("exiftool").addArgument("-ver").build();
		assertThat(cmd.toString()).isEqualTo("exiftool -ver");
	}

	@Test
	void it_should_build_command_with_several_arguments() {
		Command cmd = CommandBuilder.builder("exiftool").addArgument("-ver", "-stay_open").build();
		assertThat(cmd.toString()).isEqualTo("exiftool -ver -stay_open");
	}

	@Test
	void it_should_not_build_command_if_executable_is_null() {
		assertThatThrownBy(() -> CommandBuilder.builder(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Command line executable should be defined");
	}

	@Test
	void it_should_not_build_command_if_executable_is_empty() {
		assertThatThrownBy(() -> CommandBuilder.builder(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line executable should be defined");
	}

	@Test
	void it_should_not_build_command_if_executable_is_blank() {
		assertThatThrownBy(() -> CommandBuilder.builder(" "))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line executable should be defined");
	}

	@Test
	void it_should_not_build_command_if_argument_is_null() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument(null))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Command line argument should be defined if set");
	}

	@Test
	void it_should_not_build_command_if_argument_is_empty() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument(""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line argument should be defined if set");
	}

	@Test
	void it_should_not_build_command_if_argument_is_blank() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument(" "))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line argument should be defined if set");
	}

	@Test
	void it_should_not_build_command_if_one_of_argument_is_null() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument("-ver", new String[]{null}))
				.isInstanceOf(NullPointerException.class)
				.hasMessage("Command line argument should be defined if set");
	}

	@Test
	void it_should_not_build_command_if_one_of_argument_is_empty() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument("-ver", ""))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line argument should be defined if set");
	}

	@Test
	void it_should_not_build_command_if_one_of_argument_is_blank() {
		assertThatThrownBy(() -> CommandBuilder.builder("exiftool").addArgument("-ver", " "))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Command line argument should be defined if set");
	}
}
