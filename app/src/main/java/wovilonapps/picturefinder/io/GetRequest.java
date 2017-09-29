package wovilonapps.picturefinder.io;


import android.graphics.drawable.Drawable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


public interface GetRequest {
    @Headers({
            "Api-Key:4hhd43tsw4hz3nnaa9n64tet"
    })
    @GET("/v3/search/images")
    Call<Object> GETMethodRequest(@Query("fields") String fields,
                                  @Query("sort_order") String sort_order,
                                  @Query("phrase") String phrase);

}

