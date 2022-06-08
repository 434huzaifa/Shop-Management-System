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

public class EmployeeSql extends SQLiteOpenHelper {
    public EmployeeSql(@Nullable Context context) {
        super(context, "Shop.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Product (id Text primary key, name text,price text,expDate text,category text,quantity text)");
        db.execSQL("CREATE TABLE Employee (username text primary key,name text,contact text,joinDate text,password text,gender text,position text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Employee ");
        onCreate(db);


    }

    public void FirebaseToSqlite() {
        deleteAllData();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference("Employee");
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        EmployeeHelper emp = ds.getValue(EmployeeHelper.class);
                        insertData(emp);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void insertData(EmployeeHelper emp) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", emp.getUsername());
        cv.put("name", emp.getName());
        cv.put("contact", emp.getContact());
        cv.put("joinDate", emp.getJoin_date());
        cv.put("password", emp.getPassword());
        cv.put("gender", emp.getGender());
        cv.put("position", emp.getPosition());
        db.insert("Employee", null, cv);
    }

    public boolean checkData(String data, String columnname) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor;
        if (columnname.matches("position")) {
            cursor = db.rawQuery("Select " + columnname + " from Employee where username =?", new String[]{data});
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                if (cursor.getString(0).matches("Manager")) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } else {
            cursor = db.rawQuery("Select " + columnname + " from Employee where " + columnname + " =?", new String[]{data});

        }

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            System.out.println(cursor.getString(0));
            if (cursor.getString(0).matches(data)) {
                cursor.close();
                return true;
            }
        }
        cursor.close();
        return false;
    }

    public ArrayList<EmployeeHelper> sqlToList() {
        SQLiteDatabase sd = getReadableDatabase();
        ArrayList<EmployeeHelper> empArrayList = new ArrayList<>();
        Cursor c = sd.rawQuery("Select * from Employee", null);
        System.out.println(c.getCount());
        while (c.moveToNext()) {
            EmployeeHelper emp = new EmployeeHelper(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));
            empArrayList.add(emp);

        }
        return empArrayList;
    }

    public ArrayList<EmployeeHelper> listToList(String x) {
        ArrayList<EmployeeHelper> empArrayList = new ArrayList<>();
        ArrayList<EmployeeHelper> empTempArrayList = sqlToList();
        for (int i = 0; i < empTempArrayList.size(); i++) {
            if (empTempArrayList.get(i).getUsername().matches(x) || empTempArrayList.get(i).getName().matches(x) ||
                    empTempArrayList.get(i).getJoin_date().matches(x) || empTempArrayList.get(i).getContact().matches(x) ||
                    empTempArrayList.get(i).getGender().matches(x) || empTempArrayList.get(i).getPosition().matches(x)) {
                empArrayList.add(empTempArrayList.get(i));
                empTempArrayList.get(i).toString();
            }
        }
        return empArrayList;
    }


    public void deleteAllData() {
        SQLiteDatabase db = getWritableDatabase();

        db.delete("Employee", null, null);


    }
}
