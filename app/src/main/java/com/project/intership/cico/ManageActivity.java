package com.project.intership.cico;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.intership.cico.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ManageActivity extends AppCompatActivity {
    TextView name, add, phone, mail;
    Button btnChangePass, btnBack;
    String user_name="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        setClick();
        viewInfo();

    }

    private void setClick() {
        btnChangePass = (Button) findViewById(R.id.btnChangePass);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplication(),ChangePassActivity.class);
                in.putExtra("user_name",user_name);
                in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void viewInfo(){
        name = (TextView) findViewById(R.id.name);
        add = (TextView) findViewById(R.id.add);
        mail = (TextView) findViewById(R.id.mail);
        phone = (TextView) findViewById(R.id.phone);
//TODO get??? OK, must be putExtra & getStringExtra between MainActivity & ManageActivity
        try {
            JSONObject data = new JSONObject(getIntent().getStringExtra("jsonUser"));
            user_name = data.getString("full_name");
         //   String meo=data.getString("email");
            name.setText(data.getString("full_name"));
            add.setText(data.getString("address"));
            mail.setText(data.getString("email"));
            phone.setText(data.getString("phone"));
//            JSONObject data=new JSONObject(getIntent().getStringExtra("test"));
//            String sname= data.getString("A");
//            String smail=data.getString("C");
//            name.setText(sname); mail.setText(smail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
