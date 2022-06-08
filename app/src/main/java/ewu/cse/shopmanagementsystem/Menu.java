package ewu.cse.shopmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView StockOnHandButton = findViewById(R.id.btnStockOnHand);
        StockOnHandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( Menu.this,StockOnHand.class);
                startActivity(i);

            }
        });

        TextView AddProductButton = findViewById(R.id.btnAddProduct);
        AddProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( Menu.this, AddProduct.class);
                startActivity(i);

            }
        });
        TextView SearchButton = findViewById(R.id.btnMenuSearch);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this, ProductListSearch.class);
                startActivity(i);

            }
        });

        TextView SellProductButton = findViewById(R.id.btnSellProduct);
        SellProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new  Intent (Menu.this, SellProduct.class);
                startActivity(i);

            }
        });
        TextView EmployeeButton = findViewById(R.id.btnEmployee);
        EmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Menu.this,Employee.class);
                startActivity(i);

            }
        });

        TextView ExitButton = findViewById(R.id.btnExitMenu);
        ExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public void logout(View view) {
        SharedPreferences sp;
        SharedPreferences.Editor Ed;
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        Ed = sp.edit();
        Ed.putString("Unm", "");
        Ed.putString("Psw", "");
        Ed.putBoolean("sta", false);
        Ed.apply();
        Intent intent =new Intent(Menu.this,Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}