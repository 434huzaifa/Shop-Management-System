package ewu.cse.shopmanagementsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Employee extends AppCompatActivity {
    ListView empList;
    TextView tvGo, tvBack;
    EditText etSearch;
    LinearLayout errorLayout;
    SharedPreferences sp;
    ArrayList<EmployeeHelper> empArrayList = new ArrayList<>();
    EmployeeSql es = new EmployeeSql(Employee.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        etSearch = findViewById(R.id.etEmpSearch);
        tvBack = findViewById(R.id.tvBackEmp);
        tvGo = findViewById(R.id.tvEmpGo);
        errorLayout=findViewById(R.id.errorLayoutEmp);

        empList = findViewById(R.id.employeeList);
        empArrayList = es.sqlToList();
        System.out.println(empArrayList.isEmpty());
        empListAdapter(empArrayList);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean f = true;
                String searchKeyword = "";
                searchKeyword = etSearch.getText().toString();
                if (searchKeyword.matches("")) {
                    etSearch.setError("Empty");
                } else if (tvGo.getText().toString().matches("Go")) {
                    ArrayList<EmployeeHelper> searchEmployeeList = es.listToList(searchKeyword);
                    System.out.println(searchEmployeeList.isEmpty());
                    empListAdapter(searchEmployeeList);
                    tvGo.setText("Clear");
                } else if (tvGo.getText().toString().matches("Clear")) {
                    etSearch.setText("");
                    empListAdapter(empArrayList);
                    tvGo.setText("Go");
                }
            }
        });
    }

    public void empListAdapter(ArrayList<EmployeeHelper> aeh) {

        if (!aeh.isEmpty()) {
            errorLayout.setVisibility(View.GONE);
            empList.setVisibility(View.VISIBLE);
            EmployeeAdapter ea = new EmployeeAdapter(Employee.this, R.layout.employee_row, aeh);
            empList.setAdapter(ea);
        } else {
            errorLayout.setVisibility(View.VISIBLE);
            empList.setVisibility(View.GONE);

        }
    }
}