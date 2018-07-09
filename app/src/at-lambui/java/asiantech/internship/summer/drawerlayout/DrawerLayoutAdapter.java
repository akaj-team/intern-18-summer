package asiantech.internship.summer.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import asiantech.internship.summer.R;

public class DrawerLayoutAdapter extends RecyclerView.Adapter {
    private static final int TYPE_HEAD = 0;
    private List<ItemMenu> mListItemMenu;
    private List<UserHeader> mListUserHeader;
    private OnClickListener mOnClickListener;

    DrawerLayoutAdapter(List<ItemMenu> listItemMenu, List<UserHeader> listUserHeader, OnClickListener listener) {
        mListItemMenu = listItemMenu;
        mListUserHeader = listUserHeader;
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {
        View itemview;
        switch (viewtype) {
            case TYPE_HEAD:
                itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_header_viewholder, viewGroup, false);
                return new HeaderViewHolder(itemview, mOnClickListener);
            default:
                itemview = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_menu_viewholder, viewGroup, false);
                return new MenuItemViewHolder(itemview, mOnClickListener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        final int MENUITEM = 1;
        int HEADER = 0;
        return position == 0 ? HEADER : MENUITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (position) {
            case TYPE_HEAD: {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                UserHeader userHeader = mListUserHeader.get(position);
                if (userHeader.getUri() != null) {
                    headerViewHolder.getmImgAvatar().setImageURI(userHeader.getUri());
                } else {
                    headerViewHolder.getmImgAvatar().setImageResource(userHeader.getAvatar());
                }
                break;
            }
            default: {
                MenuItemViewHolder menuItemViewHolder = (MenuItemViewHolder) viewHolder;
                ItemMenu itemMenu = mListItemMenu.get(position - 1);
                menuItemViewHolder.setPosition(position - 1);
                menuItemViewHolder.getImgItemMenu().setImageResource(itemMenu.getImageItem());
                menuItemViewHolder.getTvItemMenu().setText(itemMenu.getTitleItem());
            }
        }
    }

    @Override
    public int getItemCount() {
        return mListItemMenu.size() + mListUserHeader.size();
    }
}

