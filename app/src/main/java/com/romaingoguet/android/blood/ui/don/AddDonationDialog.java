package com.romaingoguet.android.blood.ui.don;


import android.app.DatePickerDialog;
import android.app.Dialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.databinding.FragmentAddDonationDialogBinding;
import com.romaingoguet.android.blood.utils.Utils;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDonationDialog extends DialogFragment {

    public static String TAG = "lol";
    private FragmentAddDonationDialogBinding binding;
    private DonViewModel addDonationViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_donation_dialog, container, false);

        View view = binding.getRoot();

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        toolbar.setNavigationOnClickListener(v -> {
            dismiss();
        });

        toolbar.setTitle("Ajouter un don");

        EventHandlers eventHandlers = new EventHandlers();
        addDonationViewModel = ViewModelProviders.of(this).get(DonViewModel.class);
        addDonationViewModel.changeDateAsToday();
        addDonationViewModel.setSangType();

        addDonationViewModel.getCloseDialog().observe(this, aBoolean -> {
            this.dismiss();
        });

        addDonationViewModel.getIsNewDon().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                Log.d(TAG, "onChanged: " + aBoolean.toString());
            }
        });

        addDonationViewModel.getToastMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });



        binding.setViewmodel(addDonationViewModel);
        binding.setEventHandlers(eventHandlers);

        binding.setLifecycleOwner(this);

        return view;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item);
        if (item.getItemId() == android.R.id.home) {
            dismiss();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    public class EventHandlers {

        public void onClickButton(View view) {
            Log.d(TAG, "onClickButton: date");
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Log.d(TAG, "onDateSet: " + dayOfMonth + "/" + month + "/" + year);
                    now.set(year, month, dayOfMonth);
                    addDonationViewModel.setDate(Utils.calendarDateToString(now));
                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        }
    }

}
