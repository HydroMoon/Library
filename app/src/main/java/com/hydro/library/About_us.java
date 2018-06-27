package com.hydro.library;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class About_us extends AppCompatActivity {

    RecyclerView recyclerView;
    AboutAdapter aboutAdapter;
    List<About_Info> info = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.about_recycle);

        info.clear();
        info.add(new About_Info("654654654", "hgthtrhtrhrth"));
        info.add(new About_Info("dfghfdg", "grfgfdgfd"));
        info.add(new About_Info("gfhgfhgfh", "gfhfghfg"));
        info.add(new About_Info("fghgfh", "fghfgh"));

        aboutAdapter = new AboutAdapter(info, About_us.this);

        recyclerView.setLayoutManager(new LinearLayoutManager(About_us.this));
        recyclerView.addItemDecoration(new RecyclerviewDivider(About_us.this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(aboutAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
