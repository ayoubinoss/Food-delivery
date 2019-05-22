package android.example.com.lamisportif;

import android.example.com.lamisportif.fragments.BottomNavigationDrawerFragment;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
                    case R.id.search:
                        Toast.makeText(getApplication(),"my Search", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        mBottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"My menu",Toast.LENGTH_SHORT).show();
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.logout:
                Toast.makeText(getApplication(),"deconnexion..", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
