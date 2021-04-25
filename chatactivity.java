package com.example.notify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

public class chatactivity extends AppCompatActivity {
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatactivity);
        MyListData[] myListData = new MyListData[] {
                new MyListData("Nikhil","kadiri", R.drawable.ic_baseline_person_24),
                new MyListData("Preethi","kadapa",  R.drawable.ic_baseline_person_24),
                new MyListData("Ooha","piler",  R.drawable.ic_baseline_person_24),
                new MyListData("Sandhya", "rayachoti", R.drawable.ic_baseline_person_24),
                new MyListData("Prasanna","pulivendula" , R.drawable.ic_baseline_person_24),
                new MyListData("raju", "kurnool",R.drawable.ic_baseline_person_24),
                new MyListData("sharath", "guntur", R.drawable.ic_baseline_person_24),
                new MyListData("koushik", "hyderabad", R.drawable.ic_baseline_person_24),
                new MyListData("vishwa", "kakiada",R.drawable.ic_baseline_person_24),
                new MyListData("harsha", "east godavari", R.drawable.ic_baseline_person_24),
                new MyListData("kattu", "godavari", R.drawable.ic_baseline_person_24),
                new MyListData("guru", "jammalamadugu", R.drawable.ic_baseline_person_24),
        };

        RecyclerView recycle = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void call(View view)
    {
        Intent i = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:9876543210"));
        startActivity(i);
    }
    public void locate(View view)
    {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/place/Kadiri,+Andhra+Pradesh/@14.1230212,78.1444028,14z/data=!3m1!4b1!4m5!3m4!1s0x3bb3cef4f144ae5d:0x358dec2a622ff177!8m2!3d14.1137566!4d78.1610702"));
        startActivity(intent);
    }
}