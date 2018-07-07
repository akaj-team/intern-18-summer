package asiantech.internship.summer.drawerlayout.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import asiantech.internship.summer.R;
import asiantech.internship.summer.drawerlayout.models.OptionData;
import de.hdodenhof.circleimageview.CircleImageView;

public class DrawerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;

    private List<OptionData> mListData;
    private ClickListener mListener;

    public DrawerAdapter(List<OptionData> mListData, ClickListener listener) {
        this.mListData = mListData;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_header, parent, false);
            return new ViewHolderHeader(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_function, parent, false);
            return new ViewHolderFunction(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                break;
            case TYPE_TWO:
                initLayoutFunction((ViewHolderFunction) holder, position);
                break;
        }
    }

    private void initLayoutFunction(ViewHolderFunction holder, int position) {
        OptionData data = mListData.get(position - 1);
        holder.imgOption.setImageResource(data.getOptionImage());
        holder.tvOption.setText(data.getOptionName());
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : (mListData.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    public interface ClickListener {
        void onItemClick(String nameFunction);

        void onImageAvatarClick(CircleImageView circleImageView);
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {
        private final CircleImageView cImgHeader;

        private ViewHolderHeader(View itemView) {
            super(itemView);
            cImgHeader = itemView.findViewById(R.id.imgAvatar);
            cImgHeader.setOnClickListener(view -> mListener.onImageAvatarClick(cImgHeader));
        }
    }

    private class ViewHolderFunction extends RecyclerView.ViewHolder {
        private final ImageView imgOption;
        private final TextView tvOption;

        private ViewHolderFunction(View itemView) {
            super(itemView);
            imgOption = itemView.findViewById(R.id.imgOption);
            tvOption = itemView.findViewById(R.id.tvOptionName);

            itemView.setOnClickListener(view -> mListener.onItemClick(tvOption.getText().toString()));
        }
    }
}

