package in.bingshop.mobileapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.bingshop.bingshop.R;

/**
 * Created by Prabakaran on 26-12-2014.
 */

public class ListAdapter1 extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;
    List<String> listName,pictureID,listID;
    LayoutInflater inflater;
    GetImage getImage;
    AccessFavDatabase favDb;

    public ListAdapter1(Context context, List<String> listName, List<String> pictureID,List<String> listID,String tableName ) {
        super(context, R.layout.row_list_act, listName);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < listName.size(); ++i) {
            mIdMap.put(listName.get(i), i);
        }
        favDb = new AccessFavDatabase(context,"favourite",tableName);
        this.context = context;
        this.listID = listID;
        this.listName = listName;
        this.pictureID = pictureID;

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
        TextView textView = (TextView) rowView.findViewById(R.id.listText);
        textView.setText(listName.get(position));
        ImageView imageView = (ImageView) rowView.findViewById(R.id.listImage);
        //Bitmap bitmap = BitmapFactory.decodeStream(in);
        Drawable d = new BitmapDrawable(context.getResources(),BingUtil.getRoundedCornerBitmap(null,getImage.get(pictureID.get(position))));
       // imageView.setImageDrawable(d);
        imageView.setImageBitmap(BingUtil.getRoundedCornerBitmap(null,getImage.get(pictureID.get(position))));
        //BingUtil.imageViewQueue.add(imageView);
        //BingUtil.imageViewSQueue.add(listPrice.get(position));
        //getImage = new GetImage(activity);
        final ToggleButton favToggle = (ToggleButton) rowView.findViewById(R.id.favToggle);
        if (favDb.getInd(listID.get(position)) == 1) {
            favToggle.setChecked(true);
        } else {
            favToggle.setChecked(false);
        }

        final int pos = position;

        favToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favToggle.isChecked()) {
                    favDb.updateInd(listID.get(pos), 1);
                    Toast.makeText(context, listName.get(pos) + " added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    favDb.updateInd(listID.get(pos), 0);
                    Toast.makeText(context, listName.get(pos) + " removed from favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener(){

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

class PopulateList {
    ListView listView;
    ArrayList listName,listID,pictureID,listDescription;
    Context context;
    ActivityListInterface parentClass;
    GetImage getImage;
    TextView statusList;
    AccessFavDatabase favDb;

    public String tableData[][],tableName,tableKey;
    public ListAdapter adapter;
    MyTableLinker linker;
    public PopulateList(Context context,ListView listView,String tableName, String tableKey, ActivityListInterface parent,TextView statusList){
        this.listView = listView;
        this.tableName = tableName;
        this.tableKey = tableKey;
        this.context = context;
        this.parentClass = parent;
        this.statusList = statusList;
        getImage = new GetImage(context);
        linker = new MyTableLinker(tableName,tableKey,context);
        linker.start();
        listName = new ArrayList<String>();
        listID = new ArrayList<String>();
        listDescription = new ArrayList<String>();
        pictureID = new ArrayList<String>();
        favDb = new AccessFavDatabase(context,"favourite",tableName);
        this.start();
        //if(SearchData.filter)
        //    filterData();
    }

    private void start(){
        tableData = linker.getTableData();
        for (int i = 0; i < linker.getRowCount(); ++i) {
            listName.add(tableData[i][1]);
            listID.add(tableData[i][0]);
            listDescription.add(tableData[i][2]);
            pictureID.add(tableData[i][3]);
            getImage.downloadImage(tableData[i][3]);
            getImage.downloadImage(tableData[i][3] + "x");
            //getImage.downloadImage(tableData[i][3] + "xx");
        }
        sortData();
        adapter = new ListAdapter(context,listName,pictureID,listID,tableName);
        listView.setAdapter(adapter);

       listView.setOnItemClickListener(new OnItemClickListener() {

           @Override
           public void onItemClick(AdapterView<?> parent, final View view,
                                   int position, long id) {
               parentClass.onItemClick((String) listID.get(position), (String) listName.get(position), (String) listDescription.get(position), (String) pictureID.get(position));
           }
       });
        statusList.setText("  " + linker.getRowCount() + " item(s) listed");
    }

    public void filterData(){
        tableData = linker.getTableData();
        listName.clear();
        listID.clear();
        listDescription.clear();
        pictureID.clear();
        for (int i = 0; i < linker.getRowCount(); i++) {
            if(SearchData.idAll.indexOf(tableData[i][0]) != -1 || !SearchData.filter) {
                listName.add(tableData[i][1]);
                listID.add(tableData[i][0]);
                listDescription.add(tableData[i][2]);
                pictureID.add(tableData[i][3]);
            }
        }
        adapter = new ListAdapter(context,listName,pictureID,listID,tableName);
        listView.setAdapter(adapter);
        statusList.setText("  " + listName.size() + " item(s) result matching");
    }

    public void sortData(){
        ArrayList<Integer> sortedOrder;
        switch (SortStrings.sortOrder){
            case SortStrings.ASCENDING:
                sortedOrder = SortStrings.sortPosAscending(listName);
                listName = SortStrings.sortByPos(listName,sortedOrder);
                listID = SortStrings.sortByPos(listID,sortedOrder);
                listDescription = SortStrings.sortByPos(listDescription,sortedOrder);
                pictureID = SortStrings.sortByPos(pictureID, sortedOrder);
                break;
            case SortStrings.DESCENDING:
                sortedOrder = SortStrings.sortPosDescending(listName);
                listName = SortStrings.sortByPos(listName,sortedOrder);
                listID = SortStrings.sortByPos(listID,sortedOrder);
                listDescription = SortStrings.sortByPos(listDescription,sortedOrder);
                pictureID = SortStrings.sortByPos(pictureID, sortedOrder);
                break;
            case SortStrings.FAVORITE:
                for (int i = 0; i < listName.size(); i++) {
                    if(favDb.getInd(listID.get(i).toString()) != 1) {
                        listName = SortStrings.changeIndexToLast(listName,i);
                        listID = SortStrings.changeIndexToLast(listID,i);
                        listDescription = SortStrings.changeIndexToLast(listDescription,i);
                        pictureID = SortStrings.changeIndexToLast(pictureID,i);
                    }
                }
                break;
        }

        if(adapter != null) {
            adapter = new ListAdapter(context, listName, pictureID, listID, tableName);
            listView.setAdapter(adapter);
        }
        statusList.setText("  " + listName.size() + " item(s) result matching");
    }
}

class ExpandableListAdapter extends BaseExpandableListAdapter {

    private final SparseArray<ExpanderItemListGroup> groups;
    public LayoutInflater inflater;
    ActivityListInterface parentClass;
    GetImage getImage;

    public ExpandableListAdapter(Activity act, ActivityListInterface parent, SparseArray<ExpanderItemListGroup> groups, Context context) {
        this.groups = groups;
        this.parentClass = parent;
        inflater = act.getLayoutInflater();
        getImage = new GetImage(context);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).children.get(childPosition);
    }

    public Object getChildID(int groupPosition, int childPosition) {
        return groups.get(groupPosition).childID.get(childPosition);
    }

    public Object getChildDescription(int groupPosition, int childPosition) {
        return groups.get(groupPosition).childDescription.get(childPosition);
    }

    public Object getChildPicture(int groupPosition, int childPosition) {
        return groups.get(groupPosition).childPicture.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        final String childID = (String) getChildID(groupPosition, childPosition);
        final String childDescription = (String) getChildDescription(groupPosition, childPosition);
        final String childPicture = (String) getChildPicture(groupPosition, childPosition);
        final ExpanderItemListGroup expanderItemListGroup = (ExpanderItemListGroup) getGroup(groupPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_xlist_item_child, null);
        }
        text = (TextView) convertView.findViewById(R.id.childNameText);
        text.setText(children);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.childImage);
        imageView.setImageDrawable(getImage.get(childPicture));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentClass.onItemClick(childID,children,childDescription,childPicture, expanderItemListGroup.parentName);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).children.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_xlist_item_parent, null);
        }
        ExpanderItemListGroup expanderItemListGroup = (ExpanderItemListGroup) getGroup(groupPosition);

        TextView parentName = (TextView) convertView.findViewById(R.id.parentNameText);
        ImageView parentImage = (ImageView) convertView.findViewById(R.id.parentImage);

        parentName.setText(expanderItemListGroup.parentName);
        parentImage.setImageDrawable(getImage.get(expanderItemListGroup.parentPicID));
        /*((CheckedTextView) convertView).setText(expanderItemListGroup.parentName);
        ((CheckedTextView) convertView).setCheckMarkDrawable(getImage.get(expanderItemListGroup.parentPicID));
        ((CheckedTextView) convertView).setChecked(isExpanded);*/

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

