package org.gram.netty.example.factorial;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FactorialClientHandler extends SimpleChannelInboundHandler<BigInteger> {
    private ChannelHandlerContext ctx;
    private int receivedMessages;
    private int next = 1;
    final BlockingQueue<BigInteger> answer = new LinkedBlockingQueue<BigInteger>();
    public BigInteger getFactorial(){
        boolean interrupted=false;
        //这样写的作用是：如果answer抛中断异常，for循环的线程也中断
        try{
            for(;;){
                try{
                    return answer.take();//在阻塞时中断了会抛InterruptedException
                }catch (InterruptedException ignore){
                    interrupted=true;
                }
            }
        }finally {
            if(interrupted){
                Thread.currentThread().interrupt();
            }
        }
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx=ctx;
        sendNumbers();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * receivedMessages满1000时向answer写入一条信息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        receivedMessages++;
        if(receivedMessages==FactorialClient.COUNT){
            ctx.channel().close().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    boolean offered=answer.offer(msg);
                    assert offered;
                }
            });
        }
    }

    /**
     * 其中写1-1000，写完后调用numberSender回调，其实就是不听的写1-1000
     */
    private void sendNumbers(){
        ChannelFuture future=null;
        for (int i = 0; i < 4096 && next <= FactorialClient.COUNT; i++) {
            future = ctx.write(next);
            next++;
        }
        if (next <= FactorialClient.COUNT) {
            assert future != null;
            future.addListener(numberSender);
        }
        ctx.flush();
    }

    private final ChannelFutureListener numberSender=new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(future.isSuccess()){
                sendNumbers();
            }else{
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };
}
