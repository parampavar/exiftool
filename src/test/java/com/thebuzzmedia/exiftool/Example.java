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

package com.thebuzzmedia.exiftool;

import com.thebuzzmedia.exiftool.core.StandardFormat;
import com.thebuzzmedia.exiftool.core.StandardOptions;
import com.thebuzzmedia.exiftool.core.StandardTag;

import java.io.File;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class Example {
	public static void main(String[] args) throws Exception {
		String os = System.getProperty("os.name");
		boolean windows = os.toLowerCase().contains("windows");
		String basePath = "src/test/resources/exiftool-10_16/";
		String binaryPath = windows ? "windows/exiftool.exe" : "unix/exiftool";
		File binary = new File(basePath + binaryPath);
		ExifTool tool = new ExifToolBuilder()
				.withPath(binary.getAbsolutePath())
				.enableStayOpen()
				.build();

		File directory = new File("src/test/resources/images");
		File[] files = directory.listFiles();
		if (files == null) {
			System.out.println("No images to scan.");
			return;
		}

		System.out.println("Images to scan: ");
		for (File f : files) {
			System.out.println("  - " + f.getName());
		}

		ExifToolOptions options = StandardOptions.builder()
				.withFormat(StandardFormat.HUMAN_READABLE)
				.withIgnoreMinorErrors(true)
				.build();

		for (File f : files) {
			System.out.println("\n[" + f.getName() + "]");

			List<Tag> tags = asList(StandardTag.values());
			Map<Tag, String> exifData = tool.getImageMeta(f, options, tags);
			System.out.println(exifData);
		}

		tool.close();
	}
}
