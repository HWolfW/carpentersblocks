package carpentersblocks.util.flowerpot;

import java.util.HashSet;
import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWood;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.IShearable;
import carpentersblocks.data.FlowerPot;
import carpentersblocks.tileentity.TEBase;
import carpentersblocks.tileentity.TECarpentersFlowerPot;
import carpentersblocks.util.BlockProperties;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.registry.GameData;

public class FlowerPotProperties {

    /**
     * Will return block from ItemStack. This is to be used for plants only.
     */
    public static Block toBlock(ItemStack itemStack)
    {
        Object plant = FlowerPotHandler.itemPlant.get(itemStack.getItem());
        
        if (plant != null) {
            return (Block) plant;
        } else {
            return Block.getBlockFromItem(itemStack.getItem());
        }
    }
    
    /**
     * Returns whether flower pot has a design.
     */
    public static boolean hasDesign(TEBase TE)
    {
        return FlowerPot.getDesign(TE) > 0;
    }
    
    /**
     * Returns whether pot has soil.
     */
    public static boolean hasSoil(TEBase TE)
    {
        ItemStack itemStack = ((TECarpentersFlowerPot)TE).soil;
        
        return itemStack != null && isSoil(itemStack);
    }
    
    /**
     * Returns whether ItemStack contains soil.
     */
    public static boolean isSoil(ItemStack itemStack)
    {
        Block block = Block.getBlockFromItem(itemStack.getItem());
        
        if (itemStack.getItem() instanceof ItemBlock && !block.hasTileEntity(itemStack.getItemDamage())) {
            Material material = block.getMaterial();
            return material.equals(Material.grass) || material.equals(Material.ground) || material.equals(Material.sand);
        } else {
            return false;
        }
    }
    
    /**
     * Returns soil ItemStack.
     */
    public static ItemStack getSoil(TEBase TE)
    {
        return ((TECarpentersFlowerPot)TE).soil;
    }
    
    /**
     * Sets soil block.
     */
    public static boolean setSoil(TEBase TE, ItemStack itemStack)
    {
        if (hasSoil(TE)) {
            BlockProperties.ejectEntity(TE, ((TECarpentersFlowerPot)TE).soil);
        }

        ((TECarpentersFlowerPot)TE).soil = itemStack;
        
        World world = TE.getWorldObj();
        Block block = itemStack == null ? null : Block.getBlockFromItem(itemStack.getItem());
        
        world.notifyBlocksOfNeighborChange(TE.xCoord, TE.yCoord, TE.zCoord, block);
        world.markBlockForUpdate(TE.xCoord, TE.yCoord, TE.zCoord);
        
        return true;
    }
    
    /**
     * Returns whether pot has plant.
     */
    public static boolean hasPlant(TEBase TE)
    {
        ItemStack itemStack = ((TECarpentersFlowerPot)TE).plant;
        
        return itemStack != null && isPlant(itemStack);
    }
    
    /**
     * Returns whether ItemStack contains a plant.
     */
    public static boolean isPlant(ItemStack itemStack)
    {
        Block block = Block.getBlockFromItem(itemStack.getItem());

        if (!block.equals(Blocks.air)) {
            if (!block.hasTileEntity(itemStack.getItemDamage())) {
                return block instanceof IPlantable || block instanceof IShearable;
            } else {
                return false;
            }
        } else {
            return FlowerPotHandler.itemPlant.containsKey(itemStack.getItem());
        }
    }
    
    /**
     * Returns plant block.
     */
    public static ItemStack getPlant(TEBase TE)
    {
        return ((TECarpentersFlowerPot)TE).plant;
    }

    /**
     * Sets plant block.
     */
    public static boolean setPlant(TEBase TE, ItemStack itemStack)
    {
        if (hasPlant(TE)) {
            BlockProperties.ejectEntity(TE, ((TECarpentersFlowerPot)TE).plant);
        }

        ((TECarpentersFlowerPot)TE).plant = itemStack;
        
        World world = TE.getWorldObj();
        Block block = itemStack == null ? null : Block.getBlockFromItem(itemStack.getItem());
        
        world.notifyBlocksOfNeighborChange(TE.xCoord, TE.yCoord, TE.zCoord, block);
        world.markBlockForUpdate(TE.xCoord, TE.yCoord, TE.zCoord);
        
        return true;
    }
    
}
