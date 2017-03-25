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

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import zoranodensha.api.vehicles.vehicleParts.IVehiclePartBogie;

/**
 * An interface extended by all vehicles of Zora no Densha.<p>
 * 
 * A note on terminology:<br>
 * <b>Vehicle:</b> An IEntityTrackBound instance containing at least one IVehiclePart instance (= vehicle part). Each vehicle consists of at least one IEntityTrackBoundSection instance (= section). A moving vehicle contains at least one IVehiclePartBogie instance (= bogie).<br>
 * <b>Section:</b> An IEntityTrackBoundSection instance holding a varying amount of vehicle parts. Sections are calculated from position and amount of bogies in a vehicle.<br>
 * <b>Section Center:</b> The center between two bogies. If only a single bogie is existent, rotation and position are defined by the single bogie.<p>
 * 
 * While a vehicle computes and stores motion, it is the task of each separate bogie to turn motion into real velocity (= movement).
 * The vehicle's position must not be seen as a pivot point or the like; each section has its own rotation and world position that depend on the position and rotation of its bogie(s).
 */
public interface IEntityTrackBound {

	/** Key for maximum speed. Usually initialised after initial placement. */
	String NBT_KEY_MAXSPEED = "MaxSpeed";

	/** Commonly used String key for the name of this vehicle. */
	String NBT_KEY_NAME = "VehicleName";

	/** Key for the List of IVehiclePart instances of this vehicle. */
	String NBT_KEY_PARTSLIST = "VehicleParts";

	/** Key for weight. Usually initialised after initial placement. */
	String NBT_KEY_WEIGHT = "Weight";

	/** Coefficient of restitution, used when calculating motion of entities colliding with this vehicle. */
	float COEFFICIENT_RESTITUTION = 0.95F;

	/** A multiplier for deceleration. Higher values result in sharper braking and shorter brake distances. */
	float DECELERATION_MULTIPLIER = 0.01F;

	/** A multiplier for friction. Higher values result in greater friction, and thus a higher requirement in force to get a vehicle moving. */
	float FRICTION_MULTIPLIER = 0.0005F;



	/**
	 * Called to "push" this IEntityTrackBound with the given force, in Kilonewtons (kN) in a given local direction. This is called by external forces and won't be called during update routines by coupled vehicles of the same train.
	 * By default, the calculated force is then added to a buffer that will be cleared and used each update tick.
	 * 
	 * @param force - The force (in kN) to push this vehicle with.
	 * @param dir - The direction to push this vehicle in. See {@link #getAccelerationInverseState()} for valid directions. If the passed in direction opposes this vehicle's direction, the force will slow this vehicle.
	 */
	void addPushForce(float force, int dir);

	/**
	 * Called in several cases to apply full brake power to stop the vehicle/ train as quickly as possible.<p>
	 * 
	 * Some cases are:<br>
	 * 1. Signal Tower enforced immediate stop (Enforced if the signalling system encounters an error - this should never actually happen)<br>
	 * 2. InduSi alert; the driver might have missed or overrun a signal<br>
	 * 3. SiFa alert; the driver failed to notify the vehicle of alertness.<br>
	 * 4. Emergency button pushed by driver
	 */
	void applyEmergencyBrake();

	/**
	 * Apply an {@link IVehiclePart} instance to this IEntityTrackBound instance.
	 * Return true if successful.
	 */
	boolean applyPart(IVehiclePart part);

	/**
	 * Return the List of all {@link IVehiclePartBogie} instances this vehicle has.
	 */
	ArrayList<IVehiclePartBogie> getBogies();

	/**
	 * Return the level of brake power that this vehicle applies.
	 * 
	 * @return The applied percentage of brake power, 0.0F meaning no brake being applied, 1.0F meaning full brake force being applied.
	 */
	float getBrakeLevel();

	/**
	 * Return an ArrayList of all vehicles coupled to this one.
	 */
	<T extends Entity & IEntityTrackBound> ArrayList<T> getCoupled();

	/**
	 * Return an ArrayList of all vehicles coupled to this one, also considering vehicles coupled to following vehicles.
	 * 
	 * @param list - An ArrayList to append the vehicle(s) to, if the vehicle(s) coupled to this one is/ are <b>not</b> already included. Instantiate a new ArrayList if {@code null}.
	 */
	<T extends Entity & IEntityTrackBound> ArrayList<T> getCoupledCascading(@Nullable ArrayList<T> list);

	/**
	 * Return the maximum speed [km/h] this vehicle is capable of driving.
	 */
	int getMaxSpeed();

	/**
	 * Return the outmost point of this IEntityTrackBound as a {@link Vec3}.
	 */
	Vec3 getOutmostPoint();

	/**
	 * Return the List of all {@link IVehiclePart} instances this vehicle consists of.
	 */
	ArrayList<IVehiclePart> getPartsList();

	/**
	 * Return all push force (in Kilonewtons, kN) applied to this vehicle in the previous tick.<br>
	 * <b>Reset push force back to zero after call.</b>
	 */
	float getPushForce();

	/**
	 * Return the section(s) this vehicle consists of.
	 */
	IEntityTrackBoundSection[] getSections();

	/**
	 * Return the speed of this vehicle in meters per second (m/s; 1 m/s = 3.6 km/h).
	 */
	float getSpeed();

	/**
	 * Return the {@link UVID} of this IEntityTrackBound.
	 */
	UVID getUVID();

	/**
	 * Return the weight (in Tonnes; 1t = 1000kg) of the overall vehicle.
	 * Recalculate the weight each time this method is called, as way of updating weight.
	 */
	float getWeight();

	/**
	 * Return true if the EntityTracker that tracks this IEntityTrackBound entity has changed since the last update call.
	 * 
	 * @return True if parts should send their data to all tracking clients.
	 */
	boolean hasEntityTrackerChanged();

	/**
	 * Called by update mechanism to update the position and rotation of this vehicle's parts, according to previously calculated bogie positions.
	 */
	void onPositionUpdate();

	/**
	 * Called to remove the IVehiclePart of given partID from this IEntityTrackBound.
	 * 
	 * @return True if successful.
	 */
	boolean removePart(char partID);

	/**
	 * Called by structures (such as signals, Railway Boxes, Signal Towers, and the like) to request confirmation of the driver, usually to verify the sight of a signal or some important sign.
	 */
	void requestDriverConfirmation();

	/**
	 * Called by the first vehicle of a train whose {@link net.minecraft.entity.Entity#onUpdate()} method was called, to notify all coupled trains to cancel train updates in for the current tick.
	 */
	void setHasUpdated();

	/**
	 * Called to set the new speed limit of this IEntityTrackBound instance and all coupled vehicles.
	 * 
	 * @param maxSpeed - The new speed limit, in km/h. Set to -1 to recalculate the vehicle's maximum speed depending on its respective parts.
	 */
	void setMaxSpeed(int maxSpeed);

	/**
	 * Called by IVehiclePart instances so this IEntityTrackBound instance can refresh its part-dependent values.<br>
	 * Used e.g. by cabs for button and state updates.
	 */
	void updatePartData(IVehiclePart part);
}