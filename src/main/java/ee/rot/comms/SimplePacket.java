package ee.rot.comms;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ee.rot.comms.SimplePacket.SimpleMessage;

public class SimplePacket implements IMessageHandler<SimpleMessage, IMessage>
{
  @Override
	public IMessage onMessage(SimpleMessage message, MessageContext ctx)
	{
	  // just to make sure that the side is correct
	  if (ctx.side.isClient())
	  {
	    int integer = message.simpleInt;
	    boolean bool = message.simpleBool;
	  }
	return message;
	}
  
  public static class SimpleMessage implements IMessage
  {
    private int simpleInt;
    private boolean simpleBool;
    
    // this constructor is required otherwise you'll get errors (used somewhere in fml through reflection)
    public SimpleMessage() {}
    
    public SimpleMessage(int simpleInt, boolean simpleBool)
    {
      this.simpleInt = simpleInt;
      this.simpleBool = simpleBool;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
      // the order is important
      this.simpleInt = buf.readInt();
      this.simpleBool = buf.readBoolean();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
      buf.writeInt(simpleInt);
      buf.writeBoolean(simpleBool);
    }
  }
}