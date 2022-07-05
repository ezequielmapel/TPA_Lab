package com.tpa.lab.domain;

public class EdgeType {

    private int weight;
    private String label;
    private String source;
    private String destination;

    public EdgeType(int weight, String label) {
        this.weight = weight;
        this.label = label;
    }

    public int getWeight() { return weight; }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
