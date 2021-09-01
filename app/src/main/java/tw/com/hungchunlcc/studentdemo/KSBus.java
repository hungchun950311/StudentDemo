package tw.com.hungchunlcc.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class KSBus extends AppCompatActivity
{
    private OkHttpClient client;
    ListView buslist;
    ArrayList<BusItem> listData = null;
    private String url = "http://ibus.tbkc.gov.tw/xmlbus/StaticData/GetRoute.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ksbus);
        client = new OkHttpClient();
        buslist = findViewById(R.id.buslist);

        parserXML();
    }

    private void parserXML()
    {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

                final String busdata = response.body().string();
                final InputStream stream = new ByteArrayInputStream(busdata.getBytes(StandardCharsets.UTF_8.name()));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listData = parser(stream);

                        buslist.setAdapter(new BusAdapter(KSBus.this,listData));

                    }
                });
            }
        });
    }

    public ArrayList<BusItem> parser(InputStream input)
    {
        String tagName = null;
        ArrayList<BusItem> arrayList = new ArrayList<>();
        int find = 0;
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            //定義XML 解析器
            XmlPullParser parser = factory.newPullParser();

            //將XML數據載入
            parser.setInput(new InputStreamReader(input));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("Route") & find == 0){
                            find++;
                            BusItem busItem = new BusItem();
                            busItem.setStation(parser.getAttributeValue(null,"nameZh"));
                            busItem.setRoute(parser.getAttributeValue(null,"ddesc"));
                            busItem.setStartS(parser.getAttributeValue(null,"departureZh"));
                            busItem.setEndS(parser.getAttributeValue(null,"destinationZh"));
                            busItem.setRid(parser.getAttributeValue(null,"ID"));
                            arrayList.add(busItem);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("Route")){
                            find = 0;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        break;
                }
                eventType = parser.next();  //處理下一個事件
            }
            return arrayList;

        }catch (Exception e){
            e.printStackTrace();
        }
        return arrayList;
    }

}