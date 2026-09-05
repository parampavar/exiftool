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

import ch.qos.logback.classic.Level;

import static com.thebuzzmedia.exiftool.tests.ReflectionTestUtils.readPrivateField;

class LoggerSlf4jTest extends AbstractLoggerTest {

	@Override
	Logger getLogger() {
		return createLogger(Level.TRACE);
	}

	@Override
	Logger getLoggerWithoutDebug() {
		return createLogger(Level.INFO);
	}

	private Logger createLogger(Level level) {
		LoggerSlf4j logger = new LoggerSlf4j(getClass());

		ch.qos.logback.classic.Logger logback = readPrivateField(logger, "log");
		logback.setLevel(level);
		logback.setAdditive(true);

		return logger;
	}
}
