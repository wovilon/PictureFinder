package wovilonapps.picturefinder.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wovilonapps.picturefinder.R;
import wovilonapps.picturefinder.db.RealmDbManager;
import wovilonapps.picturefinder.interfaces.OnLoadFinisCallBack;
import wovilonapps.picturefinder.io.AsynkPicassoLoader;
import wovilonapps.picturefinder.io.GetRequest;

public class APIPictureGetter {
    Context context;

    //Restofit part of variables
    private String URL;
    private String KEY;
    private Gson gson;
    //"get"request
    //private Gson gson=new GsonBuilder().create();
    private Retrofit retrofit_get;
    private GetRequest interf_get;
    private Retrofit image_get;
    private GetRequest interf_image_get;
    String imageURL = null;
    ImageView imageView;
    Drawable drawable;
    OnLoadFinisCallBack callBack;

    public APIPictureGetter(Context context, OnLoadFinisCallBack onLoadFinisCallBack){
        this.context = context;
        this.callBack = onLoadFinisCallBack;
    }

    public void getPicture(String requestString){
        URL = context.getString(R.string.URLBase);
        KEY = context.getResources().getString(R.string.KEY);
        gson = new GsonBuilder().create();
        retrofit_get = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(URL)
                .build();
        interf_get = retrofit_get.create(GetRequest.class);

        //getImageFromURL("string");
        useGetMethod(requestString);
    }



    //get weather by city (Retrofit)
    private void useGetMethod(final String requestString) {

        Call<Object> call = interf_get.GETMethodRequest("id,title,thumb", "best", requestString);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response1) {

                try {
                    Log.d("MyLOG", response1.toString());
                    Log.d("MyLOG", "response body: " + response1.body().toString());
                    Gson gson = new Gson();

                    String jsonString = gson.toJson(response1.body());
                    try {
                        JSONObject json = new JSONObject(jsonString);
                        imageURL = json.getJSONArray("images").getJSONObject(0).getJSONArray("display_sizes")
                                .getJSONObject(0).getString("uri");
                        AsynkPicassoLoader loader = new AsynkPicassoLoader(context, imageURL, requestString, callBack);
                        loader.execute();

                    }catch (JSONException jse) {
                        Toast.makeText(context,context.getResources().getString(R.string.serverError),
                                Toast.LENGTH_LONG).show();

                        Log.d("MyLOG", "JSONException in APIPictureGetter.useGetMethod");}

                }catch (NullPointerException npe) {Log.d("MyLOG", "NullPointerException at useGerMethod()");}
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("MyLOG", "CallBack onFailture: " + t.toString());
            }
        });

    }


    private Drawable loadImageFromURL(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, null);
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private void setDrawable(){
        imageView.setImageDrawable(drawable);
    }

    private static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}
