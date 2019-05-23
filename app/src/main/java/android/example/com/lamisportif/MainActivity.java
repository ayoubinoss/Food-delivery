package android.example.com.lamisportif;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.example.com.lamisportif.fragments.BottomMenuFragment;
import android.example.com.lamisportif.fragments.BottomNavigationDrawerFragment;
import android.example.com.lamisportif.fragments.FilterFragment;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    BottomAppBar mBottomAppBar;
    FloatingActionButton mButtonBasket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomAppBar = findViewById(R.id.bottomAppBar);
        mButtonBasket = findViewById(R.id.add_post);

        mBottomAppBar.replaceMenu(R.menu.home_menu);

        mBottomAppBar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.filter:
                        Toast.makeText(getApplication(),"my Filter", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        mBottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"My menu",Toast.LENGTH_SHORT).show();
                BottomMenuFragment bottomMenuFragment = new BottomMenuFragment();
                bottomMenuFragment.show(getSupportFragmentManager(),"");
            }
        });

        mButtonBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"My cart", Toast.LENGTH_SHORT).show();
                BottomNavigationDrawerFragment f = new BottomNavigationDrawerFragment();
                f.show(getSupportFragmentManager(),"TAG");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_top_menu,menu);

        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplication(), "hello " + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)) {
                    Toast.makeText(getApplication(),"empty",Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.search:
                Toast.makeText(getApplication(),"search..", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this,"you type : " + query, Toast.LENGTH_SHORT).show();
        }
    }
}
