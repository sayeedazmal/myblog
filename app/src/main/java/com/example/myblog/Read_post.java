package com.example.myblog;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myblog.Interface.OnBlogItemClick;
import com.squareup.picasso.Picasso;


public class Read_post extends Fragment {
    //private Context context;
    private OnBlogItemClick onBlogItemClick;

    @Override
    public void onAttach(Context context) {
        onBlogItemClick = (OnBlogItemClick) context;
        super.onAttach(context);
    }

    public Read_post() {

    }





    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String imageview = bundle.getString("Image");
        String  title = bundle.getString("title");
        String  description = bundle.getString("description");

        View v = inflater.inflate(R.layout.fragment_read_post, container, false);
        TextView titleTv = v.findViewById(R.id.titelTv);
        TextView descriptionTv = v.findViewById(R.id.descripTv);
        ImageView image_View = v.findViewById(R.id.displayImag);

        Picasso.get()
                .load(imageview)
                .placeholder(R.mipmap.ic_launcher_round)
                .fit().centerCrop()
                .into(image_View);
        titleTv.setText(title);
        descriptionTv.setText(description);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    onBlogItemClick.onBackPress();
                }
                return false;
            }
        });

        return v;
    }





}
