package ca.grm.rot.libs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import ca.grm.rot.extendprops.ExtendPlayer;

public class RotSkillToggle extends RotSkill
{
	private boolean isToggled;
	private float manaDrain;

	public RotSkillToggle(String name, int iconIndex, IMessage packet, int coolDown,int reqLevel, float manaDrain)
	{
		super(name, iconIndex, packet, coolDown,reqLevel);
		isToggled = false;
	}
	
	public void update(Entity entity)
	{
		if (entity instanceof EntityPlayer && manaDrain != 0f)
		{
			ExtendPlayer props = ExtendPlayer.get((EntityPlayer)entity);
			props.consumeMana(manaDrain / 20);
		}
		this.update();
	}
	
	@Override
	public void use()
	{
		if (coolDown == 0)
			isToggled = !isToggled;
	}
	
	public boolean getActive()
	{
		return isToggled;
	}

}
