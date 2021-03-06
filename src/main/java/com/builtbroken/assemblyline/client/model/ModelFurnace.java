// Date: 8/14/2012 3:02:31 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX

package com.builtbroken.assemblyline.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFurnace extends ModelBase
{
    //fields
    ModelRenderer Body;
    ModelRenderer top;
    ModelRenderer bottom;
    ModelRenderer Shape1;

    public ModelFurnace()
    {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(-8F, -8F, -8F, 14, 14, 12);
        Body.setRotationPoint(1F, 18F, 1F);
        Body.setTextureSize(64, 32);
        Body.mirror = true;
        setRotation(Body, 0F, 0F, 0F);
        top = new ModelRenderer(this, 80, 20);
        top.addBox(-8F, 0F, -8F, 16, 2, 16);
        top.setRotationPoint(0F, 8F, 0F);
        top.setTextureSize(64, 32);
        top.mirror = true;
        setRotation(top, 0F, 0F, 0F);
        bottom = new ModelRenderer(this, 80, 0);
        bottom.addBox(-8F, 22F, -8F, 16, 2, 16);
        bottom.setRotationPoint(0F, 0F, 0F);
        bottom.setTextureSize(64, 32);
        bottom.mirror = true;
        setRotation(bottom, 0F, 0F, 0F);
        Shape1 = new ModelRenderer(this, 0, 27);
        Shape1.addBox(-4F, -4F, 0F, 10, 8, 1);
        Shape1.setRotationPoint(-1F, 16F, 5F);
        Shape1.setTextureSize(64, 32);
        Shape1.mirror = true;
        setRotation(Shape1, 0F, 0F, 0F);
    }

    public void genRender(float f5)
    {
        Body.render(f5);
        top.render(f5);
        bottom.render(f5);
        Shape1.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

}
