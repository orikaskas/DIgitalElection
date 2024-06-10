package com.example.digitalelections.UI.Vote;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class modle {
    private Repository repository;//משתנה מסוג ריפוזיטורי
    modle(Context context){
        repository = new Repository(context);
    }

    public void Updatevote(int vote, int VoteCity, String id, Repository.Completed completed) {
        repository.UpdateVote(vote, VoteCity, id, completed);
    }

    public void UpdateNormal(String answer) {
        repository.UpdateNormal(answer);
    }

    public void UpdateNormalCity( String string) {
        repository.UpdateNormalCity(string);
    }

}
