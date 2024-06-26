package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class OilHeaterBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = Properties.LIT;
	public static final IntProperty OIL = AstroviaProperties.OIL_3;
	public static final IntProperty GAS = AstroviaProperties.GAS_3;
	private BlockPos input, output;
	protected OilHeaterBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(FACING, Direction.NORTH)
			.with(LIT, false)
			.with(OIL, 0)
			.with(GAS, 0)
		);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx).with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new OilHeaterBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

			if (screenHandlerFactory != null) {
				player.openHandledScreen(screenHandlerFactory);
			}
		}

		return ActionResult.SUCCESS;
	}

	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity blockEntity = world.getBlockEntity(pos);

			if (blockEntity instanceof OilHeaterBlockEntity) {
				ItemScatterer.spawn(world, pos, (OilHeaterBlockEntity) blockEntity);
			}

			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING, LIT, OIL, GAS);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, AstroviaBlocks.OIL_HEATER_BLOCK_ENTITY, (OilHeaterBlockEntity :: tick));
	}

	private boolean canTakeOilFrom(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK);
	}

	private boolean canGiveGasTo(BlockState state) {
		return state.isOf(AstroviaBlocks.DIRECTIONAL_PIPE_BLOCK)
			|| state.isOf(AstroviaBlocks.HORIZONTAL_PIPE_BLOCK);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (canTakeOilFrom(neighborState)) {
			if (neighborState.get(OIL) > 0 && state.get(OIL) < 3 && neighborPos != output) {
				world.setBlockState(pos, state.with(OIL, state.get(OIL) + 1), Block.NOTIFY_LISTENERS);
				input = neighborPos;
			}
		}

		if (canGiveGasTo(neighborState)) {
			if (state.get(GAS) > 0 && neighborState.get(GAS) < 3 && neighborPos != input) {
				world.setBlockState(pos, state.with(GAS, state.get(GAS) - 1), Block.NOTIFY_LISTENERS);
				output = neighborPos;
			}
		}

		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
}
