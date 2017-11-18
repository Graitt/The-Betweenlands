package thebetweenlands.common.item.herblore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import thebetweenlands.api.herblore.aspect.Aspect;
import thebetweenlands.api.herblore.aspect.IAspectType;
import thebetweenlands.api.herblore.aspect.ItemAspectContainer;
import thebetweenlands.client.handler.ScreenRenderHandler;
import thebetweenlands.client.tab.BLCreativeTabs;
import thebetweenlands.common.block.container.BlockAspectVial;
import thebetweenlands.common.block.terrain.BlockDentrothyst;
import thebetweenlands.common.item.ITintedItem;
import thebetweenlands.common.registries.AspectRegistry;
import thebetweenlands.common.registries.BlockRegistry;
import thebetweenlands.common.registries.ItemRegistry;
import thebetweenlands.common.tile.TileEntityAspectVial;
import thebetweenlands.util.AdvancedRecipeHelper;

import javax.annotation.Nullable;

public class ItemAspectVial extends Item implements ITintedItem, ItemRegistry.ISubItemsItem {
    public ItemAspectVial() {
        this.setUnlocalizedName("item.thebetweenlands.aspectVial");

        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);

        setCreativeTab(BLCreativeTabs.HERBLORE);
        this.setContainerItem(ItemRegistry.DENTROTHYST_VIAL);
        addPropertyOverride(new ResourceLocation("aspect"), (stack, worldIn, entityIn) -> {
            List<Aspect> itemAspects = ItemAspectContainer.fromItem(stack).getAspects();
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && itemAspects.size() >= 1) {
                return AspectRegistry.ASPECT_TYPES.indexOf(itemAspects.get(0).type) + 1;
            }
            return 0;
        });
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        List<Aspect> itemAspects = ItemAspectContainer.fromItem(stack).getAspects();

        if (itemAspects.size() >= 1) {
            Aspect aspect = itemAspects.get(0);
            return super.getItemStackDisplayName(stack) + " - " + aspect.type.getName() + " (" + ScreenRenderHandler.ASPECT_AMOUNT_FORMAT.format(aspect.getDisplayAmount()) + ")";
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            list.add(new ItemStack(this, 1, 0)); //green
            list.add(new ItemStack(this, 1, 1)); //orange

            //Add all aspects
            for (IAspectType aspect : AspectRegistry.ASPECT_TYPES) {
                ItemStack stackGreen = new ItemStack(this, 1, 0);
                ItemAspectContainer greenAspectContainer = ItemAspectContainer.fromItem(stackGreen);
                greenAspectContainer.add(aspect, 400);
                list.add(stackGreen);
                ItemStack stackOrange = new ItemStack(this, 1, 1);
                ItemAspectContainer orangeAspectContainer = ItemAspectContainer.fromItem(stackOrange);
                orangeAspectContainer.add(aspect, 400);
                list.add(stackOrange);
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        try {
            switch (stack.getItemDamage()) {
                case 0:
                    return "item.thebetweenlands.aspect_vial.green";
                case 1:
                    return "item.thebetweenlands.aspect_vial.orange";
            }
        } catch (Exception e) {
        }
        return "item.thebetweenlands.unknown";
    }


    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack containerDefault;
        switch (itemStack.getItemDamage()) {
            default:
            case 0:
                containerDefault = ItemRegistry.DENTROTHYST_VIAL.createStack(0);
                break;
            case 1:
                containerDefault = ItemRegistry.DENTROTHYST_VIAL.createStack(2);
                break;
        }
        ItemStack containerRubberBoots = AdvancedRecipeHelper.getContainerItem(itemStack, null, "rubberBoots");
        ItemStack containerBait = AdvancedRecipeHelper.getContainerItem(itemStack, null, "bait");
        return containerRubberBoots != null ? containerRubberBoots : (containerBait != null ? containerBait : containerDefault);
    }

    @Override
    public Map<Integer, ResourceLocation> getModels() {
        Map<Integer, ResourceLocation> models = new HashMap<>();
        models.put(0, new ResourceLocation(getRegistryName().toString() + "_green"));
        models.put(1, new ResourceLocation(getRegistryName().toString() + "_orange"));
        return models;
    }

    @Override
    public int getColorMultiplier(ItemStack stack, int tintIndex) {
        switch(tintIndex){
            case 0:
                //TODO get color for the liquid somehow
                //Liquid
                /*List<Aspect> aspects = ItemAspectContainer.fromItem(stack).getAspects();
                if(aspects.size() > 0) {
                    Aspect aspect = aspects.get(0);
                    float[] aspectRGBA = ColorUtils.getRGBA(aspect.type.getColor());
                    return ColorUtils.toHex(aspectRGBA[0], aspectRGBA[1], aspectRGBA[2], 1.0F);
                }*/
                return 0xFFFFFFFF;
            case 2:
                return 0xFFFFFFFF;
        }
        return 0xFFFFFFFF;
    }

    /*@Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }*/

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flagIn) {
        if (world != null) {
            List<Aspect> itemAspects = ItemAspectContainer.fromItem(stack).getAspects();
            if (!itemAspects.isEmpty() && itemAspects.get(0).type == AspectRegistry.BYARIIS) {
                //TODO add then Repeller is added
                //tooltip.add(TranslationHelper.translateToLocal("tooltip.aspectvial.byariis.fuel"));
            }
        }
        tooltip.add(TextFormatting.RED + "Not yet fully implemented!");
    }

    /**
     * Places an aspect vial with the specified aspects in it (can be null)
     * @param pos
     * @param vialType Vial type: 0: green, 1: orange
     * @param aspect
     */
    public static void placeAspectVial(World world, BlockPos pos, int vialType, Aspect aspect) {
        world.setBlockState(pos, BlockRegistry.ASPECT_VIAL_BLOCK.getDefaultState().withProperty(BlockAspectVial.TYPE, BlockDentrothyst.EnumDentrothyst.values()[vialType]), 2);
        TileEntityAspectVial tile = (TileEntityAspectVial) world.getTileEntity(pos);
        if(tile != null)
            tile.setAspect(aspect);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return world.getBlockState(pos).getBlock() == BlockRegistry.ASPECT_VIAL_BLOCK;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        List<Aspect> itemAspects = ItemAspectContainer.fromItem(stack).getAspects();
        if(player.isSneaking() && itemAspects.size() == 1 && facing == EnumFacing.UP) {
            if(world.isAirBlock(pos.up())) {
                if(!world.isRemote) {
                    ItemAspectVial.placeAspectVial(world, pos.up(), stack.getItemDamage(), itemAspects.get(0));
                    stack.shrink(1);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }
}