class PopulateExpandableList {
    ExpandableListView listView;
    SparseArray<ExpanderItemListGroup> groups;

    Activity activity;
    public String tableData[][], tableData2[][],tableName,tableKey, tableName2;
    ActivityListInterface parentClass;
    Context context;
    GetImage getImage;
    TextView statusList;
    MyTableLinker linker;

    public PopulateExpandableList(Activity activity,ExpandableListView listView,String tableName, String tableKey, String tableName2, ActivityListInterface parent,Context context,TextView statusList){
        this.listView = listView;
        this.tableName = tableName;
        this.tableName2 = tableName2;
        this.tableKey = tableKey;
        this.activity = activity;
        this.parentClass = parent;
        groups = new SparseArray<ExpanderItemListGroup>();
        this.context = context;
        this.statusList = statusList;
        getImage = new GetImage(context);
        linker = new MyTableLinker(tableName, tableKey,context);
        this.start();
    }

    private void start() {
        linker.start();
        tableData = linker.getTableData();
        int subItems = 0;

        for(int i = 0; i < linker.getRowCount(); i++){
            ExpanderItemListGroup expanderItemListGroup = new ExpanderItemListGroup(tableData[i][1],tableData[i][3]);
            getImage.downloadImage(tableData[i][3]);
            MyTableLinker linker2 = new MyTableLinker(tableName2, tableData[i][0],context);
            linker2.start();
            tableData2 = linker2.getTableData();
            subItems = subItems + linker2.getRowCount();
            for(int j = 0; j < linker2.getRowCount(); j++){
                expanderItemListGroup.children.add(tableData2[j][1]);
                expanderItemListGroup.childID.add(tableData2[j][0]);
                expanderItemListGroup.childDescription.add(tableData2[j][2]);
                expanderItemListGroup.childPicture.add(tableData2[j][3]);
                getImage.downloadImage(tableData2[j][3]);
                getImage.downloadImage(tableData2[j][3]+"x");
            }
            groups.append(i, expanderItemListGroup);
        }
        statusList.setText("  " + linker.getRowCount() + " item(s) & " + subItems + " subitem(s) listed");
        ExpandableListAdapter adapter = new ExpandableListAdapter(activity,parentClass,groups,context);
        listView.setAdapter(adapter);
    }

