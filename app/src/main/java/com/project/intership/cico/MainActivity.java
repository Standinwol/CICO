package com.project.intership.cico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;
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

public class MainActivity extends AppCompatActivity {

   // private static String url_login = "http://192.168.1.4/CICO/01.Server/checkin_checkout/public/index.php/user/login";
    JSONParser jParser = new JSONParser();
    JSONObject json, JSON,data;
    String username;
    String icon = "";
    Integer back = 0;
    Button btnLogout, btnInfo, btnCheckin, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setClick();
        if(true){btnCheckout.setClickable(false);}
        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name"); Toast.makeText(getApplicationContext(),username,Toast.LENGTH_LONG).show();
    }

    private void setClick(){
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnCheckin = (Button) findViewById(R.id.btnCheckin);
        btnCheckout = (Button) findViewById(R.id.btnCheckout);

        btnInfo.setOnClickListener(mainClick);
        btnLogout.setOnClickListener(mainClick);
        btnCheckin.setOnClickListener(mainClick);
        btnCheckout.setOnClickListener(mainClick);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Close Application...");
        alertDialog.setMessage("Are you sure you want quit?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                finish();
          //      MainActivity.super.onBackPressed();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private View.OnClickListener mainClick = new View.OnClickListener() {

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
        Intent in = new Intent(getApplication(),ManageActivity.class);
        //send jsonUser to ManageActivity,... data from Login
        String s = getIntent().getStringExtra("jsonUser");
        in.putExtra("jsonUser",getIntent().getStringExtra("jsonUser"));
        in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
        startActivity(in);
    }

    private void logoutActivity() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Confirm Logout...");
        alertDialog.setMessage("Are you sure you want logout?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent in = new Intent(getApplication(),LoginActivity.class);
                in.setFlags(getIntent().FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void checkin_dialog(){
        final Dialog dialogin = new Dialog(this);
        dialogin.setContentView(R.layout.checkin_dialog);
        dialogin.setTitle("CHECK IN");

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        Button OK = (Button) dialogin.findViewById(R.id.ci_action);
        TextView text = (TextView) dialogin.findViewById(R.id.tv_day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkinActivity();
                dialogin.dismiss();
            }
        });
        Button cancel = (Button) dialogin.findViewById(R.id.cancel_action);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogin.dismiss();
            }
        });
        dialogin.show();
    }

    private void checkout_dialog() {
        final Dialog dialogout = new Dialog(this);
        dialogout.setContentView(R.layout.checkout_dialog);
        dialogout.setTitle("CHECK OUT");

        Bundle extras = getIntent().getExtras();
        username = extras.getString("user_name");
        Button checkout = (Button) dialogout.findViewById(R.id.co_action);
        final Button cancel = (Button) dialogout.findViewById(R.id.cancel_action);

        Button so_sad = (Button) dialogout.findViewById(R.id.ic_sosad);
        Button sad = (Button) dialogout.findViewById(R.id.ic_sad);
        Button happy = (Button) dialogout.findViewById(R.id.ic_happy);
        Button very_happy = (Button) dialogout.findViewById(R.id.ic_veryhappy);

        final TextView text = (TextView) dialogout.findViewById(R.id.tv_day);
        final EditText txtReason = (EditText) dialogout.findViewById(R.id.txt_reason);
        txtReason.setVisibility(View.GONE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);

        View.OnClickListener iconClick =new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case (R.id.ic_sosad):
                        icon= "so sad";
                        Toast.makeText(getApplicationContext(),icon,Toast.LENGTH_SHORT).show();
                        break;
                    case (R.id.ic_sad):
                        icon= "sad";
                        Toast.makeText(getApplicationContext(),icon,Toast.LENGTH_SHORT).show();
                        break;
                    case (R.id.ic_happy):
                        icon= "happy";
                        Toast.makeText(getApplicationContext(),icon,Toast.LENGTH_SHORT).show();
                        break;
                    case (R.id.ic_veryhappy):
                        icon= "very happy";
                        Toast.makeText(getApplicationContext(),icon,Toast.LENGTH_SHORT).show();
                        break;
                    case (R.id.co_action):
                        String reason = txtReason.getText().toString();

                        if(reason.isEmpty()){txtReason.setError("You must be type your Reason");}
                        if(icon.isEmpty()) Toast.makeText(getApplicationContext(),"Please choose ICON",Toast.LENGTH_LONG).show();

                        else if(txtReason.getVisibility()==View.GONE||!reason.isEmpty()){
                            Toast.makeText(getApplicationContext(),reason,Toast.LENGTH_LONG).show();
                            checkoutActivity();
                            dialogout.dismiss();
                        }
                        break;
                    case (R.id.cancel_action):
                        dialogout.dismiss();
                        break;
                }
            }
        };

        so_sad.setOnClickListener(iconClick);
        sad.setOnClickListener(iconClick);
        happy.setOnClickListener(iconClick);
        very_happy.setOnClickListener(iconClick);
        checkout.setOnClickListener(iconClick);
        cancel.setOnClickListener(iconClick);

        dialogout.show();
    }



    private void checkinActivity() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", username));
        try {
            json = jParser.getJSONFromUrl(Constant.URL_API_LOGIN, params);
            JSONObject response;
            String s = null;
            response = json.getJSONObject("response");
            s = response.getString("xxx_status");
            Log.d("Msg", response.getString("xxx_status"));
            btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
            btnCheckin.setClickable(false);
            btnCheckout.setBackgroundResource(R.drawable.btn_check_press);
            btnCheckout.setClickable(true);
            if (s.equals("true")) {
//                btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
//                btnCheckin.setClickable(false);
//                btnCheckout.setBackgroundResource(R.drawable.btn_checkout_state);
//                btnCheckout.setClickable(true);
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
            json = jParser.getJSONFromUrl(Constant.URL_API_LOGIN, params);
            JSONObject response;
            String s = null;
            response = json.getJSONObject("response");
            s = response.getString("xxx_status");
            Log.d("Msg", response.getString("xxx_status"));
            btnCheckout.setBackgroundResource(R.drawable.btn_checkout_normal);
            btnCheckout.setClickable(false);
            btnCheckin.setBackgroundResource(R.drawable.btn_check_press);
            btnCheckin.setClickable(true);
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
