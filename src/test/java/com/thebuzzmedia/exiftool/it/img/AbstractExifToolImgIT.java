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

package com.thebuzzmedia.exiftool.it.img;

import com.thebuzzmedia.exiftool.ExifTool;
import com.thebuzzmedia.exiftool.ExifToolBuilder;
import com.thebuzzmedia.exiftool.ExifToolOptions;
import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardFormat;
import com.thebuzzmedia.exiftool.core.StandardOptions;
import com.thebuzzmedia.exiftool.core.StandardTag;
import com.thebuzzmedia.exiftool.tests.junit.ProcessLeakDetectorExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.Map;

import static com.thebuzzmedia.exiftool.tests.FileTestUtils.copy;
import static com.thebuzzmedia.exiftool.tests.TagTestUtils.parseTags;
import static com.thebuzzmedia.exiftool.tests.TestConstants.EXIF_TOOL;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractExifToolImgIT {

	private static final String PATH = EXIF_TOOL.getAbsolutePath();

	@RegisterExtension
	public ProcessLeakDetectorExtension processes = new ProcessLeakDetectorExtension(PATH);

	private ExifTool exifTool;
	private ExifTool exifToolStayOpen;
	private ExifTool exifToolPool;

	@BeforeEach
	void setUp() {
		exifTool = new ExifToolBuilder().withPath(PATH).build();
		exifToolStayOpen = new ExifToolBuilder().withPath(PATH).enableStayOpen().build();
		exifToolPool = new ExifToolBuilder().withPath(PATH).withPoolSize(2).build();
	}

	@AfterEach
	void tearDown() throws Exception {
		exifTool.close();
		exifToolStayOpen.close();
		exifToolPool.close();
	}

	@Test
	void test_get_image_meta() throws Exception {
		verifyGetMeta(exifTool);
	}

	@Test
	void test_parse_image_meta() throws Exception {
		verifyParsingMeta(exifTool);
	}

	@Test
	void test_get_all_image_meta() throws Exception {
		verifyGetAllMeta(exifTool);
	}

	@Test
	void test_get_image_meta_stay_open() throws Exception {
		verifyGetMeta(exifToolStayOpen);
	}

	@Test
	void test_get_image_meta_stay_open_two_times() throws Exception {
		verifyGetMeta(exifToolStayOpen);
		verifyGetMeta(exifToolStayOpen);
	}

	@Test
	void test_get_image_meta_pool() throws Exception {
		verifyGetMeta(exifToolPool);
	}

	@Test
	void test_set_image_meta(@TempDir File tmpFolder) throws Exception {
		verifySetMeta(exifTool, tmpFolder);
	}

	@Test
	void test_set_image_meta_stay_open(@TempDir File tmpFolder) throws Exception {
		verifySetMeta(exifToolStayOpen, tmpFolder);
	}

	@Test
	void test_set_image_meta_pool(@TempDir File tmpFolder) throws Exception {
		verifySetMeta(exifToolPool, tmpFolder);
	}

	private void verifyGetMeta(ExifTool exifTool) throws Exception {
		File file = new File("src/test/resources/images/" + image());
		checkMeta(exifTool, file, StandardTag.values(), expectations());
	}

	private void verifyParsingMeta(ExifTool exifTool) throws Exception {
		File file = new File("src/test/resources/images/" + image());
		checkMetaParsing(exifTool, file, StandardTag.values());
	}

	private void verifyGetAllMeta(ExifTool exifTool) throws Exception {
		File file = new File("src/test/resources/images/" + image());
		checkAllMetaContains(exifTool, file, expectations());
	}

	private void verifySetMeta(ExifTool exifTool, File tmpFolder) throws Exception {
		ExifToolOptions options = StandardOptions.builder().withFormat(StandardFormat.HUMAN_READABLE).build();
		File file = new File("src/test/resources/images/" + image());
		File tmpCopy = copy(file, tmpFolder);
		Map<Tag, String> meta = updateTags();

		exifTool.setImageMeta(tmpCopy, options, meta);

		Tag[] tags = new Tag[meta.size()];
		int i = 0;
		for (Tag t : meta.keySet()) {
			tags[i] = t;
			i++;
		}

		checkMeta(exifTool, tmpCopy, tags, meta);
	}

	private void checkMeta(ExifTool exifTool, File image, Tag[] tags, Map<Tag, String> expectations) throws Exception {
		ExifToolOptions options = StandardOptions.builder().withFormat(StandardFormat.HUMAN_READABLE).build();
		Map<Tag, String> results = exifTool.getImageMeta(image, options, asList(tags));
		SoftAssertions softly = new SoftAssertions();

		for (Map.Entry<Tag, String> entry : results.entrySet()) {
			Tag tag = entry.getKey();
			String result = entry.getValue();
			String expectation = expectations.get(tag);

			softly.assertThat(expectations)
					.overridingErrorMessage(String.format("Result should contain tag %s", tag))
					.containsKey(tag);

			softly.assertThat(result)
					.overridingErrorMessage(String.format("Result should contain tag %s with value %s but was %s", tag, expectation, result))
					.isEqualToIgnoringCase(expectation);
		}

		for (Map.Entry<Tag, String> entry : expectations.entrySet()) {
			Tag tag = entry.getKey();
			String expectation = entry.getValue();
			String result = entry.getValue();

			softly.assertThat(results)
					.overridingErrorMessage(String.format("Expectations should contain tag %s", tag))
					.containsKey(tag);

			softly.assertThat(result)
					.overridingErrorMessage(String.format("Expection should contain tag %s with value %s but was %s", tag, result, expectation))
					.isEqualToIgnoringCase(expectation);
		}

		softly.assertAll();
	}

	private void checkMetaParsing(ExifTool exifTool, File image, Tag[] tags) throws Exception {
		Map<Tag, String> results = exifTool.getImageMeta(image, StandardFormat.NUMERIC, asList(tags));

		SoftAssertions softly = new SoftAssertions();

		for (Map.Entry<Tag, String> entry : results.entrySet()) {
			Tag tag = entry.getKey();
			String result = entry.getValue();

			try {
				Object values = tag.parse(result);
				softly.assertThat(values)
						.overridingErrorMessage(String.format("Cannot parse tag %s with value %s", tag, result))
						.isNotNull();
			}
			catch (Exception ex) {
				softly.fail(ex.getMessage() + " -- " + String.format("Cannot parse tag %s with value %s", tag, result));
			}
		}

		softly.assertAll();
	}

	private void checkAllMetaContains(ExifTool exifTool, File image, Map<Tag, String> expectations) throws Exception {
		ExifToolOptions options = StandardOptions.builder().withFormat(StandardFormat.HUMAN_READABLE).build();
		Map<Tag, String> results = exifTool.getImageMeta(image, options);
		assertThat(results).isNotEmpty();
		assertThat(results.size()).isGreaterThanOrEqualTo(expectations.size());

		Map<String, Object> parsedResults = parseTags(results);

		SoftAssertions softly = new SoftAssertions();

		for (Map.Entry<Tag, String> entry : expectations.entrySet()) {
			Tag tag = entry.getKey();
			Object result = parsedResults.get(tag.getName());

			softly.assertThat(parsedResults)
					.overridingErrorMessage(String.format("Result should contain tag %s", tag))
					.containsKey(tag.getDisplayName());

			softly.assertThat(((String[]) result)[0])
					.overridingErrorMessage(String.format("Result should contain tag %s with value %s", tag, entry.getValue()))
					.isEqualToIgnoringCase(entry.getValue());
		}

		softly.assertAll();
	}

	abstract String image();

	abstract Map<Tag, String> expectations();

	abstract Map<Tag, String> updateTags();
}
