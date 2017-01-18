package com.project.intership.cico;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.intership.cico.JSON.JSONParser;
import com.project.intership.cico.R;
import com.project.intership.cico.until.Constant;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ManageActivity extends AppCompatActivity {
    TextView name, add, phone, mail;
    Button btnChangePass, btnBack;
    String user_name="";String[] items;
    JSONArray jsonArray;
    JSONParser jsonParser = new JSONParser();
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        setClick();
        viewInfo();
        new ViewTable().execute();

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
            name.setText(data.getString("full_name"));
            add.setText(data.getString("address"));
            mail.setText(data.getString("email"));
            phone.setText(data.getString("phone"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ViewTable extends AsyncTask<String, String, String> {
        ListView lstview=(ListView)findViewById(R.id.listview);
        @Override
        protected String doInBackground(String... args) {
      //      SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //  params.add(new BasicNameValuePair("user_name", id));
            try { //TODO method
                json = jsonParser.getJSONFromUrl(Constant.URL_CHECK+MainActivity.id+".json","GET", params);

                jsonArray = json.getJSONArray("response");
                int length = jsonArray.length();
                items=new String[length];
                String date, start, end, note;
                for (int i = 0; i < length; i++) {
                    JSONObject mJsonObject = jsonArray.getJSONObject(i);
                    date=mJsonObject.getString("date");date=date.substring(8,10)+"-"+date.substring(5,7)+"-"+date.substring(0,4);
                    start=mJsonObject.getString("start_time").substring(0,5);
                    end=mJsonObject.getString("end_time");
                    if(!end.equals("null")) end=end.substring(0,5);
                    note=mJsonObject.getString("status_user");
                    items[length-i-1]=date+"_"+start+"_"+end+"_"+note;
                }
                publishProgress("");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //        // Create an adapter to bind data to the ListView
            ListViewAdapters adapter=new ListViewAdapters(getApplicationContext(),R.layout.rowlayout,R.id.txtDate,items);
//        // Bind data to the ListView
            lstview.setAdapter(adapter);
            super.onProgressUpdate(values);
        }
    }
}
