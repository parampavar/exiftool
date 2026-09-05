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

import com.thebuzzmedia.exiftool.Tag;
import com.thebuzzmedia.exiftool.core.StandardTag;

import java.util.HashMap;
import java.util.Map;

class ExifTool_IMG12_IT extends AbstractExifToolImgIT {

	@Override
	String image() {
		return "83357962-80c4b700-a378-11ea-9b05-63745c0c7fcf.jpg";
	}

	@Override
	Map<Tag, String> expectations() {
		return new HashMap<Tag, String>() {
			{
				put(StandardTag.ISO, "200");
				put(StandardTag.WHITE_BALANCE, "Auto");
				put(StandardTag.IMAGE_WIDTH, "120");
				put(StandardTag.IMAGE_HEIGHT, "89");
				put(StandardTag.X_RESOLUTION, "300");
				put(StandardTag.Y_RESOLUTION, "300");
				put(StandardTag.FLASH, "Off, Did not fire");
				put(StandardTag.METERING_MODE, "Multi-segment");
				put(StandardTag.FNUMBER, "3.6");
				put(StandardTag.FOCAL_LENGTH, "13.0 mm");
				put(StandardTag.FOCAL_LENGTH_35MM, "26 mm");
				put(StandardTag.EXPOSURE_TIME, "1/3200");
				put(StandardTag.EXPOSURE_COMPENSATION, "0");
				put(StandardTag.EXPOSURE_PROGRAM, "Aperture-priority AE");
				put(StandardTag.ORIENTATION, "Horizontal (normal)");
				put(StandardTag.COLOR_SPACE, "sRGB");
				put(StandardTag.SENSING_METHOD, "One-chip color area");
				put(StandardTag.SOFTWARE, "darktable 3.0.2");
				put(StandardTag.MAKE, "Panasonic");
				put(StandardTag.MODEL, "DMC-GX80");
				put(StandardTag.RATING, "-1");
				put(StandardTag.RATING_PERCENT, "120");
				put(StandardTag.DATE_TIME_ORIGINAL, "2020:05:31 16:00:22");
				put(StandardTag.ROTATION, "Horizontal (normal)");
				put(StandardTag.EXIF_VERSION, "0230");
				put(StandardTag.LENS_ID, "LUMIX G VARIO 12-32mm F3.5-5.6");
				put(StandardTag.SUB_SEC_TIME_ORIGINAL, "189");
				put(StandardTag.FILE_TYPE, "JPEG");
				put(StandardTag.FILE_SIZE, "47 kB");
				put(StandardTag.MIME_TYPE, "image/jpeg");
				put(StandardTag.CREATE_DATE, "2020:05:31 16:00:22");
				put(StandardTag.FOCUS_MODE, "Auto");
				put(StandardTag.MEGA_PIXELS, "0.011");
			}
		};
	}

	@Override
	Map<Tag, String> updateTags() {
		return new HashMap<Tag, String>() {{
			put(StandardTag.COMMENT, "Hello =World");
			put(StandardTag.AUTHOR, "mjeanroy");
		}};
	}
}
