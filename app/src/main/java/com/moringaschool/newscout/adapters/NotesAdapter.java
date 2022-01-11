package com.moringaschool.newscout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moringaschool.newscout.R;
import com.moringaschool.newscout.models.Note;

import io.realm.RealmResults;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewholder> {
    Context context;
    RealmResults<Note> notesList;

    public NotesAdapter(Context context, RealmResults<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new viewholder(LayoutInflater.from(context).inflate(R.layout.note_view,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
      Note note = notesList.get(position);
      holder.mTitleOutput.setText(note.getTitle());
      holder.mDescriptionOutput.setText(note.getDescription());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class  viewholder extends RecyclerView.ViewHolder{
        TextView mTitleOutput;
        TextView mDescriptionOutput;

        public viewholder(View itemView) {
            super(itemView);
            mTitleOutput =  itemView.findViewById(R.id.title_output);
            mDescriptionOutput = itemView.findViewById(R.id.description_output);

        }
    }
}
