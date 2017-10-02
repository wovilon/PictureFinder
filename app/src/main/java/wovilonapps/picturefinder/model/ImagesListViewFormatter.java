package wovilonapps.picturefinder.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wovilonapps.picturefinder.R;
import wovilonapps.picturefinder.binder.ViewBinder;
import wovilonapps.picturefinder.db.RealmDbManager;


public class ImagesListViewFormatter {
    Context context;
    ListView listView;
    ArrayList<ResponseImage> responseImages;

    public ImagesListViewFormatter(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        fillListView();

        Log.d("MyLOG", "arraylist: "+ responseImages);
    }

    public void fillListView(){
        responseImages = new RealmDbManager(context).getAll();
        //imageView.setImageBitmap(getBitmapFromBytes(responseImages.get(0).getImage()));

        String name = "name";
        String image = "image";

        //add imageResponse to data for listview
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        Map<String, Object> m;

        for (int i=0; i<responseImages.size(); i++){
            m = new HashMap<>();
            m.put(name, responseImages.get(i).getName());
            m.put(image, getBitmapFromBytes(responseImages.get(i).getImage()));
            data.add(m);
        }
        //abjust adapter
        String[] from = {name, image};
        int[] to = {R.id.itemText, R.id.itemImage};

        final SimpleAdapter adapter = new SimpleAdapter(context, data, R.layout.images_list_item, from, to);
        adapter.setViewBinder(new ViewBinder());
        listView.setAdapter(adapter);


    }

    private Bitmap getBitmapFromBytes(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
