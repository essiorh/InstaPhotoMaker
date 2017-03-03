package developer.essiorh.instaphotomaker.presentatioin;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import developer.essiorh.instaphotomaker.common.App;
import developer.essiorh.instaphotomaker.R;

/**
 * Created by eSSiorh
 * on 28.02.17
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<String> urlList;
    private final MainRouter router;

    public ListAdapter(List<String> urlList, MainRouter router) {
        this.urlList = urlList;
        this.router = router;
    }

    @SuppressLint("InflateParams")
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(App.getContext()).inflate(R.layout.list_item,
                null));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final String item = getItem(position);
        holder.ivFresco.setImageURI(item);
        holder.itemView.setOnClickListener(v -> router.openDetailInfo(item));
    }

    @Override
    public int getItemCount() {
        if (urlList == null) {
            return 0;
        }
        return urlList.size();
    }

    @Nullable
    public String getItem(int position) {
        if (urlList == null || urlList.size() == 0) {
            return null;
        }
        return urlList.get(position);
    }

    public void updateData(List<String> newUrlList) {
        urlList = newUrlList;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView ivFresco;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ivFresco = (SimpleDraweeView) itemView.findViewById(R.id.ivFresco);

        }
    }
}
