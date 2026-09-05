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

import static com.thebuzzmedia.exiftool.tests.ReflectionTestUtils.readPrivateField;
import static org.apache.logging.log4j.Level.INFO;
import static org.apache.logging.log4j.Level.TRACE;

class LoggerLog4j2Test extends AbstractLoggerTest {

	@Override
	Logger getLogger() {
		return createLogger(TRACE);
	}

	@Override
	Logger getLoggerWithoutDebug() {
		return createLogger(INFO);
	}

	private Logger createLogger(org.apache.logging.log4j.Level level) {
		LoggerLog4j2 logger = new LoggerLog4j2(getClass());

		org.apache.logging.log4j.core.Logger log4j = readPrivateField(logger, "log");
		log4j.setAdditive(true);
		log4j.setLevel(level);

		return logger;
	}
}
