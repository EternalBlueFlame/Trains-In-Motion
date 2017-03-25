/*
 * Copyright © January 2017 Leshuwa Kaiheiwa
 * All rights reserved.
 * 
 * 
 * §0 COPYRIGHT OWNER PSEUDONYM AND AREA OF JURISDICTION
 * 
 * 1. The area of jurisdiction is the Federal Republic of Germany.
 * 
 * 2. The copyright owner reserves the right to hide his identity behind
 *  the given pseudonym as stated in §1.3 of the Council Directive 93/98/EWG
 *  of the council of the European Communities from 24th November 1993.
 * 
 * 
 * §1 DISCLAIMER OF LIABILITY
 * 
 * 1. This software is provided by the copyright owner and contributors
 *  "as is" and any express or implied warranties are expressly disclaimed.
 * 
 * 2. In no event shall the copyright owner or contributors or their relatives
 *  be liable for any damages, including, but not limited to, direct, indirect,
 *  physical, mental, or consequential damages, however caused, arising in any way
 *  out of the use of this software, even if advised of the possibility of such
 *  damage.
 * 
 * 
 * §2 CODEX OF FAIR USAGE
 * 
 * Any action executed by third parties that involve the project in any way is
 *  allowed, provided the following conditions are met:
 * 
 * 1. The idea, the process of creation, and the final result must not, under
 *  any circumstances, follow the intention of, or create a result that is, harming
 *  or discriminating against any entity, including, but not limited to, a single
 *  person, a group, or a country.
 * 
 * 2. If the suspicion or clear fact arises that the end-product breaks the
 *  condition described in §2.1, the author of the product remains responsible to
 *  remove all sources of harm originating from the product.
 * 
 * 3. The usage of this software, in parts or as a whole, is free to the end-user.
 * 
 * 4. Additional conditions for the packages 'worlddreamer' and 'assets' may apply.
 * 
 * 
 * §3 TERMS OF USAGE (ZoraNoTorakku Packages)
 * 
 * Any action executed by third parties that involve any works except for the package
 * 'worlddreamer' is allowed, provided the following conditions are met:
 * 
 * 1. All actions and intentions must not break the terms described in §2.
 * 
 * 2. Actions that are permitted without the author's knowledge are:
 *  2a Decompiling and viewing the source code for educational purposes;
 *  2b Usage of the API package 'zoranotorakku.api' for any purpose under previously
 *   mentioned conditions;
 *  2c Copying non-API code and files, modified or unmodified, provided that the used code
 *   is clearly marked as the copyright owner's property and that the usage of the code does
 *   not break the agreement described in §2.
 * 
 * 3. Actions that require written permission from the author:
 *  3a Re-uploading the project, partially or as a whole, with or without modification.
 * 
 * 4. Actions that are strictly prohibited:
 *  4a Any modification of the files found in the package 'worlddreamer'.
 * 
 * 
 * §4 APPLICATION OF THIS LICENSE
 * 
 * 1. The project covered by this license remains property of the copyright owner to the
 *  point of his death.
 *  1a Afterwards, the project is considered public good and shall be accessible and free
 *   to use for anyone who desires access, provided their works do not break with the terms
 *   described in §2.
 *  1b The copyright owner waives all copyrights on packages 'assets' and 'mods', as well as
 *   on files where credit is given to third party copyright owners. All rights belong to
 *   the rightful copyright owners.
 * 
 * 2. The death of the copyright owner renders §3 invalid; these terms shall be overridden
 *  by the terms of usage as described in §2.
 * 
 * 3. The package 'worlddreamer' must not be altered in any way and must remain part of
 *  the project. (Re-)distribution of said package is allowed following the terms and
 *  conditions of this license.
 */
package zoranodensha.api.vehicles;

import java.util.ArrayList;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Helper class that contains a number of repeatedly used methods.
 */
public abstract class AVehicleHelper {

	/**
	 * Return a Vector3f pointing at the common center of all IVehiclePart instances' translations in the given List.
	 */
	@SideOnly(Side.CLIENT)
	public static final Vector3f getCenter(ArrayList<IVehiclePart> list) {

		float[] f0;
		float[] f2 = new float[3];
		int i;
		int j = 0;

		for (IVehiclePart part : list) {

			f0 = part.getOffset();

			if (f0 != null) {

				j += 1;

				for (i = 0; i < f2.length; ++i) {

					f2[i] += f0[i];
				}
			}
		}

		float f = 1.0F / (float) j;

		for (i = 0; i < f2.length; ++i) {

			f2[i] *= f;
		}

		return new Vector3f(f2[0], f2[1], f2[2]);
	}

	/**
	 * Tries to find an {@link IVehiclePart} with the given id in the given {@link IEntityTrackBound}.
	 * 
	 * @return The IVehiclePart is successful, {@code null} otherwise.
	 */
	public static final IVehiclePart getPartFromID(IEntityTrackBound iEntityTrackBound, char id) {

		ArrayList<IVehiclePart> partsList = iEntityTrackBound.getPartsList();

		if (partsList != null) {

			for (int i = partsList.size() - 1; i >= 0; --i) {

				if (partsList.get(i).getID() == id) {

					return partsList.get(i);
				}
			}
		}

		return null;
	}

	/**
	 * Calculates the position of the given IVehiclePart from its AxisAlignedBoundingBox and returns it as Vec3.
	 * 
	 * @return The position of the part as Vec3.
	 */
	public static final Vec3 getPosition(IVehiclePart part) {

		AxisAlignedBB aabb = part.getBoundingBox();

		return Vec3.createVectorHelper(
				(aabb.minX + aabb.maxX) * 0.5D,
				(aabb.minY + aabb.maxY) * 0.5D,
				(aabb.minZ + aabb.maxZ) * 0.5D);
	}
}