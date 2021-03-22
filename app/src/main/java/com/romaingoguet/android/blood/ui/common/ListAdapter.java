package com.romaingoguet.android.blood.ui.common;


import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.romaingoguet.android.blood.BR;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.data.models.Result;
import com.romaingoguet.android.blood.ui.center.CenterActivity;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Result> mCollectCenters;

    public ListAdapter(List<Result> centers) {
        mCollectCenters = centers;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.collect_center_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ViewHolder holder, int position) {
        Result center = mCollectCenters.get(position);
        holder.bind(center);
    }

    @Override
    public int getItemCount() {
        return mCollectCenters.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewDataBinding binding;

        public ViewHolder(@NonNull ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }

        public void bind(Result center) {
            this.binding.setVariable(BR.viewHolder, this);
            this.binding.setVariable(BR.center, center);
            this.binding.executePendingBindings();
        }


        public void onClickCenter(Result center) {
            Log.d("lol", "onClickCenter: " + getLayoutPosition());
            AppCompatActivity activity = (AppCompatActivity) binding.getRoot().getContext();
            Intent intent = new Intent(activity, CenterActivity.class);
            intent.putExtra("CollectCenter", center);
            activity.startActivity(intent);
        }
    }

}
