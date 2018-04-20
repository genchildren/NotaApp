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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

//Адаптер для RecyclerView находящегося в MemberListActivity

public class CustomRecyclerAdapter
        extends RecyclerView.Adapter<CustomRecyclerAdapter.TaskViewHolder> {

    private List<Task> membersList; //Список идентификаторов участников

    public CustomRecyclerAdapter(List<Task> membersList) {
        this.membersList = membersList;
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
            //сохраняем поля из CardView в ViewHolder
            name = (TextView)itemView.findViewById(R.id.member_name);
            from = itemView.findViewById(R.id.member_group);
            context = itemView.getContext();
            cardView = itemView.findViewById(R.id.card);
            pic = (ImageView)itemView.findViewById(R.id.member_photo);
            itemView.setOnClickListener(this); //Делаем карточки кликабельными
        }

        @Override
        public void onClick(View view) {
            //Формируем Intent для перехода к активити, отображающей подробную информацию об участнике
            //Intent memberInfoIntent = new Intent(context, MemberInfoActivity.class);
           // memberInfoIntent.putExtra("member_id",membersList.get(getLayoutPosition()));
            //context.startActivity(memberInfoIntent);
            CardView card = view.findViewById(R.id.card);
            //card.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            TextView goneText = card.findViewById(R.id.goneText);
            if (goneText.getVisibility() == View.GONE) {
                goneText.setVisibility(View.VISIBLE);
                notifyItemChanged(getLayoutPosition()-1);
            } else {
                goneText.setVisibility(View.GONE);
                notifyItemChanged(getLayoutPosition()-1);
            }
            //TODO ANIME
           // ObjectAnimator animation = ObjectAnimator.ofInt(goneText, "maxLines", goneText.getMaxLines());
           // animation.setDuration(500).start();
            Toast.makeText(context, "this is a task", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    @NonNull
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_layout, parent, false);
        return new TaskViewHolder(v);
    }

    //Устанавливаем отображение карточки на экране
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        //ReadableDBHelper database = new ReadableDBHelper(holder.context, membersList.get(position)); //Получаем доступ к базе данных для чтения

        //String Name = database.getMemberFirstName() +
              //  " " + database.getMemberSecondName();
        String name = "Some task";

        holder.name.setText(name);
        holder.from.setText("from kek");
        //byte[] arr = database.getImageArray();
        //Bitmap img = ImageDecoder.bitmapFromByteArrayLowQ(arr); //Преобразовать байтовый массив в сжатый Bitmap
        //holder.photo.setImageBitmap(img);
        //database.close();
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setCardSize(int size) {

    }

}
