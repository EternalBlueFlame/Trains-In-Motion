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

import zoranodensha.api.vehicles.ASendsPartUpdates;


/**
 * A seat with controls for a train driver.
 * 
 * @ASendsPartUpdates
 * Updates vehicle brake and acceleration data, door and light states, SiFa and InduSi states, as well as pantograph and emergency brake states. Also sets the speed limit.<br>
 * Depending on what's implemented, further data can be exchanged.
 */
@ASendsPartUpdates
public interface IVehiclePartCab extends IVehiclePartSeat {

	/**
	 * Return the level of acceleration (>0) or brake (<0). 0 indicates neutral level.
	 */
	char getAcceleration();

	/**
	 * Return whether acceleration is inverse.<p>
	 * 
	 * <b>IMPORTANT</b><br>
	 * Before a cab may invert acceleration, it has to ensure that the vehicle which
	 * acceleration is inverted is not moving, or else weird things will happen.
	 */
	boolean getAccelerationInverse();

	/**
	 * Return the percentage of brake force to apply.
	 * 
	 * @return The percentage of brake force, between 0.0F (brakes loose) and 1.0F (apply full brake force).
	 */
	float getBrakeLevel();

	/**
	 * Returns which side of doors are to be opened.
	 * 
	 * @return 0 - None, 1 - Left, 2 - Right, 3 - All
	 */
	char getDoorsOpen();

	/**
	 * Return the total world time (in ms, milliseconds) when this cab was last ridden. Said value must:<br>
	 * - be set <b>once</b> per rider change, and
	 * - be set <b>only</b> if the rider was allowed to ride/ interact with this cab.
	 * 
	 * @return The time in ms when this cab was last ridden, or 0L if it has never been ridden.
	 */
	long getLastRiddenTime();

	/**
	 * Returns the state of the vehicle's lights.
	 * 
	 * @return 0 - All off, 1 - Front Yellow/ Back Red, 2 - Front Red/ Back Yellow
	 */
	char getLights();

	/**
	 * Returns which side of pantographs are to up.
	 * 
	 * @return 0 - None, 1 - Front, 2 - Back, 3 - All
	 */
	char getPantographsUp();

	/**
	 * Calculate the maximum speed that can be achieved in the current gear.
	 * 
	 * @return The maximum speed in the current gear (in m/s).
	 */
	float getSpeedLimit();

	/**
	 * Return true if the emergency button is down.
	 */
	boolean isEmergencyButtonDown();

	/**
	 * Return true if the InduSi timer should be reset, confirming that the driver noticed a signal/ etc.
	 */
	boolean shouldRefreshInduSi();

	/**
	 * Return true if the SiFa (dead man's switch) timer should be reset, indicating the driver is present.
	 */
	boolean shouldRefreshSiFa();
}