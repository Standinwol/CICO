package com.project.intership.cico;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.intership.cico.JSON.JSONParser;
import com.project.intership.cico.until.Constant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import static com.example.giang.checkin_checkout.R.id.txtUser;

public class LoginActivity extends AppCompatActivity {
    EditText txtUser;
    EditText txtPass;
    Button btnLogin;
    TextView text;



  //  private static String url_login = "http://192.168.1.4/CICO/01.Server/checkin_checkout/public/index.php/user/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type()) {
                    test();
//                                            new Login().execute();
/*                    Check check = new Check();
                    if(check.isConnectedToServer(url_login, 15000))*/

//                    else
//                    Toast.makeText(getApplicationContext(),"Can not connect to server",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void test() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);
        String data="{\"full_name\":\"a\",\"address\":\"b,c,dn\",\"email\":\"c@gg.cc\"}";JSONObject test;

        {
            String username = txtUser.getText().toString();
            Intent login = new Intent(getApplicationContext(), MainActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            login.putExtra("user_name", username);

            try {
                test= new JSONObject(data);
                login.putExtra("jsonUser",test.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            startActivity(login);}

    }

    private void findViewById() {
        text = (TextView) findViewById(R.id.lfail);
        txtUser = (EditText) findViewById(R.id.txtUser);
        txtPass = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    private boolean type(){
        String username = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        boolean check=false;
        if(pass.isEmpty()) {
            txtPass.setError("Password can not empty");
        }
        if(username.isEmpty()) {
            txtUser.setError("User name can not empty");
        }
        else if(!username.matches("[a-zA-Z0-9_]+")) {
            txtUser.setError("Please enter only letters, numbers and _");
        }
        else if(!pass.isEmpty()){check=true;};
        return (check);
    }

    private class Login extends AsyncTask<String , String, String> {
        String username = txtUser.getText().toString();
        String pass = txtPass.getText().toString();
        JSONParser jParser = new JSONParser();
        JSONObject json,response;

    @Override
        protected String doInBackground(String... args) {
   //      String username = txtUser.getText().toString();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_name", username));
            params.add(new BasicNameValuePair("password", pass));

            try {
                json = jParser.getJSONFromUrl(Constant.URL_API_LOGIN, params);
                response = json.getJSONObject("response");
                String status = response.getString("login_status");
                Log.d("Msg", response.getString("login_status"));
                if (status.equals("true")) {
                    Intent login = new Intent(getApplicationContext(), MainActivity.class);
                    login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    login.putExtra("user_name", username);
                    String data = response.getJSONObject("user_data").toString();
                    login.putExtra("jsonUser",data);
                   // login.putExtra();
                    startActivity(login);
                    finish();
                }
                else {
                    publishProgress("The user name or password is incorrect");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e){
                publishProgress("An error occurred. Please try again");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),values[0],Toast.LENGTH_LONG).show();
        }
    }
}
