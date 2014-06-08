package net.quetzi.bluepower;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.config.Configuration;
import net.quetzi.bluepower.api.BPRegistry;
import net.quetzi.bluepower.api.part.PartRegistry;
import net.quetzi.bluepower.client.gui.GUIHandler;
import net.quetzi.bluepower.compat.CompatibilityUtils;
import net.quetzi.bluepower.init.BPBlocks;
import net.quetzi.bluepower.init.BPItems;
import net.quetzi.bluepower.init.Config;
import net.quetzi.bluepower.init.CustomTabs;
import net.quetzi.bluepower.init.OreDictionarySetup;
import net.quetzi.bluepower.init.Recipes;
import net.quetzi.bluepower.network.NetworkHandler;
import net.quetzi.bluepower.recipe.AlloyFurnaceRegistry;
import net.quetzi.bluepower.references.Refs;
import net.quetzi.bluepower.tileentities.TileEntities;
import net.quetzi.bluepower.world.WorldGenerationHandler;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Refs.MODID, name = Refs.NAME)
public class BluePower {
    
    @Instance(Refs.MODID)
    public static BluePower   instance;
    
    @SidedProxy(clientSide = Refs.PROXY_LOCATION + ".ClientProxy", serverSide = Refs.PROXY_LOCATION + ".CommonProxy")
    public static CommonProxy proxy;
    public static Logger      log;
    
    @EventHandler
    public void PreInit(FMLPreInitializationEvent event) {
    
        event.getModMetadata().version = Refs.fullVersionString();
        
        log = event.getModLog();
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        
        CustomTabs.init();
        // Load configs
        config.load();
        Config.setUp(config);
        config.save();
        BPBlocks.init();
        BPItems.init();
        BPRegistry.alloyFurnaceRegistry = AlloyFurnaceRegistry.getInstance();
        
        TileEntities.init();
        OreDictionarySetup.init();
        GameRegistry.registerWorldGenerator(new WorldGenerationHandler(), 0);
        
        PartRegistry.init();
        
        CompatibilityUtils.preInit(event);
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event) {
    
        Recipes.init(CraftingManager.getInstance());
        proxy.init();
        NetworkHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
        CompatibilityUtils.init(event);
    }
    
    @EventHandler
    public void PostInit(FMLPostInitializationEvent event) {
    
        CompatibilityUtils.postInit(event);
        proxy.initRenderers();
    }
    
    @EventHandler
    public void ServerStarting(FMLServerStartingEvent event) {
    
        // register commands
    }
}
