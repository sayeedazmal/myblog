package com.example.myblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Myadapter extends RecyclerView.Adapter<Myadapter.Myviewholder>{

    private Context context;
    private List<Postnew> postList ;
    private Onclicklistener onclicklistener;


    public Myadapter(Context context, List<Postnew> postList) {
        this.context = context;
        this.postList = postList;

    }

    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =  layoutInflater.inflate(R.layout.list_item,parent,false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        Postnew postnew  = postList.get(position);
        holder.titletxt.setText(postnew.getPostTitle());
        Picasso.get()
                .load(postnew.getImageUri())
                .placeholder(R.mipmap.ic_launcher_round)
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }
    @Override
    public int getItemCount() {
        return postList.size();
    }


    public class Myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titletxt;
        CardView cardView;
        private Context context;


        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewId);
            titletxt = itemView.findViewById(R.id.textViewId);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onclicklistener.onclick(postList.get(getAdapterPosition()));

        }
    }


     public interface Onclicklistener{
        void onclick(Postnew postnew);
    }
    public void setOnclicklistener(Onclicklistener onclicklistener){
        this.onclicklistener = onclicklistener;
    }
}
