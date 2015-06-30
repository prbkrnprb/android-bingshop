package in.bingshop.mobileapp;

/**
 * Created by Prabakaran on 02-02-2015.
 */
public interface ActivityListInterface {
    Cart cart = new Cart();
    void onItemClick(String key, String name, String description, String picture);
    void onItemClick(String key, String name, String description, String picture, String pName);
    void onNavItemClick(String parent, int childPos);
}
