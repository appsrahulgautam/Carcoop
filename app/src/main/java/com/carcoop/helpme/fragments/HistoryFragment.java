package com.carcoop.helpme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carcoop.helpme.Constance;
import com.carcoop.helpme.R;
import com.carcoop.helpme.adapters.EmergencyList_adapter;
import com.carcoop.helpme.interfaces.HistoryDeleteOnclicklisterner;
import com.carcoop.helpme.pojos.Emergency_detailPOJO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import io.paperdb.Paper;

public class HistoryFragment extends Fragment implements HistoryDeleteOnclicklisterner, LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = "HistoryFragment";
    private static final int GRID_COUNT = 2;
    private static final int DELETE_FILE_ID = 1101;
    private static final String FILE_NAME = "filename";
    private RecyclerView history_RV;
    private ConstraintLayout emptycontainer;
    private EmergencyList_adapter adapter;
    private ArrayList<Emergency_detailPOJO> emergency_detailPOJOS;
    private Context mcontext;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initviews(view);

    }

    @Override
    public void onStart() {
        super.onStart();
        setRecyclerview();
    }

    private void setRecyclerview() {

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),GRID_COUNT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        if (Paper.book().contains(Constance.EMERG_STORE)) {

            emergency_detailPOJOS = Paper.book().read(Constance.EMERG_STORE);
        } else {
            emergency_detailPOJOS = new ArrayList<>();
        }

        Collections.reverse(emergency_detailPOJOS);

        adapter = new EmergencyList_adapter(getContext(), emergency_detailPOJOS);
        history_RV.setLayoutManager(linearLayoutManager);
        adapter.setdeleteHistoryListener(this::onhistorydelete);
        history_RV.setAdapter(adapter);

        Log.e(TAG, "setRecyclerview: size---> " + adapter.getsize());

        checkforempty();

    }


    //    initviews
    private void initviews(View view) {

        history_RV = view.findViewById(R.id.history_RV);
        emptycontainer = view.findViewById(R.id.emptycontainer);
    }


    @Override
    public void onhistorydelete(int position) {
        ArrayList<Emergency_detailPOJO> emergency_detailPOJOS = Paper.book().read(Constance.EMERG_STORE);
        ArrayList<String> imageURIS = emergency_detailPOJOS.get(position).getImageuris();
        Log.e(TAG, "onhistorydelete: " + emergency_detailPOJOS.size() + " adapter array size->  " + adapter.getsize());
        emergency_detailPOJOS.remove(position);

        Paper.book().write(Constance.EMERG_STORE, emergency_detailPOJOS);
        checkforempty();

        deletefileinbackground(imageURIS, new Date().getSeconds());


    }

    public void deletefileinbackground(ArrayList<String> imageURIS, int seconds) {
        LoaderManager loaderManager = LoaderManager.getInstance(Objects.requireNonNull(getActivity()));


        Bundle bundle = new Bundle();
        bundle.putStringArrayList(FILE_NAME, imageURIS);
        loaderManager.initLoader(seconds, bundle, this).forceLoad();


    }


    private void checkforempty() {
        Log.e(TAG, "checkforempty: adapter.getsize-> " + adapter.getsize());
        if (adapter.getsize() <= 0) {
            showEmptyhistory();
        } else {
            hideEmptyhistory();
        }
    }


    private void hideEmptyhistory() {

//        Toast.makeText(getContext(), "hideEmptryhistory", Toast.LENGTH_SHORT).show();
        history_RV.setVisibility(View.VISIBLE);
        emptycontainer.setVisibility(View.GONE);

    }


    private void showEmptyhistory() {

//        Toast.makeText(getContext(), "showEmptryhistory", Toast.LENGTH_SHORT).show();
        history_RV.setVisibility(View.GONE);
        emptycontainer.setVisibility(View.VISIBLE);

    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {

        return new AsyntaskHolder(getActivity(), args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {


    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {


    }

    private static class AsyntaskHolder extends AsyncTaskLoader<String> {
        private Bundle args;

        public AsyntaskHolder(@NonNull Context context, Bundle args) {
            super(context);
            this.args = args;
        }

        @Nullable
        @Override
        public String loadInBackground() {

            for (String filename : args.getStringArrayList(FILE_NAME)) {
                File file = new File(filename);
                if (file.exists()) {
                    Log.e(TAG, "loadInBackground: file is deleted " + file.delete());
                }
            }
//            String filename = args.getString(FILE_NAME);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e(TAG, "run: file deleted-> "+filename );
//                }
//            }).start();


            return null;
        }
    }
}