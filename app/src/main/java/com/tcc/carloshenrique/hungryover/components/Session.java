package com.tcc.carloshenrique.hungryover.components;

import com.squareup.moshi.Json;
import com.tcc.carloshenrique.hungryover.models.ItemModel;
import com.tcc.carloshenrique.hungryover.models.UserModel;

import java.util.List;

public class Session {

    @Json(name = "id")
    private Integer id;
    @Json(name = "cliente")
    private UserModel user;
    @Json(name = "pedidos")
    private List<ItemModel> pedidos;
    @Json(name = "abertura")
    private String abertura;
    @Json(name = "fechamento")
    private String fechamento;
    @Json(name = "valor")
    private double valor;

    public Session() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Session withId(Integer id) {
        this.id = id;
        return this;
    }

    public List<ItemModel> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<ItemModel> pedidos) {
        this.pedidos = pedidos;
    }

    public Session withPedidos(List<ItemModel> pedidosd) {
        this.pedidos = pedidosd;
        return this;
    }

    public String getAbertura() {
        return abertura;
    }

    public void setAbertura(String abertura) {
        this.abertura = abertura;
    }

    public Session withAbertura(String abertura) {
        this.abertura = abertura;
        return this;
    }

    public String getFechamento() {
        return fechamento;
    }

    public void setFechamento(String fechamento) {
        this.fechamento = fechamento;
    }

    public Session withFechamento(String fechamento) {
        this.fechamento = fechamento;
        return this;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Session withValor(Double valor) {
        this.valor = valor;
        return this;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}