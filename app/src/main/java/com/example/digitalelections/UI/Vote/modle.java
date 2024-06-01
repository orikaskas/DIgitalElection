package com.example.digitalelections.UI.Vote;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class modle {

    public void Updatevote(Context context, int vote, int VoteCity, String id, Repository.Completed completed) {
        Repository repository = new Repository(context);
        repository.UpdateVote(vote, VoteCity, id, completed);
    }

    public void UpdateNormal(Context voteActivity,String answer) {
        Repository repository = new Repository(voteActivity);
        repository.UpdateNormal(answer);
    }

    public void UpdateNormalCity(Context voteActivity, String string) {
        Repository repository = new Repository(voteActivity);
        repository.UpdateNormalCity(string);
    }

}
