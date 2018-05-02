package com.thesis.smile.data.remote.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoPriceRemote {

    @SerializedName("price_vazio")
    @Expose
    private String priceVazio;

    @SerializedName("price_fora_vazio")
    @Expose
    private String priceForaVazio;

    @SerializedName("price_ponta")
    @Expose
    private String pricePonta;

    @SerializedName("price_cheia")
    @Expose
    private String priceCheia;


    public InfoPriceRemote(){}

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
