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

public class SettingPage extends Page {

    private User user;
    private static final Logger logger = LogManager.getLogger(SettingPage.class);

    public SettingPage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print(
                "This is setting page, enter commands to use them.\n" +
                "Commands to be used:\n" +
                "1)setPrivacy\n" +
                "2)setLastSeen\n" +
                "3)setActivity\n" +
                "4)logout\n" +
                "5)delete\n" +
                "6)help\n" +
                "7)back\n" +
                "8)exit\n");
    }

    @Override
    public void setPrivacy() {
        cli.print("Do you want your account to be public or private(type public or private: ");
        String answer = "";
        while (true) {
            answer = cli.inputString();
            if (answer.equals("private")) {
                user.setPrivate(true);
                cli.print("Your account is private now.");
                return;
            }
            if (answer.equals("public")) {
                user.setPrivate(false);
                cli.print("Your account is public now.");
                return;
            }
            else {
                cli.print("You are doing it wrong bro!");
            }
        }
    }

    @Override
    public void setActivity() {
        cli.print("Do you want your account to be activated or deactivated(type activated or deactivated:");
        String answer = "";
        while (true) {
            answer = cli.inputString();
            if (answer.equals("activated")) {
                user.setActive(true);
                cli.print("Your account is activated now.");
                return;
            }
            if (answer.equals("deactivated")) {
                user.setActive(false);
                cli.print("Your account is deactivated now.");
                return;
            }
            else {
                cli.print("You are doing it wrong bro!");
            }
        }
    }

    @Override
    public void logout() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        user.setLastSeenDate(date);
        cli.print("You logged out successfully.");
        pageHandler.getPages().removeAll(pageHandler.getPages());
        LoginPage loginPage = new LoginPage(cli, modelBase, pageHandler);
        pageHandler.setCurrentPage(loginPage);
        logger.info(user.getUsername() + " logged out.");
    }

    @Override
    public void setLastSeen() {
        cli.print("How do you want your last seen to be(type all or noOne or onlyFollowings:");
        String answer = "";
        while (true) {
            answer = cli.inputString();
            if (answer.equals("all")) {
                user.setLastSeenStyle(2);
                return;
            }
            if (answer.equals("noOne")) {
                user.setLastSeenStyle(0);
                return;
            }
            if (answer.equals("onlyFollowings")) {
                user.setLastSeenStyle(1);
                return;
            }
        }
    }

    @Override
    public void delete() {
        for (User user1 : modelBase.getUsers()) {
            user1.removeFromFollowings(user.getId());
            user1.removeFromFollowers(user.getId());
            user1.removeFromBlacklist(user.getId());
            user1.removeFromFollowRequests(user.getId());
            user1.removeFromPendingIds(user.getId());
            user1.removeFromAcceptedIds(user.getId());
            user1.removeFromRejectedIds(user.getId());
        }
        for (Tweet tweet : modelBase.getTweets()) {
            if (tweet.isRelatedToUser(user.getId(), modelBase)) {
                for (User user1 : modelBase.getUsers()) {
                    user1.removeTweet(tweet.getId());
                }
                for (Tweet tweet1 : modelBase.getTweets()) {
                    tweet1.removeTweetId(tweet.getId());
                }
                modelBase.removeText(tweet);
            }
        }
        modelBase.getTweets().removeIf(tweet -> tweet.isRelatedToUser(user.getId(), modelBase));
        modelBase.removeUser(user);
        pageHandler.getPages().removeAll(pageHandler.getPages());
        LoginPage loginPage = new LoginPage(cli, modelBase, pageHandler);
        pageHandler.setCurrentPage(loginPage);
        logger.info(user.getUsername() + " deleted his/her account.");
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
