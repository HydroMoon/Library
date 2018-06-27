package com.hydro.library;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class MainAdapterGrid extends RecyclerView.Adapter<MainAdapterGrid.ViewHolder> {

    private List<Lectures> myLectures;
    private Context context;
    private AdapterParse adapterParse;
    private String lecName;

    MainAdapterGrid(List<Lectures> myLec, Context mContext) {
        myLectures = myLec;
        context = mContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView lec_Name;
        TextView en_Name;

        ViewHolder(View itemView) {
            super(itemView);

            lec_Name = itemView.findViewById(R.id.lecName);
            en_Name = itemView.findViewById(R.id.en_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), lecName.getText(), Toast.LENGTH_LONG).show();

            /*ChoiceDialog dialog = new ChoiceDialog(view.getContext());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();*/

            lecName = "";
            String[] Dirs = {"ExpertSystems", "ElectronicCommerce", "SoftwareTesting",
                    "SoftwareMeth", "InformationRetrieval", "CloudComputing",
                    "InformationSecurity", "UserInterface"};
            switch (String.valueOf(en_Name.getText())) {
                case "ES":
                    lecName = "ExpertSystems";
                    break;
                case "EC":
                    lecName = "ElectronicCommerce";
                    break;
                case "ST":
                    lecName = "SoftwareTesting";
                    break;
                case "SM":
                    lecName = "SoftwareMeth";
                    break;
                case "IR":
                    lecName = "InformationRetrieval";
                    break;
                case "CC":
                    lecName = "CloudComputing";
                    break;
                case "IS":
                    lecName = "InformationSecurity";
                    break;
                case "UI":
                    lecName = "UserInterface";
                    break;
            }

            ConnectInternet testConnection = new ConnectInternet(context);


            if (testConnection.is_connected()) {
                new InternetCheck(new InternetCheck.Consumer() {
                    @Override
                    public void accept(Boolean internet) {
                        if (internet) {
                            new JsonParser(new AsyncResponse() {
                                @Override
                                public void AResponse(List<Object> bList) {
                                    adapterParse = (AdapterParse) context;
                                    adapterParse.SendData(bList);
                                }
                            }).execute(lecName);
                        } else  {
                            ListFilesAndDirectories list = new ListFilesAndDirectories(lecName);
                            File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "ELibrary" + File.separator + lecName);
                            String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
                            Fragment f = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
                            LibraryFragment fragment = (LibraryFragment) f;
                            fragment.FillAdapter(list.walk(dir));
                        }
                    }
                });

            } else {
                ListFilesAndDirectories list = new ListFilesAndDirectories(lecName);
                File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "ELibrary" + File.separator + lecName);
                String tag = "android:switcher:" + R.id.viewpager + ":" + 1;
                Fragment f = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(tag);
                LibraryFragment fragment = (LibraryFragment) f;
                fragment.FillAdapter(list.walk(dir));
            }
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.main_frag_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.lec_Name.setText(myLectures.get(position).getLecName());
        holder.en_Name.setText(myLectures.get(position).getLecIcon());

    }

    @Override
    public int getItemCount() {
        return myLectures.size();
    }
}
