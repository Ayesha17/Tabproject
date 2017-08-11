package com.example.ayeshashahid.tabproject;

/**
 * Created by ayesha.shahid on 8/9/2017.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

ArrayList<Book> book;
    private Context context;

    public MyAdapter(Context context, ArrayList<Book> books) {
        this.book=books;
        this.context=context;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        private TextView name,course_category,tution_fee;
        private TextView authorName;
        private ImageView image;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            course_category = (TextView) v.findViewById(R.id.course_category);
            tution_fee = (TextView) v.findViewById(R.id.tutionfee);
            name = (TextView) v.findViewById(R.id.course_name);
            image = (ImageView) v.findViewById(R.id.course_image);
            authorName = (TextView) v.findViewById(R.id.author_name);
           // mTextView = (TextView) v.findViewById(R.id.tv_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
//        holder.mTextView.setText(mDataset[position]);

        holder.name.setText(book.get(position).getName());
        holder.authorName.setText("By "+book.get(position).getAuthorName());
        holder.course_category.setText(book.get(position).getCourse_category());
        holder.tution_fee.setText(book.get(position).getTutionfee());
//        Picasso.with(activity).load(book.getImageUrl()).into(holder.image);


        String imageString = book.get(position).getImageUrl();

        //decode base64 string to image
        byte[]  imageBytes = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image.setImageBitmap(decodedImage);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentValue = book.get(position).getCourse_category();
                Log.d("CardView", "CardView Clicked: " + currentValue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return book.size();
    }
}