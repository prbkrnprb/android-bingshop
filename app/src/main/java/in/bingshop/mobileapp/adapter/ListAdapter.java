package in.bingshop.mobileapp.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import in.bingshop.bingshop.R;
import in.bingshop.mobileapp.external.AccessFavDatabase;
import in.bingshop.mobileapp.external.GetImage;
import in.bingshop.util.Utilities;

/**
 * Created by Prabakaran on 07-14-15.
 */

public class ListAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;
    ListGroup listGroup;
    LayoutInflater inflater;
    GetImage getImage;
    AccessFavDatabase favDb;

    public ListAdapter(Context context, ListGroup listGroup,String tableName ) {
        super(context, R.layout.row_list_act, listGroup.getName());
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < listGroup.getName().size(); ++i) {
            mIdMap.put(listGroup.getName().get(i), i);
        }
        this.listGroup = listGroup;
        favDb = new AccessFavDatabase(context,"favourite",tableName);
        this.context = context;
        getImage = new GetImage(context);
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.row_list_act, parent, false);
        }
        TextView listNameView = (TextView) rowView.findViewById(R.id.listText);
        listNameView.setText(listGroup.getName().get(position));
        ImageView listImageView = (ImageView) rowView.findViewById(R.id.listImage);
        //Bitmap bitmap = BitmapFactory.decodeStream(in);
        Drawable d = new BitmapDrawable(context.getResources(), Utilities.getRoundedCornerBitmap(null, getImage.get(listGroup.getPictureId().get(position))));
        // imageView.setImageDrawable(d);
        listImageView.setImageBitmap(Utilities.getRoundedCornerBitmap(null, getImage.get(listGroup.getPictureId().get(position))));
        //BingUtil.imageViewQueue.add(imageView);
        //BingUtil.imageViewSQueue.add(listPrice.get(position));
        //getImage = new GetImage(activity);
        final ToggleButton favToggle = (ToggleButton) rowView.findViewById(R.id.favToggle);
        if (favDb.getInd(listGroup.getId().get(position)) == 1) {
            favToggle.setChecked(true);
        } else {
            favToggle.setChecked(false);
        }

        final int pos = position;

        favToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favToggle.isChecked()) {
                    favDb.updateInd(listGroup.getId().get(pos), 1);
                    Toast.makeText(context, listGroup.getName().get(pos) + " added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    favDb.updateInd(listGroup.getId().get(pos), 0);
                    Toast.makeText(context, listGroup.getName().get(pos) + " removed from favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listImageView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                Animation rotation = AnimationUtils.loadAnimation(context, R.anim.first_act_translate);
                v.startAnimation(rotation);
                return false;
            }
        });

        return rowView;
    }
}
