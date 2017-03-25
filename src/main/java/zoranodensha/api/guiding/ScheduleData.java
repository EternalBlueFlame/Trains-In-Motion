/*
 * Copyright © August 2016 Leshuwa Kaiheiwa
 * All rights reserved.
 * 
 * §0 COPYRIGHT OWNER PSEUDONYM AND AREA OF JURISDICTION
 * 
 * 1. The area of jurisdiction is the Federal Republic of Germany.
 * 
 * 2. The copyright owner reserves the right to hide his identity behind
 * the given pseudonym as stated in §1.3 of the Council Directive 93/98/EWG
 * of the council of the European Communities from 24th November 1993.
 * 
 * 
 * §1 DISCLAIMER OF LIABILITY
 * 
 * 1. This software is provided by the copyright owner and contributors
 * "as is" and any express or implied warranties are expressly disclaimed.
 * 
 * 2. In no event shall the copyright owner or contributors or their relatives
 * be liable for any damages, including, but not limited to, direct, indirect,
 * physical, mental, or consequential damages, however caused, arising in any way
 * out of the use of this software, even if advised of the possibility of such
 * damage.
 * 
 * 
 * §2 CODEX OF FAIR USAGE
 * Any action executed by third parties that involve the project in any way is
 * allowed, provided the following conditions are met:
 * 
 * 1. The idea, the process of creation, and the final result must not, under
 * any circumstances, follow the intention of, or create a result that is, harming
 * or discriminating against any entity, including, but not limited to, a single
 * person, a group, or a country.
 * 
 * 2. If the suspicion or clear fact arises that the end-product breaks the
 * condition described in §2.1, the author of the product remains responsible to
 * remove all sources of harm originating from the product.
 * 
 * 3. The usage of this software, in parts or as a whole, is free to the end-user.
 * 
 * 4. Additional conditions for the packages 'worlddreamer' and 'assets.zoranodensha'
 * may apply.
 * 
 * 
 * §3 TERMS OF USAGE (ZoraNoDensha Packages)
 * Any action executed by third parties that involve any works except for the package
 * 'worlddreamer' is allowed, provided the following conditions are met:
 * 
 * 1. All actions and intentions must not break the terms described in §2.
 * 
 * 2. Actions that are permitted without the author's knowledge are:
 * 2a Decompiling and viewing the source code for educational purposes;
 * 2b Usage of the API package 'zoranodensha.api' for any purpose under previously
 * mentioned conditions;
 * 2c Copying non-API code and files, modified or unmodified, provided that the used code
 * is clearly marked as the copyright owner's property and that the usage of the code does
 * not break the agreement described in §2.
 * 
 * 3. Actions that require written permission from the author:
 * 3a Re-uploading the project, partially or as a whole, with or without modification.
 * 
 * 4. Actions that are strictly prohibited:
 * 4a Any modification of the files found in the package 'worlddreamer'.
 * 
 * 
 * §4 APPLICATION OF THIS LICENSE
 * 
 * 1. The project covered by this license remains property of the copyright owner to the
 * point of his death.
 * 1a Afterwards, the project is considered public good and shall be accessible and free
 * to use for anyone who desires access, provided their works do not break with the terms
 * described in §2.
 * 1b The copyright owner waives all copyrights on packages 'assets.zoranodensha' and 'mods',
 * as well as on files where credit is given to third party copyright owners. All rights belong
 * to the rightful copyright owners.
 * 
 * 2. The death of the copyright owner renders §3 invalid; these terms shall be overridden
 * by the terms of usage as described in §2.
 * 
 * 3. The package 'worlddreamer' must not be altered in any way and must remain part of
 * the project. (Re-)distribution of said package is allowed following the terms and
 * conditions of this license.
 */
package zoranodensha.api.guiding;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.nbt.NBTTagCompound;

/**
 * The class containing a scheme to use for transmitting schedules throughout the train network (e.g. to platforms).
 */
public final class ScheduleData {

	/** The corresponding platform. This should be a number between 0 and 59, inclusive. */
	public final int platform;

	/**
	 * The matching line declaration.
	 * Japanese syllables can be translated directly. Numbers remain numerical.<p>
	 * 
	 * An example (English/ German):<br>
	 * 'AX-123', where the '-' will remain silent and all letters and numbers are pronounced individually.
	 */
	public final String line;

	/** The time of expected arrival. The (local) server's system time is used. */
	public final String timeDue;

	/** The language of this schedule. Can either be English ('en'), Japanese ('jp') or German ('de'). */
	public final String language;

