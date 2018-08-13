package asiantech.internship.summer.async_task_thread_and_handler;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import asiantech.internship.summer.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {
    private final List<Bitmap> mListImage;
    private final Context mContext;

    ImageAdapter(List<Bitmap> mListImage, Context mContext) {
        this.mListImage = mListImage;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_images_download, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        holder.mImgBig.setImageBitmap(mListImage.get(position));
        holder.mImgSmall.setImageBitmap(mListImage.get(position));
    }

    @Override
    public int getItemCount() {
        return mListImage.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        private final ImageView mImgBig;
        private final ImageView mImgSmall;

        ImageHolder(View itemView) {
            super(itemView);
            mImgBig = itemView.findViewById(R.id.imgBig);
            mImgSmall = itemView.findViewById(R.id.imgSmall);
        }
    }
}
