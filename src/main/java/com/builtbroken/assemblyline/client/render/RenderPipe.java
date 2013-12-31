package com.builtbroken.assemblyline.client.render;

import java.util.HashMap;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.builtbroken.assemblyline.AssemblyLine;
import com.builtbroken.assemblyline.client.model.ModelLargePipe;
import com.builtbroken.assemblyline.client.model.ModelOpenTrough;
import com.builtbroken.assemblyline.fluid.pipes.EnumPipeType;
import com.builtbroken.assemblyline.fluid.pipes.FluidPartsMaterial;
import com.builtbroken.assemblyline.fluid.pipes.TileEntityPipe;
import com.builtbroken.common.Pair;
import com.builtbroken.minecraft.DarkCore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPipe extends TileEntitySpecialRenderer
{
    public static ModelLargePipe MODEL_PIPE = new ModelLargePipe();
    public static ModelOpenTrough MODEL_TROUGH_PIPE = new ModelOpenTrough();
    private static HashMap<Pair<FluidPartsMaterial, Integer>, ResourceLocation> TEXTURES = new HashMap<Pair<FluidPartsMaterial, Integer>, ResourceLocation>();
    public static ResourceLocation TEXTURE = new ResourceLocation(AssemblyLine.DOMAIN, DarkCore.MODEL_DIRECTORY + "pipes/Pipe.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double d, double d1, double d2, float f)
    {
        // Texture file
        GL11.glPushMatrix();
        GL11.glTranslatef((float) d + 0.5F, (float) d1 + 1.5F, (float) d2 + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);

        FluidPartsMaterial mat = FluidPartsMaterial.IRON;
        if (te.getBlockMetadata() < FluidPartsMaterial.values().length)
        {
            mat = FluidPartsMaterial.values()[te.getBlockMetadata()];
        }
        bindTexture(RenderPipe.getTexture(mat, 0));
        if (te instanceof TileEntityPipe)
        {
            this.render(mat, ((TileEntityPipe) te).getSubID(), ((TileEntityPipe) te).renderConnection);
        }
        else
        {
            this.render(mat, 0, new boolean[6]);
        }
        GL11.glPopMatrix();

    }

    public static ResourceLocation getTexture(FluidPartsMaterial mat, int pipeID)
    {
        if (mat != null)
        {
            Pair<FluidPartsMaterial, Integer> index = new Pair<FluidPartsMaterial, Integer>(mat, pipeID);
            if (!TEXTURES.containsKey(index))
            {
                String pipeName = "";
                if (EnumPipeType.get(pipeID) != null)
                {
                    pipeName = EnumPipeType.get(pipeID).getName(pipeID);
                }
                TEXTURES.put(index, new ResourceLocation(AssemblyLine.DOMAIN, DarkCore.MODEL_DIRECTORY + "pipes/" + mat.matName + "/" + pipeName + "Pipe.png"));
            }
            return TEXTURES.get(index);
        }
        return TEXTURE;
    }

    public static ResourceLocation getTexture(int meta)
    {
        return getTexture(FluidPartsMaterial.getFromItemMeta(meta), FluidPartsMaterial.getType(meta));
    }

    public static void render(FluidPartsMaterial mat, int pipeID, boolean[] side)
    {
        if (mat == FluidPartsMaterial.WOOD)
        {
            MODEL_TROUGH_PIPE.render(side, false);
        }
        else if (mat == FluidPartsMaterial.STONE)
        {
            MODEL_TROUGH_PIPE.render(side, true);
        }
        else
        {
            MODEL_PIPE.render(side);
        }
    }

    public static void render(int meta, boolean[] bs)
    {
        render(FluidPartsMaterial.getFromItemMeta(meta), FluidPartsMaterial.getType(meta), bs);
    }

}