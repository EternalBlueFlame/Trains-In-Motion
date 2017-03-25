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
package zoranodensha.api.vehicles.vehicleParts;

import net.minecraft.entity.Entity;
import zoranodensha.api.vehicles.IEntityTrackBound;
import zoranodensha.api.vehicles.IEntityTrackBoundSection;
import zoranodensha.api.vehicles.IVehiclePart;

/**
 * An object that guides the vehicle on a track - be it a bogie of one or several axles, or a magnet of a Maglev.<br>
 * Vehicle parts of this type should handle movement and position updates. They have to ensure their world position is correctly written to/ read from NBT, too.
 */
public interface IVehiclePartBogie extends IVehiclePart, Comparable<IVehiclePartBogie> {

	/**
	 * Return this bogie's rotation (roll (rotation about X-axis), yaw (rotation about Y-axis), and pitch (rotation about Z-axis)).
	 */
	float[] getBogieRotation();

	/**
	 * Return the maximum deceleration (in m/s²) depending on the given weight exerted onto this bogie.<p>
	 * 
	 * For reference:<br>
	 * Bogies of the BR 101 return for a weight of 84t (=84.000 kg) a deceleration of 0.9645 m/s² per bogie.<br>
	 * The BR 101 thus has a brake distance of about 200m at 100km/h, without any coupled vehicles (see below).<p>
	 * 
	 * (Velocity² [m²/s²]) / (2 * deceleration [m/s²]) = Brake distance [m]<p>
	 * 
	 * (100 / 3.6)² / 2 * (2 * (0.9645 * (42.0 / 42.0))) = 200m
	 * 
	 * @param weight - The weight exerted onto this bogie
	 * @param percentage - The amount of brake force to apply. 0.0F meaning no brake force, 1.0F meaning full brake force.
	 * @return The maximum deceleration of this bogie, or 0.0F if this bogie has no brakes
	 */
	float getDeceleration(float weight, float percentage);

	/**
	 * Return true if this bogie is a dummy and thus shouldn't influence path-finding mechanism.
	 */
	boolean getIsDummy();

	/**
	 * Return the maximum speed (in km/h) this bogie can handle.
	 */
	int getMaxSpeed();

	/**
	 * Return the motion of this bogie (on X-, Y-, and Z-axis). Might be {@code null}.
	 */
	double[] getMotion();

	/**
	 * Return this bogie's position in the world (on X-, Y-, and Z-axis). Might be {@code null}.
	 */
	double[] getWorldPosition();

	/**
	 * Return this bogie's position in the world (on X-, Y-, and Z-axis) during the last tick.
	 */
	double[] getWorldPrevPosition();

	/**
	 * Return true if this bogie has collided with a heavy object (and thus was rapidly stopped). Living entities are not considered "heavy objects".
	 */
	boolean hasCollided();

	/**
	 * Return true if this bogie has derailed.
	 */
	boolean hasDerailed();

	/**
	 * Return true while the bogie is on ground, false while in the air.
	 */
	boolean isOnGround();

	/**
	 * Called by the given IEntityTrackBoundSection upon its instantiation to add itself to this bogie.
	 * This step is required in order for a bogie to be able to retrieve the front vector(s) of the section(s) it is associated to.
	 * Please note that this method might be called by up to two different sections. If this happens, the implementation has to
	 * interpolate the front vector of each section to retrieve the correct front vector of this bogie.
	 */
	void addToSection(IEntityTrackBoundSection section);

	/**
	 * Called by vehicle update logics to move this bogie with a given speed.
	 * 
	 * @param speed - The speed to move with, in meters per second [m/s]. Convert to meters per tick [m/t] before applying.
	 * @param inverse - True if movement should oppose the front bogie's front vector (e.g. when going backwards).
	 */
	<T extends Entity & IEntityTrackBound> void moveBogie(float speed, boolean inverse);

	/**
	 * Set the bogie's motion on X-, Y-, and Z-axis.
	 */
	void setBogieMotion(double motionX, double motionY, double motionZ);

	/**
	 * Set the bogie's rotation (roll (rotation about X-axis), yaw (rotation about Y-axis), and pitch (rotation about Z-axis)).
	 */
	void setBogieRotation(float... values);

	/**
	 * Set the bogie's position in the world (on X-, Y-, and Z-axis).
	 */
	void setWorldPosition(double... values);



	public static class CompareHelper {

		/**
		 * Compares the given IVehiclePartBogie bogie to the given IVehiclePartBogie other and returns an int as defined by {@link Comparable#compareTo(Object)}.
		 */
		public static int compareTo(IVehiclePartBogie thisBogie, IVehiclePartBogie otherBogie) {

			float[] f = thisBogie.getOffset();

			if (f != null) {

				float thisX = f[0];
				f = otherBogie.getOffset();

				return (f != null ? Float.compare(thisX, f[0]) : 1);
			}

			return -1;
		}
	}
}