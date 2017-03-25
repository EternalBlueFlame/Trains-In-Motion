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

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import zoranodensha.api.boundingBox.IMultiBoundingBoxPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The basic vehicle part without further specialization.<br>
 * Custom vehicle parts should <b>implement</b> this interface, while custom vehicle part types should <b>extend</b> this interface.
 * 
 * @param P - Type parameter. Defines this IVehiclePart's type in order to match with the renderer class' type parameter.
 */
public interface IVehiclePart extends IMultiBoundingBoxPart {

	/**
	 * Called by ItemVehiclePart for custom vehicle part Item tool tips.
	 * For parameters see: {@link net.minecraft.item.Item#addInformation(ItemStack, EntityPlayer, List, boolean)}
	 */
	@SuppressWarnings("rawtypes")
	void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advancedTooltip);

	/**
	 * Return a HashMap of types String and Integer containing all additionally used keys that can be changed in the Model Editor.
	 */
	HashMap<String, Integer> getAdditionalPropertyKeys();

	/**
	 * Return true if this IVehiclePart is finished. If not, it will be rendered as transparent gag part.
	 */
	boolean getFinished();

	/**
	 * Return the ID of this IVehiclePart. Internally handled and assigned, used to identify IVehiclePart instances during packet handling.
	 * 
	 * @return The ID of this IVehiclePart, or 0 if undefined.
	 */
	char getID();

	/**
	 * Return the localised name of this IVehiclePart instance.
	 */
	@SideOnly(Side.CLIENT)
	String getLocalizedName();

	/**
	 * Return the unique name of this vehicle part's name.
	 * This name is also used for the corresponding texture and model of this IVehiclePart.
	 */
	String getName();

	/**
	 * Get the offset of this vehicle part. By convention, offset is defined as float array with three indices, of X-axis, Y-axis, and Z-axis.
	 */
	float[] getOffset();

	/**
	 * Return a property from this IVehiclePart's property list.
	 * 
	 * @param key - The index of the property.
	 * @param type - The expected Class type of the property.
	 * @param defReturn - The default value to return when an error occurs or the property cannot be found.
	 */
	<T extends Object> T getProperty(int key, Class<T> type, @Nullable T defReturn);

	/**
	 * Return the {@link IVehiclePartRenderer} instance that renders the given IVehiclePart.
	 * 
	 * @param part - An IVehiclePart instance of the overriding class' type.
	 */
	@SideOnly(Side.CLIENT)
	<P extends IVehiclePart> IVehiclePartRenderer<P> getRenderer(P part);

	/**
	 * Get the rotation of this vehicle part. By convention, rotation is defined as float array with three indices, of order pitch, yaw, roll.
	 * 
	 * @return The rotation of this vehicle part.
	 */
	float[] getRotation();

	/**
	 * Get the scale of this vehicle part. By convention, scale is defined as float array with three indices, of order X-axis, Y-axis, and Z-axis.
	 * 
	 * @return The scale of this vehicle part.
	 */
	float[] getScale();

	/**
	 * Return the weight (in Tonnes; 1t = 1000kg) of this IVehiclePart.
	 */
	float getWeight();

	/**
	 * Called by {@link IEntityTrackBound} whenever this IVehiclePart is activated (i.e. right-clicked).
	 * 
	 * @param player - The EntityPlayer that clicked this IVehiclePart.
	 * @return True if something happens.
	 */
	boolean onActivated(EntityPlayer player);

	/**
	 * Called when this part is added to an {@link IEntityTrackBound} instance.
	 * 
	 * @param vehicle - The vehicle this part belongs to. Might not be fully initialised at this point.
	 */
	<T extends Entity & IEntityTrackBound> void onAddedToVehicle(T vehicle);

	/**
	 * Called to allow Item-dependent interaction with the given IEntityTrackBound vehicle.<br>
	 * Unlike {@link #onItemUse(ItemStack, EntityPlayer, World, int, int, int, int, float, float, float)},
	 * this method is triggered whenever an IEntityTrackBound vehicle is clicked with the IVehiclePart's ItemStack in hand.<p>
	 * 
	 * This method is called on server-side only.
	 * 
	 * @return True if something happens.
	 */
	<T extends Entity & IEntityTrackBound> boolean onInteractWith(ItemStack itemStack, EntityPlayer player, T vehicle);

	/**
	 * Called by ItemVehiclePart for custom vehicle part Item functionality.
	 * For parameters see: {@link net.minecraft.item.Item#onItemUse(ItemStack, EntityPlayer, World, int, int, int, int, float, float, float)}<p>
	 * 
	 * Unlike {@link #onInteractWith(ItemStack, EntityPlayer, Entity)}, this method is called when the player clicks on the ground with this IVehiclePart's ItemStack in hand.
	 * 
	 * @return True if something happens.
	 */
	boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float px, float py, float pz);

	/**
	 * Called by {@link IEntityTrackBound}'s onUpdate method to allow special behavior for IVehicleParts.
	 * 
	 * @param section - The IEntityTrackBoundSection this IVehiclePart is a part of.
	 */
	void onUpdate(@Nullable IEntityTrackBoundSection section);

	/**
	 * Called to read all custom properties of this IVehiclePart from the given NBTTagCompound.
	 * 
	 * @param nbt - The NBTTagCompound to read from.
	 * @param callType - The ETagCompoundCall type that can be used to write/ read case-dependent data.
	 */
	void readFromNBT(NBTTagCompound nbt, ETagCompoundCall callType);

	/**
	 * Called by the Vehicle Part Registry to register all recipes for vehicle parts.
	 * 
	 * @param itemStack - The ItemStack instance containing this IVehiclePart instance's NBTTagCompound.
	 */
	void registerItemRecipe(ItemStack itemStack);

	/**
	 * Set whether this IVehiclePart is finished. If false, the part will be rendered as transparent gag part.
	 * 
	 * @param isFinished - True if the part is finished, false otherwise.
	 */
	void setFinished(boolean isFinished);

	/**
	 * Set the ID of this IVehiclePart instance. Internally handled and assigned, the implementing class solely has to save the field and ensure it is correctly returned in {@code getID()}.
	 */
	void setID(char id);

	/**
	 * Set the offset of this vehicle part. By convention, offset is defined as float array with three indices, of X-axis, Y-axis, and Z-axis.
	 */
	void setOffset(float... values);

	/**
	 * Set the custom property for the given key.
	 */
	void setProperty(int key, Object property);

	/**
	 * Set the rotation of this vehicle part. By convention, rotation is defined as float array with three indices, of order pitch, yaw, roll.
	 */
	void setRotation(float... values);

	/**
	 * Set the scale of this vehicle part. By convention, scale is defined as float array with three indices, of order X-axis, Y-axis, and Z-axis.
	 */
	void setScale(float... values);

	/**
	 * Called to write all custom properties of this IVehiclePart to the vehicle's NBT.
	 * 
	 * @param nbt - The NBTTagCompound to write to.
	 * @param callType - The ETagCompoundCall type that can be used to write/ read case-dependent data.
	 */
	void writeToNBT(NBTTagCompound nbt, ETagCompoundCall callType);



	/**
	 * Common references used when accessing properties of an IVehiclePart instance.
	 */
	public enum EVehicleData {

		OFFSET("offset", 0),
		ROTATION("rotation", 1),
		SCALE("scale", 2),
		STATE("state", 3);

		/** The name of this EVehicleData property. Use this for writing to and reading from NBT. */
		private final String name;

		/** The ID of this EVehicleData property. Used for identification in packet handling. */
		public final int val;

		private EVehicleData(String name, int id) {

			this.name = name;
			this.val = id;
		}

		@Override
		public String toString() {

			return this.name;
		}
	}
}