package ace.actually.vakyrienengines.items;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithTooltip extends BlockItem {
    Text tooltipIn;

    public ItemWithTooltip(Block block, Settings settings,Text tooltip) {
        super(block, settings);
        tooltipIn=tooltip;
    }


    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(tooltipIn);
    }
}
