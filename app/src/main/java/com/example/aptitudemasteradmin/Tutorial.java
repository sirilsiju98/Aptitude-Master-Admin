package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Tutorial extends AppCompatActivity implements OnSuccessListener {
    ProgressDialog dialog;
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
            dialog.show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Tutorial");
            Map<String,Object> taskMap = new HashMap<>();
            taskMap.put("topic",topictxt);
            taskMap.put("url",urltxt);
            myRef.push().updateChildren(taskMap).addOnSuccessListener(this);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Tutorial");
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Adding.....");


    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show();
        dialog.cancel();
        finish();
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
