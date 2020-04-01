package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.Presenter.Note.UpdateNotePresenter;
import com.example.tripandroidproject.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NotesAdapter.ViewHolder>  {
    private View view;
    List<Note> notes;
    Context context;
    UpdateNotePresenter updateNotePresenter;
    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        updateNotePresenter = new UpdateNotePresenter(context);
    }
    @NonNull
    @Override
    public com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.note_item,parent,false);
        com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NotesAdapter.ViewHolder vh = new com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NotesAdapter.ViewHolder(view);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull final com.example.tripandroidproject.View.NavDrawer_UpComingTrip.NotesAdapter.ViewHolder holder, final int position) {
        holder.checkedTextView.setText(notes.get(position).getName());
        Toast.makeText(context, notes.get(position).getStatus(), Toast.LENGTH_SHORT).show();

        if(notes.get(position).getStatus().equals("checked")) {
            //Toast.makeText(context, "checked", Toast.LENGTH_SHORT).show();
            holder.checkedTextView.setCheckMarkDrawable(R.drawable.checked);
            holder.checkedTextView.setChecked(true);

        }
        else {
            //Toast.makeText(context, "unchecked", Toast.LENGTH_SHORT).show();
           // holder.checkedTextView.setCheckMarkDrawable(null);
          //  holder.checkedTextView.setChecked(false);
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        //        private final TextView textTest;
        public CheckedTextView checkedTextView;

        //        public ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            checkedTextView = itemView.findViewById(R.id.checkedTextView);
//            textTest = itemView.findViewById(R.id.textTest);
//            constraintLayout = itemView.findViewById(R.id.rootLayout);




                }

    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}

