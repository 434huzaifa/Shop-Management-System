package ewu.cse.shopmanagementsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    Button btlogin;
    TextView btsignup;
    EditText etusername, etpassword;
    CheckBox rememberme;
    String username = "", password = "";
    SharedPreferences sp;
    SharedPreferences.Editor Ed;
    ProgressDialog dialog;
    EmployeeSql es = new EmployeeSql(Login.this);
    ProductSql ps = new ProductSql(Login.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etusername = findViewById(R.id.et_login_username);
        etpassword = findViewById(R.id.et_login_password);
        btsignup = findViewById(R.id.btsignup);
        rememberme = findViewById(R.id.cb_login_remember);
        sp = getSharedPreferences("Login", MODE_PRIVATE);
        Ed = sp.edit();

        String unm = sp.getString("Unm", null);
        String pass = sp.getString("Psw", null);
        boolean state = sp.getBoolean("sta", false);
        etpassword.setText(pass);
        etusername.setText(unm);
        rememberme.setChecked(state);
        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);
            }
        });
        btlogin = findViewById(R.id.btlogin);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonLogin();
            }
        });
    }

    private void buttonLogin() {
        boolean boolUsername = false, boolPassword = false, boolposition = false;
        username = etusername.getText().toString();
        password = etpassword.getText().toString();
        if (username.matches("")) {
            etusername.setError("Empty");
        } else {
            boolUsername = es.checkData(username, "username");
            boolposition = es.checkData(username, "position");
            Ed.putBoolean("pos", boolposition);

        }
        if (password.matches("")) {
            etpassword.setError("Empty");
        } else {
            boolPassword = es.checkData(password, "password");
            if (boolPassword && boolUsername) {
                if (rememberme.isChecked()) {
                    autoLogin(username, password, true);
                } else {
                    autoLogin(null, null, false);
                }

                Intent intent = new Intent(Login.this, Menu.class);
                startActivity(intent);
            } else {
                etpassword.setError("Login Credential did not match");
                etusername.setError("Login Credential did not match");
                System.out.println("passStatus "+boolPassword+" userStatus "+boolUsername);
            }
        }

    }

    public void dataBase() {

        es.FirebaseToSqlite();
        ps.FirebaseToSqlite();
    }

    private void autoLogin(String username, String password, boolean state) {
        Ed.putString("Unm", username);
        Ed.putString("Psw", password);
        Ed.putBoolean("sta", state);
        Ed.apply();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}