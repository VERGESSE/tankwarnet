package www.vergessen.top.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import www.vergessen.top.Dir;
import www.vergessen.top.Group;

import java.util.List;
import java.util.UUID;

public class MsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 8) return;
        //引入读指针
        in.markReaderIndex();

        MsgType msgType = MsgType.values()[in.readInt()];
        int length = in.readInt();

        if(in.readableBytes() < length){
            // 重置读指针
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[length];
        in.readBytes(bytes);

        Msg msg = null;
        msg = (Msg)Class.forName("www.vergessen.top.net." + msgType.toString() + "Msg").getDeclaredConstructor().newInstance();

        msg.parse(bytes);
        out.add(msg);
    }

}