	/** Whether this schedule is the train's last destination (2), a normal stop (1) or a departure (0). */
	public final int end;

	/**
	 * The sub-platforms the train shall occupy. This may be null. Characters from A to G are used.<p>
	 * 
	 * An example:<br>
	 * new char[] {'A', 'C'}; Will occupy all sub-platforms from A to C.
	 */
	public final char[] subplatforms;

	/**
	 * Constructs an empty ScheduleData instance that is used by the speaker system to annotate a vehicle passing through.
	 */
	public ScheduleData() {

		this.platform = 0;
		this.line = "";
		this.timeDue = " : ";
		this.language = "";
		this.end = 1;
		this.subplatforms = new char[0];
	}

	/**
	 * Construct a ScheduleData instance from the given parameters.
	 */
	public ScheduleData(int platform, String line, String timeDue, String language, int end, char... subplatforms) {

		this.platform = platform;
		this.line = line;
		this.timeDue = timeDue;
		this.language = language;
		this.end = end;
		this.subplatforms = subplatforms;
	}

	/**
	 * Writes the data to the given NBTTagCompound with the given key and returns it.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt, String key) {

		nbt.setInteger("ScheduleData_Platform_" + key, this.platform);
		nbt.setString("ScheduleData_Line_" + key, this.line);
		nbt.setString("ScheduleData_Time_" + key, this.timeDue);
		nbt.setString("ScheduleData_lang_" + key, this.language);
		nbt.setInteger("ScheduleData_End_" + key, this.end);
		nbt.setString("ScheduleData_Subplatforms_" + key, (this.subplatforms.length > 0 ? this.subplatforms[0] : "") + "" + (this.subplatforms.length > 0 ? this.subplatforms[this.subplatforms.length - 1] : ""));

		return nbt;
	}

	/**
	 * Tries to read data from the given NBTTagCompound with the given key to create a new ScheduleData instance.
	 * If successful, returns it. Otherwise returns null.
	 */
	public static ScheduleData readFromNBT(NBTTagCompound nbt, String key) {

		try {

			return new ScheduleData(nbt.getInteger("ScheduleData_Platform_" + key),
					nbt.getString("ScheduleData_Line_" + key),
					nbt.getString("ScheduleData_Time_" + key),
					nbt.getString("ScheduleData_lang_" + key),
					nbt.getInteger("ScheduleData_End_" + key),
					nbt.getString("ScheduleData_Subplatforms_" + key).toCharArray());
		}
		catch (Throwable t) {

			return null;
		}
	}

	/**
	 * Reads from the given NBTTagCompound and returns a List of Strings containing all line declarations of the given keys.
	 */
	public static List<String> readFromNBTFindLines(NBTTagCompound nbt, int keyMax, int keyMin, String key) {

		List<String> list = new ArrayList<String>();

		for (int i = keyMin; i < keyMax; ++i) {

			ScheduleData sd = ScheduleData.readFromNBT(nbt, key + i);

			if (sd != null) {

				list.add(sd.line);
			}
		}

		return list;
	}

	/**
	 * True if this schedule contains the given line.
	 */
	public boolean containsLine(String line) {

		return this.line.equals(line);
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof ScheduleData) {

			int flag = 0;
			boolean isDifferentLanguage = false;

			ScheduleData schedule = (ScheduleData) obj;

			if (schedule.platform == this.platform)
				++flag;

			if (schedule.line.equals(this.line))
				++flag;

			if (schedule.timeDue.equals(this.timeDue))
				++flag;

			if (schedule.language.equals(this.language))
				++flag;
			else
				isDifferentLanguage = true;

			if (schedule.end == this.end)
				++flag;

			if (schedule.subplatforms == this.subplatforms)
				++flag;

			return (flag == 6 || (flag == 5 && isDifferentLanguage));
		}

