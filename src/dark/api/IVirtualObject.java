package dark.api;

import java.io.File;

import net.minecraft.nbt.NBTTagCompound;

/** Used in combination with the save manager and other managers to say this object needs to be save
 * since its not connected with the world
 * 
 * @author DarkGuardsman */
public interface IVirtualObject
{
    /** File this is saved as, don't create anything here as the manager will do that for you */
    public File getSaveFile();

    /** Will only be called after an object has been loaded. Allows the object to know were its been
     * loaded from and decide if it wants to use the location as its getSaveFile return */
    public void setSaveFile(File file);

    /** Saves the object to NBT */
    public void save(NBTTagCompound nbt);

    /** Load the object from NBT */
    public void load(NBTTagCompound nbt);
}
