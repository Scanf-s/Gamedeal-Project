package com.ggamdeal.app.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.ggamdeal.app.R;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mImages = {R.drawable.image1, R.drawable.image2, R.drawable.image3};

    public ImagePagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.item_image, container, false);

        ImageView imageView = itemView.findViewById(R.id.topImageView);
        imageView.setImageResource(mImages[position]);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}