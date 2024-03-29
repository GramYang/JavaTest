package org.gram.netty.example.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import org.gram.netty.example.util.ServerUtil;

public class EchoClient {
    static final String HOST=System.getProperty("host","127.0.0.1");
    static final int PORT=Integer.parseInt(System.getProperty("port","8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
    public static void main(String[] args) throws Exception{
        final SslContext sslCtx = ServerUtil.buildSslContext();
        EventLoopGroup group=new NioEventLoopGroup();
        try{
            Bootstrap b=new Bootstrap();
            b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p=ch.pipeline();
                        //添加ssl支持
                        if(sslCtx!=null){
                            p.addLast(sslCtx.newHandler(ch.alloc(),HOST,PORT));
                        }
                        p.addLast(new EchoClientHandler());
                    }
                });
            ChannelFuture f =b.connect(HOST,PORT).sync();
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }
}
