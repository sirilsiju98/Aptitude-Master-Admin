package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Leader_Board extends AppCompatActivity {
    DataSnapshot leadB;
    List<Map<String,String>> lead ;
    Map<String,String> map;
    ListView leadlistView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader__board);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        int pos=intent.getIntExtra("pos",0);
        String title=intent.getStringExtra("tit");
        getSupportActionBar().setTitle(title);
        leadB=Quiz.quiz.get(pos).child("LeaderBoard");
        leadlistView=findViewById(R.id.lbl);
        lead = new ArrayList<Map<String,String>>();
        for(DataSnapshot ds:leadB.getChildren())
        {
            String mainElt=ds.child("user").getValue().toString();
            DecimalFormat df = new DecimalFormat("##.##");
            String subElt ="Score: "+df.format(ds.child("score").getValue());
            map = new HashMap();
            map.put("var1", mainElt);
            map.put("var2", subElt);
            lead.add(map);

        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,lead,android.R.layout.simple_list_item_2,new String[]{"var1","var2"},new int[]{android.R.id.text1,android.R.id.text2});
        leadlistView.setAdapter(simpleAdapter);
        TextView textView = findViewById(R.id.no);
        if(lead.size()==0)
        {
            textView.setText("No one has Attempted this quiz");
            textView.setVisibility(View.VISIBLE);
        }




    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
