package cofh.thermaldynamics.util;

import cofh.lib.util.helpers.ItemHelper;
import cofh.thermaldynamics.duct.TDDucts;
import cofh.thermaldynamics.duct.attachments.cover.CoverHelper;
import cofh.thermaldynamics.init.TDItems;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;

public class RecipeCover implements IRecipe {

	public static final RecipeCover INSTANCE = new RecipeCover();

	@Override
	public boolean matches(InventoryCrafting craft, World world) {

		boolean a = false;
		boolean b = false;

		for (int i = 0; i < craft.getSizeInventory(); i++) {
			ItemStack stack = craft.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			if (ItemHelper.itemsEqualForCrafting(stack, TDDucts.structure.itemStack)) {
				if (a) {
					return false;
				} else {
					a = true;
				}
			} else {
				// Invalid cover -> Stone cover.
				if (stack.getItem() == TDItems.itemCover && CoverHelper.getCoverItemStack(stack, false) == null) {
					return true;
				}
				if (!CoverHelper.isValid(stack)) {
					return false;
				}
				if (b) {
					return false;
				} else {
					b = true;
				}
			}
		}
		return a && b;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting craft) {

		for (int i = 0; i < craft.getSizeInventory(); i++) {
			ItemStack stack = craft.getStackInSlot(i);
			if (stack == null) {
				continue;
			}
			// Invalid cover -> Stone cover.
			if (stack.getItem() == TDItems.itemCover && CoverHelper.getCoverItemStack(stack, false) == null) {
				return ItemHelper.cloneStack(CoverHelper.getCoverStack(Blocks.STONE, 0));
			}
			if (!ItemHelper.itemsEqualForCrafting(stack, TDDucts.structure.itemStack)) {

				return ItemHelper.cloneStack(CoverHelper.getCoverStack(stack), 6);
			}
		}
		return null;
	}

	@Override
	public int getRecipeSize() {

		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {

		return new ItemStack(TDItems.itemCover, 6);
	}

	@Override
	public ItemStack[] getRemainingItems(@Nonnull InventoryCrafting inv) {

		return ForgeHooks.defaultRecipeGetRemainingItems(inv);
	}

}
