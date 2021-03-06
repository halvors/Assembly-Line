package com.builtbroken.assemblyline.machine;

import java.util.Set;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import universalelectricity.api.UniversalElectricity;

import com.builtbroken.assemblyline.AssemblyLine;
import com.builtbroken.common.Pair;
import com.builtbroken.minecraft.prefab.BlockMachine;

/** @author Archadia */
public class BlockApertureExcavator extends BlockMachine
{

    public BlockApertureExcavator()
    {
        super(AssemblyLine.CONFIGURATION, "Machine_ApertureExcavator", UniversalElectricity.machine);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileApertureExcavator();
    }

    @Override
    public void getTileEntities(int blockID, Set<Pair<String, Class<? extends TileEntity>>> list)
    {
        list.add(new Pair<String, Class<? extends TileEntity>>("TileApertureExcavator", TileApertureExcavator.class));
    }
}
