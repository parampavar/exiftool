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

package com.thebuzzmedia.exiftool.commons.reflection;

import com.thebuzzmedia.exiftool.commons.reflection.ClassUtils.ReflectionException;
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClassUtilsTest {

	@Test
	void it_should_get_class() {
		assertThat(ClassUtils.lookupClass("com.thebuzzmedia.exiftool.commons.reflection.ClassUtils")).isEqualTo(ClassUtils.class);
		assertThatThrownBy(() -> ClassUtils.lookupClass("com.thebuzzmedia.exiftool.commons.FooBar")).isInstanceOf(ReflectionException.class).hasCauseInstanceOf(ClassNotFoundException.class);
	}

	@Test
	void it_should_get_static_method() {
		assertThat(ClassUtils.findStaticMethod(TestClass.class, "noop", void.class)).isNotNull();
		assertThat(ClassUtils.findStaticMethod(TestClass.class, "isEmpty", boolean.class, String.class)).isNotNull();
		assertThatThrownBy(() -> ClassUtils.findStaticMethod(TestClass.class, "unknown_static_method", void.class)).isInstanceOf(ReflectionException.class).hasCauseInstanceOf(NoSuchMethodException.class);
	}

	@Test
	void it_should_get_method() {
		assertThat(ClassUtils.findMethod(TestClass.class, "getId", int.class)).isNotNull();
		assertThatThrownBy(() -> ClassUtils.findMethod(TestClass.class, "unknown_static_method", void.class)).isInstanceOf(ReflectionException.class).hasCauseInstanceOf(NoSuchMethodException.class);
	}

	@Test
	void it_should_invoke_static_method() throws Exception {
		MethodHandle methodHandle = MethodHandles.publicLookup().findStatic(TestClass.class, "isEmpty", MethodType.methodType(boolean.class, String.class));
		assertThat(ClassUtils.invokeStatic(methodHandle, "")).isEqualTo(true);
		assertThat(ClassUtils.invokeStatic(methodHandle, "not_empty_string")).isEqualTo(false);
	}

	@Test
	void it_should_invoke_instance_method() throws Exception {
		TestClass target = new TestClass();
		MethodHandle methodHandle_getId = MethodHandles.publicLookup().findVirtual(TestClass.class, "getId", MethodType.methodType(int.class));
		assertThat(ClassUtils.invoke(methodHandle_getId, target)).isEqualTo(0);

		MethodHandle methodHandle_setId = MethodHandles.publicLookup().findVirtual(TestClass.class, "setId", MethodType.methodType(void.class, int.class));
		int newId = 10;
		ClassUtils.invoke(methodHandle_setId, target, newId);
		assertThat(target.getId()).isEqualTo(newId);
	}

	@Test
	void it_should_check_if_class_is_available() {
		assertThat(ClassUtils.isPresent("com.thebuzzmedia.exiftool.commons.reflection.ClassUtils")).isTrue();
		assertThat(ClassUtils.isPresent("com.thebuzzmedia.exiftool.commons.FooBar")).isFalse();
	}
}
