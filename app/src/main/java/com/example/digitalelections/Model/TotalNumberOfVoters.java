package com.example.digitalelections.Model;

import java.util.HashMap;
import java.util.Map;

public class TotalNumberOfVoters {
    private Map<String, Integer> partyVotes;

    public TotalNumberOfVoters() {
        this.partyVotes = new HashMap<>();
    }

    public void addPartyVotes(String partyRepresentative, int votes) {
        partyVotes.put(partyRepresentative, votes);
    }

    // Getters and setters
    public Map<String, Integer> getPartyVotes() {
        return partyVotes;
    }
}
