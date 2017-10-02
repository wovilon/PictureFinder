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

    // class for work with database
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
            //Log.d("MyLOG", list.get(i).getImage().toString());
        }
        return new ArrayList(mRealm.where(ResponseImage.class).findAll());
    }


    public void close(){
        mRealm.close();
    }
}
