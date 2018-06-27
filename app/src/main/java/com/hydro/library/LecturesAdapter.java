package com.hydro.library;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LecturesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = LecturesAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Object> myBooks;
    private BooksInfo booksInfo;

    public LecturesAdapter(List<Object> myBKS) {
        myBooks = myBKS;
    }


    public class itemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bookName;
        TextView bookSize;
        ImageView imgIcon;

        TextView type;

        ProgressBar DownProgress;

        public itemViewHolder(View itemView) {
            super(itemView);

            bookName = itemView.findViewById(R.id.bookName);
            bookSize = itemView.findViewById(R.id.bookSize);
            imgIcon = itemView.findViewById(R.id.bookImg);
            DownProgress = itemView.findViewById(R.id.download_progress_row);

            type = new TextView(itemView.getContext());

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), bookName.getText(), Toast.LENGTH_LONG).show();

            if (booksInfo.getBookName().contains("/")) {
                OpenFile(new File(booksInfo.getBookName()), view.getContext(), String.valueOf(bookName.getText()));
            } else {
                Log.i("LEC", bookName.getText().toString());
                new Downloader(view.getContext(), DownProgress).execute(String.valueOf(bookName.getText()), String.valueOf(type.getText()), booksInfo.getBookLecture());
            }
        }
    }

    public class headerViewHolder extends RecyclerView.ViewHolder {

        TextView header;

        public headerViewHolder(View itemView) {
            super(itemView);

            header = itemView.findViewById(R.id.text_header);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_HEADER) {
            View v1 = inflater.inflate(R.layout.recycle_row_header, parent, false);
            return new headerViewHolder(v1);
        } else if (viewType == TYPE_ITEM) {
            View v2 = inflater.inflate(R.layout.recycle_rows, parent, false);
            return new itemViewHolder(v2);
        }
        throw new RuntimeException("DDD");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case TYPE_HEADER:
                headerViewHolder headerHolder = (headerViewHolder) holder;
                headerHolder.header.setText((CharSequence) myBooks.get(position));
                break;
            case TYPE_ITEM:
                itemViewHolder itemHolder = (itemViewHolder) holder;
                booksInfo = (BooksInfo) myBooks.get(position);
                if (booksInfo != null) {
                    if (booksInfo.getBookName().contains("/")) {
                        itemHolder.bookName.setText(booksInfo.getBookName().substring(booksInfo.getBookName().lastIndexOf("/")+1));
                    } else {
                        itemHolder.bookName.setText(booksInfo.getBookName());
                    }
                    itemHolder.type.setText(booksInfo.getBookType());
                    itemHolder.bookSize.setText(booksInfo.getBookSize());
                }
                if (myBooks.get(position) instanceof BooksInfo) {
                    if (booksInfo.getBookName().endsWith(".docx")){
                        itemHolder.imgIcon.setImageResource(R.drawable.doc);
                    }else if (booksInfo.getBookName().endsWith(".doc")){
                        itemHolder.imgIcon.setImageResource(R.drawable.doc);
                    }else if (booksInfo.getBookName().endsWith(".pdf")){
                        itemHolder.imgIcon.setImageResource(R.drawable.pdf);
                    }else if (booksInfo.getBookName().endsWith(".ppt")){
                        itemHolder.imgIcon.setImageResource(R.drawable.ppt);
                    }else if (booksInfo.getBookName().endsWith(".pptx")){
                        itemHolder.imgIcon.setImageResource(R.drawable.ppt);
                    }else if (booksInfo.getBookName().endsWith(".rar")){
                        itemHolder.imgIcon.setImageResource(R.drawable.zip);
                    }else if (booksInfo.getBookName().endsWith(".txt")){
                        itemHolder.imgIcon.setImageResource(R.drawable.txt);
                    }else if (booksInfo.getBookName().endsWith(".zip")){
                        itemHolder.imgIcon.setImageResource(R.drawable.zip);
                    } else {
                        itemHolder.imgIcon.setImageResource(R.mipmap.ic_launcher);
                    }

                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return myBooks.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (myBooks.get(position) instanceof String) {
            return TYPE_HEADER;
        } else if (myBooks.get(position) instanceof BooksInfo) {
            return TYPE_ITEM;
        }
        return -1;
    }

    private void OpenFile(File file, Context context, String fileName) {
        Intent openIntent = new Intent(Intent.ACTION_VIEW);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        openIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        MimeTypeMap typeMap = MimeTypeMap.getSingleton();
        String mime_type = typeMap.getMimeTypeFromExtension(fileEx(fileName));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileURI = FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    file);
            openIntent.setDataAndType(fileURI, mime_type);
        } else {
            Uri fileURI = Uri.fromFile(file);
            openIntent.setDataAndType(fileURI, mime_type);
        }

        try {

            context.startActivity(openIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "no app found", Toast.LENGTH_LONG).show();
        }
    }

    private String fileEx(String uri){
        if (uri.contains("?")){
            uri = uri.substring(0,uri.indexOf("?"));
        }
        if (uri.lastIndexOf(".")==-1){
            return null;
        }else{
            String ext = uri.substring(uri.lastIndexOf(".") + 1);

            if (ext.contains("%")){
                ext = ext.substring(0,ext.indexOf("%"));
            }
            if (ext.contains("/")){
                ext  = ext.substring(0,ext.indexOf("/"));
            }
            return ext.toLowerCase();
        }
    }

}
