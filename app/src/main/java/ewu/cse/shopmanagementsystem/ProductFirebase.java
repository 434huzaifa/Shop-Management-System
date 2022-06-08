package ewu.cse.shopmanagementsystem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProductFirebase {
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    DatabaseReference dr = fd.getReference("Product");

    public ProductFirebase() {

    }
    public ProductFirebase(ProductHelper ph) {
        insertData(ph);
    }
    public void insertData(ProductHelper ph){
        dr.child(ph.getCategory()+ph.getProductName()).setValue(ph);
    }
    public void deleteData(ProductHelper ph){
        dr.child(ph.getCategory()+ph.getProductName()).removeValue();
    }
    public void checkNewValue(){

    }
}
