package ewu.cse.shopmanagementsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Registration extends AppCompatActivity {
    EditText etdate, etusername, etname, etcontact, etpassword, etpasswordagain;
    RadioButton rbmale, rbfemale, rbmanager, rbworker;
    Button btsignup;
    String username = "", position = "", gender = "", password = "", join_date = "", contact = "", name = "", passwordagain = "";
    EmployeeHelper emp = new EmployeeHelper();

    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        etdate = findViewById(R.id.etdate);
        etusername = findViewById(R.id.etusername);
        etname = findViewById(R.id.etname);
        etcontact = findViewById(R.id.etcontact);
        etpassword = findViewById(R.id.etpassword);
        etpasswordagain = findViewById(R.id.etpasswordagain);
        rbmale = findViewById(R.id.rbmale);
        rbfemale = findViewById(R.id.rbfemale);
        rbmanager = findViewById(R.id.rbmanager);
        rbworker = findViewById(R.id.rbworker);
        btsignup = findViewById(R.id.btsignup);


        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Signup");
                signup();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        etdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Registration.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        etdate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void signup() {
        username = etusername.getText().toString().toLowerCase().trim();
        password = etpassword.getText().toString().toLowerCase().trim();
        contact = etcontact.getText().toString();
        join_date = etdate.getText().toString();
        name = etname.getText().toString();
        passwordagain = etpasswordagain.getText().toString();
        if (username.matches("")) {
            etusername.setError("Empty");
        } else {
            if(username.length()<=4){
                etusername.setError("At leas 5 letters");
            }else{
                emp.setUsername(username);
            }

        }
        if (name.matches("")) {
            etname.setError("Empty");
        } else {
            if(name.length()<=2){
                etname.setError("At least 3 letters");
            }else{
                emp.setName(name);
            }

        }
        if (password.matches("")) {
            etpassword.setError("Empty");
        } else {
            if(password.length()<=5){
                etpassword.setError("At least 6 letters");
            }
            else if (passwordagain.matches("")) {
                etpasswordagain.setError("Empty");
            } else {
                emp.setPassword(password);
            }
        }
        if (contact.matches("")) {
            etcontact.setError("Empty");
        } else {
            System.out.println(contact.length());
            if (contact.length()<=10){
                etcontact.setError("Minimum 11 charecter");
            }else{
                System.out.println(contact);
                emp.setContact(contact);
            }



        }

        if(join_date.matches("")){
            etdate.setError("Empty");
        }else{
            emp.setJoin_date(join_date);
        }

        if(rbmanager.isChecked()){
            position=rbmanager.getText().toString();
            emp.setPosition(position);
        }else if(rbworker.isChecked()){
            position=rbworker.getText().toString();
            emp.setPosition(position);
        }else{
            rbworker.setError("Empty");
        }
        if(rbmale.isChecked()){
            gender=rbmale.getText().toString();
            emp.setGender(gender);
        }else if(rbfemale.isChecked()){
            gender=rbfemale.getText().toString();
            emp.setGender(gender);
        }else{
            rbfemale.setError("Empty");
        }
        System.out.println(emp.dataCount());
        System.out.println(emp.toString());
        if(emp.dataCount()==0){
            EmployeeFirebase employeeFirebase=new EmployeeFirebase(emp);
            setNull();
            System.out.println(emp.toString());
            Intent intent =new Intent(Registration.this,Splashscreen.class);
            startActivity(intent);
            finish();
        }



    }

    public void setNull(){
        etdate.setText("");
        etusername.setText("");
        etname.setText("");
        etcontact.setText("");
        etpassword.setText("");
        etpasswordagain.setText("");
        rbmanager.setChecked(false);
        rbworker.setChecked(false);
        rbfemale.setChecked(false);
        rbmale.setChecked(false);
    }


}