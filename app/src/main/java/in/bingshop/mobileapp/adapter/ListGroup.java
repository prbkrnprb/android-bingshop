package in.bingshop.mobileapp.adapter;

import java.util.List;

/**
 * Created by Prabakaran on 08-28-15.
 */
public class ListGroup {
    List<String> id;
    List<String> name;
    List<String> description;
    List<String> pictureId;
    public ListGroup(List<String> id,List<String> name,List<String> description, List<String> pictureId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.pictureId = pictureId;
    }

    public List<String> getId(){
        return id;
    }

    public List<String> getName(){
        return name;
    }

    public List<String> getDescription(){
        return description;
    }

    public List<String> getPictureId(){
        return pictureId;
    }
}
