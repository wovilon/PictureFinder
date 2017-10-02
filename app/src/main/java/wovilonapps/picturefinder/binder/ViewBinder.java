package wovilonapps.picturefinder.binder;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;


//class to operate with bitmaps in SimpleAdapter
public class ViewBinder implements SimpleAdapter.ViewBinder {
    @Override
    public boolean setViewValue(View view, Object data,
                                String textRepresentation) {
        // to put bitmap in imageView in needed field
        if ((view instanceof ImageView) & (data instanceof Bitmap)) {
            ImageView iv = (ImageView) view;
            Bitmap bm = (Bitmap) data;
            iv.setImageBitmap(bm);
            return true;
        }
        return false;
    }
}
