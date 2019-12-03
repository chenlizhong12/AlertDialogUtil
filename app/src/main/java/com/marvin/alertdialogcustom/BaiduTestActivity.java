package com.marvin.alertdialogcustom;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.aip.asrwakeup3.core.mini.ActivityMiniRecog;
import com.baidu.aip.asrwakeup3.core.mini.AutoCheck;
import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.google.gson.Gson;
import com.marvin.alertdialogcustom.asrprjson.AsrPartialJsonData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaiduTestActivity extends ActivityMiniRecog  {
    private static final String TAG = "BaiduTestActivity";
    //    @Override
//    public void onEvent(String s, String s1, byte[] bytes, int i, int i1) {
//
//    }
    protected TextView txtLog;
    protected TextView txtResult;
    protected Button btn;
    protected Button stopBtn;
    private static String DESC_TEXT = "精简版识别，带有SDK唤醒运行的最少代码，仅仅展示如何调用，\n" +
            "也可以用来反馈测试SDK输入参数及输出回调。\n" +
            "本示例需要自行根据文档填写参数，可以使用之前识别示例中的日志中的参数。\n" +
            "需要完整版请参见之前的识别示例。\n" +
            "需要测试离线命令词识别功能可以将本类中的enableOffline改成true，首次测试离线命令词请联网使用。之后请说出“打电话给李四”";

    private EventManager asr;
    private String final_result;
    private boolean logTime = true;

    protected boolean enableOffline = true; // 测试离线命令词，需要改成true

    /**
     * 基于SDK集成2.2 发送开始事件
     * 点击开始按钮
     * 测试参数填在这里
     */
    private void start() {
        txtLog.setText("");
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        // 基于SDK集成2.1 设置识别参数
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        // params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        // params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号

        /* 语音自训练平台特有参数 */
        // params.put(SpeechConstant.PID, 8002);
        // 语音自训练平台特殊pid，8002：搜索模型类似开放平台 1537  具体是8001还是8002，看自训练平台页面上的显示
        // params.put(SpeechConstant.LMID,1068); // 语音自训练平台已上线的模型ID，https://ai.baidu.com/smartasr/model
        // 注意模型ID必须在你的appId所在的百度账号下
        /* 语音自训练平台特有参数 */

        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
        // 复制此段可以自动检测错误
        (new AutoCheck(getApplicationContext(), new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 100) {
                    AutoCheck autoCheck = (AutoCheck) msg.obj;
                    synchronized (autoCheck) {
                        String message = autoCheck.obtainErrorMessage(); // autoCheck.obtainAllMessage();
                        txtLog.append(message + "\n");
                        ; // 可以用下面一行替代，在logcat中查看代码
                        // Log.w("AutoCheckMessage", message);
                    }
                }
            }
        }, enableOffline)).checkAsr(params);
        String json = null; // 可以替换成自己的json
        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
        printLog("输入参数：" + json);
    }

    /**
     * 点击停止按钮
     * 基于SDK集成4.1 发送停止事件
     */
    private void stop() {
        printLog("停止识别：ASR_STOP");
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }


    /**
     * enableOffline设为true时，在onCreate中调用
     * 基于SDK离线命令词1.4 加载离线资源(离线时使用)
     */
    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new JSONObject(params).toString(), null, 0, 0);
    }

    /**
     * enableOffline为true时，在onDestory中调用，与loadOfflineEngine对应
     * 基于SDK集成5.1 卸载离线资源步骤(离线时使用)
     */
    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.baidu.aip.asrwakeup3.core.R.layout.common_mini);
        initView();
        initPermission();
        // 基于sdk集成1.1 初始化EventManager对象
        asr = EventManagerFactory.create(this, "asr");
        // 基于sdk集成1.3 注册自己的输出事件类
        asr.registerListener(this); //  EventListener 中 onEvent方法
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                start();
            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                stop();
            }
        });
        if (enableOffline) {
            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        Log.i("ActivityMiniRecog", "On pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 基于SDK集成4.2 发送取消事件
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }

        // 基于SDK集成5.2 退出事件管理器
        // 必须与registerListener成对出现，否则可能造成内存泄露
        asr.unregisterListener(this);
    }

    // 基于sdk集成1.2 自定义输出事件类 EventListener 回调方法
    // 基于SDK集成3.1 开始回调事件
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = "name: " + name;


        if (params != null && !params.isEmpty()) {
            logTxt += " ;params :" + params;
        }
