package moze_intel.projecte.capability;

import javax.annotation.Nonnull;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.api.capabilities.item.IAlchChestItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;

public class AlchChestItemCapabilityWrapper extends BasicItemCapability<IAlchChestItem> implements IAlchChestItem {

	@Override
	public Capability<IAlchChestItem> getCapability() {
		return ProjectEAPI.ALCH_CHEST_ITEM_CAPABILITY;
	}

	@Override
	public void updateInAlchChest(@Nonnull Level world, @Nonnull BlockPos pos, @Nonnull ItemStack stack) {
		getItem().updateInAlchChest(world, pos, stack);
	}
}