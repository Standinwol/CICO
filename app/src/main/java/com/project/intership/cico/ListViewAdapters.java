package com.project.intership.cico;

//import android.app.Activity;
//import android.database.DataSetObserver;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static com.project.intership.cico.Constants.FIRST_COLUMN;
//import static com.project.intership.cico.Constants.FOURTH_COLUMN;
//import static com.project.intership.cico.Constants.SECOND_COLUMN;
//import static com.project.intership.cico.Constants.THIRD_COLUMN;
//
///**
// * Created by toant on 1/10/2017.
// */
//
//public class ListViewAdapters implements ListAdapter {
//    public ArrayList<HashMap<String, String>> list;
//    Activity activity;
//    TextView txtFirst;
//    TextView txtSecond;
//    TextView txtThird;
//    TextView txtFourth;
//    public ListViewAdapters(Activity activity, ArrayList<HashMap<String, String>> list){
//        super();
//        this.activity=activity;
//        this.list=list;
//    }
//
//    @Override
//    public void registerDataSetObserver(DataSetObserver observer) {
//
//    }
//
//    @Override
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//
//    }
//
//    @Override
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        // TODO Auto-generated method stub
//        return list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        // TODO Auto-generated method stub
//        return 0;
//    }
//
//    @Override
//    public boolean hasStableIds() {
//        return false;
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // TODO Auto-generated method stub
//
//
//
//        LayoutInflater inflater=activity.getLayoutInflater();
//
//        if(convertView == null){
//
//            convertView=inflater.inflate(R.layout.listview, null);
////
////            txtFirst=(TextView) convertView.findViewById(R.id.tv11);
////            txtSecond=(TextView) convertView.findViewById(R.id.tv12);
////            txtThird=(TextView) convertView.findViewById(R.id.tv13);
////            txtFourth=(TextView) convertView.findViewById(R.id.tv14);
//
//        }
//
//        HashMap<String, String> map=list.get(position);
//        txtFirst.setText(map.get(FIRST_COLUMN));
//        txtSecond.setText(map.get(SECOND_COLUMN));
//        txtThird.setText(map.get(THIRD_COLUMN));
//        txtFourth.setText(map.get(FOURTH_COLUMN));
//
//        return convertView;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return 0;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 0;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }
//}

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapters extends ArrayAdapter<String> {
    int groupid;
    String[] item_list;
    ArrayList<String> desc;
    Context context;
    public ListViewAdapters(Context context, int vg, int id, String[] item_list){
        super(context,vg, id, item_list);
        this.context=context;
        groupid=vg;
        this.item_list=item_list;

    }
    // Hold views of the ListView to improve its scrolling performance
    static class ViewHolder {
        public TextView textDate;
        public TextView textTCI;
        public TextView textTCO;
        public TextView textNote;


    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        // Inflate the rowlayout.xml file if convertView is null
        if(rowView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView= inflater.inflate(groupid, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textDate= (TextView) rowView.findViewById(R.id.txtDate);
            viewHolder.textTCI = (TextView) rowView.findViewById(R.id.txtTCI);
            viewHolder.textTCO =(TextView) rowView.findViewById(R.id.txtTCO);
            viewHolder.textNote =(TextView) rowView.findViewById(R.id.txtNote);
            rowView.setTag(viewHolder);

        }
        // Set text to each TextView of ListView item
        String[] items=item_list[position].split("__");
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.textDate.setText(items[0]);
        holder.textTCI.setText(items[1]);
        holder.textTCO.setText(items[2]);
        holder.textNote.setText(items[3]);
        return rowView;
    }

}