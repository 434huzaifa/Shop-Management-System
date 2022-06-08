package ewu.cse.shopmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductListSearch extends AppCompatActivity {
    TextView btSearch;
    EditText etSearch;
    ListView productList;
    LinearLayout errorLayout;
    ArrayList<ProductHelper> ph = new ArrayList<>();
    ProductSql productSql = new ProductSql(ProductListSearch.this);
    SharedPreferences sp;
    SharedPreferences sp1;
    SharedPreferences.Editor Ed;
    boolean manager = false;
    ProductAdapter pa;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        sp1 = getSharedPreferences("StockOnHand", MODE_PRIVATE);
        Ed = sp1.edit();

        TextView BackButtonProductList = findViewById(R.id.btnBackProductList);
        btSearch = findViewById(R.id.btnSearchProductList);
        etSearch = findViewById(R.id.etSearchSearch);
        productList = findViewById(R.id.listProduct);
        errorLayout =findViewById(R.id.errorLayoutSearch);


        manager=sp.getBoolean("pos",false);
        ph = productSql.sqlToList("All");
        searchAdapterList();
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = etSearch.getText().toString();
                if (!search.matches("")) {
                    ph.clear();
                    if (btSearch.getText().toString().matches("Go")) {
                        ph = productSql.sqlToList(search);
                        searchAdapterList();
                        btSearch.setText("Clear");
                    } else {
                        etSearch.setText("");
                        ph.clear();
                        ph = productSql.sqlToList("All");
                        searchAdapterList();
                        btSearch.setText("Go");
                    }
                } else {
                    etSearch.setError("Empty");
                }

            }
        });
        if (manager) {
            productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ProductHelper productHelper = ph.get(position);
                    Intent intent = new Intent(ProductListSearch.this, AddProduct.class);
                    intent.putExtra("productName", productHelper.getProductName());
                    intent.putExtra("productQuantity", productHelper.getQuantity());
                    intent.putExtra("productPrice", productHelper.getPrice());
                    intent.putExtra("productExpData", productHelper.getExpDate());
                    intent.putExtra("productCategory", productHelper.getCategory());
                    startActivity(intent);
                    finish();
                }
            });
        }


        BackButtonProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProductListSearch.this, Menu.class);
                startActivity(i);
                finish();
            }
        });
    }
        public void searchAdapterList() {
        if (!ph.isEmpty()) {
            productList.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            ProductAdapter sa = new ProductAdapter(ProductListSearch.this, R.layout.product_row, ph);
            productList.setAdapter(sa);
        }else{
            productList.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }


}