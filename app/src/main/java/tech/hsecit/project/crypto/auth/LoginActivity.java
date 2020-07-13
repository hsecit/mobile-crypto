package tech.hsecit.project.crypto.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import tech.hsecit.project.crypto.R;
import tech.hsecit.project.crypto.home.HomeActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout email,password;
    Button login  ;
    TextView register_now;
    ImageButton  google_login;
    ProgressDialog progressDialog;
    /// cals firebase com
    FirebaseAuth mAuth ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login = findViewById(R.id.loginBtn);
        login.setOnClickListener(this);
        register_now = findViewById(R.id.register_now);
        register_now.setOnClickListener(this);
        google_login = findViewById(R.id.logn_google_btn);
        google_login.setOnClickListener(this);
        // init firebase
        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                LoginEmailAddr();
                break;
            case R.id.register_now:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            case R.id.logn_google_btn:
                LoginWithGoole();
                break;
        }
    }

    private void LoginWithGoole() {



    }

    private void LoginEmailAddr() {
        String EmailText = email.getEditText().getText().toString();
        String PasswordText =  password.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(EmailText,PasswordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });
    }
}
