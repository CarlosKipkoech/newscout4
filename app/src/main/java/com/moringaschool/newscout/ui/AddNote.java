package com.moringaschool.newscout.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.moringaschool.newscout.R;
import com.moringaschool.newscout.models.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class AddNote extends AppCompatActivity {
    @BindView(R.id.titleInput) EditText mTitleInput;
    @BindView(R.id.descriptionInput) EditText mDescriptionInput;
    @BindView(R.id.button_add_note) MaterialButton mAddNoteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.bind(this);


        mAddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.init(getApplicationContext());         //initialize realm and get app context
                Realm realm = Realm.getDefaultInstance();        //get new instance of realm
                String title =mTitleInput.getText().toString();
                String description = mDescriptionInput.getText().toString();

                 //form validation
                  if (title == null || description == null){
                      Toast.makeText(getApplicationContext(), "Some Input fields are Missing", Toast.LENGTH_SHORT).show();
                  }
                else{
                      realm.beginTransaction();                      //realm to create new note and set in variables
                      Note note = realm.createObject(Note.class);

                      note.setTitle(title);
                      note.setDescription(description);
                      realm.commitTransaction();
                      Toast.makeText(getApplicationContext(), "Note Saved",Toast.LENGTH_LONG).show();
                      finish();    //end process

                  }
            }
        });
    }
}