package dark.core;

import org.modstats.Modstats;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public abstract class ModPrefab
{

	public String DOMAIN = "dark";
	public String PREFIX = DOMAIN + ":";

	public String DIRECTORY_NO_SLASH = "assets/" + DOMAIN + "/";
	public String DIRECTORY = "/" + DIRECTORY_NO_SLASH;
	public String LANGUAGE_PATH = DIRECTORY + "languages/";
	public String SOUND_PATH = DIRECTORY + "audio/";

	public static final String TEXTURE_DIRECTORY = "textures/";
	public static final String BLOCK_DIRECTORY = TEXTURE_DIRECTORY + "blocks/";
	public static final String ITEM_DIRECTORY = TEXTURE_DIRECTORY + "items/";
	public static final String MODEL_DIRECTORY = TEXTURE_DIRECTORY + "models/";
	public static final String GUI_DIRECTORY = TEXTURE_DIRECTORY + "gui/";

	/* START IDS */
	public static int BLOCK_ID_PREFIX = 3100;
	public static int ITEM_ID_PREFIX = 13200;

	public ModPrefab(String domain)
	{
		DOMAIN = domain;
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		this.loadModMeta();
		Modstats.instance().getReporter().registerMod(this);
		this.loadConfig();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}

	public abstract void loadConfig();

	public abstract void loadModMeta();
}
