package com.justclack.qrgeneratorandbarcodescanner;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;


public class StableArrayAdapter extends RecyclerView.Adapter<StableArrayAdapter.SingleItemRowHolder> {

    Context context;
    ArrayList<CategoryModelClass> list;

    public StableArrayAdapter(Context context, ArrayList<CategoryModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_card_view, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(final StableArrayAdapter.SingleItemRowHolder holder, final int position) {
        final CategoryModelClass singleItem = list.get(position);
        String fileName = singleItem.getTitle();
        String appDirectoryName = context.getResources().getString(R.string.app_name);
        final File pdfFile = new File(Environment.getExternalStorageDirectory().toString() + "/" + appDirectoryName + "/" + fileName);
/*
        Bitmap myBitmap = null;
        try {
            myBitmap = BitmapFactory.decodeFile(singleItem.getImage());
            if (myBitmap != null) {
                holder.imageView.setImageBitmap(myBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        Glide.with(context).load(new File(singleItem.getUser())).into(holder.imageView);
        holder.title.setText(singleItem.getTitle());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uri path = FileProvider.getUriForFile(context, "com.mujahidkhan.socialstatusapp.fileprovider", pdfFile);
                Uri path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);
                Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                pdfIntent.setDataAndType(path, "image/*");
                //pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    context.startActivity(pdfIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(context, "No Application available to view Image", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdfFile.exists()) {
                    if (pdfFile.delete()) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                    } else {
                        Toast.makeText(context, "No File Deleted", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                //shareIntent.setPackage("com.whatsapp");
                Uri path = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pdfFile);
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "This image has been shared from " + context.getResources().getString(R.string.app_name) + " , Download it from here https://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName());
                shareIntent.putExtra(Intent.EXTRA_STREAM, path);
                shareIntent.setType("image/*");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                context.startActivity(Intent.createChooser(shareIntent, "Choose App where to Share Image"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageView imageView, share, delete;
        TextView title;

        public SingleItemRowHolder(View view) {
            super(view);
            mView = view;
            this.imageView = mView.findViewById(R.id.image);
            this.title = mView.findViewById(R.id.title);
            this.share = mView.findViewById(R.id.share);
            this.delete = mView.findViewById(R.id.delete);
        }
    }
}