package moze_intel.projecte.gameObjs.block_entities;

import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class InterdictionTile extends BlockEntity {

	public InterdictionTile(BlockPos pos, BlockState state) {
		super(PEBlockEntityTypes.INTERDICTION_TORCH.get(), pos, state);
	}

	public static void tick(Level level, BlockPos pos, BlockState state, InterdictionTile blockEntity) {
		WorldHelper.repelEntitiesInterdiction(level, new AABB(pos.offset(-8, -8, -8), pos.offset(8, 8, 8)),
				pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
	}
}