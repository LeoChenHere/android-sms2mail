package org.leochen.sms2mail;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import org.leochen.sms2mail.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Context context;
    Activity activity;
    final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = MainActivity.this;
        this.activity = MainActivity.this;
        initView();

        // 动态申请权限
        applyPermission();
    }

    private void initView() {
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void applyPermission(){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,Manifest.permission.RECEIVE_SMS)
                    !=PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET)
                    != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_NETWORK_STATE)
                    !=PackageManager.PERMISSION_GRANTED
        ){

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.READ_SMS,
                            Manifest.permission.RECEIVE_SMS,
                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                    },
                    1);
        }
    }

}