package net.shadowmage.ancientwarfare.automation.render;

import net.minecraftforge.common.property.IExtendedBlockState;
import net.shadowmage.ancientwarfare.automation.tile.torque.multiblock.TileFlywheelStorage;

import static net.shadowmage.ancientwarfare.automation.render.property.AutomationProperties.*;

public class FlywheelStorageAnimationRenderer extends BaseAnimationRenderer<TileFlywheelStorage> {

    @Override
    protected IExtendedBlockState handleState(TileFlywheelStorage te, float partialTicks, IExtendedBlockState state) {
        state = state.withProperty(DYNAMIC, true);
        state = state.withProperty(IS_CONTROL, te.isControl);
        state = state.withProperty(WIDTH, te.setWidth);
        state = state.withProperty(HEIGHT, te.setHeight);
        state = state.withProperty(ROTATION, (float) te.rotation);

        return state;
    }

    @Override
    protected int getModelHashCode(IExtendedBlockState exState) {
        int result = exState.getValue(DYNAMIC).hashCode();
        result = 31 * result + exState.getValue(IS_CONTROL).hashCode();
        result = 31 * result + exState.getValue(WIDTH);
        result = 31 * result + exState.getValue(HEIGHT);
        result = 31 * result + Float.floatToIntBits(exState.getValue(ROTATION));
        return result;
    }
}
