package tech.hsecit.project.crypto.banktps;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tech.hsecit.project.crypto.R;


public class BankTPSFragment extends Fragment {

    View root;
    TextView bank_sentence,password_key;
    DatabaseReference dbRef;


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
        bank_sentence = root.findViewById(R.id.bank_sentence);
        password_key = root.findViewById(R.id.password_key);
        setDataKey();
        return root;
    }

    private void setDataKey() {
        dbRef = FirebaseDatabase.getInstance().getReference("2fact-auth");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds!=null){
                        Log.i("data Info",ds.getValue().toString());
//                        TwoFactorAuth two =  ds.getValue(TwoFactorAuth.class);
                        password_key.setText(ds.getValue().toString());
                        bank_sentence.setText("Please copy and paste in site");
                    }else {
                        bank_sentence.setText("No key password recieved");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
//    private void sendBankTransaction() {
//        if( account_number.getText().toString().equals("") ||
//               recipient.getEditText().getText().toString().equals("") ||
//                amount.getEditText().getText().toString().equals("")){
//
//            Snackbar.make(snack, R.string.empty_transaction, Snackbar.LENGTH_SHORT)
//                    .show();
//        }else {
//            TransactionBitData transaction_blockchain = new TransactionBitData(
//                    account_number.getText().toString(),
//                    recipient.getEditText().getText().toString(),
//                    amount.getEditText().getText().toString()
//            );
//
//            Call<Void> call = banKTransactionApi.makeTansaction(transaction_blockchain);
//            call.enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    recipient.getEditText().setText("");
//                    amount.getEditText().setText("");
//                    Snackbar.make(snack, R.string.succ_transaction, Snackbar.LENGTH_SHORT)
//                            .show();
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Snackbar.make(snack, R.string.erro_transaction, Snackbar.LENGTH_SHORT)
//                            .show();
//                }
//            });
//        }
//    }
//
//
//    private void bindAcountToProfile(FirebaseUser user) {
//        if (user!=null){
//            // TODO change it from statit to dinamic (the last child)
//            dbRef = FirebaseDatabase.getInstance().getReference("accounts").child(user.getUid());
//            dbRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    //  HashMap<String,Object> ac = (HashMap<String, Object>) dataSnapshot.getValue();
//
//                    for(DataSnapshot ds: dataSnapshot.getChildren()){
//                        AccountData acc = ds.getValue(AccountData.class);
//                        nom.setText(acc.getNom().toUpperCase());
//                        account_number.setText(acc.getBank_account_number());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }else {
//            Toast.makeText(getContext(), "Fialed to fetch data from server", Toast.LENGTH_SHORT).show();
//        }
//    }
//}
/////////////////////////////////////////////////////////////////////////////
////  interface for the Bank tronsaction
/////////////////////////////////////////////////////////////////////////////
//interface BanKTransactionApi{
//    @POST("api/bank/transaction")
//    Call<Void> makeTansaction(@Body TransactionBitData transaction);
//}
class TwoFactorAuth {
    public String one_time_code;
    public TwoFactorAuth(){

    }

    public TwoFactorAuth(String one_time_code) {
        this.one_time_code = one_time_code;
    }

    public String getOne_time_code() {
        return one_time_code;
    }

    public void setOne_time_code(String one_time_code) {
        this.one_time_code = one_time_code;
    }
}