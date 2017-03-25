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

import net.minecraft.nbt.NBTTagCompound;

/**
 * This class contains methods to handle paths as used in Zora no Densha.
 * A Path is a graph (defined by an {@link Expression}) that operates solely on local (i.e. relative) coordinates.
 */
public final class Path {

	final Expression[] paths = new Expression[6];

	/**
	 * Construct a new Path from the given argument arrays. May throw an exception if fed incorrect parameters.
	 * 
	 * @param pathFlat - A mathematical expression of this Path on flat (x/z) level in a String; This argument must not be null
	 * @param pathGrade - A mathematical expression of this Path's grade (x/y) in a String
	 * @param pathTilt - A mathematical expression of this Path's tilt (y/z) in a String
	 * @throws IllegalArgumentException If the flat path is invalid or null.
	 */
	public Path(String[] pathFlat, String[] pathGrade, String[] pathTilt) {

		if (pathFlat == null || pathFlat.length != 2) {

			throw new IllegalArgumentException("[ModTrackRegistry] A mod tried to register a Path of invalid expression.");
		}

		this.paths[0] = new Expression(pathFlat[0]);
		this.paths[1] = new Expression(pathFlat[1]);
		this.paths[2] = (pathGrade != null ? new Expression(pathGrade[0]) : null);
		this.paths[3] = (pathGrade != null ? new Expression(pathGrade[1]) : null);
		this.paths[4] = (pathTilt != null ? new Expression(pathTilt[0]) : null);
		this.paths[5] = (pathTilt != null ? new Expression(pathTilt[1]) : null);
	}

	/**
	 * Calculates and returns the local coordinates for the given local coordinate.
	 * 
	 * @param localX - The local X-coordinate
	 * @return The local (X-, Y- and Z-) coordinates
	 */
	public double[] calculatePosition(double localX) {

		double d0 = this.paths[0].value(localX);
		double d1 = 0.0D;
		double d2 = 0.0D;

		if (this.paths[2] != null) {

			d1 = this.paths[2].value(localX);
		}

		if (this.paths[4] != null) {

			d2 = this.paths[4].value(localX);
		}

		return new double[] {d0, d1, d2};
	}

	/**
	 * Calculates and returns the rotations for the given local coordinate.<br>
	 * <b>Note the different order of rotation axes in the returned array.</b>
	 * 
	 * @param localX - The local X-coordinate
	 * @return The array of rotations [Rotation about Y-, Z- and X-axis, respectively]
	 */
	public double[] calculateRotation(double localX) {

		double d0 = this.paths[1].value(localX);
		double d1 = 0.0D;
		double d2 = 0.0D;

		if (this.paths[3] != null) {

			d1 = this.paths[3].value(localX);
		}

		if (this.paths[5] != null) {

			d2 = this.paths[5].value(localX);
		}

		return new double[] {d0, d1, d2};
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof Path) {

			Path path = (Path) obj;

			for (int i = 0; i < this.paths.length; ++i) {

				if (this.paths[i] != null && path.paths[i] == null)  {

					return false;
				}

				if (this.paths[i] == null && path.paths[i] != null) {

					return false;
				}

				if (!this.paths[i].toString().equals(path.paths[i].toString())) {

					return false;
				}
			}

			return true;
		}

		return super.equals(obj);
	}

	@Override
	public int hashCode() {

		return ((this.paths[0] != null ? 1 : 0) +
				(this.paths[1] != null ? 2 : 0) +
				(this.paths[2] != null ? 4 : 0) +
				(this.paths[3] != null ? 8 : 0) +
				(this.paths[4] != null ? 16 : 0) +
				(this.paths[5] != null ? 32 : 0));
	}

	/**
	 * Reads a Path from NBT and returns a new Path instance.
	 */
	public static Path readFromNBT(NBTTagCompound nbt) {

		String[] path0 = new String[] {
				nbt.getString("Path_0"),
				nbt.getString("Path_1")
		};

		String[] path1 = (nbt.hasKey("Path_2") ? new String[] {
			nbt.getString("Path_2"),
			nbt.getString("Path_3")
		} : null);

		String[] path2 = (nbt.hasKey("Path_4") ? new String[] {
			nbt.getString("Path_4"),
			nbt.getString("Path_5")
		} : null);

		return new Path(path0, path1, path2);
	}

	/**
	 * Writes this Path to NBT and returns the passed NBTTagCompound again.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		for (int i = 0; i < this.paths.length; i += 2) {

			if (this.paths[i] != null && this.paths[i + 1] != null) {

				nbt.setString("Path_" + i, this.paths[i].toString());
				nbt.setString("Path_" + (i + 1), this.paths[i + 1].toString());
			}
		}

		return nbt;
	}
}