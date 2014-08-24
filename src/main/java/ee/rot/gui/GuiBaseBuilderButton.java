package ee.rot.gui;

import net.minecraft.client.gui.GuiButton;

/** Just a GuiButton with an extra variable, really wanted this in the normal button **/
public class GuiBaseBuilderButton extends GuiButton
{	
	public String coords = "";
	public GuiBaseBuilderButton(int par1, int par2, int par3, int par4,
			int par5, String par6Str, String par7Str)
	{
		super(par1, par2, par3, par4, par5, par6Str);
		this.coords = par7Str;
	}

}
