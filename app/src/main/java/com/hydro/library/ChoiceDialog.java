package com.hydro.library;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class ChoiceDialog extends Dialog implements DismissDialog {

    Context context;
    RecyclerView choiceRecycler;
    List<String> choiceList = new ArrayList<>();
     ChoiceAdapter choiceAdapter;

    public ChoiceDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void Dismiss() {
        this.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*LayoutInflater inflater = LayoutInflater.from(context);
        View dialog = inflater.inflate(R.layout.dialog, null, false);*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        choiceRecycler = getWindow().findViewById(R.id.choiceRecycler);

        PrepareAdapter();
    }

    private void PrepareAdapter() {

        choiceList.add("Lectures");
        choiceList.add("Labs");
        choiceList.add("Exams");
        DismissDialog dialog = this;
        choiceAdapter = new ChoiceAdapter(choiceList, context, dialog);

        choiceRecycler.setLayoutManager(new LinearLayoutManager(context));
        choiceRecycler.setAdapter(choiceAdapter);
    }
}
