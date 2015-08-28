package in.bingshop.mobileapp.activity.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import in.bingshop.bingshop.R;
import in.bingshop.mobileapp.ActivityListInterface;


public class MasterActivity extends Activity implements ActivityListInterface, SearchView.OnQueryTextListener,Spinner.OnItemSelectedListener {
    ListView itemList;
    ExpandableListView expandableItemList, expandableNavList;
    TextView headerText, headerDescription;
    ImageView headerImage;
    PopulateList pL;
    PopulateExpandableList pEL;
    protected String tableNameConst = "";
    protected String tableName1Const = "";
    protected String tableName2Const = "";
    protected String tableKey, itemName,tableDescription,tablePicture;
    PopulateLeftDrawer populateLeftDrawer;
    protected Intent intent;
    ProgressDialog progressDialog = null;
    int type;

    public MasterActivity(int type){
        itemName = "";
        tableKey = "";
        tableDescription = "";
        tablePicture = "";
        this.type = type;
    }

    public MasterActivity(){
        itemName = "";
        tableKey = "";
        tableDescription = "";
        tablePicture = "";
        type = 0;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (type){
            case 0:
                setContentView(R.layout.layout_act);
                break;
            case 1:
                setContentView(R.layout.layout_item_act);
                break;
        }
    }

    protected void start(){
        //setContentView(R.layout.activity_main);

        TextView statusList = (TextView) findViewById(R.id.statusTxt);

        SearchView searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setQuery(SearchData.key, true);
        searchView.setOnQueryTextListener(this);


        setHeader();
        setTrailer();
        switch (type){
            case 0:
                itemList = (ListView) findViewById(R.id.itemList);
                pL = new PopulateList(this,itemList, tableNameConst,tableKey,this,statusList);
                break;
            case 1:
                expandableItemList = (ExpandableListView) findViewById(R.id.expandableListView);
                pEL = new PopulateExpandableList(this,expandableItemList,tableName1Const,tableKey,tableName2Const,this,getBaseContext(),statusList);
                tableNameConst = tableName1Const;
                break;
        }

        expandableNavList = (ExpandableListView) findViewById(R.id.leftNavXView);
        populateLeftDrawer = new PopulateLeftDrawer(this, expandableNavList,this);

        Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        try {
            if (intent.getStringExtra("Filter").equals("0") && SearchData.filter){
                searchView.onActionViewExpanded();
                searchView.setQuery(SearchData.key, true);

            }
            else
                searchView.setQuery(SearchData.key, false);
        }catch (Exception e){
            searchView.setQuery(SearchData.key, false);
        }
    }

    public void onResume(){
        super.onResume();
        setTrailer();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    public void openRightNav(View view) {
        cart.openRightNav(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(String key,String name,String description,String picture) {
        populateLeftDrawer.addNewHist(tableNameConst, key, name);
    }

    private void setHeader(){
        GetImage getImage = new GetImage(getApplicationContext());
        headerText = (TextView) findViewById(R.id.headerText);
        headerDescription = (TextView) findViewById(R.id.headerDetail);
        headerImage = (ImageView) findViewById(R.id.headerImage);

        headerText.setText(itemName);
        headerDescription.setText(tableDescription);
        headerImage.setImageDrawable(getImage.get(tablePicture + "x"));
    }

    private void setTrailer(){
        TextView cartCount = (TextView) findViewById(R.id.cartStatus);
        if(Cart.getCartCount() == 0)
            cartCount.setText("No items in cart");
        else
            cartCount.setText(Cart.getCartCount() + " item(s) in cart");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        switch (type){
            case 0:
                SearchData.filter = SearchData.process(tableNameConst,query);
                pL.filterData();
                break;
            case 1:
                SearchData.filter = SearchData.process(tableName1Const,query,tableName2Const);
                pEL.filterData();
                break;
        }
        return SearchData.filter;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.equals("")){
            SearchData.filter = false;
            switch (type){
                case 0:
                    pL.filterData();
                    break;
                case 1:
                    pEL.filterData();
                    break;
            }
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SortStrings.sortOrder = parent.getSelectedItem().toString();
        switch (type){
            case 0:
                pL.sortData();
                break;
            case 1:
                pEL.sortData();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(String key, String name, String description, String picture, String pName) {
        populateLeftDrawer.addNewHist(tableNameConst, key, name);
    }

    public void onNavItemClick(String parent, int childPos){

        switch (parent){
            case LeftNavGroup.NAVIGATION:
                BingUtil.reqPos = (childPos * -1) + 2;
                this.finish();
                break;
        }
    }

    public void onCheckout(View view) {
        if(Cart.itemSizeName.size() > 0) {
            MyCheckoutLinker linker = new MyCheckoutLinker(Cart.itemSizeKey, Cart.quantity);
            linker.start();
            if (linker.getRC() == 0) {
                Cart.clearCart();
                Toast.makeText(this, "Your order has been placed. Order ID : " + linker.getOrderID() + "You can also track order in the Order status option", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "Order unsuccessful !! Contact support", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "No items in cart !!", Toast.LENGTH_LONG).show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(BingUtil.reqPos-- > 1)
            this.finish();
    }

    public void onExit(View view) {
        new BingUtil().onExit(this);
    }

    public void onLogout(View view) {
        BingUtil.initialize();
        BingUtil.reqPos = 5;
        this.finish();
    }
}


