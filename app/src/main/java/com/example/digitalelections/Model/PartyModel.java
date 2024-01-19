package com.example.digitalelections.Model;

public class PartyModel {
    private String representative;
    private int numberOfVoters;

    public PartyModel(String representative, int numberOfVoters) {
        this.representative = representative;
        this.numberOfVoters = numberOfVoters;
    }

    // Getters and setters
    public String getRepresentative() {
        return representative;
    }

    public int getNumberOfVoters() {
        return numberOfVoters;
    }
}
