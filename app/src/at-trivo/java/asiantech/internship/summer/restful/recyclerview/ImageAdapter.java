package asiantech.internship.summer.restful.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.restful.model.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    private List<Image> mImages;
    private Context mContext;

    public ImageAdapter(List<Image> images, Context context) {
        mImages = images;
        mContext = context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_images, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = mImages.get(position);
        Glide.with(mContext).load(image.url).into(holder.imgPicture);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }
}
