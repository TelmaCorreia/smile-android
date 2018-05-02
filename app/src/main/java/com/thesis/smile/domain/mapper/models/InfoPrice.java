package com.thesis.smile.domain.mapper.models;

public class InfoPrice {

    private String priceVazio;

    private String priceForaVazio;

    private String pricePonta;

    private String priceCheia;


    public InfoPrice(){}

    public String getPriceVazio() {
        return priceVazio;
    }

    public void setPriceVazio(String priceVazio) {
        this.priceVazio = priceVazio;
    }

    public String getPriceForaVazio() {
        return priceForaVazio;
    }

    public void setPriceForaVazio(String priceForaVazio) {
        this.priceForaVazio = priceForaVazio;
    }

    public String getPricePonta() {
        return pricePonta;
    }

    public void setPricePonta(String pricePonta) {
        this.pricePonta = pricePonta;
    }

    public String getPriceCheia() {
        return priceCheia;
    }

    public void setPriceCheia(String priceCheia) {
        this.priceCheia = priceCheia;
    }
}
