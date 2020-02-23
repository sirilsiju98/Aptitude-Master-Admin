package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddUser extends AppCompatActivity implements OnSuccessListener {
     TextView name,userid;
    ProgressDialog dialog;
     public void add(View view)
     {
        boolean j=true;
         if(name.getText().toString().isEmpty())
         {
             name.setText("Please Fill up");
             j=false;
         }
         if(userid.getText().toString().isEmpty()){
             name.setText("Please Fill up");
             j=false;
         }
         if(j)
         {
             dialog.show();
             FirebaseDatabase database = FirebaseDatabase.getInstance();
             DatabaseReference myRef = database.getReference("Users");
             Map<String,Object> taskMap = new HashMap<>();
             taskMap.put("name",name.getText().toString());
             taskMap.put("id",Long.parseLong(userid.getText().toString()));
             taskMap.put("score",(double)0);
             taskMap.put("pin",1234L);
             myRef.child(userid.getText().toString()).setValue(taskMap).addOnSuccessListener(this);
         }

     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog=new ProgressDialog(AddUser.this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Adding User......");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        name=findViewById(R.id.name);
        userid=findViewById(R.id.uid);
    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "User Added Successfully", Toast.LENGTH_SHORT).show();
         dialog.cancel();
         userid.setText("");
         name.setText("");
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
