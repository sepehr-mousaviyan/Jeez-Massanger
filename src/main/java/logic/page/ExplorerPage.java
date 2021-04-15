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

public class ExplorerPage extends Page {

    private User user;
    private static final Logger logger = LogManager.getLogger(ExplorerPage.class);

    public ExplorerPage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print(
                "This is explorer page.\n" +
                "Commands to be used:\n" +
                "1)findUser\n" +
                "2)seeTweets\n" +
                "3)back\n" +
                "4)help\n" +
                "5)exit\n");
    }

    @Override
    public void findUser() {
        cli.print("Enter a user's username to see his/her profile:");
        String username = cli.inputString();
        if(!modelBase.ifUserNameExists(username)) {
            logger.info("User not found.");
            cli.print("User not found.");
            return;
        }
        User profileOwner = modelBase.getUserByUsername(username);
        SeeProfilePage seeProfilePage = new SeeProfilePage(cli, modelBase, pageHandler, user, profileOwner);
        pageHandler.setCurrentPage(seeProfilePage);
    }

    @Override
    public void seeTweets() {
        if(modelBase.getTweets().size() == 0) {
            cli.print("No tweets found.");
            return;
        }
        ArrayList<Tweet> prelimTweets = new ArrayList<>();
        for(Tweet tweet : modelBase.getTweets()) {
            if(tweet.canBeShown(user, modelBase)) {
                prelimTweets.add(tweet);
            }
        }
        if(prelimTweets.size() == 0) {
            cli.print("No tweets to be shown.");
            return;
        }
        ArrayList<Tweet> finalTweets = new ArrayList<>();
        for(int i = 0; i < prelimTweets.size(); i += 3) {
            finalTweets.add(prelimTweets.get(i));
        }
        TweetShowPage tweetShowPage = new TweetShowPage(cli, modelBase, pageHandler, user, finalTweets);
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
