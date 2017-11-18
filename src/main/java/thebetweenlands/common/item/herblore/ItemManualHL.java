package thebetweenlands.common.item.herblore;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import thebetweenlands.api.herblore.aspect.DiscoveryContainer;
import thebetweenlands.api.item.IDiscoveryProvider;
import thebetweenlands.common.TheBetweenlands;
import thebetweenlands.common.proxy.CommonProxy;

import javax.annotation.Nullable;

public class ItemManualHL extends Item implements IDiscoveryProvider<ItemStack>{
    public ItemManualHL() {
        setMaxStackSize(1);
        this.setCreativeTab(null);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.RED + "Not yet implemented!");
	}
    
    @Override
    public DiscoveryContainer<ItemStack> getContainer(ItemStack stack) {
        if(stack != null) {
            if(stack.getTagCompound() == null)
                stack.setTagCompound(new NBTTagCompound());
            return new DiscoveryContainer<ItemStack>(this, stack).updateFromNBT(stack.getTagCompound(), false);
        }
        return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick( World world, EntityPlayer player, EnumHand hand) {
        player.openGui(TheBetweenlands.INSTANCE, CommonProxy.GUI_HL, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public void saveContainer(ItemStack stack, DiscoveryContainer<ItemStack> container) {
        if(stack != null) {
            if(stack.getTagCompound() == null)
                stack.setTagCompound(new NBTTagCompound());
            stack.setTagCompound(container.writeToNBT(stack.getTagCompound()));
        }
    }
}
