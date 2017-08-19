package in.appmetric.tabtask.comicslist;



import java.util.List;

import in.appmetric.tabtask.BasePresenter;
import in.appmetric.tabtask.BaseView;
import in.appmetric.tabtask.data.models.Comic;

/**
 * Created by abhishek.tyagi1 on 02/11/16.
 */

public interface ComicsListFragmentContract {
    interface View extends BaseView<Presenter> {
        void setComics(List<Comic> comics);
        void showComicDetail(Comic comic);
    }

    interface Presenter extends BasePresenter {
        void getComics(int limit);
        void comicClicked(Comic comic);
    }
}
