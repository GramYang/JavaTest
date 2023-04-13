package org.gram.netty.example.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;

public class FactorialClientInitializer extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;
    public FactorialClientInitializer(SslContext sslCtx){
        this.sslCtx=sslCtx;
    }
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(sslCtx!=null){
            pipeline.addLast(sslCtx.newHandler(ch.alloc(), FactorialClient.HOST, FactorialClient.PORT));
        }
        //将流使用gzip压缩
        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        //解密
        pipeline.addLast(new BigIntegerDecoder());
        //加密
        pipeline.addLast(new NumberEncoder());
        //业务逻辑
        pipeline.addLast(new FactorialClientHandler());
    }
}
