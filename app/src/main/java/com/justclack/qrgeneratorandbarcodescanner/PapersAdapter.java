package com.justclack.qrgeneratorandbarcodescanner;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PapersAdapter extends RecyclerView.Adapter<PapersAdapter.SingleItemRowHolder> {
    private ArrayList<CategoryModelClass> itemsList;
    private Context mContext;
    public static SQLiteHelper sqLiteHelper;

    public PapersAdapter(Context context, ArrayList<CategoryModelClass> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        sqLiteHelper = new SQLiteHelper(context);
        sqLiteHelper.createTable(Const.post_table_query);
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_single_card, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new SingleItemRowHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final SingleItemRowHolder holder, final int i) {
        final CategoryModelClass singleItem = itemsList.get(i);
        holder.tvTitle.setText(singleItem.getTitle());
        holder.tvBarCode.setText(singleItem.getUser());
        holder.tvDate.setText(singleItem.getImage());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLiteHelper.deleteData(singleItem.getId());
                notifyDataSetChanged();
                itemsList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, itemsList.size());
            }
        });

        if (Patterns.WEB_URL.matcher(singleItem.getUser()).matches()) {
            holder.gotoLink.setVisibility(View.VISIBLE);
        }
        holder.gotoLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(singleItem.getUser()));
                    mContext.startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Error : " + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.copyToClip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", singleItem.getUser().toString().trim());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "Copied To ClipBoard", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        View mView;
        protected TextView tvTitle, tvBarCode, tvDate;
        ImageView delete, gotoLink, copyToClip;

        public SingleItemRowHolder(View view) {
            super(view);
            mView = view;
            this.tvTitle = mView.findViewById(R.id.title);
            this.delete = mView.findViewById(R.id.delete);
            this.tvBarCode = mView.findViewById(R.id.bar_code);
            this.tvDate = mView.findViewById(R.id.date);
            this.gotoLink = mView.findViewById(R.id.gotoLink);
            this.copyToClip = mView.findViewById(R.id.copyClip);
            gotoLink.setVisibility(View.GONE);

        }
    }
}