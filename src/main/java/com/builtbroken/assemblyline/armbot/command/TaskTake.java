package com.builtbroken.assemblyline.armbot.command;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.api.vector.Vector2;
import universalelectricity.api.vector.Vector3;

import com.builtbroken.assemblyline.api.IArmbot;
import com.builtbroken.assemblyline.api.coding.args.ArgumentIntData;
import com.builtbroken.assemblyline.armbot.TaskBaseArmbot;
import com.builtbroken.assemblyline.armbot.TaskBaseProcess;
import com.builtbroken.common.science.units.UnitHelper;
import com.builtbroken.minecraft.helpers.InvInteractionHelper;
import com.builtbroken.minecraft.helpers.MathHelper;

public class TaskTake extends TaskBaseArmbot
{

    protected ItemStack stack;
    protected int ammount = -1;

    public TaskTake()
    {
        super("Take");
        this.args.add(new ArgumentIntData("blockID", -1, Block.blocksList.length - 1, -1));
        this.args.add(new ArgumentIntData("blockMeta", -1, 15, -1));
        this.args.add(new ArgumentIntData("stackSize", -1, 64, -1));
        this.UV = new Vector2(40, 80);
    }

    @Override
    public ProcessReturn onMethodCalled()
    {
        if (super.onMethodCalled() == ProcessReturn.CONTINUE)
        {
            ammount = UnitHelper.tryToParseInt(this.getArg("stackSize"), -1);
            int blockID = UnitHelper.tryToParseInt(this.getArg("blockID"), -1);
            int blockMeta = UnitHelper.tryToParseInt(this.getArg("blockMeta"), 32767);

            if (blockID > 0)
            {
                stack = new ItemStack(blockID, ammount <= 0 ? 1 : ammount, blockMeta == -1 ? 32767 : blockMeta);
            }

            return ProcessReturn.CONTINUE;
        }
        return ProcessReturn.GENERAL_ERROR;
    }

    @Override
    public ProcessReturn onUpdate()
    {
        if (super.onUpdate() == ProcessReturn.CONTINUE)
        {
            TileEntity targetTile = ((IArmbot) this.program.getMachine()).getHandPos().getTileEntity(this.program.getMachine().getLocation().left());

            if (targetTile != null && ((IArmbot) this.program.getMachine()).getHeldObject() instanceof ItemStack)
            {
                ForgeDirection direction = MathHelper.getFacingDirectionFromAngle(((IArmbot) this.program.getMachine()).getRotation().x);
                List<ItemStack> stacks = new ArrayList<ItemStack>();
                if (this.stack != null)
                {
                    stacks.add(stack);
                }
                InvInteractionHelper invEx = new InvInteractionHelper(this.program.getMachine().getLocation().left(), this.program.getMachine().getLocation().right(), stacks, false);
                ((IArmbot) this.program.getMachine()).grabObject(invEx.tryGrabFromPosition(new Vector3(targetTile), direction, this.stack != null ? stack.stackSize : 1));
                return ((IArmbot) this.program.getMachine()).getHeldObject() != null ? ProcessReturn.DONE : ProcessReturn.CONTINUE;

            }
            return ProcessReturn.CONTINUE;
        }
        return ProcessReturn.GENERAL_ERROR;
    }

    @Override
    public String toString()
    {
        return super.toString() + " " + (stack != null ? stack.toString() : "1x???@???  ");
    }

    @Override
    public void load(NBTTagCompound taskCompound)
    {
        super.load(taskCompound);
        this.stack = ItemStack.loadItemStackFromNBT(taskCompound.getCompoundTag("item"));

    }

    @Override
    public void save(NBTTagCompound taskCompound)
    {
        super.save(taskCompound);
        if (stack != null)
        {
            NBTTagCompound tag = new NBTTagCompound();
            this.stack.writeToNBT(tag);
            taskCompound.setTag("item", tag);
        }

    }

    @Override
    public TaskBaseProcess clone()
    {
        return new TaskTake();
    }
}
