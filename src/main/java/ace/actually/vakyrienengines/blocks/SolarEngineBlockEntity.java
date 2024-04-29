package ace.actually.vakyrienengines.blocks;

import ace.actually.vakyrienengines.ValkyrienEngines;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.eureka.ship.EurekaShipControl;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

public class SolarEngineBlockEntity extends BlockEntity {

    private int waterBelow = -1;
    public SolarEngineBlockEntity(BlockPos pos, BlockState state) {
        super(ValkyrienEngines.SOLAR_ENGINE_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("waterBelow", waterBelow);
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        waterBelow =nbt.getInt("waterBelow");
    }

    public static void tick(World world, BlockPos pos, BlockState state, SolarEngineBlockEntity be)
    {
        if(world instanceof ServerWorld serverWorld)
        {


            if(VSGameUtilsKt.isBlockInShipyard(serverWorld,pos))
            {
                ServerShip ship = VSGameUtilsKt.getShipManagingPos(serverWorld,pos);
                Vec3d middle = VSGameUtilsKt.toWorldCoordinates(ship, Vec3d.of(pos));
                BlockPos block = new BlockPos((int) middle.x, (int) middle.y, (int) middle.z);

                if(be.waterBelow ==-1)
                {

                    for (int i = 0; i < 5; i++) {
                        if(world.getBlockState(block.down(i)).isOf(Blocks.WATER))
                        {
                            be.setWaterBelow(i);
                            System.out.println("found water at "+i);
                            break;
                        }
                    }
                }

                //System.out.println("mass: "+ship.getInertiaData().getMass());
                EurekaShipControl shipControl = ship.getAttachment(EurekaShipControl.class);

                if(world.isSkyVisible(block.up()) && world.isDay() && world.getBlockState(block.down(be.waterBelow)).isOf(Blocks.WATER) && !world.getBlockState(block).isOf(Blocks.WATER))
                {
                    shipControl.setPowerLinear(shipControl.getPowerLinear()+2000000);
                    shipControl.setPowerAngular(shipControl.getPowerLinear()+1);
                }


            }




        }
    }

    public void setWaterBelow(int waterBelow) {
        this.waterBelow = waterBelow;
        markDirty();
    }
}
