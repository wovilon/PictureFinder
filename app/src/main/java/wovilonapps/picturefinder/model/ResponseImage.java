package wovilonapps.picturefinder.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;

// POJO class for Realm data piece
public class ResponseImage extends RealmObject {
    private String name; // requested work
    private byte[] image; //image

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }


}
