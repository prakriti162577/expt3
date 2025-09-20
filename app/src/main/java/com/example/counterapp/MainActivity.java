package com.example.counterapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private int count = 0;
    private TextView counterText;
    private MaterialButton incrementButton, decrementButton, resetButton;
    private Switch themeSwitch;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Load theme preference
        prefs = getSharedPreferences("counterPrefs", MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("darkMode", false);
        AppCompatDelegate.setDefaultNightMode(isDark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counterText = findViewById(R.id.counterText);
        incrementButton = findViewById(R.id.incrementButton);
        decrementButton = findViewById(R.id.decrementButton);
        resetButton = findViewById(R.id.resetButton);
        themeSwitch = findViewById(R.id.themeSwitch);

        count = prefs.getInt("count", 0);
        updateCounter();

        incrementButton.setOnClickListener(view -> {
            count++;
            updateCounter();
            showToast("Incremented!");
        });

        decrementButton.setOnClickListener(view -> {
            count--;
            updateCounter();
            showToast("Decremented!");
        });

        resetButton.setOnClickListener(view -> {
            count = 0;
            updateCounter();
            showToast("Reset!");
        });

        themeSwitch.setChecked(isDark);
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("darkMode", isChecked).apply();
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        });
    }

    private void updateCounter() {
        counterText.setText(String.valueOf(count));
        prefs.edit().putInt("count", count).apply();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}