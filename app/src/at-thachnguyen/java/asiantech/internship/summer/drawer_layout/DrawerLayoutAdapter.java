package asiantech.internship.summer.drawer_layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import asiantech.internship.summer.R;
import asiantech.internship.summer.drawer_layout.model.MenuItem;
import de.hdodenhof.circleimageview.CircleImageView;


public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MenuItem> mMenuItemList;
    private Context mContext;
    private List<Bitmap> mBitmaps;
    public static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;

    DrawerLayoutAdapter(List<MenuItem> mMenuItemList, Context mContext, List<Bitmap> mBitmaps) {
        this.mMenuItemList = mMenuItemList;
        this.mContext = mContext;
        this.mBitmaps = mBitmaps;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_item, parent, false);
            return new HeaderItemHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.menu_item, parent, false);
            return new MenuItemHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0) {
            ((HeaderItemHolder) holder).mImgAvtar.setImageBitmap(mBitmaps.get(0));
        }
        else {
            ((MenuItemHolder) holder).mImgItem.setImageResource(mMenuItemList.get(position - 1).getmImage());
            ((MenuItemHolder) holder).mTvTitle.setText(mMenuItemList.get(position - 1).getmTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) {
            viewType = 0;
        }
        return viewType;
    }

    @Override
    public int getItemCount() {
        return mMenuItemList.size() + mBitmaps.size();
    }

    class MenuItemHolder extends RecyclerView.ViewHolder {
        ImageView mImgItem;
        TextView mTvTitle;

        MenuItemHolder(View itemView) {
            super(itemView);
            mImgItem = itemView.findViewById(R.id.imgItem);
            mTvTitle = itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(v -> {
                MenuItem menuItem = mMenuItemList.get(getLayoutPosition() - 1);
                Toast.makeText(mContext, menuItem.getmTitle(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    class HeaderItemHolder extends RecyclerView.ViewHolder {
        CircleImageView mImgAvtar;
        Spinner mSpnMail;

        HeaderItemHolder(View itemView) {
            super(itemView);
            mSpnMail = itemView.findViewById(R.id.spnMail);
            mImgAvtar = itemView.findViewById(R.id.imgAvatar);
            mImgAvtar.setOnClickListener(v -> {
                final String[] items = new String[]{"Camera", "Gallery"};
                ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.select_dialog_item, items);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Add Photo");
                builder.setAdapter(adapter, (dialog, which) -> {

                    if (which == 0) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        ((Activity)mContext).startActivityForResult(intent, PICK_FROM_CAMERA);

                    } else {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        ((Activity)mContext).startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
                    }
                });
                builder.create();
                builder.show();
            });
        }

    }
}
