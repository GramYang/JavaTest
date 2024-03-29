package org.gram.netty.example.factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import org.gram.netty.example.util.ServerUtil;

public class FactorialClient {
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8322"));
    static final int COUNT = Integer.parseInt(System.getProperty("count", "1000"));
    public static void main(String[] args) throws Exception {
        final SslContext sslCtx = ServerUtil.buildSslContext();
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new FactorialClientInitializer(sslCtx));
            ChannelFuture f = b.connect(HOST, PORT).sync();
            FactorialClientHandler handler =(FactorialClientHandler) f.channel().pipeline().last();
            System.out.format("Factorial of %,d is: %,d", COUNT, handler.getFactorial());
        }finally {
            group.shutdownGracefully();
        }
    }
}
