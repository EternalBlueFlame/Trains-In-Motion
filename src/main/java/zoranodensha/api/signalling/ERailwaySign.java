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
package zoranodensha.api.signalling;

/**
 * All railway signs added by Zora no Densha.
 */
public enum ERailwaySign {

	/** Crossing sign (single crossing within normal distance). Top part */
	CROSSING_SiNo_Top(0, 0, 30, 1),
	/** Crossing sign (single crossing within normal distance). Bottom part */
	CROSSING_SiNo_Bot(1, 0, 30, 14),
	/** Crossing sign (single crossing within short distance). Top part */
	CROSSING_SiSh_Top(2, 1, 37, 1),
	/** Crossing sign (single crossing within short distance). Bottom part */
	CROSSING_SiSh_Bot(3, 1, 37, 14),
	/** Crossing sign (several crossings within normal distance). Top part */
	CROSSING_DuNo_Top(4, 2, 44, 1),
	/** Crossing sign (several crossings within normal distance). Bottom part */
	CROSSING_DuNo_Bot(5, 2, 44, 14),
	/** Crossing sign (several crossings within short distance). Top part */
	CROSSING_DuSh_Top(6, 3, 51, 1),
	/** Crossing sign (several crossings within short distance). Bottom part */
	CROSSING_DuSh_Bot(7, 3, 51, 14),

	/** Information sign (Checkerboard indicating misplaced signal/ sign). Top part */
	INFORMATION_Check_Top(8, 4, 58, 1),
	/** Information sign (Checkerboard indicating misplaced signal/ sign). Bottom part */
	INFORMATION_Check_Bot(9, 4, 58, 14),
	/** Information sign (Arrow indicating end of speed restriction). Top part */
	INFORMATION_SpeedEnd_Top(10, 5, 65, 1),
	/** Information sign (Arrow indicating end of speed restriction). Bottom part */
	INFORMATION_SpeedEnd_Bot(11, 5, 65, 14),
	/** Information sign (Switch ahead). */
	INFORMATION_Switch(12, 6, 44, 27),
	/** Information sign (Crossing box nearby). */
	INFORMATION_Box(13, 7, 58, 27),
	/** Information sign (End of track within braking distance). */
	INFORMATION_End(14, 8, 65, 27),

	/** Command sign (Whistle here). */
	COMMAND_Whistle(15, 9, 30, 27),
	/** Command sign (Whistle here, only if not stopping). */
	COMMAND_Whistle1(16, 10, 37, 27),
	/** Command sign (Stop here, only if required). */
	COMMAND_Stop(17, 11, 51, 27),
	/** Command sign (Speed limit of 10 km/h). */
	COMMAND_Speed_10(18, 12, 30, 40),
	/** Command sign (Speed limit of 30 km/h). */
	COMMAND_Speed_30(19, 13, 37, 40),
	/** Command sign (Speed limit of 50 km/h). */
	COMMAND_Speed_50(20, 14, 44, 40),
	/** Command sign (Speed limit of 70 km/h). */
	COMMAND_Speed_70(21, 15, 51, 40),
	/** Command sign (Speed limit of 90 km/h). */
	COMMAND_Speed_90(22, 16, 58, 40),
	/** Command sign (Speed limit of 110 km/h). */
	COMMAND_Speed_110(23, 17, 65, 40),

	/** Construction sign (Construction area starts here). */
	CONSTRUCTION_Start(24, 18, 30, 53),
	/** Construction sign (Construction area ends here). */
	CONSTRUCTION_End(25, 19, 37, 53),
	/** Construction sign (Ignore signal). */
	CONSTRUCTION_Ignore(26, 20, 44, 53),
	/** Construction sign (Stop in front of crossing and close manually/ Crossing deactivated). */
	CONSTRUCTION_Crossing(27, 21, 51, 53),
	/** Construction sign (Speed limit of 10km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_10(28, 22, 30, 66),
	/** Construction sign (Speed limit of 30km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_30(29, 23, 37, 66),
	/** Construction sign (Speed limit of 50km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_50(30, 24, 44, 66),
	/** Construction sign (Speed limit of 70km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_70(31, 25, 51, 66),
	/** Construction sign (Speed limit of 90km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_90(32, 26, 58, 66),
	/** Construction sign (Speed limit of 110km/h, only inside the given construction area). */
	CONSTRUCTION_Speed_110(33, 27, 65, 66);

	private final int sign_index;
	private final int item_index;
	private final int[] texCoords;

	private ERailwaySign(int signID, int itemID, int... coordinates) {

		this.sign_index = signID;
		this.item_index = itemID;
		this.texCoords = coordinates;
	}

	/**
	 * Returns an integer representing the given sign.
	 */
	public int toInt() {

		return this.sign_index;
	}

	/**
	 * Returns an integer representing the given sign's item.
	 */
	public int toItemInt() {

		return this.item_index;
	}

	public static ERailwaySign toSign(int i) {

		switch (i) {

		default:
			return null;

		case 0:
			return CROSSING_SiNo_Top;
		case 1:
			return CROSSING_SiNo_Bot;
		case 2:
			return CROSSING_SiSh_Top;
		case 3:
			return CROSSING_SiSh_Bot;
		case 4:
			return CROSSING_DuNo_Top;
		case 5:
			return CROSSING_DuNo_Bot;
		case 6: 
			return CROSSING_DuSh_Top;
		case 7:
			return CROSSING_DuSh_Bot;

		case 8:
			return INFORMATION_Check_Top;
		case 9:
			return INFORMATION_Check_Bot;
		case 10:
			return INFORMATION_SpeedEnd_Top;
		case 11:
			return INFORMATION_SpeedEnd_Bot;
		case 12:
			return INFORMATION_Switch;
		case 13:
			return INFORMATION_Box;
		case 14:
			return INFORMATION_End;

		case 15:
			return COMMAND_Whistle;
		case 16:
			return COMMAND_Whistle1;
		case 17:
			return COMMAND_Stop;
		case 18:
			return COMMAND_Speed_10;
		case 19:
			return COMMAND_Speed_30;
		case 20:
			return COMMAND_Speed_50;
		case 21:
			return COMMAND_Speed_70;
		case 22:
			return COMMAND_Speed_90;
		case 23:
			return COMMAND_Speed_110;

		case 24:
			return CONSTRUCTION_Start;
		case 25:
			return CONSTRUCTION_End;
		case 26:
			return CONSTRUCTION_Ignore;
		case 27:
			return CONSTRUCTION_Crossing;
		case 28:
			return CONSTRUCTION_Speed_10;
		case 29:
			return CONSTRUCTION_Speed_30;
		case 30:
			return CONSTRUCTION_Speed_50;
		case 31:
			return CONSTRUCTION_Speed_70;
		case 32:
			return CONSTRUCTION_Speed_90;
		case 33:
			return CONSTRUCTION_Speed_110;
		}
	}

	/**
	 * Returns the coordinate indices of this ERailwaySign in an integer array.
	 */
	public int[] toCoordinate() {

		return this.texCoords;
	}
}