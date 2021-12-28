package moze_intel.projecte.gameObjs.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

//TODO - 1.18: Look at vanilla's BaseEntityBlock given there may be stuff we need to copy into our block classes
public interface PEEntityBlock<BLOCK_ENTITY extends BlockEntity> extends EntityBlock {

	@Nullable
	BlockEntityTypeRegistryObject<? extends BLOCK_ENTITY> getType();

	@Nullable
	@Override
	default BLOCK_ENTITY newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		BlockEntityTypeRegistryObject<? extends BLOCK_ENTITY> type = getType();
		return type == null ? null : type.get().create(pos, state);
	}

	@Nullable
	@Override
	default <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
		BlockEntityTypeRegistryObject<? extends BLOCK_ENTITY> type = getType();
		if (type != null && blockEntityType == type.get()) {
			return (BlockEntityTicker<T>) type.getTicker(level.isClientSide);
		}
		return null;
	}
}