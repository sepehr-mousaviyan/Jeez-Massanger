package model;

import logic.IdGenerator;
import model.texts.Tweet;

import java.util.ArrayList;

public class User {
    private String firstName;
    private String familyName;
    private String username;
    private String bio;
    private String password;
    private String birthday;
    private String email;
    private String phonenumber;
    private int id;
    private ArrayList<Integer> tweetIds;
    private ArrayList<Integer> followerIds;
    private ArrayList<Integer> followingIds;
    private ArrayList<Integer> blacklistIds;
    private ArrayList<Integer> mutedIds;
    private ArrayList<String> systemMessages;
    private ArrayList<Integer> followRequestIds;
    private ArrayList<Integer> acceptedIds;
    private ArrayList<Integer> rejectedIds;
    private ArrayList<Integer> pendingIds;
    private ArrayList<Integer> savedMessageIds;
    private boolean isPrivate;
    private boolean isActive;
    private int lastSeenStyle;
    private String lastSeenDate;

    public User(String firstName, String familyName, String username, String bio, String password, String birthday, String email, String phonenumber) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.username = username;
        this.bio = bio;
        this.password = password;
        this.birthday = birthday;
        this.email = email;
        this.phonenumber = phonenumber;
        this.id = IdGenerator.generate();
        this.tweetIds = new ArrayList<>();
        this.followerIds = new ArrayList<>();
        this.followingIds = new ArrayList<>();
        this.blacklistIds = new ArrayList<>();
        this.systemMessages = new ArrayList<>();
        this.mutedIds = new ArrayList<>();
        this.savedMessageIds = new ArrayList<>();
        this.followRequestIds = new ArrayList<>();
        this.lastSeenStyle = 1;
        this.isPrivate = false;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public ArrayList<Integer> getBlacklistIds() {
        return blacklistIds;
    }

    public ArrayList<Integer> getFollowerIds() {
        return followerIds;
    }

    public ArrayList<Integer> getFollowingIds() {
        return followingIds;
    }

    public ArrayList<Integer> getMutedIds() {
        return mutedIds;
    }

    public ArrayList<Integer> getTweetIds() {
        return tweetIds;
    }

