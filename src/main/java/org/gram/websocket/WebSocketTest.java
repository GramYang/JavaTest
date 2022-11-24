package org.gram.websocket;

import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class WebSocketTest {
    public void t1(){
        try{
            //准备数据
            Gson gson=new Gson();
            ByteBuffer bb= ByteBuffer.allocate(2);
            bb.order(ByteOrder.LITTLE_ENDIAN);
            HashMap<String,Object> m1=new HashMap<>();
            m1.put("Msg","hello");
            m1.put("Value",1234);
            byte[] content3=gson.toJson(m1).getBytes();
            bb.putShort((short)1234);
            byte[] content2=bb.array();
            byte[] totalContent=new byte[content3.length+2];
            System.arraycopy(content2,0,totalContent,0,2);
            System.arraycopy(content3,0,totalContent,2,content3.length);
            bb.clear();
            //websocket定义
            WebSocketClient webSocketClient=new WebSocketClient(new URI("ws://127.0.0.1:8800/echo")) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.printf("onopen %s\n",serverHandshake.getHttpStatusMessage());
                }

                @Override
                public void onMessage(String s) {
                    System.out.printf("onmessage %s\n",s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.printf("onclose %s %s %s\n",i,s,b);
                }

                @Override
                public void onError(Exception e) {
                    System.out.printf("onerror: %s\n",e.getMessage());
                }
            };
            webSocketClient.connectBlocking();
            for(byte b:totalContent){
                System.out.printf("%x-",b);
            }
            webSocketClient.send(totalContent);
            webSocketClient.closeBlocking();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
