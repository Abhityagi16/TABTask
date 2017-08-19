package in.appmetric.tabtask.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import in.appmetric.tabtask.data.models.Comic;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by abhishektyagi on 15/08/17.
 */

public class ComicsDao implements ComicsDataSource {
    @Nullable
    private static ComicsDao INSTANCE = null;
    private final ComicsDataSource mComicsRemoteDataSource;

    private final ComicsDataSource mComicsLocalDataSource;
    Map<Integer, Comic> mCachedComics;

    boolean mIsCacheDirty = false;

    private ComicsDao(@NonNull ComicsDataSource comicsRemoteDataSource,
                      @NonNull ComicsDataSource comicsLocalDataSource) {
        mComicsRemoteDataSource = checkNotNull(comicsRemoteDataSource);
        mComicsLocalDataSource = checkNotNull(comicsLocalDataSource);
    }

    public static ComicsDao getInstance(@NonNull ComicsDataSource comicsRemoteDataSource,
                                        @NonNull ComicsDataSource comicsLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ComicsDao(comicsRemoteDataSource, comicsLocalDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Single<List<Comic>> getComics(int limit) {
        if (mCachedComics != null && !mIsCacheDirty) {
            return Observable.fromIterable(mCachedComics.values()).toList();
        } else if (mCachedComics == null) {
            mCachedComics = new LinkedHashMap<>();
        }

        Single<List<Comic>> remoteData = getAndSaveRemoteComics(limit);

        if (mIsCacheDirty) {
            return remoteData;
        } else {
            // Query the local storage if available. If not, query the network.
            Single<List<Comic>> localData = getAndCacheLocalComics(limit);
            return Single.concat(localData, remoteData)
                    .filter(new Predicate<List<Comic>>() {
                        @Override
                        public boolean test(@io.reactivex.annotations.NonNull List<Comic> comics) throws Exception {
                            return !comics.isEmpty();
                        }
                    }).first(new ArrayList<Comic>());
        }
    }

    @Override
    public void saveComic(Comic comic) {
        checkNotNull(comic);
        mComicsLocalDataSource.saveComic(comic);
        mComicsRemoteDataSource.saveComic(comic);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedComics == null) {
            mCachedComics = new LinkedHashMap<>();
        }
        mCachedComics.put(comic.getId(), comic);
    }

    private Single<List<Comic>> getAndCacheLocalComics(int limit) {
        return mComicsLocalDataSource.getComics(limit)
                .flatMap(new Function<List<Comic>, SingleSource<? extends List<Comic>>>() {
                    @Override
                    public SingleSource<? extends List<Comic>> apply(@io.reactivex.annotations.NonNull List<Comic> comics) throws Exception {
                        return Observable.fromIterable(comics)
                                .doOnNext(new Consumer<Comic>() {
                                    @Override
                                    public void accept(@io.reactivex.annotations.NonNull Comic comic) throws Exception {
                                        mCachedComics.put(comic.getId(), comic);
                                    }
                                })
                                .toList();
                    }
                });
    }

    private Single<List<Comic>> getAndSaveRemoteComics(int limit) {
        return mComicsRemoteDataSource
                .getComics(limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Comic>, SingleSource<? extends List<Comic>>>() {
                    @Override
                    public SingleSource<? extends List<Comic>> apply(@io.reactivex.annotations.NonNull List<Comic> comics) throws Exception {
                        return Observable.fromIterable(comics).doOnNext(new Consumer<Comic>() {
                            @Override
                            public void accept(@io.reactivex.annotations.NonNull Comic comic) throws Exception {
                                mComicsLocalDataSource.saveComic(comic);
                                mCachedComics.put(comic.getId(), comic);
                            }
                        }).toList();
                    }
                })
                .doAfterSuccess(new Consumer<List<Comic>>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull List<Comic> comics) throws Exception {
                        mIsCacheDirty = false;
                    }
                });
    }
}
