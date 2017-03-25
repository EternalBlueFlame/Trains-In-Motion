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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import zoranodensha.api.items.IItemTrackPart;

public abstract class RailwaySectionBase implements IRailwaySection {

	protected int fastenings;
	protected int length;
	protected int plates;
	protected int rails;
	protected int trackbeds;
	protected String name;
	protected Path[] paths;

	public RailwaySectionBase(int trackbeds, int fastenings, int rails, int plates, String name, int length, Path[] paths) {

		this.fastenings = fastenings;
		this.length = length;
		this.plates = plates;
		this.rails = rails;
		this.trackbeds = trackbeds;
		this.name = name;
		this.paths = paths;
	}

	@Override
	public boolean getCanStay(World world, int x, int y, int z, int rotation, EDirection dir) {

		List<int[]> list = getGagBlocksWithRotation(0, rotation, this.getGagBlocks(dir));
		Iterator<int[]> itera = list.iterator();

		while (itera.hasNext()) {

			int[] i = itera.next();

			if (!world.getBlock(x + i[0], y + i[1] - 1, z + i[2]).isSideSolid(world, x + i[0], y + i[1] - 1, z + i[2], ForgeDirection.UP)) {

				return false;
			}
		}

		return true;
	}

	/**
	 * Rotates the given List of coordinate triplets to match the given new rotation.<p> 
	 * 
	 * Rotation flags as following:<br>
	 * 0 = North<br>
	 * 1 = East<br>
	 * 2 = South<br>
	 * 3 = West<p>
	 * 
	 * This method only supports calculation on the x- and z-axis.
	 */
	public static List<int[]> getGagBlocksWithRotation(int rotationOld, int rotationNew, List<int[]> coordinates) {

		List<int[]> newCoords = new ArrayList<int[]>();
		Iterator<int[]> itera = coordinates.iterator();

		int rotation = rotationOld > rotationNew ? (rotationOld + rotationNew) % 4 : 4 - ((rotationOld + rotationNew) % 4);

		while (itera.hasNext()) {

			int[] i = itera.next();
			int x;
			int z;

			switch (rotation) {

			default:
				x = i[0];
				z = i[2];
				break;

			case 1:
				x = i[2];
				z = -i[0];
				break;

			case 2:
				x = -i[0];
				z = -i[2];
				break;

			case 3:
				x = -i[2];
				z = i[0];
				break;
			}

			newCoords.add(new int[] {x, i[1], z});
		}

		return newCoords;
	}

	@Override
	public int getLength() {

		return this.length;
	}

	@Override
	public String getName() {

		return this.name;
	}

	@Override
	public Path getPath(int i) {

		return this.paths[i];
	}

