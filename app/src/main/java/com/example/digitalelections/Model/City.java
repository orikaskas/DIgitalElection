package com.example.digitalelections.Model;

import java.util.ArrayList;
import java.util.List;

public class City
{
    private List<PartyModel> parties;
    private int votersPerMile;

    public City(int votersPerMile) {
        this.parties = new ArrayList<>();
        this.votersPerMile = votersPerMile;
    }

    public void addParty(PartyModel party) {
        parties.add(party);
    }

    // Getters and setters
    public List<PartyModel> getParties() {
        return parties;
    }

    public int getVotersPerMile() {
        return votersPerMile;
    }
}
