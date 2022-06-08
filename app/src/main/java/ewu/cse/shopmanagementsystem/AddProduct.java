package ewu.cse.shopmanagementsystem;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddProduct extends AppCompatActivity {
    String[] Category;
    EditText etCategory, etProductName, etExpDate, etPrice, etQuantity;
    Button btBack, btAdd, btDelete;
    ProductFirebase productFirebase=new ProductFirebase();
    Calendar myCalendar = Calendar.getInstance();
    SharedPreferences sp;
    SharedPreferences.Editor Ed;
    SharedPreferences sp1;
    SharedPreferences.Editor Ed1;
    private Spinner spinnerCategory;
    private TextView textViewCategory;
    private String productName = "", price = "", expDate = "", category = "", quantity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        sp = getSharedPreferences("StockOnHand", MODE_PRIVATE);
        Ed = sp.edit();
        sp1 = getSharedPreferences("Login", MODE_PRIVATE);
        Ed1 = sp1.edit();

        etProductName = findViewById(R.id.etProName);
        etExpDate = findViewById(R.id.etDatePro);
        etPrice = findViewById(R.id.etPricePro);
        etQuantity = findViewById(R.id.etQuanPro);
        btAdd = findViewById(R.id.btAddPro);
        btBack = findViewById(R.id.btBackPro);
        btDelete = findViewById(R.id.btDeletePro);
        btDelete.setVisibility(View.GONE);
        Category = getResources().getStringArray(R.array.Category);

        spinnerCategory = findViewById(R.id.SpinnerAddProduct);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_category, R.id.textViewSpinnerCategory, Category);
        spinnerCategory.setAdapter(adapter);

        Intent intent=getIntent();
        if (intent.hasExtra("productName")){
            etProductName.setText(intent.getStringExtra("productName"));
            etQuantity.setText(intent.getStringExtra("productQuantity"));
            etPrice.setText(intent.getStringExtra("productPrice"));
            etExpDate.setText(intent.getStringExtra("productExpData"));
            if(intent.getStringExtra("productCategory").matches("Food")){
                spinnerCategory.setSelection(0);
            }else{
                spinnerCategory.setSelection(0);
            }
            btDelete.setVisibility(View.VISIBLE);
            btAdd.setText("Update");
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        etExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddProduct.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductHelper ph=fetchValue();
                if (ph.dataCount()==5){
                    Dialog dialog=new Dialog(AddProduct.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.delete_box);
                    TextView tvTitle =dialog.findViewById(R.id.deleteTitle);
                    TextView tvDelete =dialog.findViewById(R.id.btnDelete);
                    TextView tvCancel =dialog.findViewById(R.id.btnCancel);
                    tvTitle.setText("Do you want to delete "+ph.getProductName()+" ?");
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.show();
                    tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    tvDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            productFirebase.deleteData(ph);
                            dialog.dismiss();
                            Intent intent1 = new Intent(AddProduct.this, ProductListSearch.class);
                            startActivity(intent1);
                            finish();

                        }
                    });


                }

            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductHelper ph= fetchValue();
                System.out.println(ph.dataCount());
                if (ph.dataCount()==5){
                    productFirebase.insertData(ph);
                    setNull();
                }
                if(btAdd.getText().toString().matches("Update")){
                    btAdd.setText("Add");
                    Intent intent1 = new Intent(AddProduct.this, ProductListSearch.class);
                    startActivity(intent1);
                    finish();
                }else{
                    Intent intent = new Intent(AddProduct.this, StockOnHand.class);
                    startActivity(intent);
                    finish();
                }


            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProduct.this, Menu.class);
                startActivity(intent) ;
                finish();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        etExpDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private ProductHelper fetchValue() {
        ProductHelper ph = new ProductHelper();
        category = String.valueOf(spinnerCategory.getSelectedItemId());
        if (category.matches("0")) {
            ph.setCategory("Food");
        } else {
            ph.setCategory("Non Food");
        }

        if (isEmpty(etProductName)) {

        } else {
            productName = getString(etProductName);
            if (productName.length() >= 2) {
                ph.setProductName(productName);
            } else {
                etProductName.setError("At least 2 letter");
            }
        }
        if (etExpDate.getText().toString().matches("")) {
            ph.setExpDate("Infinity");
        } else {
            expDate = getString(etExpDate);
            ph.setExpDate(expDate);
        }
        if (isEmpty(etPrice)) {

        } else {
            price = getString(etPrice);
            ph.setPrice(price);
        }
        if (isEmpty(etQuantity)) {

        } else {
            quantity = getString(etQuantity);
            ph.setQuantity(quantity);
        }

        ph.setProductId(ph.getProductName(), ph.getCategory());
        return ph;

    }

    private void setNull() {
        etProductName.setText("");
        etExpDate.setText("");
        etPrice.setText("");
        etQuantity.setText("");
    }

    private boolean isEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            editText.setError("Empty");
            return true;
        } else {
            return false;
        }
    }

    private String getString(EditText editText) {
        return editText.getText().toString().trim();
    }
}