//        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
//            if (params != null && params.contains("\"nlu_result\"")) {
//                if (length > 0 && data.length > 0) {
//                    logTxt += ", 语义解析结果：" + new String(data, offset, length);
//                }
//            }
//        }
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            // 临时识别结果, 长语音模式需要从此消息中取出结果
//            result += "识别临时识别结果";
//            Log.d(TAG, "Temp Params:"+params);
            parseAsrPartialJsonData(params);
        }
        else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
            // 识别结束， 最终识别结果或可能的错误
//            result += "识别结束";
//            btnStartRecord.setEnabled(true);
            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
            if (params != null && !params.isEmpty()) {
//                result += "params :" + params + "\n";
            }
//            Log.d(TAG, "Result Params:" + params);
            parseAsrFinishJsonData(params);
        }
        else if (data != null) {
            logTxt += " ;data length=" + data.length;
        }
        printLog(logTxt);
    }

    private void printLog(String text) {
        if (logTime) {
            text += "  ;time=" + System.currentTimeMillis();
        }
        text += "\n";
        Log.i(getClass().getName(), text);
        txtLog.append(text + "\n");
    }


    private void initView() {
        txtResult = (TextView) findViewById(com.baidu.aip.asrwakeup3.core.R.id.txtResult);
        txtLog = (TextView) findViewById(com.baidu.aip.asrwakeup3.core.R.id.txtLog);
        btn = (Button) findViewById(com.baidu.aip.asrwakeup3.core.R.id.btn);
        stopBtn = (Button) findViewById(com.baidu.aip.asrwakeup3.core.R.id.btn_stop);
        txtLog.setText(DESC_TEXT + "\n");
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

    //    protected String[] needPermissions = {
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA,
//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.ACCESS_NETWORK_STATE,
//            Manifest.permission.READ_PHONE_STATE
//    };
//    private boolean isNeedCheck = true;
//    private static final int PERMISSON_REQUESTCODE = 0;
//    private static final String TAG = "MainActivity";
//
//    private Button btnStartRecord;
//    private Button btnStopRecord;
//    private TextView tvResult;
//    private TextView tvParseResult;
//
//    private EventManager asr;
//
//    private boolean logTime = true;
//
//    private String final_result;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_baidu_test);
//        initView();
//        initPermissions();
//        asr = EventManagerFactory.create(this, "asr");
//        asr.registerListener(this); //  EventListener 中 onEvent方法
////        if (enableOffline) {
////            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
////        }
//        btnStartRecord.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                start();
//            }
//        });
//        btnStopRecord.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                stop();
//            }
//        });
//    }
//
//    private void initView() {
//        tvResult = (TextView) findViewById(R.id.tvResult);
//        tvParseResult = (TextView) findViewById(R.id.tvParseResult);
//        btnStartRecord = (Button) findViewById(R.id.btnStartRecord);
//        btnStopRecord = (Button) findViewById(R.id.btnStopRecord);
//        btnStopRecord.setVisibility(View.GONE);
//    }
//
//    private void initPermissions() {
//        if (isNeedCheck) {
//            checkPermissions(needPermissions);
//        }
//    }
//
//    private void checkPermissions(String... permissions) {
//        //获取权限列表
//        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
//        if (null != needRequestPermissonList
//                && needRequestPermissonList.size() > 0) {
//            //list.toarray将集合转化为数组
//            ActivityCompat.requestPermissions(this,
//                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
//                    PERMISSON_REQUESTCODE);
//        }
//    }
//
//    private List<String> findDeniedPermissions(String[] permissions) {
//        List<String> needRequestPermissonList = new ArrayList<String>();
//        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
//        for (String perm : permissions) {
//            if (ContextCompat.checkSelfPermission(this,
//                    perm) != PackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.shouldShowRequestPermissionRationale(
//                    this, perm)) {
//                needRequestPermissonList.add(perm);
//            }
//        }
//        return needRequestPermissonList;
//    }
//
//    private boolean verifyPermissions(int[] grantResults) {
//        for (int result : grantResults) {
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] paramArrayOfInt) {
//        if (requestCode == PERMISSON_REQUESTCODE) {
//            if (verifyPermissions(paramArrayOfInt)) {
//                isNeedCheck = false;
////                SPUtil.putBoolean(this,"isNeedCheck",false);
//            } else {
//                Toast.makeText(this, "你拒绝了该权限信息", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
//    }
//
//    @Override
//    public void onEvent(String name, String params, byte[] data, int offset, int length) {
//        String result = "";
//
//        if (length > 0 && data.length > 0) {
//            result += ", 语义解析结果：" + new String(data, offset, length);
//        }
//
//        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
//            // 引擎准备就绪，可以开始说话
//            result += "引擎准备就绪，可以开始说话";
//
//        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_BEGIN)) {
//            // 检测到用户的已经开始说话
//            result += "检测到用户的已经开始说话";
//
//        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_END)) {
//            // 检测到用户的已经停止说话
//            result += "检测到用户的已经停止说话";
//            if (params != null && !params.isEmpty()) {
//                result += "params :" + params + "\n";
//            }
//        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
//            // 临时识别结果, 长语音模式需要从此消息中取出结果
//            result += "识别临时识别结果";
//            if (params != null && !params.isEmpty()) {
//                result += "params :" + params + "\n";
//            }
////            Log.d(TAG, "Temp Params:"+params);
//            parseAsrPartialJsonData(params);
//        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
//            // 识别结束， 最终识别结果或可能的错误
//            result += "识别结束";
//            btnStartRecord.setEnabled(true);
//            asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
//            if (params != null && !params.isEmpty()) {
//                result += "params :" + params + "\n";
//            }
//            Log.d(TAG, "Result Params:" + params);
//            parseAsrFinishJsonData(params);
//        }
//        printResult(result);
//    }
//
//    private void printResult(String text) {
//        tvResult.append(text + "\n");
//    }
//
//    private void start() {
//        tvResult.setText("");
//        btnStartRecord.setEnabled(false);
//        Map<String, Object> params = new LinkedHashMap<String, Object>();
//        String event = null;
//        event = SpeechConstant.ASR_START;
//        // 基于SDK集成2.1 设置识别参数
//        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
//        params.put(SpeechConstant.DECODER, 2);
//        params.put(SpeechConstant.PID, 1536); // 默认1536
//        params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN); // 语音活动检测
//        params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 2000); // 不开启长语音。开启VAD尾点检测，即静音判断的毫秒数。建议设置800ms-3000ms
//        params.put(SpeechConstant.ACCEPT_AUDIO_DATA, false);// 是否需要语音音频数据回调
//        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);// 是否需要语音音量数据回调
//
//        String json = null; //可以替换成自己的json
//        json = new JSONObject(params).toString(); // 这里可以替换成你需要测试的json
//        asr.send(event, json, null, 0, 0);
//        printResult("输入参数：" + json);
//    }
//
//    private void stop() {
//        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
//    }
//
    private void parseAsrPartialJsonData(String data) {
        Log.d(TAG, "parseAsrPartialJsonData data:" + data);
        Gson gson = new Gson();
        AsrPartialJsonData jsonData = gson.fromJson(data, AsrPartialJsonData.class);
        String resultType = jsonData.getResult_type();
        Log.d(TAG, "resultType:" + resultType);
        if (resultType != null && resultType.equals("final_result")) {
            final_result = jsonData.getBest_result();
//            tvParseResult.setText("解析结果：" + final_result);
        }
    }

    private void parseAsrFinishJsonData(String data) {
        Log.d(TAG, "parseAsrFinishJsonData data:" + data);
        Gson gson = new Gson();
        AsrFinishJsonData jsonData = gson.fromJson(data, AsrFinishJsonData.class);
        String desc = jsonData.getDesc();
        if (desc != null && desc.equals("Speech Recognize success.")) {
            txtResult.setText("解析结果:" + final_result);
        } else {
            String errorCode = "\n错误码:" + jsonData.getError();
            String errorSubCode = "\n错误子码:" + jsonData.getSub_error();
            String errorResult = errorCode + errorSubCode;
            txtResult.setText("解析错误,原因是:" + desc + "\n" + errorResult);
        }
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0); //
    }
}
