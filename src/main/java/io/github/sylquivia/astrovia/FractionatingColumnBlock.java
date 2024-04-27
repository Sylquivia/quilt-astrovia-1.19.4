package io.github.sylquivia.astrovia;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FractionatingColumnBlock extends BlockWithEntity implements BlockEntityProvider {
	public static final IntProperty OIL = AstroviaProperties.OIL_3;
	public static final IntProperty GAS = AstroviaProperties.GAS_3;
	public static final IntProperty NAPHTHA = AstroviaProperties.NAPHTHA_3;
	public static final IntProperty KEROSENE = AstroviaProperties.KEROSENE_3;
	public static final IntProperty FUEL_OIL = AstroviaProperties.FUEL_OIL_3;

	protected FractionatingColumnBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState()
			.with(OIL, 0)
			.with(GAS, 0)
			.with(NAPHTHA, 0)
			.with(KEROSENE, 0)
			.with(FUEL_OIL, 0)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(OIL, GAS, NAPHTHA, KEROSENE, FUEL_OIL);
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
