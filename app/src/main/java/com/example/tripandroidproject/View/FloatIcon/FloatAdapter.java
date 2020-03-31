package com.example.tripandroidproject.View.FloatIcon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.Presenter.Note.UpdateNotePresenter;
import com.example.tripandroidproject.R;

import java.util.List;

public class FloatAdapter extends RecyclerView.Adapter<FloatAdapter.ViewHolder>  {
    private View view;
    List<Note> notes;
    Context context;
    UpdateNotePresenter updateNotePresenter;
    public FloatAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        updateNotePresenter = new UpdateNotePresenter(context);
    }
    @NonNull
    @Override
    public FloatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.note_recycler_view,parent,false);
        FloatAdapter.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FloatAdapter.ViewHolder holder, final int position) {
        holder.checkBox.setText(notes.get(position).getName());
        if(notes.get(position).getStatus().equals("checked")) {
            holder.checkBox.setChecked(true);
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.checkBox.isChecked())
                {
                    notes.get(position).setStatus("checked");
                }
                else {
                    notes.get(position).setStatus("unchecked");
                }
                updateNotePresenter.updateNote(notes.get(position));
            }
        });
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
//        private final TextView textTest;
        public CheckBox checkBox;

//        public ConstraintLayout constraintLayout;
        public View layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView;
            checkBox = itemView.findViewById(R.id.checkboxItem);
//            textTest = itemView.findViewById(R.id.textTest);
//            constraintLayout = itemView.findViewById(R.id.rootLayout);


        }

    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}
