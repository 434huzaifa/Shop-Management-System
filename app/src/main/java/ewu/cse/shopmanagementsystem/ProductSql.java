package ewu.cse.shopmanagementsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductSql extends SQLiteOpenHelper {
    String tableName = "Product";

    public ProductSql(@Nullable Context context) {
        super(context, "Shop.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void FirebaseToSqlite() {
        deleteAllData();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Product");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        ProductHelper ph = ds.getValue(ProductHelper.class);
                        insertData(ph);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void insertData(ProductHelper ph) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", ph.getCategory() + ph.getProductName());
        cv.put("name", ph.getProductName());
        cv.put("price", ph.getPrice());
        cv.put("expDate", ph.getExpDate());
        cv.put("category", ph.getCategory());
        cv.put("quantity", ph.getQuantity());

        sd.insert(tableName, null, cv);
    }
    public void updateData(ProductHelper ph) {
        SQLiteDatabase sd = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id", ph.getCategory() + ph.getProductName());
        cv.put("name", ph.getProductName());
        cv.put("price", ph.getPrice());
        cv.put("expDate", ph.getExpDate());
        cv.put("category", ph.getCategory());
        cv.put("quantity", ph.getQuantity());
        sd.update(tableName,cv,null,null);
    }

    public ArrayList<ProductHelper> sqlToList(String data) {
        SQLiteDatabase sd = getReadableDatabase();
        ArrayList<ProductHelper> productHelperArrayList = new ArrayList<>();
        Cursor c;
        if (data.matches("All")) {
            c = sd.rawQuery("Select * from Product", null);
        } else if (!data.matches("Food") && !data.matches("Non Food")) {
            c = sd.rawQuery("Select * from Product where name =?", new String[]{data});
        } else {
            c = sd.rawQuery("Select * from Product where category =?", new String[]{data});
        }
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            ProductHelper ph = new ProductHelper(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            productHelperArrayList.add(ph);

        }
        c.close();
        return productHelperArrayList;
    }

    public ArrayList<ProductHelper> sqlToListForSell(){
        SQLiteDatabase sd = getReadableDatabase();
        ArrayList<ProductHelper> productHelperArrayList = new ArrayList<>();
        Cursor c;
        c = sd.rawQuery("Select * from Product", null);
        while (c.moveToNext()) {
            ProductHelper ph = new ProductHelper(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
            if(!ph.getQuantity().matches("0")){
                productHelperArrayList.add(ph);
            }
        }
        return productHelperArrayList;
    }


    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();
            db.delete(tableName, null, null);


    }
}
