
package learning.nitish.redmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("measure")
    @Expose
    private Measure measure;
    @SerializedName("pricing")
    @Expose
    private Pricing pricing;
    @SerializedName("title")
    @Expose
    private String title;




    public String getDesc() {
        return desc;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }




    public Measure getMeasure() {
        return measure;
    }


    public Pricing getPricing() {
        return pricing;
    }

    public String getTitle() {
        return title;
    }




}