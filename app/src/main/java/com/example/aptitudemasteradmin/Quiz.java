package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Quiz extends AppCompatActivity implements ValueEventListener{

    public static ArrayList<String> list;
    ProgressDialog dialog;
    protected ListView listView;

    public static ArrayList<DataSnapshot>quiz;
    public void upload(View view)
    {
        Intent intent = new Intent(Quiz.this,Upload.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Quiz");
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Loading......");
        dialog.show();


        list=new ArrayList<String>(1000);
        quiz=new ArrayList<DataSnapshot>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Quiz");
        myRef.addValueEventListener(this);
        listView=findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Quiz.this,Leader_Board.class);
                intent.putExtra("pos",position);
                intent.putExtra("tit",list.get(position));
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        list.clear();
        quiz.clear();

        for (DataSnapshot ds:dataSnapshot.getChildren()) {
             list.add(ds.child("topic").getValue().toString());
             quiz.add(ds);

        }
        Collections.reverse(list);
        Collections.reverse(quiz);
        ArrayAdapter<String> arrayAdapter_tutorial = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(arrayAdapter_tutorial);
        dialog.cancel();


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

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
