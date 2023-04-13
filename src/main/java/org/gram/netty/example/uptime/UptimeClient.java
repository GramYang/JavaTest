package org.gram.netty.example.uptime;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class UptimeClient {
    static final String HOST=System.getProperty("host","127.0.0.1");
    static final int PORT=Integer.parseInt(System.getProperty("port","8009"));
    static final int RECONNECT_DELAY = Integer.parseInt(System.getProperty("reconnectDelay", "5"));
    static final int READ_TIMEOUT = Integer.parseInt(System.getProperty("readTimeout", "10"));
    private static final UptimeClientHandler handler = new UptimeClientHandler();
    private static final Bootstrap b=new Bootstrap();
    public static void main(String[] args) throws Exception{
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new IdleStateHandler(READ_TIMEOUT, 0, 0), handler);
                        }
                    });
            ChannelFuture f =b.connect(HOST,PORT).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    static void connect(){
        b.connect().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.cause()!=null){
                    handler.startTime = -1;
                    handler.println("Failed to connect: " + future.cause());
                }
            }
        });
    }
}
