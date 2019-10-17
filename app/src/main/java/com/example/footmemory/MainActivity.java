package com.example.footmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.footmemory.util.TraceItem;
import com.githang.statusbar.StatusBarCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    public NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(android.R.color.white));
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setTitle("足迹精灵");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        final FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        replaceFragment(new MainContentFragment());
        Connector.getDatabase();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_main);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.nav_main:
                        replaceFragment(new MainContentFragment());
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_cal:
                        //floatingActionButton.setVisibility(View.GONE);
                        replaceFragment(new AddListFragment());
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_data:
                        replaceFragment(new TodayDataFragment());
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_history_data:
                        replaceFragment(new HistoryFragment());
                        mDrawerLayout.closeDrawers();
                        break;
                        default:

                }
                return true;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.settings:
                Toast.makeText(this,"You clicked Settings",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(fragment instanceof AddListFragment)
        {transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);}
        else
        {
            transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        }
        transaction.replace(R.id.main_fragment,fragment);
        transaction.commit();
    }



}
