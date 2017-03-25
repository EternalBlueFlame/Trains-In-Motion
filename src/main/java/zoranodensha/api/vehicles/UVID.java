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
package zoranodensha.api.vehicles;

import java.rmi.UnexpectedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;

import org.apache.commons.lang3.ArrayUtils;

import zoranodensha.api.vehicles.vehicleParts.IVehiclePartEngine;
import zoranodensha.api.vehicles.vehicleParts.IVehiclePartEngine.IVehiclePartEngine_Diesel;
import zoranodensha.api.vehicles.vehicleParts.IVehiclePartEngine.IVehiclePartEngine_Electric;
import zoranodensha.api.vehicles.vehicleParts.IVehiclePartEngine.IVehiclePartEngine_Magnet;
import zoranodensha.api.vehicles.vehicleParts.IVehiclePartEngine.IVehiclePartEngine_Steam;

/**
 * The Unique Vehicle ID [{@code UVID}] is a globally consistent system to label and identify rolling stock with minimum ambiguity.<p>
 * 
 * All UVIDs are Strings of length 25 and contain the following information: <p>
 * 
 * D: Date of creation (YYY/MM/DD)<br>
 * E: Type of engine (Diesel = D, Electric = E, Steam = S, Magnetic = M, Hybrid = H, None/ Unknown = O)<br>
 * M: Maximum Speed<br>
 * U: The first three letters of the creator's user name<br>
 * R: Five-digit random number<p>
 * 
 * Formatted into a String of type: {@code DDD-DD-DD E-MMM UUU-RRRRR}.<p>
 * 
 * Examples:<br>
 * Date: 2017/09/30, Engine: Hybrid, Speed: 420km/h, Username: Ed, Random number: 34915<br>
 * {@code 017-09-30 H-420 EDD-34915}<p>
 * 
 * Date: 1997/01/03, Engine: Steam, Speed: 80km/h, Username: Ánha, Random number: 48190<br>
 * {@code 997-01-03 S-080 ANH-48190}
 */
public final class UVID {

	/** The label to use when writing an UVID to NBT */
	public static final String LABEL_NBT_STRING = "ZoraNoDenshaUVID";

	/** Character labels for different engine types used for UVID initialisation. */
	public static final char[] ENGINE_TYPES = new char[] {'D', 'E', 'H', 'M', 'O', 'S'};

	/** A statically accessible random instance, used during UVID initialisation for the five-digit random number. */
	public static final Random ran = new Random();

	/** This UVID as String */
	private final String string;

	/**
	 * Create a new UVID for the given vehicle and parameters.
	 * 
	 * @throws UnexpectedException If the generated UVID is not accepted by itself.
	 */
	public UVID(IEntityTrackBound vehicle, String username) throws UnexpectedException {

		if (vehicle == null || username == null || username.length() <= 0) {

			throw new IllegalArgumentException("[UVID] Tried creating UVID with invalid paramters: " + vehicle + " ; " + username);
		}

		String engine = "";
		Iterator<IVehiclePart> itera = vehicle.getPartsList().iterator();
		IVehiclePart part;
		boolean diesel = false, electric = false, magnetic = false, steam = false;

		while (itera.hasNext()) {

			part = itera.next();

			if (part instanceof IVehiclePartEngine) {

				if (!diesel && part instanceof IVehiclePartEngine_Diesel) {

					diesel = true;
					engine += ENGINE_TYPES[0];
				}

				if (!electric && part instanceof IVehiclePartEngine_Electric) {

					electric = true;
					engine += ENGINE_TYPES[1];
				}

				if (!magnetic && part instanceof IVehiclePartEngine_Magnet) {

					magnetic = true;
					engine += ENGINE_TYPES[3];
				}

				if (!steam && part instanceof IVehiclePartEngine_Steam) {

					steam = true;
					engine += ENGINE_TYPES[5];
				}
			}
		}

		if (engine.length() == 0) {

			engine = String.valueOf(ENGINE_TYPES[4]);
		}
		else if (engine.length() > 1) {

			engine = String.valueOf(ENGINE_TYPES[2]);
		}

		String tmp = String.valueOf(MathHelper.clamp_int(vehicle.getMaxSpeed(), 0, 999));
		String speed = ("000" + tmp).substring(tmp.length());

		if (username.length() == 1) {

			username += username + username;
		}
		else if (username.length() == 2) {

			username += username.substring(1);
		}

		username = username.substring(0, 3);
		tmp = String.valueOf(ran.nextInt(100000));
		String number = ("00000" + tmp).substring(tmp.length());

		String uvid = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date()).substring(1) + " " + engine + "-" + speed + " " + username.toUpperCase() + "-" + number;

		if (isValidUVID(uvid)) {

			this.string = uvid;
		}
		else {

			throw new UnexpectedException("[UVID] Generated UVID couldn't accept itself. [UVID: " + uvid + "]");
		}
	}

	/**
	 * Construct a UVID instance from the given NBTTagCompound.
	 * Actually just reads the NBTTagCompound and uses the String constructor.
	 */
	public UVID(NBTTagCompound nbt) {

		this(nbt.getString(LABEL_NBT_STRING));
	}

	/**
	 * Construct a UVID instance from the given String.
	 * 
	 * @throws IllegalArgumentException If the supplied String is not a valid UVID.
	 */
	public UVID(String s) throws IllegalArgumentException {

		if (!isValidUVID(s)) {

			throw new IllegalArgumentException("[UVID] Tried constructing an invalid Unique Vehicle ID (" + s + ")");
		}

		this.string = s;
	}

	@Override
	public boolean equals(Object obj) {

		return (obj instanceof UVID && this.string.equals(((UVID) obj).string));
	}

	@Override
	public int hashCode() {

		try {

			String s = this.string.replaceAll("-", "");
			int date = Integer.parseInt(s.substring(0, 7));
			int ran = Integer.parseInt(s.substring(15));

			return (date * ran);
		}
		catch (Throwable t) {

			return -1;
		}
	}

	/**
	 * Returns true if the passed in String validly represents a UVID.
	 */
	public static boolean isValidUVID(String s) {

		if (s.length() != 25) {

			return false;
		}

		try {

			char[] c = s.toCharArray();

			for (int i = 0; i < 25; ++i) {

				if (i == 10) {

					if (!ArrayUtils.contains(ENGINE_TYPES, c[i])) {

						return false;
					}
				}
				else if (i == 9 || i == 15) {

					if (!Character.isWhitespace(c[i])) {

						return false;
					}
				}
				else if (i >= 16 && i <= 18) {

					/*// Sigh.. We'll just accept whatever they throw at us..
					if (!Character.isLetterOrDigit(c[i])) {

						return false;
					}
					 */
				}
				else if (i == 3 || i == 6 || i == 11 || i == 19) {

					if ('-' != c[i]) {

						return false;
					}
				}
				else {

					if (!Character.isDigit(c[i])) {

						return false;
					}
				}
			}

			return true;
		}
		catch (Throwable t) {}

		return false;
	}

	@Override
	public String toString() {

		return this.string;
	}

	/**
	 * Write this UVID to the given NBTTagCompound.
	 */
	public void writeToNBT(NBTTagCompound nbt) {

		if (nbt != null) {

			nbt.setString(LABEL_NBT_STRING, this.string);
		}
	}
}