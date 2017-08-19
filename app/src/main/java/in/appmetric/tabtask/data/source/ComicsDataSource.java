package in.appmetric.tabtask.data.source;

import java.util.List;

import in.appmetric.tabtask.data.models.Comic;
import io.reactivex.Single;

/**
 * Created by abhishektyagi on 15/08/17.
 */

public interface ComicsDataSource {

    Single<List<Comic>> getComics(int limit);

    void saveComic(Comic comic);
}
