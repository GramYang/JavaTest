package org.gram.netty.example.telnet;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        String response;
        boolean close=false;
        if(request.isEmpty()){
            response = "Please type something.\r\n";
        }else if("bye".equalsIgnoreCase(request)){
            response = "Have a good day!\r\n";
            close = true;
        }else{
            response = "Did you say '" + request + "'?\r\n";
        }
        ChannelFuture future = ctx.write(response);
        //如果收到了bye，返回一个Have a good day!后关闭连接
        if(close){
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
