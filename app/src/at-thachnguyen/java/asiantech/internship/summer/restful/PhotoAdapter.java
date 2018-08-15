package asiantech.internship.summer.restful;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.restful.model.Photo;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {
    private final List<Photo> mListPhoto;
    private final Context mContext;

    PhotoAdapter(List<Photo> photos, Context context) {
        mListPhoto = photos;
        mContext = context;
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_image, parent, false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoHolder holder, int position) {
        Glide.with(mContext).load(mListPhoto.get(position).getUrl())
                .into(holder.mImgPhoto);
    }

    @Override
    public int getItemCount() {
        return mListPhoto.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {
        final ImageView mImgPhoto;

        PhotoHolder(View itemView) {
            super(itemView);
            mImgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }
}
