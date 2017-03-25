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
package zoranodensha.api.vehicles.vehicleParts;

import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;



/**
 * Interface implemented by vehicle parts that can be colored.
 */
public interface IVehiclePartColorable {

	/* No methods yet. */

	/**
	 * Helper class to handle RGB (Red-Green-Blue) colors.
	 */
	public static class ColorRGB {

		/** Red color component, ranging from 0.0F to 1.0F. */
		float r;
		/** Green color component, ranging from 0.0F to 1.0F. */
		float g;
		/** Blue color component, ranging from 0.0F to 1.0F. */
		float b;

		/** Key index used by packet handling. */
		final int key;

		/**
		 * Instantiate a default white color.
		 */
		public ColorRGB(int key) {

			this(1.0F, 1.0F, 1.0F, key);
		}

		/**
		 * Instantiate a color of given red, green, blue components with the given packet handling key.
		 */
		public ColorRGB(float r, float g, float b, int key) {

			this.r = r;
			this.g = g;
			this.b = b;
			this.key = key;
		}

		/**
		 * Client-side method to apply this color using OpenGL's {@link GL11#glColor4f(float, float, float, float)} method.
		 */
		@SideOnly(Side.CLIENT)
		public void applyGLColor(float alpha) {

			GL11.glColor4f(this.r, this.g, this.b, alpha);
		}

		/**
		 * Encodes the given red, green and blue color components into HEX color space.
		 */
		public static int encodeRGBtoHEX(float r, float g, float b) {

			return (((int) (r * 255F + 0.5F) & 0xFF) << 16) |
					(((int) (g * 255F + 0.5F) & 0xFF) << 8) |
					(((int) (b * 255F + 0.5F) & 0xFF) << 0);
		}

		/**
		 * Encodes the given HEX color space into RGB color components and returns them as 3D float array.
		 */
		public static float[] encodeHEXtoRGB(int col) {

			return new float[] {
					(float) (col >> 16 & 0xFF) / 255.0F,
					(float) (col >> 8  & 0xFF) / 255.0F,
					(float) (col >> 0  & 0xFF) / 255.0F
			};
		}

		/**
		 * Called (usually by {@link zoranodensha.api.vehicles.IVehiclePart#getProperty(int, Class, Object)}) to check whether this ColorRGB instance's key suits. If so, return the corresponding value.
		 * 
		 * @return The corresponding value if the key suits this instance, or {@code null} otherwise.
		 */
		public <T extends Object> T getProperty(int key, Class<T> type, T defReturn) {

			if (key == this.key) {

				int colInt = this.toHEX();
				String colStr = Integer.toHexString(colInt).toUpperCase();

				if (colStr.length() < 6) {

					colStr = "000000".substring(colStr.length()) + colStr;
				}

				colStr = "#" + colStr;

				if (type.isInstance(colStr)) {

					return type.cast(colStr);
				}
				else if (type.isInstance(colInt)) {

					return type.cast(colInt);
				}

				return defReturn;
			}

			return null;
		}

		/**
		 * Read new RGB color date from the given NBTTagCompound with the given String key. Re-uses this instance's RGB and HEX keys.
		 */
		public void readFromNBT(NBTTagCompound nbt, String key) {

			if (nbt.hasKey(key + "_0")) {

				this.r = (nbt.hasKey(key + "_0") ? nbt.getFloat(key + "_0") : 1.0F);
				this.g = (nbt.hasKey(key + "_1") ? nbt.getFloat(key + "_1") : 1.0F);
				this.b = (nbt.hasKey(key + "_2") ? nbt.getFloat(key + "_2") : 1.0F);
			}
			else {

				float[] arr = encodeHEXtoRGB(nbt.getInteger(key));
				this.r = arr[0];
				this.g = arr[1];
				this.b = arr[2];
			}
		}

		/**
		 * Set the color of this ColorRGB instance.
		 */
		public void setColor(float r, float g, float b) {

			this.r = r;
			this.g = g;
			this.b = b;
		}

		/**
		 * Called (usually by {@link zoranodensha.api.vehicles.IVehiclePart#setProperty(int, Object)}) to check whether this ColorRGB instance's key suits. If so, act accordingly.
		 * 
		 * @return True if the given key suited this instance.
		 */
		public boolean setProperty(int key, Object property) {

			if (key != this.key) {

				return false;
			}

			int colInt = 0;
			boolean apply = false;

			if (property instanceof String) {

				String colStr = ((String) property).toLowerCase();

				if (colStr.length() > 0) {

					if (!colStr.startsWith("#") && !colStr.startsWith("0x")) {

						colStr = "#" + colStr;
					}

					try {

						colInt = Integer.decode(colStr);
						apply = true;
					}
					catch (NumberFormatException ex) {}
				}
			}
			else if (property instanceof Integer) {

				colInt = (Integer) property;
				apply = true;
			}

			if (apply) {

				float[] arr = encodeHEXtoRGB(colInt);

				this.r = arr[0];
				this.g = arr[1];
				this.b = arr[2];
			}

			return true;
		}

		/**
		 * Encode this ColorRGB into HEX color space.
		 */
		public int toHEX() {

			return encodeRGBtoHEX(this.r, this.g, this.b);
		}

		/**
		 * Write this ColorRGB instance to the given NBTTagCompound with the given String key.
		 */
		public void writeToNBT(NBTTagCompound nbt, String key) {

			nbt.setInteger(key, this.toHEX());
		}
	}
}