package tw.com.hungchunlcc.studentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MenuPage extends AppCompatActivity
{
    private Button btnbike,btnbus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);

        btnbike = findViewById(R.id.btnbike);
        btnbus = findViewById(R.id.btnbus);

        btnbike.setOnClickListener(v->{
            Intent intent = new Intent(MenuPage.this,MainActivity.class);
            startActivity(intent);
        });

        btnbus.setOnClickListener(v->{
            Intent intent =new Intent(MenuPage.this,KSBus.class);
            startActivity(intent);
        });
    }
}