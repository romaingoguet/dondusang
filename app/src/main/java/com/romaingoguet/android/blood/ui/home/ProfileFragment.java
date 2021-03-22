package com.romaingoguet.android.blood.ui.home;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.databinding.FragmentProfileBinding;
import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.ui.main.MainActivity;
import com.romaingoguet.android.blood.utils.DonUtils;
import com.romaingoguet.android.blood.utils.Utils;

import java.util.List;


public class ProfileFragment extends Fragment {

    private static final String TAG = "lol";
    private ProfileViewModel viewModel;
    private FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Accueil");

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, null, false);
        //binding.setViewModel(viewModel);

        MainActivity mainActivity = (MainActivity) getActivity();
        MainActivity.ClickHandler clickHandler = mainActivity.new ClickHandler(mainActivity);

        binding.setHandler(clickHandler);

        View view = binding.getRoot();

        viewModel.getLastDonDone().observe(this, new Observer<List<Don>>() {
            @Override
            public void onChanged(List<Don> dons) {
                String[] newdons;
                if (dons.size() > 0) {
                    newdons = DonUtils.calculFuturDonationsFromList(dons);
                } else {
                    newdons = new String[]{DonUtils.getNextDonDate(DonUtils.NOW), DonUtils.getNextDonDate(DonUtils.NOW), DonUtils.getNextDonDate(DonUtils.NOW)};
                }
                binding.setViewModel(newdons);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onStop() {
        // stop the autocycle of the slider, this is for leak memory purpose
        super.onStop();
    }


}
