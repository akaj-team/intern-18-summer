package asiantech.internship.summer.rest_api.adapter;

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

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mListUrl;
    private clickImagesListener mListener;

    public ImageAdapter(Context context, List<String> listUrl, clickImagesListener listener) {
        this.mContext = context;
        this.mListUrl = listUrl;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_rest_api, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        String url = mListUrl.get(position);

        Glide.with(mContext).load(url).into(holder.mImgResultAPI);
    }

    @Override
    public int getItemCount() {
        return mListUrl.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgResultAPI;

        private MyViewHolder(View itemView) {
            super(itemView);
            mImgResultAPI = itemView.findViewById(R.id.imgResultAPI);
            mImgResultAPI.setOnClickListener(v -> mListener.onImageClick(getAdapterPosition()));
        }
    }

    public interface clickImagesListener{
        void onImageClick(int id);
    }
}
