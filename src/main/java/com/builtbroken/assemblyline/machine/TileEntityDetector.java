package com.builtbroken.assemblyline.machine;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;
import calclavia.lib.network.PacketHandler;

import com.builtbroken.assemblyline.ALRecipeLoader;
import com.builtbroken.assemblyline.imprinter.prefab.TileEntityFilterable;
import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.Player;

public class TileEntityDetector extends TileEntityFilterable
{
    private boolean powering = false;

    public TileEntityDetector()
    {
        super(100);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!this.worldObj.isRemote && this.ticks % 10 == 0)
        {
            int metadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            AxisAlignedBB testArea = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord + 1, this.yCoord + 1, this.zCoord + 1);
            ForgeDirection dir = ForgeDirection.getOrientation(metadata);
            testArea.offset(dir.offsetX, dir.offsetY, dir.offsetZ);

            ArrayList<Entity> entities = (ArrayList<Entity>) this.worldObj.getEntitiesWithinAABB(EntityItem.class, testArea);
            boolean powerCheck = false;

            if (entities.size() > 0)
            {
                if (getFilter() != null)
                {
                    for (int i = 0; i < entities.size(); i++)
                    {
                        EntityItem e = (EntityItem) entities.get(i);
                        ItemStack itemStack = e.getEntityItem();

                        powerCheck = this.isFiltering(itemStack);
                    }
                }
                else
                {
                    powerCheck = true;
                }
            }
            else
            {
                powerCheck = false;
            }

            if (powerCheck != this.powering)
            {
                this.powering = powerCheck;
                this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, ALRecipeLoader.blockDetector.blockID);
                this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord + 1, this.zCoord, ALRecipeLoader.blockDetector.blockID);
                for (int x = this.xCoord - 1; x <= this.xCoord + 1; x++)
                {
                    for (int z = this.zCoord - 1; z <= this.zCoord + 1; z++)
                    {
                        this.worldObj.notifyBlocksOfNeighborChange(x, this.yCoord + 1, z, ALRecipeLoader.blockDetector.blockID);
                    }
                }

                PacketHandler.instance().sendPacketToClients(getDescriptionPacket());
            }
        }
    }

    @Override
    public void invalidate()
    {
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, ALRecipeLoader.blockDetector.blockID);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord + 1, this.zCoord, ALRecipeLoader.blockDetector.blockID);
        super.invalidate();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        this.powering = tag.getBoolean("powering");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setBoolean("powering", this.powering);
    }

    @Override
    public Packet getDescriptionPacket()
    {
        return PacketHandler.instance().getTilePacket(this.getChannel(), "detector", this, this.functioning, this.isInverted());
    }

    @Override
    public boolean simplePacket(String id, ByteArrayDataInput dis, Player player)
    {
        try
        {
            if (this.worldObj.isRemote && !super.simplePacket(id, dis, player))
            {
                if (id.equalsIgnoreCase("detector"))
                {
                    this.functioning = dis.readBoolean();
                    this.setInverted(dis.readBoolean());
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public int isPoweringTo(ForgeDirection side)
    {
        return this.powering && this.getDirection() != side.getOpposite() ? 15 : 0;
    }

    public boolean isIndirectlyPoweringTo(ForgeDirection side)
    {
        return this.isPoweringTo(side) > 0;
    }

    @Override
    public boolean canConnect(ForgeDirection direction)
    {
        return direction != this.getDirection();
    }

}
