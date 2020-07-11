package tech.hsecit.project.crypto.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import tech.hsecit.project.crypto.R;
import tech.hsecit.project.crypto.auth.LoginActivity;
import tech.hsecit.project.crypto.banktps.BankTPSFragment;
import tech.hsecit.project.crypto.blockchain.BitTransactionViewFragment;
import tech.hsecit.project.crypto.profile.ProfileActivity;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth ;
    MaterialToolbar mToolbar ;
    TabAdapter tabAdapter ;
    TabLayout tabLayout;
    ViewPager viewPager ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = findViewById(R.id.main_menu);
        setSupportActionBar(mToolbar);
        ////tabs conf
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new BitTransactionViewFragment(),"BlockChain");
        tabAdapter.addFragment(new BankTPSFragment(),"BanK TPS");
        int[] iconTab ={ R.drawable.ic_icons8_bitcoin,R.drawable.ic_action_name};
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_icons8_bitcoin);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_name);
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);


        //
        mAuth = FirebaseAuth.getInstance() ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.signout_btn:
                mAuth.signOut();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                return true;
            case R.id.profile_btn:
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
