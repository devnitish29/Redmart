
package learning.nitish.redmart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pricing {

    @SerializedName("promo_id")
    @Expose
    private Double promoId;
    @SerializedName("on_sale")
    @Expose
    private Double onSale;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("promo_price")
    @Expose
    private Double promoPrice;
    @SerializedName("savings")
    @Expose
    private Double savings;
    @SerializedName("savings_amount")
    @Expose
    private Double savingsAmount;
    @SerializedName("savings_type")
    @Expose
    private Double savingsType;
    @SerializedName("savings_text")
    @Expose
    private String savingsText;
    @SerializedName("applicable_discount")
    @Expose
    private String applicableDiscount;

    public Double getPromoId() {
        return promoId;
    }

    public void setPromoId(Double promoId) {
        this.promoId = promoId;
    }

    public Double getOnSale() {
        return onSale;
    }

    public void setOnSale(Double onSale) {
        this.onSale = onSale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(Double savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public Double getSavingsType() {
        return savingsType;
    }

    public void setSavingsType(Double savingsType) {
        this.savingsType = savingsType;
    }

    public String getSavingsText() {
        return savingsText;
    }

    public void setSavingsText(String savingsText) {
        this.savingsText = savingsText;
    }


    public String getApplicableDiscount() {
        return applicableDiscount;
    }

    public void setApplicableDiscount(String applicableDiscount) {
        this.applicableDiscount = applicableDiscount;
    }

}
