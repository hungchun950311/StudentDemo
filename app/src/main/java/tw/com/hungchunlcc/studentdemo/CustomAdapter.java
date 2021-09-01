package tw.com.hungchunlcc.studentdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter  extends BaseAdapter
{

    private ArrayList<BikeItem> listData;
    private LayoutInflater layoutInflater;

    public CustomAdapter(Context context, ArrayList<BikeItem> listData)
    {
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
        if (convertView == null)
            convertView = layoutInflater.inflate(R.layout.data_item,parent,false);
            System.out.println(position);
            BikeItem item = listData.get(position);
            TextView station = convertView.findViewById(R.id.tvstation);
            TextView rent = convertView.findViewById(R.id.tvrent);
            TextView space = convertView.findViewById(R.id.tvspace);
            station.setText(item.getStation());
            rent.setText(item.getRent());
            space.setText(item.getSpace());

            return  convertView;
    }
}
