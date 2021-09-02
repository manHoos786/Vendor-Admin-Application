package com.example.vendoradmin;

public class Schema {

    private String _id;
    private String machine;
    private Double product_id;
    private String image;
    private String key_razorpay;
    private Integer quantity;
    private Integer price;
    private Boolean status;
    private Integer v;


    public String getKey_razorpay() {
        return key_razorpay;
    }

    public void setKey_razorpay(String key_razorpay) {
        this.key_razorpay = key_razorpay;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public Double getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Double productId) {
        this.product_id = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}

