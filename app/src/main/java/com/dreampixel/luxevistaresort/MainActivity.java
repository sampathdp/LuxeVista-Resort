package com.dreampixel.luxevistaresort;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Replace the container with the WelcomeFragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, new WelcomeFragment())
                .commit();
    }
}