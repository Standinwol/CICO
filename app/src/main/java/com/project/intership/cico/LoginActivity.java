package com.project.intership.cico;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toant.cico.R;

public class LoginActivity extends AppCompatActivity {

    private Dialog dialog;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Button btnLogin = (Button)findViewById(R.id.btnLogin);
//        final TextView fail = (TextView) findViewById(R.id.lfail);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fail.setVisibility(View.VISIBLE);
//
//            }
//        });
        final Button btnCheckin = (Button) findViewById(R.id.btnCheckout);
        btnCheckin.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
////                final Dialog dialog = new Dialog(this);
//                LayoutInflater li = LayoutInflater.from(context);
//                View promptsView = li.inflate(R.layout.checkin_dialog, null);
//
//                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
//                        context);
//
//                // set prompts.xml to alertdialog builder
//                alertDialogBuilder.setView(promptsView);
//
//                // set dialog message
//                alertDialogBuilder
//                        .setCancelable(false)
//                        .setPositiveButton("CHECK IN",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        // get user input and set it to result
//                                        // edit text
//                                    }
//                                })
//                        .setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//
//                // create alert dialog
//                AlertDialog alertDialog = alertDialogBuilder.create();
//
//                // show it
//                alertDialog.show();

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.checkout_dialog);
                dialog.setTitle("CHECK OUT");

                // set the custom dialog components - text, image and button
                TextView text1 = (TextView) dialog.findViewById(R.id.tv_day);
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm", Locale.getDefault());
//                    Date date= new Date();
//                    String curentDate = dateFormat.format(date);
//                    text1.setText("shit");
                EditText editText = (EditText) dialog.findViewById(R.id.edt_reason);

                Button dialogButton = (Button) dialog.findViewById(R.id.co_action);
                // if button is clicked, close the custom dialog
               dialogButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
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
        });
    }


}