    public void filterData(){
        tableData = linker.getTableData();
        groups.clear();

        int subItems = 0;
        int parentItems = 0;
        int filterPassParent = 0;
        int filterPassChild = 0;
        int incr = 0;

        for(int i = 0; i < linker.getRowCount(); i++){
            if(SearchData.idPrev.indexOf(tableData[i][0]) != -1 || !SearchData.filter) {
                filterPassParent = 1;
            }
            ExpanderItemListGroup expanderItemListGroup = new ExpanderItemListGroup(tableData[i][1],tableData[i][3]);
            MyTableLinker linker2 = new MyTableLinker(tableName2, tableData[i][0],context);
            linker2.start();
            tableData2 = linker2.getTableData();
            for(int j = 0; j < linker2.getRowCount(); j++){
                if(SearchData.idAll.indexOf(tableData2[j][0]) != -1 || !SearchData.filter || filterPassParent == 1 ) {
                    expanderItemListGroup.children.add(tableData2[j][1]);
                    expanderItemListGroup.childID.add(tableData2[j][0]);
                    expanderItemListGroup.childDescription.add(tableData2[j][2]);
                    expanderItemListGroup.childPicture.add(tableData2[j][3]);
                    subItems++;
                    filterPassChild = 1;
                }
            }

            if(filterPassParent == 1 || filterPassChild ==1) {
                groups.append(incr++, expanderItemListGroup);
                parentItems++;
            }
            filterPassParent = 0;
            filterPassChild = 0;
        }
        statusList.setText("  " + parentItems + " item(s) & " + subItems + " subitem(s) listed");
        ExpandableListAdapter adapter = new ExpandableListAdapter(activity,parentClass,groups,context);
        listView.setAdapter(adapter);
    }

