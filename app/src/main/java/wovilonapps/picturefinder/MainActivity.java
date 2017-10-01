package wovilonapps.picturefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;

import wovilonapps.picturefinder.db.RealmDbManager;
import wovilonapps.picturefinder.model.APIPictureGetter;
import wovilonapps.picturefinder.model.ImagesListViewFormatter;
import wovilonapps.picturefinder.model.ResponseImage;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    RealmDbManager dbManager;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView2);
        listView = (ListView)findViewById(R.id.imagesListView);
        dbManager = new RealmDbManager(this);

        /*ResponseImage responseImage = new ResponseImage();
        responseImage.setName("name");
        responseImage.setImage(drawableToBytes(getDrawable(R.mipmap.ic_launcher)));
        dbManager.add(responseImage);*/


        ImagesListViewFormatter listViewFormatter = new ImagesListViewFormatter(this, listView);


    }

    public void onButtonSearchClick(View view) {
        APIPictureGetter apiPictureGetter = new APIPictureGetter(this, imageView);
        apiPictureGetter.getPicture("cat");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

    private static byte[] drawableToBytes(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public static Drawable bytesToDrawable(byte[] image) {

        return new BitmapDrawable(BitmapFactory.decodeByteArray(image, 0, image.length));
    }
}
