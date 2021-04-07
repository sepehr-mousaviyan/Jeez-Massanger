package logic.page;

import logic.Cli;
import logic.PageHandler;
import model.ModelBase;
import model.User;
import model.texts.Tweet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TimelinePage extends Page {

    private User user;

    public TimelinePage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print(
                "This timeline page.\n" +
                "Commanads to be used:\n" +
                "1)seeTimelineTweets\n" +
                "2)help\n" +
                "3)back\n" +
                "4)exit\n");
    }

    @Override
    public void seeTimelineTweets() {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for (Integer id : user.getFollowingIds()) {
            User following = modelBase.getUserById(id);
            for (Tweet tweet : following.getTweets(modelBase)) {
                if (tweet.canBeShown(user, modelBase)) {
                    tweets.add(tweet);
                }
            }
        }
        if (tweets.size() == 0) {
            cli.print("No tweets found.");
            return;
        }
        TweetShowPage tweetShowPage = new TweetShowPage(cli, modelBase, pageHandler, user, tweets);
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
