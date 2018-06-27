package com.hydro.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {

    List<Object> myBooks = new ArrayList<>();
    CustomRecyclerView recyclerView;
    LecturesAdapter adapter;
    TextView emptyRV;

    public LibraryFragment() {
    }

    public void FillAdapter(List<Object> bList) {
        myBooks = bList;
        adapter = new LecturesAdapter(myBooks);
        recyclerView.setAdapter(adapter);
        if (bList != null) {
            ((MainActivity) getActivity()).getPager().setCurrentItem(1);
        } else {
            Toast.makeText(getContext(), "Error 404", Toast.LENGTH_SHORT).show();
        }
    }


    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
/*        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = fView.findViewById(R.id.bookslist);
        emptyRV = fView.findViewById(R.id.empty_view);
        recyclerView.setEmptyView(emptyRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LecturesAdapter(myBooks);
        //recyclerView.addItemDecoration(new HeaderItemDecoration(recyclerView, (HeaderItemDecoration.StickyHeaderInterface) adapter));
        recyclerView.setAdapter(adapter);

        /*myBooks.add(new BooksInfo("Web Application Lec 1.pptx", "315.45 KB"));
        myBooks.add(new BooksInfo("Operating Systems Lec 3.docx", "1.45 MB"));
        myBooks.add(new BooksInfo("Computer Network Lec 8.docx", "546.4 KB"));
        myBooks.add(new BooksInfo("This is a Test.pptx", "785.2 KB"));
        myBooks.add(new BooksInfo("IDK what happend.docx", "656 KB"));
        myBooks.add(new BooksInfo("Web Application Lec 1.pptx", "315.45 KB"));
        myBooks.add(new BooksInfo("Operating Systems Lec 3.docx", "1.45 MB"));
        myBooks.add(new BooksInfo("Computer Network Lec 8.dox", "546.4 KB"));
        myBooks.add(new BooksInfo("This is a Test.pptx", "785.2 KB"));
        myBooks.add(new BooksInfo("IDK what happend.docx", "656 KB"));
        myBooks.add(new BooksInfo("Web Application Lec 1.pptx", "315.45 KB"));
        myBooks.add(new BooksInfo("Operating Systems Lec 3.docx", "1.45 MB"));
        myBooks.add(new BooksInfo("Computer Network Lec 8.docx", "546.4 KB"));
        myBooks.add(new BooksInfo("This is a Test.pptx", "785.2 KB"));
        myBooks.add(new BooksInfo("IDK what happend.docx", "656 KB"));
        myBooks.add(new BooksInfo("Web Application Lec 1.pptx", "315.45 KB"));
        myBooks.add(new BooksInfo("Operating Systems Lec 3.docx", "1.45 MB"));
        myBooks.add(new BooksInfo("Computer Network Lec 8.docx", "546.4 KB"));
        myBooks.add(new BooksInfo("This is a Test.pptx", "785.2 KB"));
        myBooks.add(new BooksInfo("IDK what happend.docx", "656 KB"));*/



        return fView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}
