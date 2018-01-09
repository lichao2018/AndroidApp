package com.lc.androidapp.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lc.androidapp.R;
import com.lc.androidapp.api.ZhiHuApi;
import com.lc.androidapp.bean.ZhiHuDaily;
import com.lc.androidapp.bean.ZhiHuStory;
import com.lc.androidapp.fragment.NewsFragment;
import com.lc.androidapp.fragment.ZhiHuFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.drawer)
    DrawerLayout drawerLayout;
    @InjectView(R.id.navigation)
    NavigationView navigation;

    ZhiHuDaily mZhiHuDaily;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);

        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch(item.getItemId()){
                    case R.id.navigation_menu_1:
                        ZhiHuFragment fZhiHu = new ZhiHuFragment();
                        transaction.replace(R.id.main_frame, fZhiHu);
                        break;
                    case R.id.navigation_menu_2:
                        NewsFragment fNews = new NewsFragment();
                        transaction.replace(R.id.main_frame, fNews);
                        break;
                }
                transaction.commit();
                return true;
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_open:
                        Toast.makeText(MainActivity.this, "OPEN", Toast.LENGTH_SHORT).show();
                        drawerLayout.openDrawer(navigation);
                        break;
                    case R.id.menu_about:
                        Toast.makeText(MainActivity.this, "ABOUT", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        ZhiHuFragment zhihuFragment = new ZhiHuFragment();
        FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        fTransaction.replace(R.id.main_frame, zhihuFragment);
        fTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
