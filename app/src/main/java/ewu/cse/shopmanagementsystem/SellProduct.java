package ewu.cse.shopmanagementsystem;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SellProduct extends AppCompatActivity {
    EditText etSearch;
    TextView tvGo, tvBack, tvTotal, tvDone, tvCart,tvShop;
    ListView listSell;
    LinearLayout totalLayout,errorLayout;
    boolean flag = true;
    int quantity = 0;
    float totalPrice = 0;
    ArrayList<ProductHelper> productHelperArrayList = new ArrayList<>();
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    ProductSql ps = new ProductSql(SellProduct.this);
    ArrayList<Integer> pos = new ArrayList<Integer>();
    int posid = 0;
    int cc=0;
    SharedPreferences sp;
    SharedPreferences.Editor Ed;
    SharedPreferences sp1;
    SharedPreferences.Editor Ed1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_product_shop);

        sp = getSharedPreferences("StockOnHand", MODE_PRIVATE);
        Ed = sp.edit();
        sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        Ed1 = sp1.edit();

        productHelperArrayList = ps.sqlToListForSell();
        Customer e=new Customer("Name","Quantity","");
        customerArrayList.add(e);
        etSearch = findViewById(R.id.etSearchSell);
        tvGo = findViewById(R.id.tvGoSell);
        tvBack = findViewById(R.id.tvBackSell);
        tvDone = findViewById(R.id.tvDoneSell);
        listSell = findViewById(R.id.listSell);
        tvCart = findViewById(R.id.tvCartSell);
        tvCart.setText("Cart("+Integer.toString(cc)+")");
        totalLayout = findViewById(R.id.layoutTotalSell);
        tvTotal = findViewById(R.id.tvTotalSell);
        tvShop = findViewById(R.id.tvShopSell);
        errorLayout=findViewById(R.id.errorLayoutSell);
        tvTotal.setText("0");
        totalLayout.setVisibility(View.GONE);
        sellAdapterList();
        if (flag) {
            listSell.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    posid = position;
                    showCustomDialog();
                }
            });
        }

        tvShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalLayout.setVisibility(View.GONE);
                sellAdapterList();
            }
        });
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pos.isEmpty()){
                    updateFirebase();
                }
                Intent intent = new Intent(SellProduct.this, Menu.class);
                startActivity(intent);
                finish();

            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellProduct.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalLayout.setVisibility(View.GONE);
                flag = true;
                String search = etSearch.getText().toString();
                if (!search.matches("")) {
                    productHelperArrayList.clear();
                    if (tvGo.getText().toString().matches("Go")) {
                        productHelperArrayList = ps.sqlToList(search);
                        sellAdapterList();
                        tvGo.setText("Clear");
                    } else {
                        etSearch.setText("");
                        productHelperArrayList.clear();
                        productHelperArrayList = ps.sqlToList("All");
                        sellAdapterList();
                        tvGo.setText("Go");
                    }
                } else {
                    etSearch.setError("Empty");
                }

            }
        });

        tvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalLayout.setVisibility(View.VISIBLE);
                if (customerArrayList.isEmpty()) {
                    tvCart.setError("Nothing");
                    CustomerAdapter ca = new CustomerAdapter(SellProduct.this, R.layout.cart_row, customerArrayList);
                    listSell.setAdapter(ca);
                } else {
                    tvTotal.setText(Float.toString(totalPrice));
                    flag = false;
                    CustomerAdapter ca = new CustomerAdapter(SellProduct.this, R.layout.cart_row, customerArrayList);
                    listSell.setAdapter(ca);
                }

            }
        });
    }

    public void showCustomDialog() {
        Dialog dialog = new Dialog(SellProduct.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.sell_box);
        EditText etQuan = dialog.findViewById(R.id.boxQuan);
        TextView tvBack = dialog.findViewById(R.id.boxBack);
        TextView tvAdd = dialog.findViewById(R.id.boxAdd);
        dialog.show();
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etQuan.getText().toString().matches("")) {
                    etQuan.setError("Empty");
                } else {
                    quantity = Integer.parseInt(etQuan.getText().toString());
                    change();
                    dialog.dismiss();
                }

            }

            public void change() {
                System.out.println(quantity);
                if (quantity != 0) {
                    ProductHelper p = productHelperArrayList.get(posid);
                    int q = Integer.parseInt(p.getQuantity());
                    int t = q - quantity;
                    System.out.println("t" + t);
                    if (t >= 0) {
                        pos.add(posid);
                        productHelperArrayList.get(posid).setQuantity(Integer.toString(t));
                        float f = (float) quantity * Float.parseFloat(p.getPrice());
                        totalPrice += f;
                        System.out.println(productHelperArrayList.get(posid).toString());
                        Customer c = new Customer(p.getProductName(), Integer.toString(quantity), Float.toString(f));
                        cc++;
                        tvCart.setText("Cart("+Integer.toString(cc)+")");
                        System.out.println(c.toString());
                        customerArrayList.add(c);
                        if (t==0){
                            productHelperArrayList.remove(posid);
                        }
                        quantity = 0;
                        sellAdapterList();
                    } else {
                        Toast.makeText(SellProduct.this, "More then stock", Toast.LENGTH_SHORT).show();
                        quantity = 0;
                    }
                } else {
                    Toast.makeText(SellProduct.this, "Nothing Sold", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void updateFirebase() {
        for (int i = 0; i < pos.size(); i++) {
            ProductFirebase pf = new ProductFirebase(productHelperArrayList.get(pos.get(i)));
        }
        ps.FirebaseToSqlite();
    }


    public void sellAdapterList() {

        if (!productHelperArrayList.isEmpty()) {
            listSell.setVisibility(View.VISIBLE);
            errorLayout.setVisibility(View.GONE);
            ProductAdapter sa = new ProductAdapter(SellProduct.this, R.layout.product_row, productHelperArrayList);
            listSell.setAdapter(sa);
        }else{
            listSell.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}