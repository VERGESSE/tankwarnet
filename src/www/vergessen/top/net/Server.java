package www.vergessen.top.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public void serverStart() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ChannelFuture f = b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY,true)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pl = ch.pipeline();
						pl.addLast(new ServerChildHandler());
					}
				})
				.bind(8888)
				.sync();
			
			ServerFrame.INSTANCE.updateServerMsg("server started!");
			
			f.channel().closeFuture().sync(); //close()->ChannelFuture
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	

}

class ServerChildHandler extends ChannelInboundHandlerAdapter { //SimpleChannleInboundHandler Codec
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ServerFrame.INSTANCE.updateClientMsg(msg.toString());
        System.out.println(Server.clients.size());
	    Server.clients.writeAndFlush(msg);
	    //		System.out.println("channelRead");
//		try {
//            TankJoinMsg tm = (TankJoinMsg)msg;
//
//			System.out.println(tm);
//		} finally {
//			ReferenceCountUtil.release(msg);
//		}
		/*ByteBuf buf = null;
		try {
			buf = (ByteBuf)msg;
			
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(), bytes);
			String s = new String(bytes);
			
			if(s.equals("_bye_")) {
				System.out.println("�ͻ���Ҫ���˳�");
				Server.clients.remove(ctx.channel());
				ctx.close();
			} else {
				Server.clients.writeAndFlush(msg);
			}*/
			
			//System.out.println(buf);
			//System.out.println(buf.refCnt());
		/*} finally {
			//if(buf != null && buf) ReferenceCountUtil.release(buf);
			//System.out.println(buf.refCnt());
		}*/
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		Server.clients.remove(ctx.channel());
		ctx.close();
	}
	
	
}





