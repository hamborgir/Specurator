package com.example.specurator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.specurator.adapter.PhoneAdapter;
import com.example.specurator.database.DBHelper;
import com.example.specurator.model.PhoneModel;

import java.util.List;


public class SearchCompareFragment extends Fragment {

    RecyclerView fragRVContainer;
    List<PhoneModel> phoneList;
    EditText fragBarET;

    DBHelper dbHelper = DBHelper.getInstance(getContext());



    public SearchCompareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_compare, container, false);

        fragRVContainer = rootView.findViewById(R.id.fragRVContainer);
        fragBarET = rootView.findViewById(R.id.fragBarET);

        dbHelper = new DBHelper(getContext());

//        fragBarET.setHint("Find phone to compare...");

        fragBarET.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                performSearch();
                return true;
            }
            return false;
        });

        return rootView;
    }

    private void performSearch() {
        String searchString = fragBarET.getText().toString().trim();
        if (!searchString.isEmpty()) {
            List<PhoneModel> phoneList = dbHelper.getPhonesByName(searchString);
            fillRV(phoneList);
        }
    }

    private void fillRV(List<PhoneModel> phoneList) {
        PhoneAdapter adapter = new PhoneAdapter(phoneList, CompareActivity.class);
        fragRVContainer.setLayoutManager(new LinearLayoutManager(getContext()));
        fragRVContainer.setAdapter(adapter);
    }
}