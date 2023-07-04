package com.example.expendituremanagementapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.expendituremanagementapp.ui.user.LoginActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.expendituremanagementapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "With you have a good time with out budgetmanagement app!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_expense, R.id.nav_revenue)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.nav_exit)
                    finish();
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Kiểm tra và cập nhật tên người dùng trong nav_header_main.xml
        if (getIntent().hasExtra("username")) {
            String username = getIntent().getStringExtra("username");
            View headerView = navigationView.getHeaderView(0);
            TextView textUsername = headerView.findViewById(R.id.textUsername);
            textUsername.setText(username);
        }
        userId = getIntent().getIntExtra("userID", 0);

        // Xử lý sự kiện khi click vào action item "Change Password"
        MenuItem changePasswordItem = navigationView.getMenu().findItem(R.id.nav_change_password);
        changePasswordItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                closeDrawer();
                navigateToChangePassword();
                return true;
            }
        });
    }

    public void navigateToChangePassword() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_changePassword);
    }

    public void navigateToHome() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_home);
    }

    public void closeDrawer() {
        DrawerLayout drawerLayout = binding.drawerLayout;
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }


    public void logout(MenuItem item) {
        // Xóa thông tin đăng nhập và chuyển về LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    public int userID(){
        return userId;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}