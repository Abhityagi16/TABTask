package in.appmetric.tabtask.data.source;

import in.appmetric.tabtask.data.source.local.LocalComicsDataSource;
import in.appmetric.tabtask.data.source.remote.RemoteComicsDataSource;

/**
 * Created by abhishektyagi on 15/08/17.
 */

public class DaoManager {

    public static ComicsDao getComicsDao() {
        return ComicsDao.getInstance(RemoteComicsDataSource.getInstance(), LocalComicsDataSource
                .getInstance());
    }
}
