package in.appmetric.tabtask.comicslist;

import java.util.List;

import in.appmetric.tabtask.data.models.Comic;
import in.appmetric.tabtask.data.source.ComicsDao;
import in.appmetric.tabtask.data.source.DaoManager;
import io.reactivex.functions.BiConsumer;

/**
 * Created by abhishek.tyagi1 on 02/11/16.
 */

public class ComicsListFragmentPresenter implements ComicsListFragmentContract.Presenter {

    ComicsDao mComicsDao;

    ComicsListFragmentContract.View mComicFragmentView;



    public ComicsListFragmentPresenter(ComicsListFragmentContract.View
            comicFragmentView) {
        mComicFragmentView = comicFragmentView;
        mComicsDao = DaoManager.getComicsDao();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getComics(int limit) {
        mComicsDao.getComics(limit)
                .subscribe(new BiConsumer<List<Comic>, Throwable>() {
                    @Override
                    public void accept(List<Comic> comics, Throwable throwable) throws Exception {
                        mComicFragmentView.setComics(comics);
                    }
                });
    }

    @Override
    public void comicClicked(Comic comic) {
        mComicFragmentView.showComicDetail(comic);
    }
}

