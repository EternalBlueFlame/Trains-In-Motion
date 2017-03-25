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

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zoranodensha.api.structures.tracks.EDirection;

/**
 * The interface that has to be extended in order to add content to the EngineerTable of Zora no Densha.
 */
public interface IEngineerTableExtension {

	/**
	 * Called to retrieve all ingredients for internal registration to the Engineer's Table registry.
	 */
	ArrayList<EngineerTableIngredientHelper> registerIngredients();

	/**
	 * Called to retrieve all recipes for internal registration to the Engineer's Table registry.
	 */
	ArrayList<EngineerTableRecipeHelper> registerRecipes();

	/**
	 * A helper class to pack ingredient data for registration at Zora no Densha.
	 */
	public final class EngineerTableIngredientHelper {

		/** The ItemStack instance to register. */
		public final ItemStack itemStack;

		/** The name of this ingredient. */
		public final String name;

		/** The page(s) where the ingredient is supposed to appear. */
		public final EEngineerTableTab[] types;

		/**
		 * Pack an Engineer's Table ingredient for registration at Zora no Densha.
		 * 
		 * @param itemStack - The ItemStack instance to register
		 * @param name - The name of the registered ingredient; Used to avoid multiple registration on a single ingredient
		 * @param types - The page(s) where the ingredient is supposed to appear
		 */
		public EngineerTableIngredientHelper(ItemStack itemStack, String type, EEngineerTableTab... types) {

			this.itemStack = itemStack;
			this.name = type;
			this.types = types;
		}

		/**
		 * Pack an Engineer's Table ingredient for registration at Zora no Densha.
		 * 
		 * @param item - The ItemStack instance to register
		 * @param types - The page(s) where the ingredient is supposed to appear
		 */
		public EngineerTableIngredientHelper(Item item, String type, EEngineerTableTab... types) {

			this.itemStack = new ItemStack(item, 0, 0);
			this.name = type;
			this.types = types;
		}
	}

	/**
	 * A helper class to pack EngineerTableRecipe data for registration at Zora no Densha.
	 */
	public final class EngineerTableRecipeHelper {

		/** The page where the ingredients appear at. */
		public final EEngineerTableTab type;

		/** The resulting IRailwaySection's name. */
		public final String result;

		/** The EDirection the resulting IRailwaySection will face. */
		public final EDirection dir;

		/** The recipe matrix. */
		public final String[] matrices;

		/** The Object declaration of the matrix' indices. */
		public final Object[] indices;

		/**
		 * Pack an EngineerTable recipe for registration at Zora no Densha.
		 * 
		 * @param type - The ingredient's {@link EEngineerTableTab}
		 * @param result - The name of the resulting {@link IRailwayInstance}
		 * @param dir - The {@link EDirection} the resulting IRailwaySection will face
		 * @param matrices - The recipe matrix; An array of Strings with five places each. Use like Minecraft's recipes. Fill blank places with whitespace (i.e. space bar)
		 * @param indices - The indices used in the matrix with their corresponding ItemStack representation
		 */
		public EngineerTableRecipeHelper(EEngineerTableTab type, String result, EDirection dir, String[] matrices, Object... indices) {

			this.type = type;
			this.result = result;
			this.dir = dir;
			this.matrices = matrices;
			this.indices = indices;
		}
	}
}