package com.example.aptitudemasteradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Tutorial extends AppCompatActivity {
    EditText url,topic;
    public void submit(View view)
    {
        url=findViewById(R.id.urlview);
        topic=findViewById(R.id.topic);
        String topictxt=topic.getText().toString();
        String urltxt= url.getText().toString();
        if(urltxt.isEmpty()||topictxt.isEmpty()) {
            if(urltxt.isEmpty())
               url.setError("Enter something");
            if(topictxt.isEmpty())
               topic.setError("Enter something");

        }

        else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tutorial");
            Map<String,Object> taskMap = new HashMap<>();
            taskMap.put("topic",topictxt);
            taskMap.put("url",urltxt);
            myRef.push().updateChildren(taskMap);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);


    }
}
