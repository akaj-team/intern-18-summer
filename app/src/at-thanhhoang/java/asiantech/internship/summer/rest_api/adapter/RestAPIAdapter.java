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

public class RestAPIAdapter extends RecyclerView.Adapter<RestAPIAdapter.MyViewHolder> {
    private Context mContext;
    private List<String> mUrlList;
    private OnClickImagesListener mListener;

    public RestAPIAdapter(Context context, List<String> listUrl,@NonNull OnClickImagesListener listener) {
        mContext = context;
        mUrlList = listUrl;
        mListener = listener;
    }

    @NonNull
    @Override
    public RestAPIAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_item_rest_api, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestAPIAdapter.MyViewHolder holder, int position) {
        String url = mUrlList.get(position);
        Glide.with(mContext).load(url).into(holder.mImgResult);
    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgResult;

        private MyViewHolder(View itemView) {
            super(itemView);
            mImgResult = itemView.findViewById(R.id.imgResultAPI);
            mImgResult.setOnClickListener(v -> mListener.onImageClick(getAdapterPosition()));
        }
    }

    /**
     * This Interface is used to click on image inside recycler view
     * @param position : position of image in recycler view
     */
    public interface OnClickImagesListener {
        void onImageClick(int position);
    }
}
