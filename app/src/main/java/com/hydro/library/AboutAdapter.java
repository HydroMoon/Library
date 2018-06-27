package com.hydro.library;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AboutAdapter extends RecyclerView.Adapter<AboutAdapter.ViewHolder> {

    List<About_Info> Info;

    Context context;

    public AboutAdapter(List<About_Info> info, Context ctx) {
        Info = info;
        context = ctx;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView Text_Info;
        TextView Text_Value;
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);

            Text_Info = itemView.findViewById(R.id.abouttext);
            Text_Value = itemView.findViewById(R.id.aboutvalue);
            icon = itemView.findViewById(R.id.about_icon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            switch (pos) {
                case 0:
                    String tel = Text_Value.getText().toString();
                    try {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.trim()));
                        v.getContext().startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "Qasim", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 1:
                    try {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + Text_Value.getText()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Test Email");
                        v.getContext().startActivity(Intent.createChooser(intent, "Chooser Title"));
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "Test 123", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    String url = Text_Value.getText().toString().trim();
                    try {
                        Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url));
                        v.getContext().startActivity(browser);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(v.getContext(), "Test123123", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }


        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.about_layout_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.Text_Info.setText(Info.get(position).getAbout_Text());
        holder.Text_Value.setText(" " + Info.get(position).getAbout_value());

        ViewCompat.setLayoutDirection(holder.itemView, ViewCompat.LAYOUT_DIRECTION_RTL);


        switch (position) {
            case 0:
                holder.icon.setImageResource(R.drawable.baseline_local_phone_black_24);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.baseline_email_black_24);
                break;
            case 2:
                holder.icon.setImageResource(R.drawable.baseline_language_black_24);
                break;
            case 3:
                holder.icon.setImageResource(R.drawable.baseline_location_on_black_24);
                break;
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return Info.size();
    }
}
