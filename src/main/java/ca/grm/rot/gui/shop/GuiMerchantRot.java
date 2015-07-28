package ca.grm.rot.gui.shop;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ca.grm.rot.Rot;

public class GuiMerchantRot extends GuiContainer{
	public static final ResourceLocation texture = new ResourceLocation(Rot.MOD_ID.toLowerCase(),
			"textures/gui/rotShop.png");
	private boolean[] villagerToggledSlots = new boolean[27];

	private EntityPlayer player;
	public GuiMerchantRot(EntityPlayer player) 
	{
		super(new ContainerMerchantPlayer(player.inventory, player));
		this.player = player;//174x192
		this.xSize = 173;
		this.ySize = 165;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks,int mouseX, int mouseY) 
	{
		// Begin drawing the background
		this.mc.getTextureManager().bindTexture(texture);
        int x = (this.width - xSize) / 2;
        int y = (this.height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize,  ySize);
		
		// Other goodies
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.guiLeft + 5, this.guiTop + 62,65,28, "Purchase"));
        //this.buttonList.add(new GuiMerchantShopSlotButton(1,this.guiLeft + 5, this.guiTop + 5, player.inventory.getStackInSlot(3),500));
        //this.buttonList.add(new GuiMerchantShopSlotButton(1,this.guiLeft + 5 + 18, this.guiTop + 5 + 18, player.inventory.getStackInSlot(0),500));
        /*for (int i = 0; i < villager.inventory.size; i++)
        {
        	//Can Share the same ID because whenever a button is pressed the same method is called. using a button and just checking the ID
        	this.buttonList.add(new GuiMerchantShopSlotButton(1,this.guiLeft + 5, this.guiTop + 5, new ItemStack(Items.apple),500));
        }*/
        
        //Temp for now, using player's inventory
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            { 
                //this.addSlotToContainer(new Slot(realPlayerInventory, j + (i + 1) * 9, 6 + j * 18, 86 + i * 18));
            	this.buttonList.add(new GuiMerchantShopSlotButton(1 + (j + (i*j)),this.guiLeft + 5 + 18 * j, this.guiTop + 5 + 18 * i, player.inventory.getStackInSlot(10+(i*j)), villagerToggledSlots[j + (i*j)],(j + (i*j))));
            }
        }
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id < 1)
		{
			
		}
		else
		{
			villagerToggledSlots[((GuiMerchantShopSlotButton)button).buttonBoolIndex] = !villagerToggledSlots[((GuiMerchantShopSlotButton)button).buttonBoolIndex];
		}
		//super.actionPerformed(button);
	}
	
	/** Keyboard Clicks **/
	@Override
	protected void keyTyped(char par1, int par2)
	{
		if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode()))
		{
			this.mc.thePlayer.closeScreen();
		}
	}
	
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
