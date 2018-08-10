package asiantech.internship.summer.retrofitandgson.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.retrofitandgson.model.Image;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageViewHolder> {
    private List<Image> mImageList;
    private Context mContext;

    public ListImageAdapter(List<Image> imageList, Context context) {
        mImageList = imageList;
        mContext = context;
    }

    @NonNull
    @Override
    public ListImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewholder_restful, parent, false);
        return new ListImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListImageViewHolder holder, int position) {
        Image image = mImageList.get(position);
        Glide.with(mContext).load(image.getUrl()).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }
}
