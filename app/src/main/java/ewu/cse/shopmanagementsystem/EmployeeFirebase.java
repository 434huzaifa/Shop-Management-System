package ewu.cse.shopmanagementsystem;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EmployeeFirebase {
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    DatabaseReference dr = fd.getReference("Employee");

    public EmployeeFirebase(EmployeeHelper emp) {
        dr.child(emp.getUsername()).setValue(emp);
    }


}
