package ee.rot.events;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ee.rot.ExtendLivingBaseRot;
import ee.rot.ExtendPlayerRot;

public class RotEventHandler 
{

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void onDamage(LivingHurtEvent event)
	{
		if(event.source instanceof EntityDamageSource)
		{
			EntityDamageSource source = (EntityDamageSource) event.source;
			if(source.getEntity() instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) source.getEntity();
				
				event.ammount += MathHelper.clamp_float(ExtendPlayerRot.get(player).getStrength() / 4f, 0f, 4.5f);
				System.out.println(event.ammount);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event)
	{
		/*
		Be sure to check if the entity being constructed is the correct type
		for the extended properties you're about to add!
		The null check may not be necessary - I only use it to make sure
		properties are only registered once per entity
		*/
		if (event.entity instanceof EntityPlayer && ExtendPlayerRot.get((EntityPlayer) event.entity) == null)
			// This is how extended properties are registered using our convenient method from earlier
			ExtendPlayerRot.register((EntityPlayer) event.entity);
			// That will call the constructor as well as cause the init() method
			// to be called automatically
		/*if (event.entity instanceof EntityLivingBase)
			ExtendLivingBaseRot.register((EntityLivingBase) event.entity);*/
		// If you didn't make the two convenient methods from earlier, your code would be
		// much uglier:
		if (event.entity instanceof EntityPlayer && event.entity.getExtendedProperties(ExtendPlayerRot.EXT_PROP_NAME) == null)
			event.entity.registerExtendedProperties(ExtendPlayerRot.EXT_PROP_NAME, new ExtendPlayerRot((EntityPlayer) event.entity));
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld (EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityLivingBase)
		{
			/*ExtendLivingBaseRot props = ExtendLivingBaseRot.get((EntityLivingBase) event.entity);
			// Gives a random amount of gold between 0 and 15
			props.addGold(event.entity.worldObj.rand.nextInt(16));
			System.out.println("[LIVING BASE] Gold: " + props.getGold());*/
		}
	}
	
	/*@SubscribeEvent
	public void onLivingDeathEvent (LivingDeathEvent event)
	{
		// we only want to save data for players (most likely, anyway)
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer)
		{
		// NOTE: See step 6 for a way to do this all in one line!!!
		// create a new NBT Tag Compound to store the IExtendedEntityProperties data
		NBTTagCompound playerData = new NBTTagCompound();
		// write the data to the new compound
		((ExtendPlayerRotMana)(event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME))).saveNBTData(playerData);
		// and store it in our proxy
		proxy.storeEntityData(((EntityPlayer) event.entity).username, playerData);
		// call our handy static one-liner to save custom data to the proxy
		ExtendPlayerRotMana.saveProxyData((EntityPlayer) event.entity);
		}
	}*/
	
//	@SubscribeEvent
//	public void onLivingFallEvent(LivingFallEvent event)
//	{
//		// Remember that so far we have only added ExtendedPlayer properties
//		// so check if it's the right kind of entity first
//		if (event.entity instanceof EntityPlayer)
//		{
//			ExtendPlayerRot props = ExtendPlayerRot.get((EntityPlayer) event.entity);
//			
//			// This 'if' statement just saves a little processing time and
//			// makes it so we only deplete mana from a fall that would injure the player
//			if (event.distance > 3.0F && props.getCurrentMana() > 0)
//			{
//				// Some debugging statements so you can see what's happening
////				System.out.println("[EVENT] Fall distance: " + event.distance);
////				System.out.println("[EVENT] Current mana: " + props.getCurrentMana());
//				
//				/*
//				We need to make a local variable to store the amount to reduce both
//				the distance and mana, otherwise when we reduce one, we have no way
//				to tell by how much to reduce the other
//				
//				Alternatively, you could just try to consumeMana for the amount of the
//				fall distance and, if it returns true, set the fall distance to 0,
//				but today we're going for a cushioning effect instead.
//
//				If you want mana to be used efficiently, you would only reduce the fall
//				distance by enough to reduce it to 3.0F (3 blocks), thus ensuring the
//				player will take no damage while minimizing mana consumed.
//					
//				Be sure you put (event.distance - 3.0F) in parentheses or you'll have a
//				nasty bug with your mana! It has to do with the way "x < y ? a : b"
//				parses parameters.
//				*/
//				float reduceby = props.getCurrentMana() < (event.distance - 3.0F) ? props.getCurrentMana() : (event.distance - 3.0F);
//				event.distance -= reduceby;
//				
//				// Cast reduceby to 'int' to match our method parameter
//				props.consumeMana(reduceby);
//				
////				System.out.println("[EVENT] Adjusted fall distance: " + event.distance);
//			}
//		}
//	}

}
