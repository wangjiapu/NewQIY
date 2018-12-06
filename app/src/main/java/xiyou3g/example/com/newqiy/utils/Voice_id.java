package xiyou3g.example.com.newqiy.utils;


import android.content.Context;
import xiyou3g.example.com.newqiy.bean.Voice;

import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.logging.Handler;


public class Voice_id {

    String result ="";
    private static final String APPID="=592e9664";

    private Context mcontext=null;
    private static Voice_id voiceId=null;
    private static RecognizerDialog iatDialog=null;

    public static Voice_id getInstance(){
        if (voiceId==null){
            voiceId=new Voice_id();
        }
        return voiceId;
    }

    public void setMcontext(Context context) {
        if (mcontext==null){
            this.mcontext = context;
        }
    }

    public void discern(){

        SpeechUtility.createUtility(mcontext, SpeechConstant.APPID+APPID);
        iatDialog=new RecognizerDialog(mcontext,mInitListener);
        iatDialog.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
        iatDialog.setParameter(SpeechConstant.ACCENT,"mandarin");
        iatDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                if (!b){
                    result=parse_Voice(recognizerResult.getResultString());
                    if (!result.equals("")&&result!=null){
                        Intent intent=new Intent();
                        intent.setAction("NewQIY");
                        intent.putExtra("result",result);
                        mcontext.sendBroadcast(intent);
                    }
                    //Toast.makeText(mcontext,result,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(SpeechError speechError) {
                speechError.getPlainDescription(true);
            }
        });
        iatDialog.show();
    }



    private String parse_Voice(String resultString) {
        Gson gson=new Gson();
        Voice voiceBean=gson.fromJson(resultString,Voice.class);
        StringBuffer sb=new StringBuffer();
        ArrayList<Voice.WSBean> ws=voiceBean.ws;
        for(Voice.WSBean w:ws){
            String word=w.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }



    private InitListener mInitListener=new InitListener() {
        @Override
        public void onInit(int i) {
            Log.e("1234","444444444444444");
            if (i!= ErrorCode.SUCCESS){
                Toast.makeText(mcontext,"初始化失败,错误码为："+i,Toast.LENGTH_SHORT).show();
            }
        }
    };
}
