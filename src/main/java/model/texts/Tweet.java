package model.texts;

import model.ModelBase;
import model.User;

import java.util.ArrayList;

public class Tweet extends Text {
    private ArrayList<Integer> commentIds;
    private ArrayList<Integer> likeIds;
    private int fatherTweetId;

    public Tweet(String content, int authorId, int fatherTweetId) {
        super(content, authorId);
        commentIds = new ArrayList<>();
        likeIds = new ArrayList<>();
        this.fatherTweetId = fatherTweetId;
    }

    public ArrayList<Integer> getCommentIds() {
        return commentIds;
    }

    public ArrayList<Integer> getLikeIds() {
        return likeIds;
    }

    public void setCommentIds(ArrayList<Integer> commentIds) {
        this.commentIds = commentIds;
    }

    public void setLikeIds(ArrayList<Integer> likeIds) {
        this.likeIds = likeIds;
    }

    public int getFatherTweetId() {
        return fatherTweetId;
    }

    public void setFatherTweetId(int fatherTweetId) {
        this.fatherTweetId = fatherTweetId;
    }

    public void addComment(int commentId) {
        commentIds.add(commentId);
    }

    public void addLikeId(int userId) {
        likeIds.add(userId);
    }

    public void removeTweetId(int tweetId) {
        commentIds.removeIf(id1 -> id1.equals(tweetId));
    }

    public boolean ifLiked(int userId) {
        for (Integer id : likeIds) {
            if (id.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Tweet> getComments(ModelBase modelBase) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (Integer id : commentIds) {
            tweets.add(modelBase.getTweetById(id));
        }
        return tweets;
    }

    public boolean canBeShown(User user, ModelBase modelBase) {
        return !user.ifInMuted(authorId) && this.getAuthor(modelBase).isActive();
    }

    public boolean isRelatedToUser(int userId, ModelBase modelBase) {
        Tweet tweet = this;
        while (tweet.fatherTweetId != -1) {
            if (tweet.getAuthorId() == userId) {
                return true;
            }
            tweet = modelBase.getTweetById(tweet.getFatherTweetId());
        }
        return tweet.getAuthorId() == userId;
    }

    @Override
    public String show(ModelBase modelBase) {
        return "Comments: " + commentIds.size() + " Likes: " + likeIds.size() + "\n" + super.show(modelBase);
    }
}
