package tech.hsecit.project.crypto.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import tech.hsecit.project.crypto.R;
import tech.hsecit.project.crypto.home.HomeActivity;
import tech.hsecit.project.crypto.retrofit_api.AccountGenerate;

public class RegisterActivity extends AppCompatActivity {

    public static final String URL_SERVER ="https://aqueous-sea-37505.herokuapp.com/" ;
    TextInputLayout email,password;
    Button register;
    FirebaseAuth mAuth ;
    AccountGenerate account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.registerBtn);

        //call retrofit
        Retrofit retrofitAccount = new Retrofit.Builder().baseUrl(URL_SERVER).build();
        account = retrofitAccount.create(AccountGenerate.class);

        ///////////End of retrofit
        // call firebase
        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newRegister();
            }
        });

    }

    private void newRegister() {
        // fetch data from textinpit
        String EmailText = email.getEditText().getText().toString();
        String PasswordText = password.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(EmailText,PasswordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                   // TODO add retrofit here
                    Call<Void> call = account.generateAccount(mAuth.getUid());
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(RegisterActivity.this, "Succesfully Registered...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(RegisterActivity.this, "Failed to add new account in srver ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //end of retrofit body

                }else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
