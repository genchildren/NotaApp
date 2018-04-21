package com.hackage.genchildren.nota;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Member;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;



public class CustomRecyclerAdapter
        extends RecyclerView.Adapter<CustomRecyclerAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public CustomRecyclerAdapter(List<Task> membersList) {
        this.taskList = membersList;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder
                                                implements View.OnClickListener{
        TextView name;
        TextView from;
        ImageView pic;
        public CardView cardView;
        private Context context;

        public TaskViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.member_name);
            from = itemView.findViewById(R.id.member_group);
            context = itemView.getContext();
            cardView = itemView.findViewById(R.id.card);
            pic = (ImageView)itemView.findViewById(R.id.member_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final CardView card = view.findViewById(R.id.card);
            TextView content = card.findViewById(R.id.taskInfoHidden);
            content.setText(taskList.get(getAdapterPosition()).getContent());
            Button acceptButton = card.findViewById(R.id.completeButton);
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.where(Task.class).equalTo("fromId", taskList.get(getAdapterPosition()).getFromId()).findAll().get(getAdapterPosition()).deleteFromRealm();
                    taskList.remove(getAdapterPosition());
                    CustomRecyclerAdapter.this.notifyDataSetChanged();
                    //Toast.makeText(, "Task done!", Toast.LENGTH_SHORT).show();
                    realm.commitTransaction();
                }
            });
            if (content.getVisibility() == View.GONE) {
                content.setVisibility(View.VISIBLE);
                acceptButton.setVisibility(View.VISIBLE);
            } else {
                content.setVisibility(View.GONE);
                acceptButton.setVisibility(View.GONE);
            }

        }
    }


    @Override
    @NonNull
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        return new TaskViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task cur = taskList.get(position);
        String name = cur.getName();

        holder.name.setText(name);
        int day = cur.getDate() / 1000000;
        int month = (cur.getDate() / 10000) % 100;
        int year = cur.getDate() % 10000;
        int hours = cur.getTime() /100;
        String minutes = String.valueOf(cur.getTime()).substring(2);
        holder.from.setText(day + "." + month + "." + year + " " + hours + ":" + minutes);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
