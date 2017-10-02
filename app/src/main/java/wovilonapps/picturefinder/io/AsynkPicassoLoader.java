package wovilonapps.picturefinder.io;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import wovilonapps.picturefinder.db.RealmDbManager;
import wovilonapps.picturefinder.interfaces.OnLoadFinisCallBack;
import wovilonapps.picturefinder.model.ResponseImage;

/**
 * Created by Администратор on 02.10.2017.
 */

public class AsynkPicassoLoader extends AsyncTask {
    Context context;
    String url;
    String requestWord;
    public Bitmap bitmap;
    byte[] imageBytes;
    OnLoadFinisCallBack callBack;

    public AsynkPicassoLoader(Context context, String url, String requestWord, OnLoadFinisCallBack callBack){
        this.context = context;
        this.url = url;
        this.requestWord = requestWord;
        this.callBack = callBack;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        bitmap = null;
        try{
            bitmap = Picasso.with(context).load(url).get();
            imageBytes = bitmapToBytes(bitmap);
        }catch (IOException ioe) {}
        return bitmapToBytes(bitmap);

    }



    private static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        ResponseImage responseImage = new ResponseImage();
        responseImage.setName(requestWord);
        responseImage.setImage(imageBytes);
        new RealmDbManager(context).add(responseImage);

        callBack.onFinish();
    }

}
