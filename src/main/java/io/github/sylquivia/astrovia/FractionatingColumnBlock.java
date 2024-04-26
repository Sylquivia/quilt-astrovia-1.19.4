package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.ConnectingBlock.*;

public class FractionatingColumnBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final IntProperty FLUID = AstroviaProperties.FLUID_3;
	public static final IntProperty GAS = AstroviaProperties.GAS_3;

	protected FractionatingColumnBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(FLUID, 0)
			.with(GAS, 0)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FLUID, GAS);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new FractionatingColumnBlockEntity(pos, state);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, AstroviaBlocks.FRACTIONATING_COLUMN_BLOCK_ENTITY, (FractionatingColumnBlockEntity :: tick));
	}
}
