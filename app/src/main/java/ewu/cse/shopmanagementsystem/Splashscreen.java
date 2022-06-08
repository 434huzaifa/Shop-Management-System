package ewu.cse.shopmanagementsystem;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Splashscreen extends AppCompatActivity {
    EmployeeSql es = new EmployeeSql(Splashscreen.this);
    ProductSql ps = new ProductSql(Splashscreen.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);



            es.FirebaseToSqlite();
            ps.FirebaseToSqlite();



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Splashscreen.this, "Hi", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Splashscreen.this,Login.class);
                startActivity(intent);
            }
        }, 3000);
    }

}