    public void sortData(){
        /*ArrayList<Integer> sortedOrder;
        switch (SortStrings.sortOrder){
            case SortStrings.ASCENDING:
                sortedOrder = SortStrings.sortPosAscending(itemName);
                itemName = SortStrings.sortByPos(itemName,sortedOrder);
                listID = SortStrings.sortByPos(listID,sortedOrder);
                listDescription = SortStrings.sortByPos(listDescription,sortedOrder);
                pictureID = SortStrings.sortByPos(pictureID, sortedOrder);
                break;
            case SortStrings.DESCENDING:
                sortedOrder = SortStrings.sortPosDescending(itemName);
                itemName = SortStrings.sortByPos(itemName,sortedOrder);
                listID = SortStrings.sortByPos(listID,sortedOrder);
                listDescription = SortStrings.sortByPos(listDescription,sortedOrder);
                pictureID = SortStrings.sortByPos(pictureID, sortedOrder);
                break;
            case SortStrings.FAVORITE:
                for (int i = 0; i < itemName.size(); i++) {
                    if(favDb.getInd(listID.get(i).toString()) != 1) {
                        itemName = SortStrings.changeIndexToLast(itemName,i);
                        listID = SortStrings.changeIndexToLast(listID,i);
                        listDescription = SortStrings.changeIndexToLast(listDescription,i);
                        pictureID = SortStrings.changeIndexToLast(pictureID,i);
                    }
                }
                break;
        }

        if(adapter != null) {
            adapter = new ListAdapter(activity, itemName, pictureID, listID, itemName);
            navXList.setAdapter(adapter);
        }
        statusList.setText("  " + itemName.size() + " item(s) result matching");*/
    }
}

