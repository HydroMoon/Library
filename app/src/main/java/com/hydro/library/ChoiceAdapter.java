package com.hydro.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {

    private List<String> Choices;
    private Context context;
    DismissDialog dialog;
    private AdapterParse adapterParse;

    public ChoiceAdapter(List<String> mChoices, Context mContext, DismissDialog diag) {
        Choices = mChoices;
        context = mContext;
        dialog = diag;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ChoiceType;

        public ViewHolder(View itemView) {
            super(itemView);

            ChoiceType = itemView.findViewById(R.id.choiceText);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            DismissDialog dismissDialog = dialog;
            dismissDialog.Dismiss();
            ConnectInternet testConnection = new ConnectInternet(context);

            if (testConnection.is_connected()) {
                new JsonParser(new AsyncResponse() {
                    @Override
                    public void AResponse(List<Object> bList) {
                        if (bList != null) {
                            Toast.makeText(context, ChoiceType.getText(), Toast.LENGTH_SHORT).show();
                        }
                        adapterParse = (AdapterParse) context;
                        adapterParse.SendData(bList);
                    }
                }).execute();
            } else {
                Toast.makeText(context, "No internet connection detected", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.choice_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.ChoiceType.setText(Choices.get(position));

    }

    @Override
    public int getItemCount() {
        return Choices.size();
    }
}
