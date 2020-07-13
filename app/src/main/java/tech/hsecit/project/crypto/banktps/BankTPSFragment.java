package tech.hsecit.project.crypto.banktps;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import tech.hsecit.project.crypto.auth.LoginActivity;
import tech.hsecit.project.crypto.blockchain.TransactionBitData;
import tech.hsecit.project.crypto.profile.ProfileActivity;
import tech.hsecit.project.crypto.retrofit_api.databean.AccountData;

import static tech.hsecit.project.crypto.auth.RegisterActivity.URL_SERVER;


public class BankTPSFragment extends Fragment {

    View root;
    TextView nom , account_number;
    TextInputLayout recipient,amount;
    Button send_tr ;
    CoordinatorLayout snack;
    // firebase
    FirebaseUser user;
    DatabaseReference dbRef;
    Retrofit retrofit;
    BanKTransactionApi banKTransactionApi ;
    public BankTPSFragment() {
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
        root = inflater.inflate(R.layout.fragment_bank_transaction, container, false);
        nom =root.findViewById(R.id.sender_name);
        ///// buid retrofit
        account_number = root.findViewById(R.id.sender_account_number);
        recipient = root.findViewById(R.id.receiver);
        amount = root.findViewById(R.id.amount);
        send_tr =root.findViewById(R.id.senDBtn);
        snack = root.findViewById(R.id.snack);
        retrofit = new Retrofit.Builder().baseUrl(URL_SERVER).addConverterFactory(GsonConverterFactory.create()).build();
        banKTransactionApi = retrofit.create(BanKTransactionApi.class);
        send_tr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBankTransaction();
            }
        });
        /// init firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        bindAcountToProfile(user);
        return root;
    }

    private void sendBankTransaction() {
        if( account_number.getText().toString().equals("") ||
               recipient.getEditText().getText().toString().equals("") ||
                amount.getEditText().getText().toString().equals("")){

            Snackbar.make(snack, R.string.empty_transaction, Snackbar.LENGTH_SHORT)
                    .show();
        }else {
            TransactionBitData transaction_blockchain = new TransactionBitData(
                    account_number.getText().toString(),
                    recipient.getEditText().getText().toString(),
                    amount.getEditText().getText().toString()
            );

            Call<Void> call = banKTransactionApi.makeTansaction(transaction_blockchain);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    recipient.getEditText().setText("");
                    amount.getEditText().setText("");
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


    private void bindAcountToProfile(FirebaseUser user) {
        if (user!=null){
            // TODO change it from statit to dinamic (the last child)
            dbRef = FirebaseDatabase.getInstance().getReference("accounts").child(user.getUid());
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //  HashMap<String,Object> ac = (HashMap<String, Object>) dataSnapshot.getValue();

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        AccountData acc = ds.getValue(AccountData.class);
                        nom.setText(acc.getNom().toUpperCase());
                        account_number.setText(acc.getBank_account_number());
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
//  interface for the Bank tronsaction
///////////////////////////////////////////////////////////////////////////
interface BanKTransactionApi{
    @POST("api/bank/transaction")
    Call<Void> makeTansaction(@Body TransactionBitData transaction);
}