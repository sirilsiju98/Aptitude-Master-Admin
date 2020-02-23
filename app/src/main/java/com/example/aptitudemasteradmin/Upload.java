package com.example.aptitudemasteradmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Upload extends AppCompatActivity implements OnSuccessListener, OnFailureListener {
    Spinner spinner[];
    EditText topic ,question[] ,answers[][] ;
    String topicTxt,questionsTxt[],answersTxt[][],spinnerTxt[];
    ProgressDialog dialog;
    private void setData()
    {
        topicTxt=topic.getText().toString();
        for(int i=0;i<5;i++)
        {
            questionsTxt[i]=question[i].getText().toString();
            for(int j=0;j<4;j++)
            {
                answersTxt[i][j]=answers[i][j].getText().toString();
            }
            spinnerTxt[i]=spinner[i].getSelectedItem().toString();


        }
    }
    public String genKey(String topic)
    {
        if(Quiz.list.size()<10)
            return "00"+(Quiz.list.size())+topic;
        if(Quiz.list.size()<100)return "0"+Quiz.list.size()+topic;
        return Quiz.list.size()+topic;
    }
    public void initialize()
    {
        spinner=new Spinner[5];
        spinner[0]=(Spinner) findViewById(R.id.sp1);
        spinner[1]=(Spinner) findViewById(R.id.sp2);
        spinner[2]=(Spinner) findViewById(R.id.sp3);
        spinner[3]=(Spinner) findViewById(R.id.sp4);
        spinner[4]=(Spinner) findViewById(R.id.sp5);

        topic=(EditText)findViewById(R.id.editTextT);

        question=new EditText[5];
        question[0]=(EditText)findViewById(R.id.editText1);
        question[1]=(EditText)findViewById(R.id.editText2);
        question[2]=(EditText)findViewById(R.id.editText3);
        question[3]=(EditText)findViewById(R.id.editText4);
        question[4]=(EditText)findViewById(R.id.editText5);

        answers=new EditText[5][4];
        //1
        answers[0][0]=(EditText)findViewById(R.id.editText11);
        answers[0][1]=(EditText)findViewById(R.id.editText12);
        answers[0][2]=(EditText)findViewById(R.id.editText13);
        answers[0][3]=(EditText)findViewById(R.id.editText14);
        //2
        answers[1][0]=(EditText)findViewById(R.id.editText21);
        answers[1][1]=(EditText)findViewById(R.id.editText22);
        answers[1][2]=(EditText)findViewById(R.id.editText23);
        answers[1][3]=(EditText)findViewById(R.id.editText24);
        //3
        answers[2][0]=(EditText)findViewById(R.id.editText31);
        answers[2][1]=(EditText)findViewById(R.id.editText32);
        answers[2][2]=(EditText)findViewById(R.id.editText33);
        answers[2][3]=(EditText)findViewById(R.id.editText34);
        //4
        answers[3][0]=(EditText)findViewById(R.id.editText41);
        answers[3][1]=(EditText)findViewById(R.id.editText42);
        answers[3][2]=(EditText)findViewById(R.id.editText43);
        answers[3][3]=(EditText)findViewById(R.id.editText44);
         //5
        answers[4][0]=(EditText)findViewById(R.id.editText51);
        answers[4][1]=(EditText)findViewById(R.id.editText52);
        answers[4][2]=(EditText)findViewById(R.id.editText53);
        answers[4][3]=(EditText)findViewById(R.id.editText54);

        questionsTxt=new String[5];
        answersTxt=new String[5][4];
        spinnerTxt=new String[5];
    }
    public void setSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner[0].setAdapter(adapter);
        spinner[1].setAdapter(adapter);
        spinner[2].setAdapter(adapter);
        spinner[3].setAdapter(adapter);
        spinner[4].setAdapter(adapter);


    }

    public boolean checknotempty()
    {
        boolean ret=true;
        if(topic.getText().toString().isEmpty()) {
            topic.setError("Please fill up");
            ret=false;
        }
        for(int i=0;i<5;i++)
        {
            if(question[i].getText().toString().isEmpty()){
                question[i].setError("Please fill up");
                ret=false;
            }
            for(int j=0;j<4;j++)
            {
                if(answers[i][j].getText().toString().isEmpty())
                {
                    answers[i][j].setError("Please fill up");
                    ret=false;
                }
            }
        }
        return ret;
    }
    public void upload(View view)
    {
        if(checknotempty())
        {
            dialog.show();
            setData();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Quiz");
            Map<String,Object> taskMap = new HashMap<>();
            taskMap.put("topic",topicTxt);
            for(int i=0;i<5;i++)
            {
                taskMap.put("quest"+i,questionsTxt[i]);
                for(int j=0;j<4;j++)
                {
                   taskMap.put("ans"+i+""+j,answersTxt[i][j]);
                }
               taskMap.put("opt"+i,spinnerTxt[i]);

            }
            myRef.child(genKey(topicTxt)).setValue(taskMap).addOnSuccessListener(this).addOnFailureListener(this);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Quiz");
        initialize();
        setSpinner();
        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Posting Quiz.....");
    }

    @Override
    public void onSuccess(Object o) {
        Toast.makeText(this, "Successfully Posted A Quiz", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
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