	@Override
	public double[][] getPositionOnTrack(ITrackBase trackBase, Entity vehicle, TileEntity tileEntity, double localX, double localZ) {

		return null;
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox(double x, double y, double z) {

		return AxisAlignedBB.getBoundingBox(x - this.length, y, z - this.length, x + this.length + 1.0D, y + 1.0D, z + this.length + 1.0D);
	}

	@Override
	public List<Integer> getValidPaths() {

		List<Integer> paths = new ArrayList<Integer>();
		paths.add(0);

		return paths;
	}

	@Override
	public Map<Integer, Object> initializeFields() {

		Map<Integer, Object> map = new HashMap<Integer, Object>();

		return map;
	}

	@Override
	public TileEntity initializeTrack(ITrackBase trackBase) {

		trackBase.setField(0, (this.getEShapeType() == EShape.CROSS || this.getEShapeType() == EShape.SWITCH) ? 2 : 0);
		trackBase.setField(1, 0);

		return (TileEntity) trackBase;
	}

	@Override
	public boolean onTrackWorkedOn(ITrackBase trackBase, IInventory inventory, boolean isRemote) {

		return false;
	}

	@Override
	public boolean onTrackWorkedOn(ITrackBase trackBase, EntityPlayer player, boolean isRemote) {

		ItemStack currentItem = player.getHeldItem();

		if (currentItem == null) {

			return false;
		}

		boolean flag0 = player.capabilities.isCreativeMode;
		int damage = trackBase.getField(1, 0, Integer.class);

		if (currentItem.getItem() instanceof IItemTrackPart) {

			if (((IItemTrackPart) currentItem.getItem()).type(currentItem) != null) {

				switch (((IItemTrackPart) currentItem.getItem()).type(currentItem)) {

				case TRACKBED:
					if ((trackBase.getStateOfShape().equals(ERailwayState.BUILDINGGUIDE) || damage % 2 != 0) && this.trackbeds > 0) {

						if (flag0 || getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.trackbeds, currentItem.getItemDamage()), 0)) {

							if (damage % 2 != 0) {

								if (!isRemote && (trackBase.getField(0, 0, Integer.class)) % 2 == currentItem.getItemDamage()) {

									trackBase.setField(1, damage - 1);
									getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.trackbeds, currentItem.getItemDamage()), 1);
								}
							}
							else if (!isRemote) {

								trackBase.setField(0, currentItem.getItemDamage() + trackBase.getField(0, 0, Integer.class));
								trackBase.setStateOfShape(ERailwayState.TRACKBED);

								if (!flag0) {

									getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.trackbeds, currentItem.getItemDamage()), 1);
								}
							}

							return true;
						}
					}
					break;


				case RAIL:
					if ((trackBase.getStateOfShape().equals(ERailwayState.FASTENED) || damage > 1) && this.rails > 0) {

						if (flag0 || getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.rails, 2), 1)) {

							if (!isRemote) {

								if (damage > 1) {

									trackBase.setField(1, damage - 2);
								}
								else {

									trackBase.setStateOfShape(ERailwayState.FINISHED);
								}
							}

							return true;
						}
					}
					break;


				case PLATE:
					if (trackBase.getStateOfShape().equals(ERailwayState.FINISHED) && this.plates > 0) {

						if (flag0 || getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.plates, 3), 1)) {

							if (!isRemote) {

								trackBase.setStateOfShape(ERailwayState.PLATED);
							}

							return true;
						}
					}
					break;

				case FASTENING:
					if ((trackBase.getStateOfShape().equals(ERailwayState.TRACKBED) && damage == 0) && this.fastenings > 0) {

						if (flag0 || getInventorySufficientStack(player.inventory, new ItemStack(currentItem.getItem(), this.fastenings, 4), 1)) {

							if (!isRemote) {

								trackBase.setStateOfShape(ERailwayState.FASTENED);
							}

							return true;
						}
					}
					break;
				}
			}
		}

		return false;
	}

	@Override
	public void onReadFromNBT(NBTTagCompound nbt, ITrackBase trackBase) {

		for (int i = 0; i < 2; ++i) {

			String s = nbt.getString("FieldDec_" + i);

			if ("int".equals(s)) {

				trackBase.setField(i, (Integer) nbt.getInteger("Field_" + i));
			}
		}
	}

	@Override
	public Map<Integer, Object> onUpdate(World world, int x, int y, int z, ITrackBase trackBase, Map<Integer, Object> params) {

		return params;
	}

	@Override
	public void onWriteToNBT(NBTTagCompound nbt, ITrackBase trackBase) {

		for (int i = 0; i < 2; ++i) {

			nbt.setString("FieldDec_" + i, "int");
			nbt.setInteger("Field_" + i, trackBase.getField(i, 0, Integer.class));
		}

		for (int i = 0; i < this.paths.length; ++i) {

			this.paths[i].writeToNBT(nbt);
		}
	}

	/**
	 * Returns true if the given ItemStack exists in the given inventory, counting all present item stacks together.
	 * 
	 * Flag 0 will check whether the itemStack is present and return true if so,
	 * Flag 1 will behave as flag 0 but will also remove the item stack.
	 */
	public static boolean getInventorySufficientStack(IInventory inventory, ItemStack itemStack, int flag) {

		if (inventory != null && itemStack != null && flag > -1) {

			int count = 0;

			for (int i = 0; i < inventory.getSizeInventory(); ++i) {

				ItemStack itemStack0 = inventory.getStackInSlot(i);

				if (itemStack0 != null && itemStack0.getItem().equals(itemStack.getItem()) && itemStack0.getItemDamage() == itemStack.getItemDamage()) {

					count += itemStack0.stackSize;
				}
			}

			if (count >= itemStack.stackSize && flag < 2) {

				if (flag == 0) {

					return true;
				}
				else {

					int max = itemStack.stackSize;

					for (int i = 0; i < inventory.getSizeInventory(); ++i) {

						ItemStack itemStack0 = inventory.getStackInSlot(i);

						if (itemStack0 != null && itemStack0.getItem().equals(itemStack.getItem()) && itemStack0.getItemDamage() == itemStack.getItemDamage()) {

							if (max >= itemStack0.stackSize) {

								inventory.setInventorySlotContents(i, null);
								max -= itemStack0.stackSize;

								if (max == 0)
									return true;
							}
							else {

								itemStack0.stackSize -= max;

								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
}