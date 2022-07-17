package com.example.shopapplication.models;

public class Product {
    private int img;
    private int number;
    private String name;
    private double price;
    private String introduceStr;

    public Product(int img, int number, String name, double price, String introduceStr){
        this.img = img;
        this.number = number;
        this.name = name;
        this.price = price;
        this.introduceStr = introduceStr;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIntroduceStr() {
        return introduceStr;
    }

    public void setIntroduceStr(String introduceStr) {
        this.introduceStr = introduceStr;
    }

    @Override
    public String toString() {
        return "Product{" +
                "img=" + img +
                ", number=" + number +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", introduceStr='" + introduceStr + '\'' +
                '}';
    }
}