		return false;
	}

	/**
	 * Formats a String s to the given length with the given String, either at the begin of the String s or at its end.
	 */
	public static final String getFormattedString(String s, int length, String append, boolean toBegin) {

		for (int i = s.length(); i < length; i++) {

			if (toBegin) {

				s = append + s;
			}
			else {

				s += append;
			}
		}

		return s;
	}

	/**
	 * Returns a new ScheduleData instance from the given String array.
	 */
	public static ScheduleData getFromStringArray(String[] s) {

		if (s.length != 8)
			return null;

		return new ScheduleData(
				(s[0].isEmpty() ? 0 : Integer.parseInt(s[0])),
				s[3],
				getFormattedString(s[4], 2, "0", true) + ":" + getFormattedString(s[5], 2, "0", true),
				s[7],
				!s[6].isEmpty() ? (s[6].toLowerCase().equals("d") ? 0 : s[6].toLowerCase().equals("a") ? 1 : 2) : 0,
						(!s[1].isEmpty() && s[1].toCharArray().length > 0 ? s[1].toCharArray()[0] : ' '),
						(!s[2].isEmpty() && s[2].toCharArray().length > 0 ? s[2].toCharArray()[0] : ' ')
				);
	}

	public String getOuterSubplatforms() {

		if (this.subplatforms.length > 0) {

			return ((Character.isAlphabetic(this.subplatforms[0]) ? this.subplatforms[0] : "_") + "-" + (Character.isAlphabetic(this.subplatforms[this.subplatforms.length - 1]) ? this.subplatforms[this.subplatforms.length - 1] : "_"));
		}

		return "_-_";
	}

	/**
	 * Returns an array of Strings with 7 indices.
	 * In order: Platform (0), Sub-platform beginning (1), Sub-platform end (2), Line (3), Hour (4), Minute (5), Type of destination (6), Language (7).
	 */
	public String[] getScheduleAsArray() {

		String[] s0 = this.toString().split(Pattern.quote(" | "));
		String[] s1 = s0[0].split(" ");
		String[] s2 = s1[1].split("-");
		String[] s3 = s0[2].split(":");

		return new String[] {s1[0], s2[0], s2[1], s0[1], s3[0], s3[1], s0[3], this.language};
	}

	/**
	 * Scans each part of the ScheduleData for whether it is properly formatted, focusing on platform (is it above 0) and line name (does the name contain both Japanese and Roman letters).
	 * If it is valid, returns 2.
	 */
	public int getValidity() {

		int i = 0;

		if (this.platform > 0)
			++i;

		if (this.language.equals("jp")) {

			char[] sequence = this.line.toCharArray();

			for (int j = 0; j < sequence.length; ++j) {

				if (!Character.UnicodeScript.of(sequence[j]).equals(Character.UnicodeScript.HIRAGANA) && !Character.isDigit(sequence[j]) && !(sequence[j] == '-') && !(sequence[j] == '_')) {

					return i;
				}
			}

			++i;
		}
		else {

			char[] sequence = this.line.toCharArray();

			for (int j = 0; j < sequence.length; ++j) {

				if (!(Character.isAlphabetic(sequence[j]) && !Character.UnicodeScript.of(sequence[j]).equals(Character.UnicodeScript.HIRAGANA)) && !Character.isDigit(sequence[j]) && !(sequence[j] == '-') && !(sequence[j] == '_')) {

					return i;
				}
			}

			++i;
		}

		return i;
	}

	@Override
	public int hashCode() {

		int time = 0;
		int subplatforms = 0;

		String[] s = this.getOuterSubplatforms().split("-");

		for (int i = 0; i < Math.min(s.length, 2); ++i) {

			if ("A".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 1) {

					subplatforms += 1;
				}
			}
			else if ("B".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 2) {

					subplatforms += 2;
				}
			}
			else if ("C".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 4) {

					subplatforms += 4;
				}
			}
			else if ("D".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 8) {

					subplatforms += 8;
				}
			}
			else if ("E".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 16) {

					subplatforms += 16;
				}
			}
			else if ("F".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 32) {

					subplatforms += 32;
				}
			}
			else if ("G".equalsIgnoreCase(s[i])) {

				if (i == 0 || subplatforms != 64) {

					subplatforms += 64;
				}
			}
		}

		try {

			s = this.timeDue.split(":");
			time = ((Integer.parseInt(s[0]) * 100) + Integer.parseInt(s[1]));
		}
		catch (Throwable t) {}

		return (this.platform * 10000000 + time * 1000 + this.end * 100 + subplatforms);
	}

	/**
	 * Converts the given list of ScheduleData instances to an array.
	 */
	public static ScheduleData[] toArray(List<ScheduleData> list) {

		ScheduleData[] array = new ScheduleData[list.size()];

		for (int i = 0; i < array.length; ++i) {

			array[i] = list.get(i);
		}

		return array;
	}

	@Override
	public String toString() {

		return ((this.platform < 10 ? "0" + this.platform : this.platform) + " " + this.getOuterSubplatforms().toUpperCase() + " | " + this.line.toUpperCase() + " | " + this.timeDue + " | " + (this.end == 0 ? "D" : this.end == 1 ? "A" : "E"));
	}
}