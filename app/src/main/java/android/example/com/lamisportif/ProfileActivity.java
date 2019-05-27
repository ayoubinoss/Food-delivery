package android.example.com.lamisportif;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mPhone;
    private TextView mEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mEmail = findViewById(R.id.mail);
        mPhone = findViewById(R.id.phone);
        mName = findViewById(R.id.name);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.edit:
                Toast.makeText(this,"edit",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails();
    }

    /**
     * get the current User details.
     */
    public void getUserDetails() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            mEmail.setText(user.getEmail());
            mPhone.setText(user.getPhoneNumber());
            mName.setText(user.getDisplayName());
        }
    }
}
