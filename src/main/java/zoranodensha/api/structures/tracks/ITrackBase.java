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
package zoranodensha.api.structures.tracks;

import net.minecraft.entity.Entity;

/**
 * The interface that is implemented by all tracks of Zora no Densha.
 */
public interface ITrackBase {

	/**
	 * Return the direction instance this shape faces.
	 */
	EDirection getDirectionOfSection();

	/**
	 * Return the field of internally handled Objects at the given index.
	 */
	<T extends Object> T getField(int index, T defaultVal, Class<T> type);

	/**
	 * Return the field of internally handled Object at the given index without type checks or default return value.
	 */
	Object getFieldUnchecked(int index);

	/**
	 * Returns the corresponding instance name of the overall track.
	 */
	IRailwaySection getInstanceOfShape();

	/**
	 * Return the length of this overall track section.
	 */
	int getLengthOfSection();

	/**
	 * Returns the orientation this ITrackBase faces. Used as replacement for Minecraft's Metadata flag.<p>
	 * 
	 * 0 = East  (+X)<br>
	 * 1 = South (+Z)<br>
	 * 2 = West  (-X)<br>
	 * 3 = North (-Z)
	 */
	int getOrientation();

	/**
	 * Return the current path number.
	 */
	int getPath();

	/**
	 * Calculates and returns the next position of the given Entity on this track.<br>
	 * Also returns the rotation (yaw [about Y-axis], pitch [about Z-axis], and roll [about X-axis]) at the new position.
	 * 
	 * @param vehicle - The Entity whose position shall be calculated.
	 * @return The position and rotation of the vehicle, respectively. Index 0 = Position to apply; Index 1 = Rotation (yaw, pitch, roll).
	 */
	double[][] getPositionOnTrack(Entity vehicle);

	/**
	 * Tries to set the given Object into the given index of the track's internal field Map.
	 * Setting an index will override the field at the given index.
	 * 
	 * Returns true if the previous value was null.
	 */
	boolean setField(int index, Object param);

	/**
	 * Returns the state of this track segment, i.e. whether it is a track bed, has fastening, etc.
	 * See {@link ERailwayState} for all states.
	 */
	ERailwayState getStateOfShape();

	/**
	 * Sets the path variable of this track.
	 */
	void setPath(int path);

	/**
	 * Sets the state of the overall track.
	 */
	void setStateOfShape(ERailwayState state);
}