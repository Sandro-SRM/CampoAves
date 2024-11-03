package com.example.campoaves;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.campoaves.fragment.PedidosFragment;
import com.example.campoaves.fragment.CuentaFragment;
import com.example.campoaves.fragment.InicioFragment;
import com.example.campoaves.fragment.TiendaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView hbottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        hbottomNavigation = findViewById(R.id.bottom_navigation);
        hbottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(new InicioFragment());
    }

        public void openFragment(Fragment fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener(){
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId()==R.id.itemInicio) {
                        openFragment(new InicioFragment());

                    } else if (item.getItemId()==R.id.itemTienda) {
                        openFragment(new PedidosFragment());
                        
                    } else if (item.getItemId()==R.id.itemPedidos) {
                        openFragment(new CuentaFragment());

                    } else if (item.getItemId()==R.id.itemCuenta) {
                        openFragment(new TiendaFragment());

                    }
                    return true;
                }
            };
}