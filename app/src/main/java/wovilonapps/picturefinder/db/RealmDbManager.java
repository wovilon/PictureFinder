package wovilonapps.picturefinder.db;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.realm.Realm;
import wovilonapps.picturefinder.model.ResponseImage;

public class RealmDbManager {
    Context context;
    Realm mRealm;

    public RealmDbManager(Context context) {
        mRealm = Realm.getInstance(context);
    }

    public void add(ResponseImage responseImage){
        mRealm.beginTransaction();
        ResponseImage realmObject = mRealm.createObject(ResponseImage.class);
        realmObject.setImage(responseImage.getImage());
        realmObject.setName(responseImage.getName());
        mRealm.commitTransaction();
    }

    public ArrayList<ResponseImage> getAll(){
        ArrayList<ResponseImage> list = new ArrayList(mRealm.where(ResponseImage.class).findAll());

        for (int i=0; i<list.size(); i++){
            Log.d("MyLOG", list.get(i).getName());
        }
        return new ArrayList(mRealm.where(ResponseImage.class).findAll());
    }

    /*private static byte[] drawableToBytes(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Drawable bytesToDrawable(byte[] image) {

        return new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
    }*/

    public void close(){
        mRealm.close();
    }
}
