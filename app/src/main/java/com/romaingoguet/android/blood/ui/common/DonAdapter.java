package com.romaingoguet.android.blood.ui.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.romaingoguet.android.blood.BR;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.ui.don.DonViewModel;

import java.util.ArrayList;
import java.util.List;

public class DonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Don> dons = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.don_item_new, parent, false);
        return new DonHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        if (dons.get(position).getType().equals("")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Don currentNote = dons.get(position);
        DonHolder donHolder = (DonHolder) viewHolder;
        donHolder.bind(currentNote);
    }

    public void setDons(List<Don> dons) {
        this.dons = dons;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dons.size();
    }

    public class DonHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public DonHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }


        public void bind(Don don) {
            this.binding.setVariable(BR.donholder, this);
            this.binding.setVariable(BR.don, don);
            this.binding.executePendingBindings();
        }

        public boolean onItemLongClick(Don don) {
            AppCompatActivity activity = (AppCompatActivity) binding.getRoot().getContext();

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Etes-vous sur de vouloir supprimer ce don: " + don.getPlace() + " ?");

            builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                // do the delete item
                DonViewModel donViewModel = ViewModelProviders.of(activity).get(DonViewModel.class);
                donViewModel.delete(don);
                dons.remove(don);
                notifyDataSetChanged();
            });
            builder.setNegativeButton(android.R.string.cancel, null);
            builder.create().show();
            return false;
        }
    }

    public class YearHolder extends RecyclerView.ViewHolder {

        ViewDataBinding binding;

        public YearHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Don don) {
            this.binding.setVariable(BR.yearholder, this);
            this.binding.setVariable(BR.don, don);
            this.binding.executePendingBindings();
        }

    }
}
