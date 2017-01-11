package com.project.intership.cico;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import com.project.intership.cico.JSON.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChangePassActivity extends AppCompatActivity {
    private static String url = "http://192.168.1.4/CICO/01.Server/checkin_checkout/public/index.php/user/login";
    TextView txtOldPass, txtNewPass, txtConfirm;
    Button btnChange, btnCancel;
    String oldPass, newPass, confirm, user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        user_name = getIntent().getExtras().getString("user_name");

        setClick();
    }

    private void getData(){

    }

    private void setClick() {
        txtOldPass = (TextView) findViewById(R.id.txtOldPass);
        txtNewPass = (TextView) findViewById(R.id.txtNewPass);
        txtConfirm = (TextView) findViewById(R.id.txtConfirm);
        btnChange = (Button) findViewById(R.id.btnChangePass);
        btnCancel = (Button) findViewById(R.id.btnCancel);



        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkPass()) new ChangePass().execute();


            }
        });
    }

    private boolean checkPass(){
        String oldPass = txtOldPass.getText().toString();
        String newPass = txtNewPass.getText().toString();
        String confirm = txtConfirm.getText().toString();
        boolean fault = false;
        if(oldPass.isEmpty()){
            txtOldPass.setError("Can not empty");
            fault = true;
        }
        if(newPass.isEmpty()){
            txtNewPass.setError("Can not empty");
            fault = true;
        }
        if(confirm.isEmpty()){
            txtConfirm.setError("Can not empty");
            fault = true;
        } else if(!confirm.equals(newPass)){
            txtConfirm.setError("Confirm wrong");
        }
        return fault;
    }

    private class ChangePass extends AsyncTask<String, Integer, String> {
        String oldPass = txtOldPass.getText().toString();
        String newPass = txtNewPass.getText().toString();
        String confirm = txtConfirm.getText().toString();
        JSONParser jParser = new JSONParser();
        JSONObject json;

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_name",user_name));
            params.add(new BasicNameValuePair("password",oldPass));
            params.add(new BasicNameValuePair("new_pass",newPass));

            try{
                jParser.getJSONFromUrl(url,params);
                JSONObject response = json.getJSONObject("response");
                String status = response.getString("login_status");
                Log.d("Msg", response.getString("login_status"));
                if(status.equals("true")){
                    publishProgress(1);
                }
                else{
                    publishProgress(0);
                }
            }catch(Exception e){
                publishProgress(-1);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0]==0){
                Intent in = new Intent(getApplication(),ManageActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
            else if(values[0]==1){
                Toast.makeText(getApplicationContext(),"Please try again",Toast.LENGTH_LONG).show();
            }
            else if(values[0]==-1){
                Toast.makeText(getApplicationContext(),"An error occurred. Please try again",Toast.LENGTH_LONG).show();
            }
        }
    }
}
