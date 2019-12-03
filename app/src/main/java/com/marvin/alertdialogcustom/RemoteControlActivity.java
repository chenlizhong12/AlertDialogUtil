package com.marvin.alertdialogcustom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zhangke.websocket.SocketListener;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;
import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class RemoteControlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebSocketSetting webSocketSetting = new WebSocketSetting();
        webSocketSetting.setConnectUrl("wss://echo.websocket.org");
        webSocketSetting.setConnectTimeout(10*1000);
        webSocketSetting.setConnectionLostTimeout(60);
        webSocketSetting.setReconnectFrequency(4);
        HashMap map = new HashMap();
        map.put("","");
        webSocketSetting.setHttpHeaders(map);
        WebSocketManager manager = WebSocketHandler.init(webSocketSetting);
        manager.start();

        manager.addListener(new SocketListener() {
            @Override
            public void onConnected() {

            }

            @Override
            public void onConnectFailed(Throwable e) {

            }

            @Override
            public void onDisconnect() {

            }

            @Override
            public void onSendDataError(ErrorResponse errorResponse) {
                Toast.makeText(RemoteControlActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public <T> void onMessage(String message, T data) {
                Log.i("clz", "onMessage: "+message);
            }

            @Override
            public <T> void onMessage(ByteBuffer bytes, T data) {
                Log.i("clz", "onMessage: "+bytes);
            }

            @Override
            public void onPing(Framedata framedata) {

            }

            @Override
            public void onPong(Framedata framedata) {

            }
        });

    }
}
