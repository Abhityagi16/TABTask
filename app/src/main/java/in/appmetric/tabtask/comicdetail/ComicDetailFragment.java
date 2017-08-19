package in.appmetric.tabtask.comicdetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.appmetric.tabtask.OnFragmentInteractionListener;
import in.appmetric.tabtask.R;
import in.appmetric.tabtask.comicslist.ComicsListAdapter;
import in.appmetric.tabtask.comicslist.ComicsListFragmentContract;
import in.appmetric.tabtask.comicslist.ComicsListFragmentPresenter;
import in.appmetric.tabtask.data.models.Author;
import in.appmetric.tabtask.data.models.Comic;
import in.appmetric.tabtask.data.models.Price;

import static android.R.attr.author;


public class ComicDetailFragment extends Fragment {

    private Comic mComic;

    private ImageView mComicThumbnail;
    private TextView mComicTitle;
    private TextView mComicDescription;
    private TextView mComicPageCount;
    private TextView mComicCreators;
    private LinearLayout mComicPricesContainer;

    public ComicDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public void setComic(Comic comic) {
        this.mComic = comic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.comic_detail, container, false);
        mComicThumbnail = (ImageView) rootView.findViewById(R.id.comic_thumbnail);
        mComicTitle = (TextView) rootView.findViewById(R.id.comic_title);
        mComicDescription = (TextView) rootView.findViewById(R.id.comic_description);
        mComicPageCount = (TextView) rootView.findViewById(R.id.comic_pageCount);
        mComicCreators = (TextView) rootView.findViewById(R.id.comic_creator);
        mComicPricesContainer = (LinearLayout) rootView.findViewById(R.id.comic_price_container);

        Picasso.with(getContext()).load(mComic.getThumbnail().getCompletePath()).into(mComicThumbnail);
        mComicTitle.setText(mComic.getTitle());
        String description = mComic.getDescription();
        mComicDescription.setText(description != null ? description : "No description found");
        mComicPageCount.setText(mComic.getPageCount() + " Pages");
        StringBuilder sb = new StringBuilder();
        List<Author> authors = mComic.getCreators().getItems();
        for (int i=0; i<authors.size()-1; i++) {
            if(i > 0) sb.append(", ");
            sb.append(authors.get(i).getName());
        }
        mComicCreators.setText(sb.toString());
        mComicPricesContainer = (LinearLayout) rootView.findViewById(R.id.comic_price_container);
        for (Price price: mComic.getPrices()) {
            View priceLayout = inflater.inflate(R.layout.comic_price, mComicPricesContainer, false);
            TextView comicType = (TextView) priceLayout.findViewById(R.id.comic_type);
            TextView comicPrice = (TextView) priceLayout.findViewById(R.id.comic_price);
            comicType.setText(price.getType() + ": ");
            comicPrice.setText("$" + price.getPrice());
            mComicPricesContainer.addView(priceLayout);
        }

        return rootView;
    }
}
