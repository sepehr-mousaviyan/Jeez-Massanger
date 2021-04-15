package logic.page;

import logic.Cli;
import logic.PageHandler;
import model.ModelBase;
import model.User;
import model.texts.Tweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TweetShowPage extends Page {
    private User user;
    private ArrayList<Tweet> tweets;
    private int index;
    private static final Logger logger = LogManager.getLogger(TweetShowPage.class);

    public TweetShowPage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user, ArrayList<Tweet> tweets) {
        super(cli, modelBase, pageHandler);
        this.user = user;
        this.tweets = tweets;
        this.index = 0;
    }

    public Tweet getTweet() {
        return tweets.get(index);
    }

    @Override
    public void help() {
        cli.print(
                "Commands to be used:\n" +
                "1)tweetContent\n" +
                "2)next\n" +
                "3)prev\n" +
                "4)addToSaveMessage\n" +
                "5)reTweet\n" +
                "6)block\n" +
                "7)mute\n" +
                "8)report\n" +
                "9)seeAuthorProfile\n" +
                "10)comment\n" +
                "11)like\n" +
                "12)seeComments\n" +
                "13)forward\n" +
                "14)help\n" +
                "15)back\n" +
                "16)exit\n");
    }

    @Override
    public void tweetContent() {
        cli.print(this.getTweet().show(modelBase));
    }

    @Override
    public void next() {
        if(this.index != this.tweets.size() - 1) {
            index++;
            cli.print("You are now in the next tweet.");
        }
        else {
            cli.print("There is no next one.");
        }
    }

    @Override
    public void prev() {
        if(this.index != 0) {
            index--;
            cli.print("You are now in the previous tweet.");
        }
        else {
            cli.print("There is no previous one.");
        }
    }

    @Override
    public void addToSaveMessage() {
        user.addSavedMessage(this.getTweet().getId());
        cli.print("Added to saved messages successfully.");
    }

    @Override
    public void reTweet() {
        user.addTweet(this.getTweet().getId());
        cli.print("ReTweeted successfully.");
        logger.info(user.getUsername() + " retweeted.");
    }

    @Override
    public void block() {
        User blockUser = this.getTweet().getAuthor(modelBase);
        if(user.ifInBlacklist(blockUser.getId())) {
            cli.print("You have already blocked " + blockUser.getUsername());
        }
        else {
            user.addToBlacklist(blockUser.getId());
            cli.print("You blocked " + blockUser.getUsername() + " successfully.");
            logger.info(user.getUsername() + " blocked " + blockUser.getUsername() + ".");
        }
    }

    @Override
    public void mute() {
        user.addMuted(this.getTweet().getAuthor(modelBase).getId());
        cli.print("You muted " + this.getTweet().getAuthor(modelBase).getUsername() + " successfully.");
        logger.info(user.getUsername() + " muted " + this.getTweet().getAuthor(modelBase).getUsername());
    }

    @Override
    public void report() {
        cli.print("You reported this tweet successfully.");
        this.getTweet().getAuthor(modelBase).addSystemMessage("Your tweet has been reported.");
    }

    @Override
    public void seeAuthorProfile() {
        User profileOwner = this.getTweet().getAuthor(modelBase);
        SeeProfilePage seeProfilePage = new SeeProfilePage(cli, modelBase, pageHandler, user, profileOwner);
        pageHandler.setCurrentPage(seeProfilePage);
    }

    @Override
    public void comment() {
        cli.print("Enter your comment text:");
        String content = cli.inputString();
        Tweet comment = new Tweet(content, user.getId(), this.getTweet().getId());
        this.getTweet().addComment(comment.getId());
        modelBase.addTweet(comment);
        modelBase.addText(comment);
        cli.print("You commented successfully.");
        logger.info(user.getUsername() + " commented.");
    }

    @Override
    public void like() {
        if(this.getTweet().ifLiked(user.getId())) {
            cli.print("You liked this already.");
        }
        else {
            cli.print("You liked this tweet successfully.");
            this.getTweet().addLikeId(user.getId());
        }
    }

    @Override
    public void seeComments() {
        ArrayList<Tweet> commentsToShow = new ArrayList<>();
        for(Tweet tweet : this.getTweet().getComments(modelBase)) {
            if(tweet.canBeShown(user, modelBase)) {
                commentsToShow.add(tweet);
            }
        }
        if(commentsToShow.size() == 0) {
            cli.print("No comments to show.");
            return;
        }
        TweetShowPage tweetShowPage = new TweetShowPage(cli, modelBase, pageHandler, user, commentsToShow);
        pageHandler.setCurrentPage(tweetShowPage);
    }

    @Override
    public void exit() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        user.setLastSeenDate(date);
        super.exit();
    }
}
