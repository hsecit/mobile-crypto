package tech.hsecit.project.crypto;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tech.hsecit.project.crypto.auth.LoginActivity;
import tech.hsecit.project.crypto.home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        if (user!= null){
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}
