package wovilonapps.picturefinder.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

import io.realm.RealmObject;


public class ResponseImage extends RealmObject {
    private String name;
    private byte[] image;

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = new byte[]{};
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }


   /* private static byte[] drawableToBytes(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Drawable bytesToDrawable(byte[] image) {

        return new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
    }*/
}
