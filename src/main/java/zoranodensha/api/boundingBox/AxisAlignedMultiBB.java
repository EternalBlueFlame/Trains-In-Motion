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
package zoranodensha.api.boundingBox;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * This bounding box type can handle multiple AxisAlignedBB instances at once, thus allowing multiple bounding boxes per Entity.
 * The value is set via Reflection, due to the boundingBox field in Entity.class being declared as final.
 */
public class AxisAlignedMultiBB extends AxisAlignedBB {

	/** An ArrayList of all IMultiBoundingBoxPart instances that are part of this AxisAlignedMultiBB instance. */
	public final ArrayList<IMultiBoundingBoxPart> boundingBoxParts = new ArrayList<IMultiBoundingBoxPart>();

	/**
	 * Private constructor to initialise the AAMBB.
	 */
	private <T extends IMultiBoundingBoxPart> AxisAlignedMultiBB(AxisAlignedBB defaultBB, List<T> boundingBoxParts) {

		super(defaultBB.minX, defaultBB.minY, defaultBB.minZ, defaultBB.maxX, defaultBB.maxY, defaultBB.maxZ);

		this.boundingBoxParts.addAll(boundingBoxParts);
	}

	/**
	 * Construct a new AAMBB instance from the given List of parts. Automatically calculates and applies a surrounding AABB.
	 */
	public <T extends IMultiBoundingBoxPart> AxisAlignedMultiBB(List<T> boundingBoxParts) {

		this(getSurroundingAABB(boundingBoxParts), boundingBoxParts);
	}

	@Override
	public MovingObjectPosition calculateIntercept(Vec3 vecPos, Vec3 vecLook) {

		MovingObjectPosition movObjPos;

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			if ((movObjPos = part.getBoundingBox().calculateIntercept(vecPos, vecLook)) != null) {

				return movObjPos;
			}
		}

		return null;
	}

	@Override
	public double calculateXOffset(AxisAlignedBB aabb, double offset) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			offset = part.getBoundingBox().calculateXOffset(aabb, offset);
		}

		return offset;
	}

	@Override
	public double calculateYOffset(AxisAlignedBB aabb, double offset) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			offset = part.getBoundingBox().calculateYOffset(aabb, offset);
		}

		return offset;
	}

	@Override
	public double calculateZOffset(AxisAlignedBB aabb, double offset) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			offset = part.getBoundingBox().calculateZOffset(aabb, offset);
		}

		return offset;
	}

	@Override
	public AxisAlignedBB copy() {

		ArrayList<IMultiBoundingBoxPart> list = new ArrayList<IMultiBoundingBoxPart>();

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			list.add(part);
		}

		return new AxisAlignedMultiBB(AxisAlignedBB.getBoundingBox(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ), list);
	}

	@Override
	public AxisAlignedBB getOffsetBoundingBox(double x, double y, double z) {

		return (new AxisAlignedMultiBB(super.getOffsetBoundingBox(0.0D, 0.0D, 0.0D), this.boundingBoxParts)).offset(x, y, z);
	}

	/**
	 * Calculates and returns an AxisAlignedBB instance that encapsulates all bounding box parts of the given List.
	 */
	public static <T extends IMultiBoundingBoxPart> AxisAlignedBB getSurroundingAABB(List<T> boundingBoxParts) {

		AxisAlignedBB aabb;
		double minX = 0.0D;
		double minY = 0.0D;
		double minZ = 0.0D;
		double maxX = 0.0D;
		double maxY = 0.0D;
		double maxZ = 0.0D;

		for (T part : boundingBoxParts) {

			if (!part.containInParentBox()) {

				continue;
			}

			aabb = part.getBoundingBox();

			if (minX == 0.0D || aabb.minX < minX) {

				minX = aabb.minX;
			}

			if (minY == 0.0D || aabb.minY < minY) {

				minY = aabb.minY;
			}

			if (minZ == 0.0D || aabb.minZ < minZ) {

				minZ = aabb.minZ;
			}

			if (maxX == 0.0D || aabb.maxX > maxX) {

				maxX = aabb.maxX;
			}

			if (maxY == 0.0D || aabb.maxY > maxY) {

				maxY = aabb.maxY;
			}

			if (maxZ == 0.0D || aabb.maxZ > maxZ) {

				maxZ = aabb.maxZ;
			}
		}

		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	@Override
	public boolean intersectsWith(AxisAlignedBB aabb) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			if (part.getBoundingBox().intersectsWith(aabb)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isVecInside(Vec3 vec3) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			if (part.getBoundingBox().isVecInside(vec3)) {

				return true;
			}
		}

		return false;
	}

	@Override
	public AxisAlignedBB offset(double x, double y, double z) {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			part.getBoundingBox().offset(x, y, z);
		}

		return super.offset(x, y, z);
	}

	/**
	 * Render all parts of this AAMBB, except for the encapsulating box.
	 */
	@SideOnly(Side.CLIENT)
	public void render() {

		for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

			RenderGlobal.drawOutlinedBoundingBox(part.getBoundingBox().copy(), 16777215);
		}
	}

	/**
	 * Set the given AxisAlignedBB in the given Entity's boundingBox field.
	 * 
	 * @return True if successful.
	 */
	public static final boolean setAAMBB(Entity entity, AxisAlignedBB aabb) {

		Field boundingBox = null;

		try {

			if ((boundingBox = Entity.class.getDeclaredField(boundingBox.toString())) != null) {

				boundingBox.setAccessible(true);
				boundingBox.set(entity, aabb);

				return true;
			}
		}
		catch (Throwable t) {}

		return false;
	}

	@Override
	public String toString() {

		String s = "AxisAlignedMultiBB (" + (this.boundingBoxParts != null ? this.boundingBoxParts.size() : 0) + ") {";

		if (this.boundingBoxParts != null) {

			for (IMultiBoundingBoxPart part : this.boundingBoxParts) {

				s += System.lineSeparator() + "\t[" + (part != null ? (part.toString() + " | " + (part.getBoundingBox() != null ? part.getBoundingBox() : "null")) : "null") + "]";
			}
		}
		else {

			s += System.lineSeparator() + "null";
		}

		s += System.lineSeparator() + "}";

		return s;
	}
}