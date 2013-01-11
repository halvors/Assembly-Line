package assemblyline.common.machine.command;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import assemblyline.common.machine.armbot.TileEntityArmbot;

/**
 * Used by arms to search for entities in a region
 * 
 * @author Calclavia
 */
public class CommandGrab extends Command
{

	public static final int radius = 1;

	/**
	 * The item to be collected.
	 */
	private Class<? extends Entity> entityToInclude;

	public CommandGrab()
	{
		super();
		this.entityToInclude = Entity.class;
	}

	@Override
	protected boolean doTask()
	{
		super.doTask();

		List<Entity> found = this.tileEntity.worldObj.getEntitiesWithinAABB(entityToInclude, AxisAlignedBB.getBoundingBox(tileEntity.xCoord - radius, tileEntity.yCoord - radius, tileEntity.zCoord - radius, tileEntity.xCoord + radius, tileEntity.yCoord + radius, tileEntity.zCoord + radius));

		if (found != null && found.size() > 0)
		{
			this.tileEntity.grabbedEntities.add(found.get(0));
			return false;
		}
		/**
		 * Move the robotic arm around and emulate an item search. Then initiate a collect task.
		 */

		return true;
	}
}
