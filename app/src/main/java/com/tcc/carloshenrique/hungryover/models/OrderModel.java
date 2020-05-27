package com.tcc.carloshenrique.hungryover.models;

import com.squareup.moshi.Json;

import java.util.List;

public class OrderModel {
    @Json(name = "idPedido")
    private int Id;
    @Json(name = "idCliente")
    private int userId;
    @Json(name = "idSessao")
    private int sessionId;
    @Json(name = "produtoList")
    private List<ItemModel> orderItems;
    @Json(name = "obs")
    private String observations;

    public OrderModel() {

    }

    public OrderModel(int userId, int sessionId, List<ItemModel> orderItems, String observations) {
        super();
        this.userId = userId;
        this.sessionId = sessionId;
        this.orderItems = orderItems;
        this.observations = observations;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public List<ItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
