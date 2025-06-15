package com.bhashasetu.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import com.bhashasetu.app.model.AppSettings;
import com.bhashasetu.app.util.LanguageManagerLegacy;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SettingsActivity extends AppCompatActivity {

    private AppSettings appSettings;
    private LanguageManagerLegacy languageManager;
    
    private TextView titleTextView;
    private SwitchCompat hindiInterfaceSwitch; // Changed from Switch to SwitchCompat for consistency
    private SwitchCompat dailyReminderSwitch;
    private SwitchCompat audioEnabledSwitch;
    private TimePicker reminderTimePicker;
    private MaterialButton clearProgressButton;
    private TextView versionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Initialize settings and language manager
        appSettings = AppSettings.getInstance(this);
        languageManager = new LanguageManagerLegacy(this);
        languageManager.applyLanguageSettings();
        
        setContentView(R.layout.activity_settings);
        
        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings_title);
        
        // Initialize views
        titleTextView = findViewById(R.id.settings_title);
        titleTextView.setText(languageManager.isHindiMode() ? 
                R.string.settings_title_hindi : R.string.settings_title);
                
        hindiInterfaceSwitch = findViewById(R.id.switch_hindi_interface);
        dailyReminderSwitch = findViewById(R.id.switch_daily_reminder);
        audioEnabledSwitch = findViewById(R.id.switch_audio_enabled);
        reminderTimePicker = findViewById(R.id.time_picker_reminder);
        clearProgressButton = findViewById(R.id.button_clear_progress);
        versionTextView = findViewById(R.id.text_view_version);
        
        // Set initial values
        hindiInterfaceSwitch.setChecked(appSettings.useHindiInterface());
        dailyReminderSwitch.setChecked(appSettings.isDailyReminderEnabled());
        audioEnabledSwitch.setChecked(appSettings.isAudioEnabled());
        
        // Set reminder time
        int reminderMinutes = appSettings.getDailyReminderTime();
        int hours = reminderMinutes / 60;
        int minutes = reminderMinutes % 60;
        reminderTimePicker.setIs24HourView(true);
        reminderTimePicker.setHour(hours);
        reminderTimePicker.setMinute(minutes);
        
        // Set version
        String versionName = BuildConfig.VERSION_NAME;
        versionTextView.setText(versionName);
        
        // Set listeners
        hindiInterfaceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {  // Only trigger on user interaction, not programmatic changes
                    appSettings.setUseHindiInterface(isChecked);
                    recreateActivity();
                }
            }
        });
        
        dailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettings.setDailyReminderEnabled(isChecked);
                reminderTimePicker.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                
                // Update notification settings
                // In a real app, this would schedule or cancel notifications
                // NotificationManager.updateReminderSettings(getApplicationContext());
            }
        });
        
        audioEnabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appSettings.setAudioEnabled(isChecked);
            }
        });
        
        reminderTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                int minutes = hourOfDay * 60 + minute;
                appSettings.setDailyReminderTime(minutes);
                
                // Update notification time
                // In a real app, this would reschedule notifications
                // NotificationManager.updateReminderTime(getApplicationContext());
            }
        });
        
        clearProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(SettingsActivity.this)
                        .setTitle(R.string.clear_progress)
                        .setMessage(R.string.clear_progress_confirmation)
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            appSettings.clearAllProgress();
                            // In a real app, this would also clear database progress
                            Toast.makeText(SettingsActivity.this, "Progress cleared", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
        
        // Show/hide reminder time picker based on switch state
        reminderTimePicker.setVisibility(appSettings.isDailyReminderEnabled() ? View.VISIBLE : View.GONE);
    }
    
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void recreateActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}