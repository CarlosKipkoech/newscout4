package com.moringaschool.newscout.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moringaschool.newscout.R;
import com.moringaschool.newscout.adapters.NotesAdapter;
import com.moringaschool.newscout.models.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MyNotes extends AppCompatActivity{
    @BindView(R.id.imageViewOpenDialog)  ImageView ButtonOpenAddNote;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        ButterKnife.bind(this);

        //set an view for onclick of Add note Button

        ButtonOpenAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotes.this, AddNote.class);  //intent Addnote form
                startActivity(intent );

            }
        });


        //initialize realm  with current  context

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();     //get new realm instance

        RealmResults<Note> notesList = realm.where(Note.class).findAll();



        RecyclerView recyclerView = findViewById(R.id.notesRecyclerView);  //assign recyclerview to notes recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //set layout manager to current context
        NotesAdapter notesAdapter = new NotesAdapter(getApplicationContext(),notesList);
        recyclerView.setAdapter(notesAdapter);

        notesList.addChangeListener(new RealmChangeListener<RealmResults<Note>>() {

           //get results from realm
            @Override
            public void onChange(RealmResults<Note> notes) {
                notesAdapter.notifyDataSetChanged();
            }
        });

    }

}