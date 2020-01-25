package www.vergessen.top.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import www.vergessen.top.Dir;
import www.vergessen.top.Group;

import java.util.List;
import java.util.UUID;

public class TankMsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 33) return;

        //in.markReaderIndex();
        TankJoinMsg msg = new TankJoinMsg();

        msg.x = in.readInt();
        msg.y = in.readInt();
        msg.dir = Dir.values()[in.readInt()];
        msg.moving = in.readBoolean();
        msg.group = Group.values()[in.readInt()];
        msg.id = new UUID(in.readLong(), in.readLong());

        out.add(msg);
	}

}
