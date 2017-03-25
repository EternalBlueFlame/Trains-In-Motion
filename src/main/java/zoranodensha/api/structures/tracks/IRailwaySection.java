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

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * An interface for all Railway Section instances.
 * Implement this to add a new Railway Section to the game.
 */
public interface IRailwaySection {

	/**
	 * Returns true if this IRailwaySection can stay at its position.
	 * Passed coordinates are those of the source tile.
	 * Breaks the track if false returns.
	 */
	boolean getCanStay(World world, int x, int y, int z, int rotation, EDirection dir);

	/**
	 * Return all relative coordinates for gag blocks of this RailwaySection in a List.
	 */
	List<int[]> getGagBlocks(EDirection dir);

	/**
	 * Return the length of this RailwaySection.
	 */
	int getLength();

	/**
	 * Return the name of this RailwaySection instance.
	 */
	String getName();

	/**
	 * Return the Path instance corresponding to the given path number.
	 * 
	 * @param path - The path number
	 */
	Path getPath(int path);

	/**
	 * Calculate the position of the given Entity vehicle and return it as a double array. See {@link zoranodensha.api.structures.tracks.ITrackBase getPositionOnTrack()} for further information.
	 * Return {@code null} if this IRailwaySection instance does not calculate position offset on its own and/ or if default position/ rotation calculation shall be applied.
	 * It is recommended to use the local X-coordinate in combination with a function/ Path, whereas a local Z-coordinate can be used to compare the actual position of a vehicle in order to
	 * differentiate between different paths (e.g. on a switch or cross track).
	 * 
	 * @param trackBase - The ITrackBase instance of this track.
	 * @param vehicle - The vehicle whose position and/ or rotation shall be calculated.
	 * @param tileEntity - The TileEntity of the ITrackBase instance.
	 * @param localX - The local X-coordinate of the vehicle. Usually within {@code -0.5D} and {@code length + 0.5D}.
	 * @param localZ - The local Z-coordinate of the vehicle. May be negative.
	 * @return Null if this IRailwaySection instance does not inherit own calculation. Otherwise a multi-dimensional array of double values where each array contains three values for position/ rotation, respectively.
	 */
	double[][] getPositionOnTrack(ITrackBase trackBase, Entity vehicle, TileEntity tileEntity, double localX, double localZ);

	/**
	 * Return the {@link IRailwaySectionRender} class for this IRailwaySection instance.
	 */
	@SideOnly(Side.CLIENT)
	IRailwaySectionRender getRender();

	/**
	 * Return a RenderBoundingBox for this instance.
	 */
	@SideOnly(Side.CLIENT)
	AxisAlignedBB getRenderBoundingBox(double x, double y, double z);

	/**
	 * Return this RailwaySection instance's EShape name. Used by e.g. item rendering.
	 */
	EShape getEShapeType();

	/**
	 * Return a List of Integers containing all valid path numbers.
	 * The default path number (which is 0) is a prerequisite, as the default path will be written to NBT.
	 */
	List<Integer> getValidPaths();

	/**
	 * Called to initialize and return the Object Map (i.e. this track's field Map).
	 */
	Map<Integer, Object> initializeFields();

	/**
	 * Called to initialize and return the given ITrackBase track instance as a TileEntity instance.
	 * If the TileEntity does not implement ITrackBase, an IllegalArgumentException will be thrown.
	 */
	TileEntity initializeTrack(ITrackBase trackBase);

	/**
	 * Called when a track of this IRailwaySection name is about to be extended/ repaired.
	 * 
	 * @param trackBase - The ITrackBase instance of the track.
	 * @param inventory - The inventory of the Entity trying to execute this action.
	 * @param isRemote - True if this is a client world.
	 * @return True if successful.
	 */
	boolean onTrackWorkedOn(ITrackBase trackBase, IInventory inventory, boolean isRemote);

	/**
	 * Called when a player tries to place/ extend/ repair a track of this IRailwaySection name.
	 * 
	 * @param trackBase - The ITrackBase instance of the track.
	 * @param player - The player trying to execute this action.
	 * @param isRemote - True if this is a client world.
	 * @return True if successful.
	 */
	boolean onTrackWorkedOn(ITrackBase trackBase, EntityPlayer player, boolean isRemote);

	/**
	 * Called when this IRailwaySection name is (partially) destroyed.
	 * This is a method handle sent by Block's removedByPlayer(...) method.
	 * 
	 * @param tileEntity - The ITrackBase we are dealing with. Use this to access the World and coordinate fields.
	 * @param player - The player trying to edit this block.
	 * @return A List of ItemStack instances to drop. Return null to suppress a block update or add a null ItemStack to disassemble the track by one piece (for example to remove fastening and leave track bed).
	 */
	List<ItemStack> onTrackDestroyed(TileEntity tileEntity, @Nullable EntityPlayer player);

	/**
	 * Called to read custom data from the given NBTTagCompound.
	 */
	void onReadFromNBT(NBTTagCompound nbt, ITrackBase trackBase);

	/**
	 * Called by the track's onUpdate method.
	 * Use this to move switch tongues, update/ decay stress, ...
	 * 
	 * @param world - This track's World object.
	 * @param x - This track's x-coordinate.
	 * @param y - This track's y-coordinate.
	 * @param z - This track's z-coordinate.
	 * @param trackBase - The ITrackBase instance of this track.
	 * @param params - The Object Map containing all arguments of this IRailwaySection that are handled internally.
	 * @return The altered Map of Objects that was passed in as argument.
	 */
	Map<Integer, Object> onUpdate(World world, int x, int y, int z, ITrackBase trackBase, Map<Integer, Object> params);

	/**
	 * Called to write custom data in the given NBTTagCompound.
	 */
	void onWriteToNBT(NBTTagCompound nbt, ITrackBase trackBase);
}