package wovilonapps.picturefinder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;

import wovilonapps.picturefinder.db.RealmDbManager;
import wovilonapps.picturefinder.interfaces.OnLoadFinisCallBack;
import wovilonapps.picturefinder.model.APIPictureGetter;
import wovilonapps.picturefinder.model.ImagesListViewFormatter;
import wovilonapps.picturefinder.model.ResponseImage;

public class MainActivity extends AppCompatActivity {
    EditText editTextSearch;
    RealmDbManager dbManager;
    ListView listView;
    ImagesListViewFormatter listViewFormatter;
    OnLoadFinisCallBack onLoadFinisCallBack;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize UI views
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        listView = (ListView)findViewById(R.id.imagesListView);
        dbManager = new RealmDbManager(this);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //initialize callback (when loading of image finishes, it works)
        onLoadFinisCallBack = new OnLoadFinisCallBack() {
            @Override
            public void onFinish() {
                listViewFormatter.fillListView(); // refresh listView
                progressBar.setVisibility(View.INVISIBLE);  // hide progress circle
            }
        };

        // refresh listView (at activity start)
        listViewFormatter = new ImagesListViewFormatter(this, listView);

    }

    public void onButtonSearchClick(View view) {
        APIPictureGetter apiPictureGetter = new APIPictureGetter(this, onLoadFinisCallBack);
        apiPictureGetter.getPicture(editTextSearch.getText().toString()); //start API request
        progressBar.setVisibility(View.VISIBLE); // setup progress circle
        progressBar.animate();

        // Check if no view has focus and hide keyboard
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();  //close base
    }

}
