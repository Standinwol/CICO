package com.project.intership.cico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.intership.cico.JSON.JSONParser;
import com.project.intership.cico.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
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

        setClick();
        if(true){btnCheckin.setVisibility(View.VISIBLE);}
        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name"); Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject JSon=new JSONObject(getIntent().getStringExtra("jsonString"));
                            JSONObject data=JSon.getJSONObject("user_data");
                            String name= data.getString("full_name");
                            String mail=data.getString("email");
                            Toast.makeText(getApplicationContext(),name+" "+mail,Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



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

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnInfo:
                    manageActivity();

                    break;
                case R.id.btnLogout:
                    logoutActivity();

                    break;
                case R.id.btnCheckin:
                    checkin_dialog();
                    break;
                case R.id.btnCheckout:
                    checkout_dialog();
                    break;

                default: break;
            }
        }
    };

    private void manageActivity() {
        Intent in = new Intent(getApplication(),InfoActivity.class);
        in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

    private void logoutActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Logout...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want logout?");

        // Setting Icon to Dialog
       // alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                Intent in = new Intent(getApplication(),LoginActivity.class);
                in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    private void checkin_dialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.checkin_dialog);
        dialog.setTitle("CHECK IN");

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        Button dialogButton = (Button) dialog.findViewById(R.id.ci_action);
        TextView text = (TextView) dialog.findViewById(R.id.tv_day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkinActivity();
                dialog.dismiss();
            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkout_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.checkin_dialog);
        dialog.setTitle("CHECK OUT");

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        Button OK = (Button) dialog.findViewById(R.id.co_action);
        TextView text = (TextView) dialog.findViewById(R.id.tv_day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);
        // if button is clicked, close the custom dialog
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkinActivity();
                dialog.dismiss();
            }
        });
        Button cancel = (Button) dialog.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkinActivity() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", username));
        try {
            json = jParser.getJSONFromUrl(url_login, params);
            JSONObject response;
            String s = null;
            response = json.getJSONObject("response");
            s = response.getString("xxx_status");
            Log.d("Msg", response.getString("xxx_status"));
            if (s.equals("true")) {
//                Intent login = new Intent(getApplicationContext(), MainActivity.class);
//                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                login.putExtra("user_name", username);
//                startActivity(login);
//                finish();
            }
            else {
             //   publishProgress();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"An error occurred. Please try again.",Toast.LENGTH_LONG).show();
        }

        Toast.makeText(getApplicationContext(),time(),Toast.LENGTH_LONG).show();
    }

    private String time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }

    private void checkoutActivity() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", username));
        try {
            json = jParser.getJSONFromUrl(url_login, params);
            JSONObject response;
            String s = null;
            response = json.getJSONObject("response");
            s = response.getString("xxx_status");
            Log.d("Msg", response.getString("xxx_status"));
            if (s.equals("true")) {
//                Intent login = new Intent(getApplicationContext(), MainActivity.class);
//                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                login.putExtra("user_name", username);
//                startActivity(login);
//                finish();
            }
            else {
                //   publishProgress();
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(),"An error occurred. Please try again.",Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(),time(),Toast.LENGTH_LONG).show();
    }



}