class ListAdapter2 extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    Context context;
    List<String> listName,listID;

    List<Double> listPrice;
    TextView cartCount;

    public ListAdapter2(Context context, List<String> listName, List<String> listID, List<Double> listPrice,TextView cartCount) {
        super(context, R.layout.row_list_itemsize_act, listName);
        for (int i = 0; i < listName.size(); ++i) {
            mIdMap.put(listName.get(i), i);
        }
        this.context = context;
        this.listName = listName;
        this.listID = listID;
        this.listPrice = listPrice;
        this.cartCount = cartCount;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_list_itemsize_act, parent, false);
        final TextView textView = (TextView) rowView.findViewById(R.id.listText);
        final String listName = this.listName.get(position);
        final String listID = this.listID.get(position);
        final double listPrice = this.listPrice.get(position);

        textView.setText(listName);

        Button addQuantity = (Button) rowView.findViewById(R.id.addQuantity);
        Button subQuantity = (Button) rowView.findViewById(R.id.subQuantity);
        final Button addCart = (Button) rowView.findViewById(R.id.addCart);
        final EditText quantity = (EditText) rowView.findViewById(R.id.quantity);

        int ind = Cart.itemSizeKey.indexOf(listID);
        if( ind == -1){
            addCart.setEnabled(false);
        }else{
            quantity.setText(Cart.quantity.get(ind)+"");
            addCart.setText("Update Cart");
            addCart.setEnabled(false);
        }
        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityValue = Integer.parseInt((quantity.getText()).toString());
                if (quantityValue <= 0)
                    quantityValue = 1;
                else if (quantityValue >= 99)
                    quantityValue = 99;
                else
                    quantityValue++;
                quantity.setText(String.valueOf(quantityValue));
                addCart.setEnabled(true);
            }
        });
        subQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityValue = Integer.parseInt((quantity.getText()).toString());
                if (quantityValue <= 0)
                    quantityValue = 0;
                else if (quantityValue >= 99)
                    quantityValue = 99;
                else
                    quantityValue--;
                quantity.setText(String.valueOf(quantityValue));
                if (quantityValue == 0 && addCart.getText() != "Add to Cart") {
                    addCart.setText("Remove from Cart");
                }
                if (addCart.getText() != "Add to Cart")
                    addCart.setEnabled(true);
            }
        });
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantityValue = Integer.parseInt((quantity.getText()).toString());
                if (quantityValue != 0) {
                    int index = Cart.itemSizeKey.indexOf(listID);
                    if (index == -1) {
                        Cart.itemSizeKey.add(listID);
                        Cart.itemSizeName.add(listName);
                        Cart.quantity.add(quantityValue);
                        Cart.itemPrice.add(quantityValue * listPrice);
                        Toast.makeText(context, listName + " added to cart", Toast.LENGTH_SHORT).show();

                    } else {
                        Cart.quantity.set(index, quantityValue);
                        Cart.itemPrice.set(index, quantityValue * listPrice);
                        Toast.makeText(context, listName + " updated in cart", Toast.LENGTH_SHORT).show();
                    }
                    addCart.setText("Update Cart");
                } else {
                    Cart.removeById(listID);
                    addCart.setText("Add to Cart");
                }
                addCart.setEnabled(false);
            }
        });
        Cart.updateStatusBarCart(cartCount);
        return rowView;
    }
}

class PopulateList2 {
    ListView listView;
    ArrayList listName,listID;
    ArrayList<Double> listPrice;
    Context context;
    ActivityListInterface parentClass;
    TextView statusList;
    TextView cartCount;

    public String tableData[][],tableName,tableKey,parent1Name,parent2Name;
    public ListAdapter2 adapter;
    public PopulateList2(Context context,ListView listView,String tableName, String tableKey, ActivityListInterface parent,String parent1Name,String parent2Name,TextView statusList,TextView cartCount){
        this.listView = listView;
        this.tableName = tableName;
        this.tableKey = tableKey;
        this.context = context;
        this.parentClass = parent;
        this.parent1Name = parent1Name;
        this.statusList = statusList;
        this.parent2Name = parent2Name;
        this.cartCount = cartCount;
        this.start();
    }

    private void start(){
        MyTableLinker linker = new MyTableLinker(tableName,tableKey,context);
        linker.start();
        tableData = linker.getTableData();
        listName = new ArrayList<String>();
        listID = new ArrayList<String>();
        listPrice = new ArrayList<Double>();
        for (int i = 0; i < linker.getRowCount(); ++i) {
            listName.add(parent2Name + " " + parent1Name + " " + tableData[i][1]);
            listID.add(tableData[i][0]);
            listPrice.add(Double.parseDouble(tableData[i][3]));
        }
        adapter = new ListAdapter2(context,listName,listID,listPrice,cartCount);
        listView.setAdapter(adapter);

        statusList.setText("  " + linker.getRowCount() + " item(s) listed");

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                //parentClass.onItemClick((String)listID.get(position));
            }
        });
    }
}


class CartListAdapter extends ArrayAdapter<String> {

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    List<String> cartListName;
    List<Integer> cartListQuantity;
    List<Double> cartListPrice;
    LayoutInflater inflater;
    Context context;

