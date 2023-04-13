package org.gram.netty.example.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;

public class HexDumpProxyFrontendHandler extends ChannelInboundHandlerAdapter {
    private final String remoteHost;
    private final int remotePort;
    //连接远程被代理地址获取的channel
    private Channel outboundChannel;
    public HexDumpProxyFrontendHandler(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    /**
     * 建立连接后，新建一个Bootstrap，使用ctx的eventloop来发起一个客户端连接被代理地址获得outboundChannel
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        final Channel inboundChannel = ctx.channel();
        Bootstrap b = new Bootstrap();
        b.group(inboundChannel.eventLoop()).channel(ctx.channel().getClass())
                .handler(new HexDumpProxyBackendHandler(inboundChannel)).option(ChannelOption.AUTO_READ, false);
        ChannelFuture f = b.connect(remoteHost, remotePort);
        outboundChannel = f.channel();
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    inboundChannel.read();//从channel中读取数据写入第一个handler的缓存，这里的必要性存疑？
                }else{
                    inboundChannel.close();
                }
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if(outboundChannel!=null){
            closeOnFlush(outboundChannel);
        }
    }

    /**
     * 收到的msg直接发给outboundChannel，发完了继续读触发channelRead
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(outboundChannel.isActive()){
            outboundChannel.writeAndFlush(msg).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        ctx.channel().read();//inboundChannel读取
                    }else{
                        future.channel().close();
                    }
                }
            });
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        closeOnFlush(ctx.channel());
    }

    static void closeOnFlush(Channel ch){
        if(ch.isActive()){
            ch.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
