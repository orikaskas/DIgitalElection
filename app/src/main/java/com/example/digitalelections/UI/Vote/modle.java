package com.example.digitalelections.UI.Vote;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;

public class modle {
    public void GetVote(Context context, String id, Repository.Completed completed) {
        Repository repository = new Repository(context);
        repository.GetVoteCountry(id, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                completed.onComplete(flag);
            }
        });
    }

    public void Updatevote(Context context, int vote, int VoteCity, String id, Repository.Completed completed) {
        Repository repository = new Repository(context);
        repository.UpdateVote(vote, VoteCity, id, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                completed.onComplete(flag);
            }
        });
    }

    public void UpdateNormal(Context voteActivity,String answer) {
        Repository repository = new Repository(voteActivity);
        repository.UpdateNormal(answer);
    }

    public void UpdateNormalCity(Context voteActivity, String string) {
        Repository repository = new Repository(voteActivity);
        repository.UpdateNormalCity(string);
    }

    public void GetVoteCity(Context voteActivity, String id, Repository.Completed completed) {
        Repository repository = new Repository(voteActivity);
        repository.GetVoteCity(id, new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                completed.onComplete(flag);
            }
        });
    }
}