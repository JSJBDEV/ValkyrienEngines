package ace.actually.vakyrienengines;

import ace.actually.vakyrienengines.blocks.PoweredEngineBlock;
import ace.actually.vakyrienengines.blocks.PoweredEngineBlockEntity;
import ace.actually.vakyrienengines.blocks.SolarEngineBlock;
import ace.actually.vakyrienengines.blocks.SolarEngineBlockEntity;
import ace.actually.vakyrienengines.items.ItemWithTooltip;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.reborn.energy.api.EnergyStorage;

public class ValkyrienEngines implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("valkyrienengines");



    @Override
    public void onInitialize() {
       registerBlocks();
       registerItems();
       registerEnergy();

    }




    public static final SolarEngineBlock SOLAR_ENGINE_BLOCK = new SolarEngineBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK));
    public static final PoweredEngineBlock POWERED_ENGINE_BLOCK = new PoweredEngineBlock(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK));

    private void registerBlocks()
    {
        Registry.register(Registries.BLOCK,new Identifier("valkyrienengines","solar_engine"), SOLAR_ENGINE_BLOCK);
        Registry.register(Registries.BLOCK,new Identifier("valkyrienengines","powered_engine"), POWERED_ENGINE_BLOCK);
    }
    private void registerItems()
    {
        Registry.register(Registries.ITEM,new Identifier("valkyrienengines","solar_engine"),new ItemWithTooltip(SOLAR_ENGINE_BLOCK,new Item.Settings(), Text.of("Needs to be at most 5 blocks above the water!")));
        Registry.register(Registries.ITEM,new Identifier("valkyrienengines","powered_engine"),new ItemWithTooltip(POWERED_ENGINE_BLOCK,new Item.Settings(), Text.of("Needs to be at most 5 blocks above the water! (requires 10E/t)")));
    }

    public static BlockEntityType<SolarEngineBlockEntity> SOLAR_ENGINE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("valkyrienengies", "solar_engine_block_entity"),
            FabricBlockEntityTypeBuilder.create(SolarEngineBlockEntity::new, SOLAR_ENGINE_BLOCK).build()
    );

    public static BlockEntityType<PoweredEngineBlockEntity> POWERED_ENGINE_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            new Identifier("valkyrienengies", "powered_engine_block_entity"),
            FabricBlockEntityTypeBuilder.create(PoweredEngineBlockEntity::new, POWERED_ENGINE_BLOCK).build()
    );


    private void registerEnergy()
    {
        EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> myBlockEntity.energyStorage,POWERED_ENGINE_ENTITY);
    }


}
