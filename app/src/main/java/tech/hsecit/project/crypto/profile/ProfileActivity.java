package tech.hsecit.project.crypto.profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import tech.hsecit.project.crypto.R;
import tech.hsecit.project.crypto.auth.LoginActivity;
import tech.hsecit.project.crypto.retrofit_api.AccountGenerate;
import tech.hsecit.project.crypto.retrofit_api.databean.AccountData;

public class ProfileActivity extends AppCompatActivity {
    public final static String ACCOUNT_ID = "-MBVgsRs3mHypYtEMi0k";
    TextView nom,tel,email,addr,bank_account,bit_addr,pubkey,privatekey;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseDatabase fdbase;
    DatabaseReference dbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///// get elemnt
        nom =findViewById(R.id.nom);
        tel = findViewById(R.id.tel);
        email = findViewById(R.id.email);
        addr = findViewById(R.id.addr);
        bank_account = findViewById(R.id.account_number);
        bit_addr = findViewById(R.id.blockchin_addr);
        pubkey =findViewById(R.id.public_key);
        privatekey = findViewById(R.id.private_key);
        /// init firebase
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        bindAcountToProfile(user);
    }

    private void bindAcountToProfile(FirebaseUser user) {
        if (user!=null){
            fdbase = FirebaseDatabase.getInstance();
            dbRef = fdbase.getReference("accounts").child(user.getUid()).child(ACCOUNT_ID);
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  //  HashMap<String,Object> ac = (HashMap<String, Object>) dataSnapshot.getValue();
                    AccountData acc = dataSnapshot.getValue(AccountData.class);
                     nom.setText(acc.getNom().toUpperCase());
                    tel.setText(acc.getPhone());
                    email.setText(user.getEmail());
                    addr.setText(acc.getAddress());
                    bank_account.setText(acc.getBank_account_number());
                    bit_addr.setText(acc.getWallet().getPair_pub_addr().getPubaddr1());
                    pubkey.setText(acc.getWallet().getPair_pub_addr().getPubkey());
                    privatekey.setText(acc.getWallet().getPrivate_key().getHex());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }
    }
}
