package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    protected ListView listView;
    public static DataSnapshot quiz;
    public void upload(View view)
    {
        Intent intent = new Intent(Quiz.this,Upload.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        list=new ArrayList<String>(1000);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Quiz");
        myRef.addValueEventListener(this);
        listView=findViewById(R.id.listview);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        list.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
             list.add(ds.child("topic").getValue().toString());

        }
        Collections.reverse(list);
        ArrayAdapter<String> arrayAdapter_tutorial = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,list);
        listView.setAdapter(arrayAdapter_tutorial);
        quiz=dataSnapshot;

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
