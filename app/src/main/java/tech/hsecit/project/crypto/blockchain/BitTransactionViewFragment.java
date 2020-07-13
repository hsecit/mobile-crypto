package tech.hsecit.project.crypto.blockchain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import tech.hsecit.project.crypto.R;
import tech.hsecit.project.crypto.profile.ProfileActivity;
import tech.hsecit.project.crypto.retrofit_api.databean.AccountData;

import static tech.hsecit.project.crypto.auth.RegisterActivity.URL_SERVER;

public class BitTransactionViewFragment extends Fragment {

    View root;
    TextView bit_addresse ;
    TextInputLayout receiver_bit,amount_bit;
    Button sendBtn;
    CoordinatorLayout snack;
    // firebase
    FirebaseUser user;
    DatabaseReference dbRef;
    Retrofit retrofit;
    BitTransactionApi bitTransactionApi;
    public BitTransactionViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root =inflater.inflate(R.layout.fragment_blockchain_transaction_view, container, false);
        bit_addresse = root.findViewById(R.id.bit_addresse);
        receiver_bit = root.findViewById(R.id.bit_receiver) ;
        amount_bit = root.findViewById(R.id.bit_Amount);
        sendBtn = root.findViewById(R.id.sendBtn);
        snack = root.findViewById(R.id.sanckbar);
        ///inti retrofit
        retrofit = new Retrofit.Builder().
                baseUrl(URL_SERVER).addConverterFactory(GsonConverterFactory.create()).
                build();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendBitTransaction();
            }
        });
        //init firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        bindAcountToProfile(user);
        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Transaction Process
    ///////////////////////////////////////////////////////////////////////////
    private void SendBitTransaction() {
        if( bit_addresse.getText().toString().equals("") ||
        receiver_bit.getEditText().getText().toString().equals("") ||
                amount_bit.getEditText().getText().toString().equals("")){
            Snackbar.make(snack, R.string.empty_transaction, Snackbar.LENGTH_SHORT)
                    .show();
        }else {

        TransactionBitData transaction_blockchain = new TransactionBitData(
                bit_addresse.getText().toString(),
                receiver_bit.getEditText().getText().toString(),
                amount_bit.getEditText().getText().toString()
        );

        bitTransactionApi = retrofit.create(BitTransactionApi.class);

        Call<Void> call = bitTransactionApi.makeTansaction(transaction_blockchain);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                receiver_bit.getEditText().setText("");
                amount_bit.getEditText().setText("");
                Snackbar.make(snack, R.string.succ_transaction, Snackbar.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Snackbar.make(snack, R.string.erro_transaction, Snackbar.LENGTH_SHORT)
                        .show();
            }
        });



        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //  get data from firebase to fragment
    ///////////////////////////////////////////////////////////////////////////
    private void bindAcountToProfile(FirebaseUser user) {
        if (user!=null){
            // TODO change it from statit to dinamic (the last child)
            dbRef = FirebaseDatabase.getInstance().getReference("accounts").child(user.getUid());///.child(ProfileActivity.ACCOUNT_ID)
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //  HashMap<String,Object> ac = (HashMap<String, Object>) dataSnapshot.getValue();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        AccountData acc = ds.getValue(AccountData.class);
                        bit_addresse.setText(acc.getWallet().getPair_pub_addr().getPubaddr1());
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            Toast.makeText(getContext(), "Fialed to fetch data from server", Toast.LENGTH_SHORT).show();
        }
    }
}
    ///////////////////////////////////////////////////////////////////////////
    // Interface of BitCoin Transaction to integrate in retrofit
    ///////////////////////////////////////////////////////////////////////////
interface BitTransactionApi{
    @POST("api/blockchain/transaction")
        Call<Void> makeTansaction(@Body TransactionBitData transaction);
    }