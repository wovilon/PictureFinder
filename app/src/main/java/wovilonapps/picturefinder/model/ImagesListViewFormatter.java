package wovilonapps.picturefinder.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import wovilonapps.picturefinder.R;
import wovilonapps.picturefinder.binder.ViewBinder;
import wovilonapps.picturefinder.db.RealmDbManager;

// presents downloaded images in listView
public class ImagesListViewFormatter {
    private Context context;
    private ListView listView;

    public ImagesListViewFormatter(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        fillListView();
    }

    public void fillListView(){
        ArrayList<ResponseImage> responseImages = new RealmDbManager(context).getAll();

        String name = "name";
        String image = "image";

        //add imageResponse to data for listview
        ArrayList<Map<String,Object>> data = new ArrayList<>();
        Map<String, Object> m;
        int imax = responseImages.size();
        for (int i=1; i <= imax; i++){
            m = new HashMap<>();
            m.put(name, responseImages.get(imax - i).getName());
            m.put(image, getBitmapFromBytes(responseImages.get(imax - i).getImage()));
            data.add(m);
        }
        //abjust adapter
        String[] from = {name, image};
        int[] to = {R.id.itemText, R.id.itemImage};

        //set adapter to listView
        final SimpleAdapter adapter = new SimpleAdapter(context, data, R.layout.images_list_item, from, to);
        adapter.setViewBinder(new ViewBinder());
        listView.setAdapter(adapter);
    }

    private Bitmap getBitmapFromBytes(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
