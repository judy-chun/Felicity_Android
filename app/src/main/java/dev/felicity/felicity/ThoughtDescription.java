package dev.felicity.felicity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ThoughtDescription extends AppCompatActivity {


    private ImageButton mNext;
    private RadioGroup mGroup;
    private RadioButton mYes;
    private RadioButton mNo;
    private TextView mText;
    private EditText mEdit;
    private HashMap<String,Object> mInfo;
    private String info1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thought_description);

        mNext = findViewById(R.id.next);
        mText=findViewById(R.id.textView2);
        mEdit=findViewById(R.id.textView3);
        mInfo = (HashMap<String,Object>)getIntent().getSerializableExtra("mInfo");
        mYes=findViewById(R.id.yes);
        mNo=findViewById(R.id.no);
        mGroup=findViewById(R.id.group);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info1=mEdit.getText().toString();
                if(info1.equals("")){
                    Toast.makeText(ThoughtDescription.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                }
                else {
                    mInfo.put("ThoughtDescription1",info1);
                    Intent intentLoadNewActivity = new Intent(ThoughtDescription.this, ThoughtSituation.class);
                    intentLoadNewActivity.putExtra("mInfo", mInfo);
                    startActivity(intentLoadNewActivity);
                }
            }
        });

        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);

                switch (index) {
                    case 0: // first button
                        mText.setVisibility(View.VISIBLE);
                        mEdit.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
                        popDialog();
                        break;
                }
            }
        });

    }
    public void popDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Would you like to exit the form, or continue practicing CBT with a negative memory?");
        alertDialogBuilder.setPositiveButton("Continue",
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    mText.setVisibility(View.VISIBLE);
                    mEdit.setVisibility(View.VISIBLE);
                }
            });

        alertDialogBuilder.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LocalDateTime now = LocalDateTime.now();
                String date= now.getMonth().toString()+" "+now.getDayOfMonth()+", "+now.getYear();

                DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
                String mUser= FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase.child("Users").child(mUser).child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                String key = mDatabase.child("Users").child(mUser).child("Journal").child(date).push().getKey();
                mDatabase.child("Users").child(mUser).child("Journal").child(date).child(key).setValue(now.toString());

                mDatabase.child("Journal").child(key).setValue(mInfo);
                mInfo.clear();

                Intent intentLoadNewActivity = new Intent(ThoughtDescription.this, LandingPage.class);
                intentLoadNewActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentLoadNewActivity);
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