    public String getBio() {
        return bio;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFamilyName() {
        return familyName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public ArrayList<String> getSystemMessages() {
        return systemMessages;
    }

    public String getLastSeenDate() {
        return lastSeenDate;
    }

    public int getLastSeenStyle() {
        return lastSeenStyle;
    }

    public ArrayList<Integer> getFollowRequestIds() {
        return followRequestIds;
    }

    public ArrayList<Integer> getAcceptedIds() {
        return acceptedIds;
    }

    public ArrayList<Integer> getPendingIds() {
        return pendingIds;
    }

    public ArrayList<Integer> getRejectedIds() {
        return rejectedIds;
    }

    public ArrayList<Integer> getSavedMessageIds() {
        return savedMessageIds;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setSavedMessageIds(ArrayList<Integer> savedMessageIds) {
        this.savedMessageIds = savedMessageIds;
    }

    public void setAcceptedIds(ArrayList<Integer> acceptedIds) {
        this.acceptedIds = acceptedIds;
    }

    public void setLastSeenDate(String lastSeenDate) {
        this.lastSeenDate = lastSeenDate;
    }

    public void setPendingIds(ArrayList<Integer> pendingIds) {
        this.pendingIds = pendingIds;
    }

    public void setRejectedIds(ArrayList<Integer> rejectedIds) {
        this.rejectedIds = rejectedIds;
    }

    public void setFollowRequestIds(ArrayList<Integer> followRequestIds) {
        this.followRequestIds = followRequestIds;
    }

    public void setLastSeenStyle(int lastSeenStyle) {
        this.lastSeenStyle = lastSeenStyle;
    }

    public void setSystemMessages(ArrayList<String> systemMessages) {
        this.systemMessages = systemMessages;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setBlacklistIds(ArrayList<Integer> blacklistIds) {
        this.blacklistIds = blacklistIds;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFollowerIds(ArrayList<Integer> followerIds) {
        this.followerIds = followerIds;
    }

    public void setFollowingIds(ArrayList<Integer> followingIds) {
        this.followingIds = followingIds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMutedIds(ArrayList<Integer> mutedIds) {
        this.mutedIds = mutedIds;
    }

    public void setUsername(String nickname) {
        this.username = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setTweetIds(ArrayList<Integer> tweetIds) {
        this.tweetIds = tweetIds;
    }

    public void addTweet(int tweetId) {
        this.tweetIds.add(tweetId);
    }

    public void addFollower(int followerid) {
        if (!this.ifInFollowers(followerid)) {
            this.followerIds.add(followerid);
        }
    }

    public void addFollowing(int followingid) {
        if (!this.ifInFollowings(followingid)) {
            this.followingIds.add(followingid);
        }
    }

    public void addSystemMessage(String systemMessage) {
        this.systemMessages.add(systemMessage);
    }

    public void addFollowRequest(int userId) {
        this.followRequestIds.add(userId);
    }

    public void addAcceptedId(int userId) {
        this.acceptedIds.add(userId);
    }

    public void addRejectedId(int userId) {
        this.rejectedIds.add(userId);
    }

    public void addPendingId(int userId) {
        this.pendingIds.add(userId);
    }

    public void addSavedMessage(int messageId) {
        this.savedMessageIds.add(messageId);
    }

    public void addMuted(int userid) {
        boolean b = false;
        for (Integer id1 : mutedIds) {
            if (id1.equals(userid)) {
                b = true;
                break;
            }
        }
        if (!b) {
            mutedIds.add(userid);
        }
    }

    public void removeFromAcceptedIds(int userId) {
        acceptedIds.removeIf(id -> id.equals(userId));
    }

    public void removeFromRejectedIds(int userId) {
        rejectedIds.removeIf(id -> id.equals(userId));
    }

    public void removeFromPendingIds(int userId) {
        pendingIds.removeIf(id -> id.equals(userId));
    }

    public void removeFromFollowRequests(int userId) {
        followRequestIds.removeIf(id -> id.equals(userId));
    }

    public boolean ifInBlacklist(int userId) {
        for (Integer id1 : this.blacklistIds) {
            if (id1.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public void addToBlacklist(int blockedId) {
        if (!ifInBlacklist(blockedId)) {
            this.blacklistIds.add(blockedId);
        }
    }

    public void removeFromBlacklist(int blockedId) {
        this.blacklistIds.removeIf(id1 -> id1.equals(blockedId));
    }

    public void removeFromFollowers(int userId) {
        followerIds.removeIf(id1 -> id1.equals(userId));
    }

    public void removeFromFollowings(int userId) {
        followingIds.removeIf(id1 -> id1.equals(userId));
    }

    public void removeTweet(int tweetId) {
        tweetIds.removeIf(id1 -> id1.equals(tweetId));
    }

    public ArrayList<Tweet> getTweets(ModelBase modelBase) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (Integer integer : tweetIds) {
            tweets.add(modelBase.getTweetById(integer));
        }
        return tweets;
    }

    public boolean ifInFollowers(int userId) {
        for (Integer id1 : this.followerIds) {
            if (id1.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifInFollowings(int userId) {
        for (Integer id1 : this.followingIds) {
            if (id1.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifInMuted(int userId) {
        for (Integer id1 : this.mutedIds) {
            if (id1.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public String showLastSeen(int userId) {
        if (this.lastSeenStyle == 0) {
            return "last seen recently";
        }
        if (this.lastSeenStyle == 1) {
            if (this.ifInFollowings(userId)) {
                return this.lastSeenDate;
            }
            return "last seen recently";
        }
        if (this.lastSeenStyle == 2) {
            return "last seen recently";
        }
        return "last seen recently";
    }

    public boolean canChat(User user) {
        return (!user.ifInBlacklist(this.id) && !this.ifInBlacklist(user.getId())) &&
                (this.ifInFollowings(user.getId()) || this.ifInFollowers(user.getId()))
                && (this.isActive() && user.isActive());
    }

}
