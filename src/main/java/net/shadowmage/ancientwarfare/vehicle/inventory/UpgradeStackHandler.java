package net.shadowmage.ancientwarfare.vehicle.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.shadowmage.ancientwarfare.vehicle.entity.VehicleBase;
import net.shadowmage.ancientwarfare.vehicle.registry.UpgradeRegistry;

import javax.annotation.Nonnull;

public class UpgradeStackHandler extends ItemStackHandler {
	private VehicleBase vehicle;

	public UpgradeStackHandler(VehicleBase vehicle, int size) {
		super(size);
		this.vehicle = vehicle;
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		return isItemValid(stack) ? super.insertItem(slot, stack, simulate) : stack;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return UpgradeRegistry.getUpgrade(par1ItemStack).map(upgrade -> vehicle.vehicleType.isUpgradeValid(upgrade)).orElse(false);
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (!vehicle.world.isRemote) {
			vehicle.upgradeHelper.updateUpgrades();
		}
	}
}
