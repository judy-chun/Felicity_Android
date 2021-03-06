package dev.felicity.felicity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class AlternativeOptions  extends AppCompatActivity {


    private ImageButton mNext;
    private HashMap<String,Object> mInfo;
    private Button mButton;
    private EditText mEdit1;
    private EditText mEdit2;
    private HashMap<String,String> altMap= new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternative_options);

        mNext = findViewById(R.id.next);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mButton=findViewById(R.id.button);
        mEdit1=findViewById(R.id.edit1);
        mEdit2=findViewById(R.id.edit2);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1 = mEdit1.getText().toString();
                String info2 = mEdit2.getText().toString();
                if(altMap.size()==0 && (info1.equals("") || info2.equals(""))){
                    Toast.makeText(AlternativeOptions.this,"You must add at least one thought and description",Toast.LENGTH_LONG).show();
                }
                else if (info1.equals("") ^ info2.equals("")) {
                        Toast.makeText(AlternativeOptions.this, "Complete your thought and description or leave them blank", Toast.LENGTH_LONG).show();
                    }
                else if (!info1.equals("") && !info2.equals("")) {
                    altMap.put(info1,info2);
                    mInfo.put("AlternativeOptions1", altMap);
                    Intent intentLoadNewActivity = new Intent(AlternativeOptions.this, ChallengingThought.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
                else{
                    mInfo.put("AlternativeOptions1", altMap);
                    Intent intentLoadNewActivity = new Intent(AlternativeOptions.this, ChallengingThought.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info1=mEdit1.getText().toString();
                String info2=mEdit2.getText().toString();

                if(info1.equals("") || info2.equals("")){
                    Toast.makeText(AlternativeOptions.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                }
                else{
                    altMap.put(info1,info2);
                    Toast.makeText(AlternativeOptions.this,"Thought was recorded",Toast.LENGTH_LONG).show();
                    mEdit1.setText("");
                    mEdit2.setText("");
                }
            }
        });
    }
}
