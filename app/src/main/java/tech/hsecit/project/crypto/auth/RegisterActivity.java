package tech.hsecit.project.crypto.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

    public static final String URL_SERVER ="http://192.168.1.5" ;
    TextInputLayout email,password;
    Button register;
    FirebaseAuth mAuth ;
    AccountGenerate account;
    ProgressDialog progressDialog;
    CoordinatorLayout snack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.registerBtn);
        snack= findViewById(R.id.snack_b);


        //call retrofit
        Retrofit retrofitAccount = new Retrofit.Builder().baseUrl(URL_SERVER).build();
        account = retrofitAccount.create(AccountGenerate.class);

        ///////////End of retrofit
        // call firebase
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        //
        progressDialog.setView(getLayoutInflater().inflate(R.layout.progress_dialog,null));
        View root =getLayoutInflater().inflate(R.layout.progress_dialog,null);
        TextView process = root.findViewById(R.id.process);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getEditText().getText().toString().equals("")||password.getEditText().getText().toString().equals("")){
                    Snackbar.make(snack, R.string.succ_transaction, Snackbar.LENGTH_SHORT)
                            .show();
                }else{
                    progressDialog.show();
                    progressDialog.setTitle("startit");
                    Log.i("Register process","call register info");
                    newRegister(process);
                    progressDialog.hide();
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
        super.onBackPressed();
    }

    private void newRegister(TextView process) {
        // fetch data from textinpit
        String EmailText = email.getEditText().getText().toString();
        String PasswordText = password.getEditText().getText().toString();

        progressDialog.setTitle("Create new Account in dbase");
        process.setText("Create new Account in dbase");
        Log.i("Register process","Create user with email");

        mAuth.createUserWithEmailAndPassword(EmailText,PasswordText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.setTitle("connect To the server");
                process.setText("connect To the server");
                Log.i("Register process","end of creation");

                if (task.isSuccessful()){
                   // TODO add retrofit here

                    process.setText("generate account data");
                    Log.i("Register process","succesful creation");

                    Call<Void> call = account.generateAccount(mAuth.getUid());
                    Log.i("Register process","Generate account data");
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {

                            process.setText("Ready to start your activity");
                            Log.i("Register process","suucessfully generated");
                            progressDialog.setTitle("succesfully");

                            Toast.makeText(RegisterActivity.this, "Succesfully Registered...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);


                            process.setText("redirect to profile");
                            Log.i("Register process","go to home");
                            progressDialog.setTitle("redirect to profile");

                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.i("Register process","server accounts error");

                            Toast.makeText(RegisterActivity.this, "Failed to add new account in srver ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //end of retrofit body

                }else {
                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    Log.i("Register process","database error");

                }
            }
        });
    }
}
