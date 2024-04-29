package ace.actually.vakyrienengines.blocks;

import ace.actually.vakyrienengines.ValkyrienEngines;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.eureka.ship.EurekaShipControl;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class PoweredEngineBlockEntity extends BlockEntity {

    private int waterBelow = -1;
    public final SimpleEnergyStorage energyStorage = new SimpleEnergyStorage(1000, 1000, 0) {
        @Override
        protected void onFinalCommit() {
            markDirty();
        }
    };

    public PoweredEngineBlockEntity(BlockPos pos, BlockState state) {
        super(ValkyrienEngines.POWERED_ENGINE_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PoweredEngineBlockEntity be)
    {
        if (!world.isClient && be.energyStorage.amount >= 10) {
            be.energyStorage.amount -= 10;

            if(VSGameUtilsKt.isBlockInShipyard(world,pos))
            {
                ServerShip ship = (ServerShip) VSGameUtilsKt.getShipManagingPos(world,pos);
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
                if(world.getBlockState(block.down(be.waterBelow)).isOf(Blocks.WATER) && !world.getBlockState(block).isOf(Blocks.WATER))
                {
                    EurekaShipControl shipControl = ship.getAttachment(EurekaShipControl.class);
                    shipControl.setPowerLinear(shipControl.getPowerLinear()+6000000);
                    shipControl.setPowerAngular(shipControl.getPowerLinear()+1);
                }
            }

            be.markDirty();
        }
    }

    public void setWaterBelow(int waterBelow) {
        this.waterBelow = waterBelow;
        markDirty();
    }
}
