package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

public class CartItemModel {
    @Json(name = "item")
    private ItemModel item;
    @Json(name = "price")
    private double price;
    @Json(name = "amount")
    private int amount;

    public CartItemModel() {

    }

    public CartItemModel(ItemModel item, double price, int amount) {
        super();
        this.item = item;
        this.price = price;
        this.amount = amount;
    }

    public ItemModel getItem() {
        return item;
    }

    public void setItem(ItemModel item) {
        this.item = item;
    }

    public CartItemModel withItem(ItemModel item) {
        this.item = item;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CartItemModel withPrice(double price) {
        this.price = price;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CartItemModel withAmount(int amount) {
        this.amount = amount;
        return this;
    }
}