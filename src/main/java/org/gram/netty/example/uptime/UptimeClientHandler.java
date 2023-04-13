package org.gram.netty.example.uptime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.concurrent.TimeUnit;

public class UptimeClientHandler extends SimpleChannelInboundHandler<Object> {
    long startTime = -1;

    /**
     * 断线重连
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        println("Sleeping for: " + UptimeClient.RECONNECT_DELAY + 's');
        //通过channel所在的eventloop执行此任务
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                println("Reconnecting to: " + UptimeClient.HOST + ':' + UptimeClient.PORT);
                UptimeClient.connect();
            }
        },UptimeClient.RECONNECT_DELAY, TimeUnit.SECONDS);
    }

    /**
     * 记录startTime
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(startTime<0){
            startTime=System.currentTimeMillis();
        }
        println("Connected to: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        println("Disconnected from: " + ctx.channel().remoteAddress());
    }

    /**
     * 处理心跳事件，客户端和服务端分开单独处理
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(!(evt instanceof IdleStateEvent)){
            return;
        }
        IdleStateEvent e=(IdleStateEvent) evt;
        if(e.state()== IdleState.READER_IDLE){
            println("Disconnecting due to no inbound traffic");
            ctx.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃消息
    }

    void println(String msg){
        if(startTime<0){
            System.out.format("[SERVER IS DOWN] %s%n", msg);
        }else{
            System.out.format("[UPTIME: %5ds] %s%n", (System.currentTimeMillis() - startTime) / 1000, msg);
        }
    }
}
