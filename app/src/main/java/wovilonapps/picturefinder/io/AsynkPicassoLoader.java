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


public class AsynkPicassoLoader extends AsyncTask {
    private Context context;
    private String url;
    private String requestWord;
    private  Bitmap bitmap;
    private byte[] imageBytes;
    private OnLoadFinisCallBack callBack;

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
            bitmap = Picasso.with(context).load(url).get(); // load image with Picasso
            imageBytes = bitmapToBytes(bitmap); // convert to bytes (for POJO)
        }catch (IOException ioe) {}
        return bitmapToBytes(bitmap);

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        // add to database
        ResponseImage responseImage = new ResponseImage();
        responseImage.setName(requestWord);
        responseImage.setImage(imageBytes);
        new RealmDbManager(context).add(responseImage);

        callBack.onFinish(); // signal to mainActivity
    }

    private static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

}