    public CartListAdapter(Context context, List<String> cartListName,List<Integer> cartListQuantity,List<Double> cartListPrice) {
        super(context, R.layout.row_cart_drawer, cartListName);
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < cartListName.size(); ++i) {
            mIdMap.put(cartListName.get(i), i);
        }
        this.cartListName = cartListName;
        this.context = context;
        this.cartListQuantity = cartListQuantity;
        this.cartListPrice = cartListPrice;
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

    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (convertView == null) {
            rowView = inflater.inflate(R.layout.row_cart_drawer, parent, false);
        }
        TextView listName = (TextView) rowView.findViewById(R.id.listText);
        listName.setText(cartListName.get(position));
        TextView listQuantity = (TextView) rowView.findViewById(R.id.listQuantity);
        listQuantity.setText(cartListQuantity.get(position)+"");
        TextView listPrice = (TextView) rowView.findViewById(R.id.listPrice);
        listPrice.setText(cartListPrice.get(position)+"");

        listName.setOnTouchListener(new OnSwipeTouchListener(context) {
                                       @Override
                                       public void onSwipeLeft() {
                                           remove(position);
                                       }
        });


            Button removeItem = (Button) rowView.findViewById(R.id.removeItem);
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        return rowView;
    }

    public void remove(int position) {
        Cart.remove(position);
        notifyDataSetChanged();
    }
}

class PopulateCartList {
    ListView listView;
    Context context;
    TextView noItems;

    public CartListAdapter adapter;
    public PopulateCartList(Context context,ListView listView,TextView noItems){
        this.listView = listView;
        this.context = context;
        this.noItems = noItems;
        this.start();
    }

    public void start(){
        if(Cart.itemSizeName.isEmpty()){
            noItems.setVisibility(View.VISIBLE);
        }
        else{
            noItems.setVisibility(View.INVISIBLE);
            ArrayList<String> listName = Cart.itemSizeName;
            ArrayList<Integer> listQuantity = Cart.quantity;
            ArrayList <Double> listPrice = Cart.itemPrice;
            adapter = new CartListAdapter(context,listName,listQuantity,listPrice);
            listView.setAdapter(adapter);
        }
    }
}


class ExpandableBrandListAdapter1 extends BaseExpandableListAdapter {

    private final SparseArray<LeftNavGroup> groups;
    public LayoutInflater inflater;
    ActivityListInterface parentClass;

    public ExpandableBrandListAdapter1(Activity context, SparseArray<LeftNavGroup> groups,ActivityListInterface parentClass) {
        this.groups = groups;
        inflater = context.getLayoutInflater();
        this.parentClass = parentClass;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).childName.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
        TextView text = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_xlist_item_child, null);
        }
        text = (TextView) convertView.findViewById(R.id.childNameText);
        text.setText(children);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentClass.onNavItemClick(LeftNavGroup.NAVIGATION,childPosition );
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).childName.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row_xlist_item_parent, null);
        }
        LeftNavGroup leftNavGroup = (LeftNavGroup) getGroup(groupPosition);

        TextView parentName = (TextView) convertView.findViewById(R.id.parentNameText);
        parentName.setText(leftNavGroup.parentName);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

class PopulateLeftDrawer {
    ExpandableListView navXList;
    SparseArray<LeftNavGroup> groups;

    Activity context;
    GetImage getImage;
    static HistList histList;
    ActivityListInterface parentClass;

    public PopulateLeftDrawer(Activity context,ExpandableListView navXList,ActivityListInterface parentClass){
        this.navXList = navXList;
        groups = new SparseArray<LeftNavGroup>();
        this.context = context;
        histList = new HistList();
        getImage = new GetImage(context);
        this.parentClass = parentClass;
        this.start();
    }

    private void start() {
        histRefresh();
    }

    public void histRefresh(){
        LeftNavGroup group;
        group = new LeftNavGroup(LeftNavGroup.NAVIGATION);
        group.childName = histList.getItems();

        if(groups.size() > 0)
            groups.delete(0);

        groups.append(0, group);

        ExpandableBrandListAdapter1 adapter = new ExpandableBrandListAdapter1(context,groups,parentClass);
        navXList.setAdapter(adapter);
    }

    public void addNewHist(String tableName,String id,String name){
        histList.newRoot(tableName,id,name);
    }
}

