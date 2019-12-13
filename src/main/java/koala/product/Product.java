package koala.product;

import java.sql.Blob;

public class Product {
    private int id;
    private String name;
    private String desc;
    private String provider;
    private byte[] image;
    private float buyPrice;
    private float sellPrice;
    private String howToUse;

    public Product(int id,
                   String name,
                   String desc,
                   String provider,
                   byte[] image,
                   float buyPrice,
                   float sellPrice,
                   String howToUse) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.provider = provider;
        this.image = image;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.howToUse = howToUse;
    }

    public byte[] getImage() {
        return image;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public int getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public String getHowToUse() {
        return howToUse;
    }

    public String getName() {
        return name;
    }

    public String getProvider() {
        return provider;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setHowToUse(String howToUse) {
        this.howToUse = howToUse;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }
}
