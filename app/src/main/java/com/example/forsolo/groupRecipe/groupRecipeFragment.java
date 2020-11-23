package com.example.forsolo.groupRecipe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.forsolo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class groupRecipeFragment extends Fragment {

    private View view;

    private RecipeBordAdapter bordAdapter;

    RecyclerView recyclerView;

    FloatingActionButton fab;

    EditText searchView;

    ArrayList<RecipeBoardInfo> r_getDataList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerViewAction();
        fabAction();
        refresh();

        filter();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        searchView = view.findViewById(R.id.RecipeSearchView);
        searchView.setText("");

        r_getDataList.clear();
        bordAdapter.notifyDataSetChanged();
        recyclerView.removeAllViewsInLayout();
        recyclerView.removeAllViews();
        bordAdapter.refresh();
        bordAdapter.notifyDataSetChanged();
        getData();

    }

    private void refresh() {
        final SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.RecipeSwipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPinkPurple);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                r_getDataList.clear();
                bordAdapter.notifyDataSetChanged();
                recyclerView.removeAllViewsInLayout();
                recyclerView.removeAllViews();
                bordAdapter.refresh();
                bordAdapter.notifyDataSetChanged();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public void fabAction() {
        fab = view.findViewById(R.id.recipe_write_text);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), RecipeWritePostActivity.class);

                onDestroyView();

                r_getDataList.clear();
                bordAdapter.notifyDataSetChanged();
                recyclerView.removeAllViewsInLayout();
                recyclerView.removeAllViews();
                bordAdapter.refresh();
                bordAdapter.notifyDataSetChanged();

                startActivity(intent);
            }
        });
    }


    public void recyclerViewAction() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView = view.findViewById(R.id.Recipe_Bord_RecyclerView);

        recyclerView.setLayoutManager(layoutManager);

        bordAdapter = new RecipeBordAdapter(getActivity());

        bordAdapter.refresh();

        recyclerView.setAdapter(bordAdapter);

    }

    public void getData() {
        // 데이터 받아오는 공간


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Recipe").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    List<RecipeBoardInfo> resultData = task.getResult().toObjects(RecipeBoardInfo.class);

                    r_getDataList.clear();

                    r_getDataList.addAll(resultData);

                    // 정렬 해보자
                    Collections.sort(r_getDataList, new Comparator<RecipeBoardInfo>() {
                        @Override
                        public int compare(RecipeBoardInfo o1, RecipeBoardInfo o2) {
                            return o1.r_getDate().compareTo(o2.r_getDate());
                        }
                    });

                    Collections.reverse(r_getDataList);

                    for (int i = 0; i < r_getDataList.size(); i++) {
                        bordAdapter.addData(r_getDataList.get(i));
                    }

                    bordAdapter.setList();

                    bordAdapter.notifyDataSetChanged();

                } else {
                    Log.d("not working", "yes");
                }
            }
        });
    }

    public void filter() {
        searchView = view.findViewById(R.id.RecipeSearchView);

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                bordAdapter.filter(text);
            }
        });
    }

}
