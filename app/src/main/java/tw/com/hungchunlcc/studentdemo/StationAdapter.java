package tw.com.hungchunlcc.studentdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class StationAdapter extends BaseAdapter
{
    private ArrayList<BusItem> listData;
    private LayoutInflater layoutInflater;
    Context context;

    public StationAdapter(Context context,ArrayList<BusItem> listData)
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
        BusAdapter.ViewAction action;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.bus_item, parent,false);
            action = new BusAdapter.ViewAction();
            action.station = convertView.findViewById(R.id.txtstation);
            convertView.setTag(action);
        }else {
            action = (BusAdapter.ViewAction) convertView.getTag();
        }
        BusItem item = listData.get(position);
        action.station.setText(item.getStation());
        return convertView;
    }

    static class ViewAction
    {
        Button start,end;
        TextView station,txtS,txtE;
    }


}

