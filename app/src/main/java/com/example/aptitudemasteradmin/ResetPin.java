package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPin extends AppCompatActivity implements OnSuccessListener {
    TextView userId;
    String key;
    Boolean found;
    ProgressDialog dialog;
    public void resetP(View view)
    {
        if(userId.getText().toString().isEmpty())
            userId.setError("Required");
        else
        {
            dialog.show();
            found=false;
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference myRef = database.getReference("Users");
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(Long.parseLong(ds.child("id").getValue().toString())==Long.parseLong(userId.getText().toString())){
                            key=ds.getKey();
                            found=true;
                            break;
                        }

                    }
                    if(found)
                    {
                        myRef.child(key).child("pin").setValue(1234l).addOnSuccessListener(ResetPin.this);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "User not found!!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
           // Log.i("kkk",found.toString()+"  "+key);

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);
        userId=findViewById(R.id.userid);
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Resetting Pin......");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Pin");
    }
    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "Pin Reset  Successfully", Toast.LENGTH_SHORT).show();
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
