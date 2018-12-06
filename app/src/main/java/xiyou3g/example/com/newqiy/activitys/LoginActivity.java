package xiyou3g.example.com.newqiy.activitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import xiyou3g.example.com.newqiy.DataBase.MyDataBaseHelper;
import xiyou3g.example.com.newqiy.R;

import static xiyou3g.example.com.newqiy.utils.Content.*;

/**
 * Created by Lance on 2017/6/5.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView back;
    private EditText number;
    private EditText password;
    private Button login;

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        myDataBaseHelper = new MyDataBaseHelper(this,"NewQIY.db",null,1);
        db = myDataBaseHelper.getWritableDatabase();
        initWight();
    }

    private void initWight() {
        back = (ImageView) findViewById(R.id.back);
        number = (EditText) findViewById(R.id.login_number);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_button);

        back.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.login_button:
                Cursor cursor = db.query("User",null,"phone = "+number.getText().toString(),null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String pass = cursor.getString(cursor.getColumnIndex("password"));
                        if(pass.equals(password.getText().toString())){
                            Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
                            isLoginIn = 1;
                            userPhone = number.getText().toString();                    //用户的手机号;
                            Message message = new Message();
                            message.what  = LOGININ_SUCCESS;
                            handler.sendMessage(message);
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            },500);
                        }else{
                            Toast.makeText(this,"账号密码输入不正确，请重新输入！",Toast.LENGTH_SHORT).show();
                        }
                    }while (cursor.moveToNext());
                    cursor.close();
                }else{
                    Toast.makeText(this,"用户不存在",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
