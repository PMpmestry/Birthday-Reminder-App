package com.example.prathamesh1.lavanya_makeup;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {

//    FloatingActionButton fabAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        fabAdd = (FloatingActionButton) findViewById(R.id.fab);
//        fabAdd.setVisibility(View.INVISIBLE);
        Fragment setting_Fragment = new settingFragment();
        getFragmentManager().beginTransaction().add(R.id.setting_layout,setting_Fragment).commit();
        //getFragmentManager().beginTransaction().replace(R.id.include,new settingFragment()).commit();
    }
}
