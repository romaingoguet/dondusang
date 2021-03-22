package com.romaingoguet.android.blood.ui.don;


import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.databinding.FragmentDonsBinding;
import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.utils.Utils;
import com.romaingoguet.android.blood.ui.common.DonAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonsFragment extends Fragment {

    private DonViewModel donViewModel;
    private AddDonationViewModel addDonationViewModel;
    private String TAG = "lol";
    private FragmentDonsBinding binding;
    private int itemNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Mes Dons");


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dons, null, false);
        View view = binding.getRoot();

        DonHandlers donHandlers = new DonHandlers();
        binding.setClickHandler(donHandlers);

        binding.setLifecycleOwner(this);

        RecyclerView recyclerView = view.findViewById(R.id.don_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final DonAdapter adapter = new DonAdapter();
        recyclerView.setAdapter(adapter);

        addDonationViewModel = ViewModelProviders.of(this).get(AddDonationViewModel.class);
        donViewModel = ViewModelProviders.of(this).get(DonViewModel.class);
        donViewModel.getAllDons().observe(this, dons -> {
            Log.d(TAG, "onChanged: " + dons.size());
            itemNumber = dons.size();
            binding.setItemNumber(itemNumber);
            if (dons.size() != 0) {
                //modify list to insert just years
//                List<Don> newlist = Utils.addYearToList(dons);
                adapter.setDons(dons);
            }
        });

        donViewModel.getCloseDialog().observe(this, aBoolean -> {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Don Sauvegarder", Toast.LENGTH_SHORT);
            toast.show();
        });

        donViewModel.getToastMessage().observe(this, s -> {
            Log.d(TAG, "onChanged: toast ok");
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    public class DonHandlers {

        public void onClickAddButton1(View view) {
            donViewModel.setIsNewDon(true);
            Log.d(TAG, "onClickAddButton: fab click");
            AddDonationDialog dialog = new AddDonationDialog();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            addDonationViewModel.openNewDon(null);
            dialog.show(ft, AddDonationDialog.TAG);
        }


    }

}
