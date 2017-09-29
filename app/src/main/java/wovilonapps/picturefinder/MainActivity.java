package wovilonapps.picturefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import wovilonapps.picturefinder.model.APIPictureGetter;

public class MainActivity extends AppCompatActivity {
ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView2);



    }

    public void onButtonSearchClick(View view) {
        APIPictureGetter apiPictureGetter = new APIPictureGetter(this, imageView);
        apiPictureGetter.getPicture("cat");
    }
}
