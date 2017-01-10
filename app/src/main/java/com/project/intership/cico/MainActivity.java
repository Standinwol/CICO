package com.project.intership.cico;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.intership.cico.JSON.JSONParser;
import com.project.intership.cico.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static String url_login = "http://192.168.1.16/CICO/01.Server/checkin_checkout/public/index.php/user/login";
    JSONParser jParser = new JSONParser();
    JSONObject json;
    String username;
    private Dialog dialog;
    Button btnLogout, btnInfo, btnCheckin, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        setClick();


//        btnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"avc",Toast.LENGTH_LONG).show();
//            }
//        });
    }

    private void setClick(){
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        btnInfo.setOnClickListener(onClickListener);
        btnLogout.setOnClickListener(onClickListener);
        btnCheckin.setOnClickListener(onClickListener);
        btnLogout.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        Intent in;
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnInfo:
                    Toast.makeText(getApplicationContext(),"avc",Toast.LENGTH_LONG).show();
                    in = new Intent(getApplication(),InfoActivity.class);
                    in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    break;
                case R.id.btnLogout:
                    in = new Intent(getApplication(),LoginActivity.class);
                    in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    break;
                case R.id.btnCheckin:
                    checkinActivity();
                    break;
                case R.id.btnCheckout:
                    checkoutActivity();
                    break;
                default: break;
            }
        }
    };

    private void checkinActivity() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", username));
        params.add(new BasicNameValuePair("times", time()));
        params.add(new BasicNameValuePair("state", "check in"));
        json = jParser.getJSONFromUrl(url_login, params);
        JSONObject response;
     //   String s = null;
        /*try {
            response = json.getJSONObject("response");
            s = response.getString("login_status");
            Log.d("Msg", response.getString("login_status"));
            if (s.equals("true")) {
                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                login.putExtra("user_name", username);
                startActivity(login);
                finish();
            }
            else {
             //   publishProgress();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

        Toast.makeText(getApplicationContext(),time(),Toast.LENGTH_LONG).show();
    }

    private String time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    private void checkoutActivity() {
    }
}
