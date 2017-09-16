package learning.nitish.redmart.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import learning.nitish.redmart.R;


/**
 * Created by Nitish Singh Rathore on 15/9/17.
 */

public class ImageSliderAdapter extends PagerAdapter {

    private static final String TAG = "NITISH";
    private ArrayList<String> images;
    private LayoutInflater inflater;
    private Context context;


    public ImageSliderAdapter(ArrayList<String> images, Context context) {
        this.images = images;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View myImageLayout = inflater.inflate(R.layout.product_image_layout, container, false);
        ImageView productImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Glide.with(context).load(images.get(position)).into(productImage);
        container.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
