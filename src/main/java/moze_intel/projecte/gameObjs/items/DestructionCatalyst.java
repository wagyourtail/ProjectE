package moze_intel.projecte.gameObjs.items;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import moze_intel.projecte.api.capabilities.item.IItemCharge;
import moze_intel.projecte.capability.ChargeItemCapabilityWrapper;
import moze_intel.projecte.gameObjs.registries.PESoundEvents;
import moze_intel.projecte.utils.PlayerHelper;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DestructionCatalyst extends ItemPE implements IItemCharge {

	public DestructionCatalyst(Properties props) {
		super(props);
		addItemCapability(ChargeItemCapabilityWrapper::new);
	}

	@Nonnull
	@Override
	public InteractionResult useOn(UseOnContext ctx) {
		Player player = ctx.getPlayer();
		if (player == null) {
			return InteractionResult.FAIL;
		}
		Level world = ctx.getLevel();
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		ItemStack stack = ctx.getItemInHand();
		int numRows = calculateDepthFromCharge(stack);
		boolean hasAction = false;
		List<ItemStack> drops = new ArrayList<>();
		for (BlockPos pos : WorldHelper.getPositionsFromBox(WorldHelper.getDeepBox(ctx.getClickedPos(), ctx.getClickedFace(), --numRows))) {
			if (world.isEmptyBlock(pos)) {
				continue;
			}
			BlockState state = world.getBlockState(pos);
			float hardness = state.getDestroySpeed(world, pos);
			if (hardness == -1.0F || hardness >= 50.0F) {
				continue;
			}
			if (!consumeFuel(player, stack, 8, true)) {
				break;
			}
			hasAction = true;
			//Ensure we are immutable so that changing blocks doesn't act weird
			pos = pos.immutable();
			if (PlayerHelper.hasBreakPermission((ServerPlayer) player, pos)) {
				List<ItemStack> list = Block.getDrops(state, (ServerLevel) world, pos, WorldHelper.getTileEntity(world, pos), player, stack);
				drops.addAll(list);
				world.removeBlock(pos, false);
				if (world.random.nextInt(8) == 0) {
					((ServerLevel) world).sendParticles(world.random.nextBoolean() ? ParticleTypes.POOF : ParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 2, 0, 0, 0, 0.05);
				}
			}
		}
		if (hasAction) {
			WorldHelper.createLootDrop(drops, world, ctx.getClickedPos());
			world.playSound(null, player.getX(), player.getY(), player.getZ(), PESoundEvents.DESTRUCT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
		}
		return InteractionResult.SUCCESS;
	}

	private int calculateDepthFromCharge(ItemStack stack) {
		int charge = getCharge(stack);
		if (charge <= 0) {
			return 1;
		}
		if (this instanceof CataliticLens) {
			return 8 + 8 * charge;
		}
		return (int) Math.pow(2, 1 + charge);
	}

	@Override
	public int getNumCharges(@Nonnull ItemStack stack) {
		return 3;
	}

	@Override
	public boolean isBarVisible(@Nonnull ItemStack stack) {
		return true;
	}

	@Override
	public int getBarWidth(@Nonnull ItemStack stack) {
		return Math.round(13.0F - 13.0F * (float) (1.0D - getChargePercent(stack)));
	}
}