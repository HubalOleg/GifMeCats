package oleg.hubal.com.gifmecats.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import oleg.hubal.com.gifmecats.Constants;
import oleg.hubal.com.gifmecats.MainActivity;
import oleg.hubal.com.gifmecats.R;

/**
 * Created by User on 07.04.2016.
 */
public class AddCatFragment extends DialogFragment implements View.OnClickListener {

    private View view;
    private Spinner spinner;
    private EditText etCatCount;
    private Button btnAdd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(R.string.dialog_title);
        view = inflater.inflate(R.layout.fragment_dialog, container, false);

        initViews();

        return view;
    }

    private void initViews() {
        spinner = (Spinner) view.findViewById(R.id.spinner_FD);
        etCatCount = (EditText) view.findViewById(R.id.etCatCount_FD);
        btnAdd = (Button) view.findViewById(R.id.btnAddCat_FD);

        Bundle bundle = getArguments();
        ArrayList<String> categoryList = bundle.getStringArrayList(Constants.KEY_CATEGORY);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryList);
        spinner.setAdapter(adapter);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String chosenCategory = spinner.getSelectedItem().toString();

        String etText = etCatCount.getText().toString();
        int catCount = 0;
        if (!etText.isEmpty()) {
            catCount = Integer.parseInt(etCatCount.getText().toString());
        }
        if (catCount > 0 && catCount < 101) {

            ((MainActivity) getActivity()).onDialogAddClicked(catCount, chosenCategory);

            dismiss();
        } else {
            Toast.makeText(getContext(), Constants.ERROR_COUNT, Toast.LENGTH_LONG).show();
        }
    }
}
