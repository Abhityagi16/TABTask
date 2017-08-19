package in.appmetric.tabtask.data.source.local;

import java.util.ArrayList;
import java.util.List;

import in.appmetric.tabtask.data.models.Comic;
import in.appmetric.tabtask.data.source.ComicsDataSource;
import io.reactivex.Single;

/**
 * Created by abhishektyagi on 15/08/17.
 */

public class LocalComicsDataSource implements ComicsDataSource {

    private static LocalComicsDataSource INSTANCE;

    private LocalComicsDataSource() {

    }

    public static LocalComicsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalComicsDataSource();
        }

        return INSTANCE;

    }

    @Override
    public Single<List<Comic>> getComics(int limit) {
        // I've not implemented local storage. We can implement this for offline usage of app.
        List<Comic> empty = new ArrayList<>();
        return Single.just(empty);
    }

    @Override
    public void saveComic(Comic comic) {

    }
}
