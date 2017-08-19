package in.appmetric.tabtask.data.source.remote;

import com.android.volley.RequestQueue;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import in.appmetric.tabtask.data.models.Comic;
import in.appmetric.tabtask.data.response.ServiceResponse;
import in.appmetric.tabtask.data.source.ComicsDataSource;
import in.appmetric.tabtask.utils.Utility;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.subjects.SingleSubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by abhishektyagi on 14/08/17.
 */

public class RemoteComicsDataSource implements ComicsDataSource{
    private static final int PORT = 80;
    private static final String API_BASE_URL = "http://gateway.marvel.com:" + PORT;
    private static final String PUBLIC_KEY = "54306733de0f5cd1418aa05a85fa062a";
    private static final String PRIVATE_KEY = "5de1fabcda2ea08912bd8b09bca4321f50563655";

    private static final int COMIC_COUNT = 100;


    private RequestQueue mRequestQueue;
    private Retrofit mRetrofit;
    private static RemoteComicsDataSource INSTANCE;
    private String mHashValue;
    private long mTimeStamp;

    private RemoteComicsDataSource() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(API_BASE_URL)
                .build();
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        mTimeStamp = calendar.getTimeInMillis() / 1000L;
        mHashValue = Utility.md5(String.valueOf(mTimeStamp) + PRIVATE_KEY + PUBLIC_KEY);
    }

    public static RemoteComicsDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteComicsDataSource();
        }

        return INSTANCE;

    }

    private interface MarvelAPI {
        @GET("v1/public/comics")
        Single<ServiceResponse<Comic>> getComics(@Query("limit") int limit
                , @Query("ts") String timestamp
                , @Query("apikey") String apikey
                , @Query("hash") String hashValue
                , @Query("format") String format);
    }

    @Override
    public Single<List<Comic>> getComics(int limit) {

//        final Subject<Comic> subject = PublishSubject.create();
        Single<List<Comic>> single = SingleSubject.create();
        MarvelAPI marvelAPI = mRetrofit.create(MarvelAPI.class);
        return  marvelAPI.getComics(limit, "" + mTimeStamp, PUBLIC_KEY,
                mHashValue, "comic")
                .flatMap(new Function<ServiceResponse<Comic>, SingleSource<? extends List<Comic>>>
                        () {
                    @Override
                    public SingleSource<? extends List<Comic>> apply(@NonNull ServiceResponse<Comic>
                            serviceResponse) throws Exception {
                        List<Comic> comics = serviceResponse.getData().getResults();
                        return Observable.fromIterable(comics).toList();
                    }
                });
//        call.enqueue(new Callback<ServiceResponse>() {
//            @Override
//            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
//                ServiceResponse comics = response.body();
//            }
//
//            @Override
//            public void onFailure(Call<ServiceResponse> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }


    @Override
    public void saveComic(Comic comic) {
        // We'll not do anything here since we are not using any server to save data
    }
}
