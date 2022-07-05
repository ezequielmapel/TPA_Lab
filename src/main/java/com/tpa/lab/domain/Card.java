package com.tpa.lab.domain;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Card {
    private String label;
    private boolean inicial = false;
    private String realiza;
    private int custo;
    private List<String> necessarias;

    public Card(String label, boolean initial, String realize, int cust, List<String> needed) {
        this.label = label;
        this.inicial = initial;
        this.realiza = realize;
        this.custo = cust;
        this.necessarias = needed;
    }

    public Card() {
    }

    public static Card extractCard(JSONObject cardJSON) {
        Card card = new Card();
        card.setLabel(cardJSON.get("label").toString());
        card.setCusto(Integer.parseInt(cardJSON.get("custo").toString()));
        card.setRealiza(cardJSON.get("realiza").toString());
        card.setInicial(cardJSON.get("inicial") != null && Boolean.parseBoolean(cardJSON.get("inicial").toString()));
        JSONArray necessarias = (JSONArray)cardJSON.get("necessarias");
        card.setNecessarias(necessarias.stream().toList());

        return card;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public String getRealiza() {
        return realiza;
    }

    public void setRealiza(String realiza) {
        this.realiza = realiza;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }

    public List<String> getNecessarias() {
        return necessarias;
    }

    public void setNecessarias(List<String> necessarias) {
        this.necessarias = necessarias;
    }

}
