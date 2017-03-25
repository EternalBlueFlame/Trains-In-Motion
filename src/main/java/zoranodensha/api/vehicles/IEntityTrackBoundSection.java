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

import java.util.ArrayList;

import net.minecraft.util.Vec3;

/**
 * Every IEntityTrackBound instance has at least one section. Vehicles with more than two bogies have several sections where each section's rotation is handled individually.<br>
 * Furthermore, each section has its own rotation, calculated from the average of its bogie(s).<p>
 * 
 * A vehicle with..<br>
 * ..1 bogie has 1 section.<br>
 * ..2 bogies has 1 section.<br>
 * ..3 bogies has 2 sections.<br>
 * ..4 bogies has 3 sections.<br>
 * ...
 */
public interface IEntityTrackBoundSection {

	/**
	 * Return a Vec3 vector originating from this section's in-world position, pointing to the front of the section.<br>
	 * Most of all important to IVehiclePartBogie instances to define direction of motion. Bogies that belong to several sections have to interpolate their front vector.
	 * 
	 * @return The Vec3 vector pointing to the section's front, or {@code null} if undefined.
	 */
	Vec3 getFrontVector();

	/**
	 * Return the local center, on X, Y and Z.
	 */
	float[] getLocalCenter();

	/**
	 * Return the global X-, Y- and Z-position.
	 */
	double[] getPosition();

	/**
	 * Return the global X-, Y- and Z-position during the previous tick.
	 */
	double[] getPrevPosition();

	/**
	 * Return the rotation (about X-axis (roll), Y-axis (yaw), and Z-axis (pitch)) of this section during the previous tick.
	 */
	float[] getPrevRotation();

	/**
	 * Return the rotation (about X-axis (roll), Y-axis (yaw), and Z-axis (pitch)) of this section.
	 */
	float[] getRotation();

	/**
	 * Return all parts associated with this section, except for the bogies.
	 */
	ArrayList<IVehiclePart> getSectionParts();

	/**
	 * Called to set the "front" vector of this section.
	 * 
	 * @param vecFront - The new Vec3 instance to set.
	 */
	void setFrontVector(Vec3 vecFront);

	/**
	 * Set the global X, Y, and Z position.
	 */
	void setPosition(double... positionArray);

	/**
	 * Set the local roll, yaw, and pitch rotation (rotation about X-, Y-, and Z-axis, respectively).
	 */
	void setRotation(float... rotationArray);

	/**
	 * Called to set all parts associated with this section.
	 */
	void setSectionParts(ArrayList<IVehiclePart> parts);
}