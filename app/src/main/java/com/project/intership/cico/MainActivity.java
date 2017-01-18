package com.project.intership.cico;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

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
    JSONObject json, JSON,data, responsee;
    static String id, checkin_status;
    String icon = "";
    Integer back = 0;
    Button btnLogout, btnInfo, btnCheckin, btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        id = getIntent().getStringExtra("id");
        checkin_status = getIntent().getStringExtra("checkin_status");
    //    new ViewMain().execute();
        setClick();
        view(checkin_status);

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

    private void view(String checkin_status) {

        switch (checkin_status) {
            //chưa check in
            case "1":
                btnCheckout.setBackgroundResource(R.drawable.btn_checkout_normal);
                btnCheckout.setClickable(false);
                btnCheckin.setBackgroundResource(R.drawable.btn_check_press);
                btnCheckin.setClickable(true);
                break;
            //chưa check out
            case "2":
                btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
                btnCheckin.setClickable(false);
                btnCheckout.setBackgroundResource(R.drawable.btn_check_press);
                btnCheckout.setClickable(true);
                break;
            //đã check out
            case "3":
                btnCheckout.setBackgroundResource(R.drawable.btn_checkout_normal);
                btnCheckout.setClickable(false);
                btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
                btnCheckin.setClickable(false);
        }
    }



//    private class ViewMain extends AsyncTask<String, String, String> {
//
//        @Override
//        protected String doInBackground(String... args) {
//            Bundle extras = getIntent().getExtras();
//            id = extras.getString("id"); //Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
//            List<NameValuePair> params = new ArrayList<NameValuePair>();
//            //      params.add(new BasicNameValuePair("id", id));
//            try{
//                json = jParser.getJSONFromUrl(Constant.URL_CHECK_STATUS+id,"GET",params);
//                responsee = json.getJSONObject("response");
//                String status = responsee.getString("checkin_status");
//                publishProgress(status);
//            }catch(JSONException e){}
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            super.onProgressUpdate(values);
//            switch (values[0]){
//                case "1":
//                    btnCheckout.setBackgroundResource(R.drawable.btn_checkout_normal);
//                    btnCheckout.setClickable(false);
//                    btnCheckin.setBackgroundResource(R.drawable.btn_check_press);
//                    btnCheckin.setClickable(true);
//                    break;
//                case "2":
//                    btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
//                    btnCheckin.setClickable(false);
//                    btnCheckout.setBackgroundResource(R.drawable.btn_check_press);
//                    btnCheckout.setClickable(true);
//                    break;
//                case "3":
//                    btnCheckout.setBackgroundResource(R.drawable.btn_checkout_normal);
//                    btnCheckout.setClickable(false);
//                    btnCheckin.setBackgroundResource(R.drawable.btn_checkout_normal);
//                    btnCheckin.setClickable(false);
//            }
//        }
//    }



    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
     //   alertDialog.setTitle("Close Application...");
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
     //   String s = getIntent().getStringExtra("jsonUser");
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
        id = extras.getString("id");
        Button OK = (Button) dialogin.findViewById(R.id.ci_action);
        TextView text = (TextView) dialogin.findViewById(R.id.tv_day);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);
        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CheckinActivity().execute();
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
        id = extras.getString("id");
        Button checkout = (Button) dialogout.findViewById(R.id.co_action);
        final Button cancel = (Button) dialogout.findViewById(R.id.cancel_action);

        Button so_sad = (Button) dialogout.findViewById(R.id.ic_sosad);
        Button sad = (Button) dialogout.findViewById(R.id.ic_sad);
        Button happy = (Button) dialogout.findViewById(R.id.ic_happy);
        Button very_happy = (Button) dialogout.findViewById(R.id.ic_veryhappy);

        final TextView text = (TextView) dialogout.findViewById(R.id.tv_day);
        final EditText txtReason = (EditText) dialogout.findViewById(R.id.txt_reason);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        text.setText(currentDate);

        //TODO calculate time to view Reason
     //   if(currentDate-)
        txtReason.setVisibility(View.GONE);

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
   //                         Toast.makeText(getApplicationContext(),reason,Toast.LENGTH_LONG).show();
                            new CheckoutActivity().execute();
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



    private class CheckinActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //  params.add(new BasicNameValuePair("user_name", id));
            try { //TODO method
                json = jParser.getJSONFromUrl(Constant.URL_CHECK+id+".json","PORT", params);
                JSONObject response;
                response = json.getJSONObject("response");
                String s = response.getString("status_user");
                //   Log.d("Msg", response.getString("xxx_status"));
                if (s.equals("0")) {
                    publishProgress("true");
                }
                else {
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e){
                publishProgress("false");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(values[0].equals("true")) {
                view("2");
                Toast.makeText(getApplicationContext(), "check in at " + time(), Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(getApplicationContext(), "An error occurred. Please try again", Toast.LENGTH_LONG).show();
        }
    }

    private String time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        Date date = new Date();
        String currentDate = dateFormat.format(date);
        return currentDate;
    }


    private class CheckoutActivity extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new )
              params.add(new BasicNameValuePair("xxx", icon ));
            try { //TODO method
                json = jParser.getJSONFromUrl(Constant.URL_CHECK+id+".json","PUT", params);
                JSONObject response;
                response = json.getJSONObject("response");
                String s = response.getString("status_user");
                //   Log.d("Msg", response.getString("xxx_status"));
                if (s.equals("0")) {
                    publishProgress("true");
                }
                else {
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (Exception e){
                publishProgress("false");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if(values[0].equals("true")) {
                view("3");
                Toast.makeText(getApplicationContext(), "check out at " + time(), Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(getApplicationContext(), "An error occurred. Please try again", Toast.LENGTH_LONG).show();
        }
    }

}
