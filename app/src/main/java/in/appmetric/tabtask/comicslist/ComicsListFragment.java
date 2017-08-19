package in.appmetric.tabtask.comicslist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import in.appmetric.tabtask.OnFragmentInteractionListener;
import in.appmetric.tabtask.R;
import in.appmetric.tabtask.data.models.Comic;


public class ComicsListFragment extends Fragment implements ComicsListFragmentContract.View {

    private static final int COMIC_LIMIT = 100;
    ComicsListFragmentContract.Presenter mComicsListFragmentPresenter;


    private OnFragmentInteractionListener mListener;
    private EditText mFilterText;
    private RecyclerView mRecyclerView;
    private ComicsListAdapter mAdapter;
    public ComicsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComicsListFragmentPresenter = new ComicsListFragmentPresenter(this);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_comic_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.comics_list);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager
                .VERTICAL, false);
        ComicsListAdapter.ComicClickListener clickListener = new ComicsListAdapter.ComicClickListener() {
            @Override
            public void onClick(Comic comic) {
                mComicsListFragmentPresenter.comicClicked(comic);
            }
        };
        mFilterText = (EditText) rootView.findViewById(R.id.filter_budget);
        mAdapter = new ComicsListAdapter(getContext(), clickListener);
        mFilterText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mComicsListFragmentPresenter.getComics(COMIC_LIMIT);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mComicsListFragmentPresenter.unsubscribe();
    }

    @Override
    public void showComicDetail(Comic comic) {
        mListener.onComicClicked(comic);
    }

    @Override
    public void setComics(List<Comic> comics) {
        mAdapter.setComicList(comics);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
