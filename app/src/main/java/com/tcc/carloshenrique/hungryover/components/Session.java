package com.tcc.carloshenrique.hungryover.components;

import com.squareup.moshi.Json;
import com.tcc.carloshenrique.hungryover.models.ItemModel;

import java.util.Date;
import java.util.List;

public class Session {

    @Json(name = "id")
    private Integer id;
    @Json(name = "idMesa")
    private Integer idMesa;
    @Json(name = "idUser")
    private Integer idUser;
    @Json(name = "pedidos")
    private List<ItemModel> pedidos;
    @Json(name = "abertura")
    private Date abertura;
    @Json(name = "fechamento")
    private Date fechamento;
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

    public Integer getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
    }

    public Session withIdMesa(Integer idMesa) {
        this.idMesa = idMesa;
        return this;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Session withIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public Date getAbertura() {
        return abertura;
    }

    public void setAbertura(Date abertura) {
        this.abertura = abertura;
    }

    public Session withAbertura(Date abertura) {
        this.abertura = abertura;
        return this;
    }

    public Date getFechamento() {
        return fechamento;
    }

    public void setFechamento(Date fechamento) {
        this.fechamento = fechamento;
    }

    public Session withFechamento(Date fechamento) {
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
}