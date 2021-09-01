package tw.com.hungchunlcc.studentdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BusAdapter extends BaseAdapter
{
    private ArrayList<BusItem> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public BusAdapter(Context context,ArrayList<BusItem> listData)
    {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewAction action;
        ButtonListener btnListener = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.bus_item, parent,false);


            btnListener = new ButtonListener(position);
            action = new ViewAction();

            action.start = convertView.findViewById(R.id.btnstart);
            action.end = convertView.findViewById(R.id.btnend);

            action.station = convertView.findViewById(R.id.txtstation);
            action.txtS = convertView.findViewById(R.id.txtstart);
            action.txtE = convertView.findViewById(R.id.txtsend);

            action.start.setOnClickListener(btnListener);
            action.end.setOnClickListener(btnListener);
            convertView.setTag(action);
            action.start.setTag(position);
            action.end.setTag(position);
        }else {
            action = (ViewAction) convertView.getTag();
        }
        BusItem item = listData.get(position);
        action.station.setText(item.getStation());
        action.txtS.setText(item.getStartS());
        action.txtE.setText(item.getEndS());
        action.start.setTag(position);
        action.end.setTag(position);


        return convertView;
    }

    static class ViewAction
    {
        Button start,end;
        TextView station,txtS,txtE;
    }

    private class ButtonListener implements View.OnClickListener
    {
        int postion;
        public ButtonListener(int postion)
        {
            this.postion = postion;
        }
        public void onClick(View view)
        {
            BusItem item = null;
            if (view.getId() == R.id.btnstart){//去程

                int p = (Integer)view.getTag();
                item = listData.get(postion);
                Bundle bundle = new Bundle();
                bundle.putString("RID",item.getRid());
                bundle.putInt("goBack",1);
                Intent intent = new Intent(context,BusStation.class);
                intent.putExtras(bundle);
                context.startActivity(intent);

            }else {//回程
                int p = (Integer)view.getTag();
                item = listData.get(p);
                Bundle bundle = new Bundle();
                bundle.putString("RID",item.getRid());
                bundle.putInt("goBack",2);
                Intent intent = new Intent(context,BusStation.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        }
    }
}
