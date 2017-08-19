package in.appmetric.tabtask.comicslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.appmetric.tabtask.R;
import in.appmetric.tabtask.data.models.Comic;

/**
 * Created by abhishektyagi on 15/08/17.
 */

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicViewHolder>
        implements Filterable {

    private Context mContext;
    private List<Comic> mComicList;
    private List<Comic> mOriginalList;
    private ComicClickListener mListener;

    private ComicPriceFilter mFilter;

    public ComicsListAdapter(Context context, ComicClickListener comicClickListener) {
        this.mContext = context;
        mListener = comicClickListener;
    }

    public void setComicList(List<Comic> comicList) {
        this.mComicList = comicList;
        notifyDataSetChanged();

    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_row,
                parent, false);

        return new ComicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        Comic comic = mComicList.get(position);
        holder.comicTitle.setText(comic.getTitle());
        Picasso.with(mContext).load(comic.getThumbnail().getCompletePath()).into(holder.comicThumbnail);

    }

    @Override
    public int getItemCount() {
        return mComicList == null ? 0 : mComicList.size();
    }

    class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView comicTitle;
        ImageView comicThumbnail;

        public ComicViewHolder(View itemView) {
            super(itemView);
            comicThumbnail = (ImageView) itemView.findViewById(R.id.comic_thumbnail);
            comicTitle = (TextView) itemView.findViewById(R.id.comic_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) mListener.onClick(mComicList.get(getAdapterPosition()));
        }
    }

    public interface ComicClickListener {
        void onClick(Comic comic);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ComicPriceFilter();
        }
        return mFilter;
    }

    private class ComicPriceFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (mOriginalList == null) {
                mOriginalList = new ArrayList<>(mComicList);
            }
            String search = constraint.toString();
            if (search.isEmpty()) {
                results.count = mOriginalList.size();
                results.values = mOriginalList;
            } else {
                float price = new Float(search);
                List<Comic> filterList = new ArrayList<>();
                for (int i = 0; i < mOriginalList.size(); i++) {
                    Comic comic = mOriginalList.get(i);
                    if (comic.lowestPrice().getPrice() <= price) {
                        filterList.add(comic);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mComicList = (List<Comic>) results.values;
            notifyDataSetChanged();
        }
    }
}
