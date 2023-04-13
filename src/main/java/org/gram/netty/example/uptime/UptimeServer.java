package org.gram.netty.example.uptime;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import org.gram.netty.example.util.ServerUtil;

/**
 * 客户端处理心跳和超时重连
 */
public class UptimeServer {
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));
    public static void main(String[] args) throws Exception {
        SslContext sslCtx = ServerUtil.buildSslContext();
        EventLoopGroup bossGroup=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{
            ServerBootstrap b=new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .handler(new LoggingHandler(LogLevel.INFO))//初始化handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {//客户端连接handler
                            ChannelPipeline p =ch.pipeline();
                            if(sslCtx!=null){
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new UptimeServerHandler());
                        }
                    });
            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
