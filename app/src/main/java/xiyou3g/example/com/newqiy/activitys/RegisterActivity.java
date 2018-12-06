package xiyou3g.example.com.newqiy.activitys;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xiyou3g.example.com.newqiy.DataBase.DataBaseModify;
import xiyou3g.example.com.newqiy.DataBase.MyDataBaseHelper;
import xiyou3g.example.com.newqiy.R;


/**
 * Created by Lance on 2017/6/5.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;         //返回按钮;
    private EditText number;        //手机号;
    private EditText password;      //密码;
    private Button register;        //登录按钮;
  //  private TextView toLogin;       //跳转登录界面;

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        myDataBaseHelper = new MyDataBaseHelper(this,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();
        initWight();
    }

    private void initWight() {
        back = (ImageView) findViewById(R.id.back);
        number = (EditText) findViewById(R.id.register_number);
        password = (EditText) findViewById(R.id.register_password);
        register = (Button) findViewById(R.id.register_button);
       // toLogin = (TextView) findViewById(R.id.tologin);

        back.setOnClickListener(this);
        register.setOnClickListener(this);
       // toLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.register_button:
                putDataIntoDB();            //将手机号与密码存入数据库;
                break;
           /* case R.id.tologin:
                break;*/
        }
    }

    private void putDataIntoDB() {
        if(number.getText().toString().length()==11 && password.getText().toString().length()>0){
            Cursor cursor = db.query("User",null,"phone = "+number.getText().toString(),null,null,null,null);
            if(cursor.moveToFirst()){
                Toast.makeText(this,"该用户已存在!",Toast.LENGTH_SHORT).show();
            }else{
                DataBaseModify.addUserDataBase(this,number.getText().toString(),password.getText().toString());
                Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },500);
            }
           cursor.close();
        }else{
            Toast.makeText(this,"信息填写不完整！",Toast.LENGTH_SHORT).show();
        }
    }
}
