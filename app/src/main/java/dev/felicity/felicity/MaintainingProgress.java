package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MaintainingProgress  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private EditText mEdit1;
    private EditText mEdit2;
    private EditText mEdit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintaining_progress);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mEdit1=findViewById(R.id.editText1);
        mEdit2=findViewById(R.id.editText2);
        mEdit3=findViewById(R.id.editText3);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1=mEdit1.getText().toString();
                String info2=mEdit2.getText().toString();
                String info3=mEdit3.getText().toString();

                if(info1.equals("") || info2.equals("") || info3.equals("")){
                    Toast.makeText(MaintainingProgress.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("MaintainingProgress1",info1);
                    mInfo.put("MaintainingProgress2",info2);
                    mInfo.put("MaintainingProgress3",info3);
                    Intent intentLoadNewActivity = new Intent(MaintainingProgress.this, FeelingReview.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });
    }
}
