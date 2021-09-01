package tw.com.hungchunlcc.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String url ="http://tbike-data.tainan.gov.tw/Service/StationStatus/Json";
    private ListView listView;
    private OkHttpClient client;

    private ArrayList<BikeItem> allData = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.bikelist);
        client = new OkHttpClient();
        getJason();
    }

    private void getJason()
    {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                final  String res = response.body().string();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //解析Json
                            JSONArray arr = new JSONArray(res);
                            for (int i=0 ; i < arr.length() ; i++){
                                JSONObject data = arr.getJSONObject(i);
                                BikeItem item = new BikeItem();
                                item.setStation(data.getString("StationName"));
                                item.setRent(""+data.getInt("AvaliableBikeCount"));
                                item.setSpace(""+data.getInt("AvaliableSpaceCount"));
                                item.setLat(data.getDouble("Latitude"));
                                item.setLng(data.getDouble("Longitude"));
                                allData.add(item);
                            }

                            listView.setAdapter(new CustomAdapter(getApplicationContext(),allData));
                            bikelistener();


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    private void bikelistener()
    {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                Bundle bundle = new Bundle();
                BikeItem item = allData.get(position);
                bundle.putString("Station",item.getStation());
                bundle.putDouble("Lat",item.getLat());
                bundle.putDouble("Lng",item.getLng());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }
}