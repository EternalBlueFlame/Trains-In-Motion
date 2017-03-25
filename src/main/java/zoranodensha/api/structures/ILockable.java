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
package zoranodensha.api.structures;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;

/**
 * A generic interface implemented by objects if they can be locked by one or more users.
 */
public interface ILockable {

	/**
	 * Returns true if this block is locked, e.g. if the corresponding field is not empty and/ or if the given username(s) do not correspond to the registered ones.
	 * 
	 * @return False if this ILockable can be accessed by the given username(s).
	 */
	boolean getIsLocked(String... usernames);

	/**
	 * Return all owners' usernames.
	 */
	String[] getLockedBy();

	/**
	 * Add/ remove the given username to the list of owners.
	 * It is highly recommended to add a call to refresh the chunk to the method, such as "markDirty()" and/ or "markBlockForUpdate(...)".
	 */
	void setLockedBy(String username, boolean remove);


	/**
	 * Static helper class to use in connection with interface {@link ILockable}.
	 */
	public static class LockableHelper {

		public static boolean getIsLocked(String[] lockedBy, String... usernames) {

			if (lockedBy == null || lockedBy.length == 0) {

				return false;
			}
			else if (usernames == null || usernames.length == 0) {

				return true;
			}
			else {

				int k = 0;

				for (int i = 0; i < lockedBy.length; ++i) {

					label0:
						for (int j = 0; j < usernames.length; ++j) {

							if (lockedBy[i].equals(usernames[j])) {

								++k;
								break label0;
							}
						}
				}

				return !(k == usernames.length);
			}
		}

		/**
		 * Reads all owners from the given NBTTagCompound and returns them in a String array.
		 */
		public static String[] readFromNBT(NBTTagCompound nbt) {

			ArrayList<String> list = new ArrayList<String>();
			int i = 0;
			boolean doLoop = true;
			String s;

			do {

				s = nbt.getString("Owner_" + i);
				++i;

				if (s != null && s.length() > 0) {

					list.add(s);
				}
				else {

					doLoop = false;
				}
			}
			while (doLoop);

			return list.toArray(new String[0]);
		}

		/**
		 * Adds/ removes the given String username to/ from the given String array lockedBy.
		 */
		public static String[] setLockedBy(String[] lockedBy, String username, boolean remove) {

			String[] s;

			if (remove) {

				s = new String[lockedBy.length - 1];

				for (int i = 0, j = 0; i < lockedBy.length; ++i) {

					if (!lockedBy[i].equals(username)) {

						s[j] = lockedBy[i];
						++j;
					}
				}
			}
			else {

				s = new String[lockedBy.length + 1];
				s[0] = username;

				for (int i = 0; i < lockedBy.length; ++i) {

					s[i + 1] = lockedBy[i];
				}
			}

			return s;
		}

		/**
		 * Write the given String array of usernames to the given NBTTagCompound.
		 */
		public static void writeToNBT(NBTTagCompound nbt, String[] lockedBy) {

			for (int i = 0; i < lockedBy.length; ++i) {

				nbt.setString("Owner_" + i, lockedBy[i]);
			}
		}
	}
}