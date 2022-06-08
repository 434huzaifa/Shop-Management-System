package ewu.cse.shopmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StockOnHand extends AppCompatActivity {
    TextView tvFood, tvNonFood, tvCategory, tvAllFood;
    ListView listView;
    LinearLayout errorLayout;
    ProductSql productSql = new ProductSql(StockOnHand.this);
    ProgressDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor Ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_on_hand);


        
        TextView BackButtonStockOnHand = findViewById(R.id.btnBackStockOnHand);

        sp = getSharedPreferences("StockOnHand", MODE_PRIVATE);
        Ed = sp.edit();


        tvFood = findViewById(R.id.tvFoodStock);
        tvNonFood = findViewById(R.id.tvNonfoodStock);
        tvCategory = findViewById(R.id.tvCateStock);
        listView = findViewById(R.id.listStock);
        tvAllFood = findViewById(R.id.tvAllFoodStock);
        errorLayout=findViewById(R.id.errorLayoutStock);
        tvCategory.setText("All");
        ArrayList<ProductHelper> productHelperArrayList3 = productSql.sqlToList("All");
        productListAdapter(productHelperArrayList3);

        tvAllFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCategory.setText("All");
                ArrayList<ProductHelper> productHelperArrayList3 = productSql.sqlToList("All");
                productListAdapter(productHelperArrayList3);
            }
        });


        tvFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCategory.setText("Food");
                ArrayList<ProductHelper> productHelperArrayList2 = productSql.sqlToList("Food");
                productListAdapter(productHelperArrayList2);
            }
        });
        tvNonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCategory.setText("Non Food");
                ArrayList<ProductHelper> productHelperArrayList1 = productSql.sqlToList("Non Food");
                productListAdapter(productHelperArrayList1);
            }
        });
        BackButtonStockOnHand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StockOnHand.this, Menu.class);
                startActivity(i);
                finish();
            }
        });


    }
    public void DataBaseThread() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//doInPreExcute
                        dialog = new ProgressDialog(StockOnHand.this);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.show();

                    }
                });
                //doInBackground
                productSql.FirebaseToSqlite();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //PostExcute
                        System.out.println("what");

                        ArrayList<ProductHelper> productHelperArrayList3 = productSql.sqlToList("All");
                        if (!productHelperArrayList3.isEmpty()) {
                            ProductAdapter pa = new ProductAdapter(StockOnHand.this, R.layout.product_row, productHelperArrayList3);
                            listView.setAdapter(pa);
                        } else {
                            Toast.makeText(StockOnHand.this, "No Data", Toast.LENGTH_SHORT).show();
                        }
                        tvCategory.setText("All");
                        dialog.cancel();
                    }
                });
            }
        });
    }
    public void productListAdapter(ArrayList<ProductHelper> pha) {

        if (!pha.isEmpty()) {
            listView.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            ProductAdapter pa = new ProductAdapter(StockOnHand.this, R.layout.product_row, pha);
            listView.setAdapter(pa);
        } else {
            listView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}