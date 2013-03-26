package com.heroez;

import net.minecraft.block.Block;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;

import com.heroez.blocks.BlockTabletDecoder;
import com.heroez.blocks.BlockTabletOre;
import com.heroez.core.proxy.CommonProxy;
import com.heroez.item.ItemMedallion;
import com.heroez.item.ItemMedallionFragment;
import com.heroez.item.ItemTablet;
import com.heroez.item.ItemTrowel;
import com.heroez.lib.ItemIds;
import com.heroez.lib.Reference;
import com.heroez.recipes.RecipesTabletDecoder;
import com.heroez.tileentity.TileEntityTabletDecoder;
import com.heroez.worldgen.WorldGeneratorHeroez;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class Heroez {

    // The instance of your mod that Forge uses.
    @Instance(Reference.MOD_NAME)
    public static Heroez instance = new Heroez();

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public final static EnumToolMaterial TROWEL_STONE = EnumHelper
	    .addToolMaterial("TROWEL_STONE", 1, 10, 4.0F, 1, 5);
    public final static EnumToolMaterial TROWEL_IRON = EnumHelper
	    .addToolMaterial("TROWEL_IRON", 2, 25, 6.0F, 2, 10);
    public final static EnumToolMaterial TROWEL_DIAMOND = EnumHelper
	    .addToolMaterial("TROWEL_DIAMOND", 3, 50, 8.0F, 3, 15);
    public final static Block blockTabletOre = new BlockTabletOre();
    public final static Item itemMedallion = new ItemMedallion();
    public final static Item itemMedallionFragment = new ItemMedallionFragment();
    public final static Item itemTablet = new ItemTablet();
    public final static Item itemTrowelStone = new ItemTrowel(
	    ItemIds.TROWEL_STONE_DEFAULT, Heroez.TROWEL_STONE)
	    .setUnlocalizedName("heroez:trowelStone");
    public final static Item itemTrowelIron = new ItemTrowel(
	    ItemIds.TROWEL_IRON_DEFAULT, Heroez.TROWEL_IRON)
	    .setUnlocalizedName("heroez:trowelIron");
    public final static Item itemTrowelDiamond = new ItemTrowel(
	    ItemIds.TROWEL_DIAMOND_DEFAULT, Heroez.TROWEL_DIAMOND)
	    .setUnlocalizedName("heroez:trowelDiamond");
    public final static Block blockTabletDecoder = new BlockTabletDecoder();

    @PreInit
    public void preInit(FMLPreInitializationEvent event) {
	// Stub Method
    }

    @Init
    public void load(FMLInitializationEvent event) {

	gameRegistry();
	languageRegistry();
	setHarvestLevels();
	tileEntities();
	networkRegisters();

	// proxy.registerRenderers();

	GameRegistry.registerWorldGenerator(new WorldGeneratorHeroez());

	RecipesTabletDecoder.decoding().addSmelting(Block.gravel.blockID,
		new ItemStack(Block.cobblestone, 1, 0), 0.7F);

    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
	// Stub Method
    }

    public void gameRegistry() {
	GameRegistry.registerBlock(blockTabletOre, "Tablet Ore");
	GameRegistry.registerBlock(blockTabletDecoder, "Tablet Decoder");
	GameRegistry.registerItem(itemMedallion, "Medallion");
	GameRegistry.registerItem(itemMedallionFragment, "Medallion Fragment");
	GameRegistry.registerItem(itemTrowelStone, "Stone Trowel");
	GameRegistry.registerItem(itemTrowelIron, "Iron Trowel");
	GameRegistry.registerItem(itemTrowelDiamond, "Diamond Trowel");

	GameRegistry.addRecipe(new ItemStack(blockTabletDecoder, 1),
		new Object[] { "XYX", "YZY", "XYX", 'X', Item.diamond, 'Y',
			Block.blockNetherQuartz, 'Z', Item.redstone });

	GameRegistry.addRecipe(new ItemStack(itemMedallion, 1), new Object[] {
		"XXX", "XXX", "XXX", 'X', itemMedallionFragment });
	GameRegistry.addRecipe(new ItemStack(itemTrowelStone, 1), new Object[] {
		" XX", " YX", "Y  ", 'X', Block.cobblestone, 'Y', Item.stick });
	GameRegistry.addRecipe(new ItemStack(itemTrowelIron, 1), new Object[] {
		" XX", " YX", "Y  ", 'X', Item.ingotIron, 'Y', Item.stick });
	GameRegistry.addRecipe(new ItemStack(itemTrowelDiamond, 1),
		new Object[] { " XX", " YX", "Y  ", 'X', Item.diamond, 'Y',
			Item.stick });
	GameRegistry.addRecipe(new ItemStack(itemTrowelDiamond, 1),
		new Object[] { " XX", " YX", "Y  ", 'X', Item.diamond, 'Y',
			Item.stick });

    }

    public void languageRegistry() {
	LanguageRegistry.addName(blockTabletOre, "Tablet Ore");
	LanguageRegistry.addName(blockTabletDecoder, "Tablet Decoder");
	LanguageRegistry.addName(itemMedallion, "Medallion");
	LanguageRegistry.addName(itemMedallionFragment, "Medallion Fragment");
	LanguageRegistry.addName(itemTrowelStone, "Stone Trowel");
	LanguageRegistry.addName(itemTrowelIron, "Iron Trowel");
	LanguageRegistry.addName(itemTrowelDiamond, "Diamond Trowel");
	LanguageRegistry.addName(itemTablet, "Ancient Tablet");

    }

    public void setHarvestLevels() {
	MinecraftForge.setBlockHarvestLevel(blockTabletOre, "trowel", 10);
	MinecraftForge.setBlockHarvestLevel(blockTabletOre, "pickaxe", 10);
	MinecraftForge.setToolClass(itemTrowelStone, "trowel", 10);
	MinecraftForge.setToolClass(itemTrowelIron, "trowel", 10);
	MinecraftForge.setToolClass(itemTrowelDiamond, "trowel", 10);

    }

    // Tile Entities
    public void tileEntities() {
	GameRegistry.registerTileEntity(TileEntityTabletDecoder.class,
		"TabletDecoderTileEntity");
    }

    // Network Registry
    public void networkRegisters() {
	NetworkRegistry.instance().registerGuiHandler(this, Heroez.proxy);
	instance = this;
    }
}