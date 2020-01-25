package www.vergessen.top.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import www.vergessen.top.Tank;
import www.vergessen.top.TankFrame;

public class Client {

	private Channel channel = null;

	public void connect() {
		EventLoopGroup group = new NioEventLoopGroup(1);

		Bootstrap b = new Bootstrap();

		try {
			ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
					.connect("localhost", 8888);

			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (!future.isSuccess()) {
						System.out.println("not connected!");
					} else {
						System.out.println("connected!");
						// initialize the channel
						channel = future.channel();
					}
				}
			});

			f.sync();
			// wait until close
			f.channel().closeFuture().sync();
			System.out.println("连接已断开");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public void send(String msg) {
		ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
		channel.writeAndFlush(buf);
	}

	public static void main(String[] args) throws Exception {
		Client c = new Client();
		c.connect();
	}

	public void closeConnect() {
		this.send("_bye_");
		//channel.close();
	}
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ch.pipeline()
			.addLast(new TankMsgEncoder())
            .addLast(new TankMsgDecoder())
			.addLast(new ClientHandler());
	}

}

class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
        if (msg.id.equals(TankFrame.INSTANCE.getMyTank().getId())||
                TankFrame.INSTANCE.findByUUID(msg.id) != null) return;
        System.out.println(msg);
        Tank tank = new Tank(msg);
        TankFrame.INSTANCE.addTank(tank);

        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
    }

    @Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMyTank()));
	}

}
