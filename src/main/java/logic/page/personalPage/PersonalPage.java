package logic.page.personalPage;

import logic.Cli;
import logic.PageHandler;
import logic.page.Page;
import logic.page.SeeProfilePage;
import logic.page.TweetShowPage;
import model.ModelBase;
import model.User;
import model.texts.Tweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersonalPage extends Page {

    private User user;
    private static final Logger logger = LogManager.getLogger(PersonalPage.class);

    public PersonalPage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print(
                "This is your personal page, enter commands names to use them\n" +
                "commands to be used:\n" +
                "1)makeTweet\n" +
                "2)showMyTweets\n" +
                "3)editProfilePage\n" +
                "4)info\n" +
                "5)handleFollowRequests\n" +
                "6)myFollowRequests\n" +
                "7)seeFollowers\n" +
                "8)seeFollowings\n" +
                "9)seeBlacklist\n" +
                "10)seeSystemMessages\n" +
                "11)help\n" +
                "12)back\n" +
                "13)exit");
    }

    @Override
    public void makeTweet() {
        cli.print("Type your tweet's text:");
        String content = cli.inputString();
        Tweet tweet = new Tweet(content, user.getId(), -1);
        user.addTweet(tweet.getId());
        modelBase.addTweet(tweet);
        modelBase.addText(tweet);
        logger.info("Tweet has been made.");
        cli.print("Tweet has been successfully added.");
    }

    @Override
    public void showMyTweets() {
        if (user.getTweetIds().size() == 0) {
            cli.print("No tweets to be shown.");
            return;
        }
        TweetShowPage tweetShowPage = new TweetShowPage(cli, modelBase, pageHandler, user, user.getTweets(modelBase));
        pageHandler.setCurrentPage(tweetShowPage);
    }

    @Override
    public void editProfilePage() {
        EditProfilePage editProfilePage = new EditProfilePage(cli, modelBase, pageHandler, user);
        pageHandler.setCurrentPage(editProfilePage);
    }

    @Override
    public void info() {
        cli.print("Firstname: " + user.getFirstName());
        cli.print("Familyname: " + user.getFamilyName());
        cli.print("Username: " + user.getUsername());
        cli.print("Email: " + user.getEmail());
        cli.print("Phonenumber: " + user.getPhonenumber());
        cli.print("Birthday: " + user.getBirthday());
        cli.print("Bio: " + user.getBio());
    }

    @Override
    public void handleFollowRequests() {
        cli.print(
                "You can:\n" +
                "1)accept\n" +
                "2)reject\n" +
                "3)rejectAndNotify\n" +
                "following requests.(Type one of above options.)");
        for (Integer id : user.getFollowRequestIds()) {
            cli.print(modelBase.getUserById(id).getUsername() + " wants to follow you.");
            while (true) {
                boolean b = false;
                String answer = cli.inputString();
                if (answer.equals("accept")) {
                    user.addFollower(id);
                    modelBase.getUserById(id).addFollowing(user.getId());
                    user.addSystemMessage(modelBase.getUserById(id).getUsername() + " started to follow you.");
                    modelBase.getUserById(id).removeFromPendingIds(user.getId());
                    modelBase.getUserById(id).addAcceptedId(user.getId());
                    modelBase.getUserById(id).addSystemMessage("You got accepted by " + user.getUsername());
                    logger.info(modelBase.getUserById(id).getUsername() + " Got accepted by " + user.getUsername());
                    b = true;
                }
                if (answer.equals("reject")) {
                    modelBase.getUserById(id).removeFromPendingIds(user.getId());
                    modelBase.getUserById(id).addRejectedId(user.getId());
                    logger.info(modelBase.getUserById(id).getUsername() + " Got rejected by " + user.getUsername());
                    b = true;
                }
                if (answer.equals("rejectAndNotify")) {
                    modelBase.getUserById(id).removeFromPendingIds(user.getId());
                    modelBase.getUserById(id).addRejectedId(user.getId());
                    modelBase.getUserById(id).addSystemMessage("You got rejected by " + user.getUsername());
                    logger.info(modelBase.getUserById(id).getUsername() + " Got rejected by " + user.getUsername());
                    b = true;
                }
                if (b) {
                    break;
                } else {
                    cli.print("Please enter your response correctly.");
                }
            }
        }
        user.getFollowRequestIds().removeAll(user.getFollowRequestIds());
    }

    @Override
    public void myFollowRequests() {
        cli.print("Your follow requests state:\n");
        cli.print("Accepted:\n");
        for (Integer id : user.getAcceptedIds()) {
            cli.print(modelBase.getUserById(id).getUsername());
        }
        cli.print("Rejected:\n");
        for (Integer id : user.getRejectedIds()) {
            cli.print(modelBase.getUserById(id).getUsername());
        }
        cli.print("Pending:\n");
        for (Integer id : user.getPendingIds()) {
            cli.print(modelBase.getUserById(id).getUsername());
        }
        user.getRejectedIds().removeAll(user.getRejectedIds());
        user.getAcceptedIds().removeAll(user.getAcceptedIds());
    }

    @Override
    public void seeFollowers() {
        cli.print("Followers:");
        for (Integer id : user.getFollowerIds()) {
            if (modelBase.getUserById(id).isActive()) {
                cli.print(modelBase.getUserById(id).getUsername());
            }
        }
        cli.print("Do you want to see any profile(type yes or no):");
        while (true) {
            boolean b = false;
            String answer = cli.inputString();
            if (answer.equals("yes")) {
                cli.print("Type one of above users names to see their profile page.");
                String follower = cli.inputString();
                if (!user.ifInFollowers(modelBase.getUserByUsername(follower).getId()) || !modelBase.getUserByUsername(follower).isActive()) {
                    b = true;
                    cli.print(follower + " Is not from the list above ://");
                    break;
                }
                User profileOwner = modelBase.getUserByUsername(follower);
                SeeProfilePage seeProfilePage = new SeeProfilePage(cli, modelBase, pageHandler, user, profileOwner);
                pageHandler.setCurrentPage(seeProfilePage);
                b = true;
            }
            if (answer.equals("no")) {
                cli.print("Ok you decided to eat kids, good luck");
                b = true;
            }
            if (b) {
                break;
            } else {
                cli.print("Type 'yes' or 'no' it is not that hard");
            }
        }
    }

    @Override
    public void seeFollowings() {
        cli.print("Followings:");
        for (Integer id : user.getFollowingIds()) {
            if (modelBase.getUserById(id).isActive()) {
                cli.print(modelBase.getUserById(id).getUsername());
            }
        }
        cli.print("Do you want to see any profile(type yes or no):");
        while (true) {
            boolean b = false;
            String answer = cli.inputString();
            if (answer.equals("yes")) {
                cli.print("Type one of above users names to see their profile page.");
                String following = cli.inputString();
                if (!user.ifInFollowings(modelBase.getUserByUsername(following).getId()) || !modelBase.getUserByUsername(following).isActive()) {
                    b = true;
                    cli.print(following + " Is not from the list above :/");
                    break;
                }
                User profileOwner = modelBase.getUserByUsername(following);
                SeeProfilePage seeProfilePage = new SeeProfilePage(cli, modelBase, pageHandler, user, profileOwner);
                pageHandler.setCurrentPage(seeProfilePage);
                b = true;
            }
            if (answer.equals("no")) {
                cli.print("Ok you decided to eat kids, good luck");
                b = true;
            }
            if (b) {
                break;
            } else {
                cli.print("Type 'yes' or 'no' it is not that hard");
            }
        }
    }

    @Override
    public void seeBlacklist() {
        cli.print("Blacklist:");
        for (Integer id : user.getBlacklistIds()) {
            if (modelBase.getUserById(id).isActive()) {
                cli.print(modelBase.getUserById(id).getUsername());
            }
        }
        cli.print("Do you want to unblock any of the above users?(type yes or no.)");
        while (true) {
            String answer = cli.inputString();
            boolean b = false;
            if (answer.equals("yes")) {
                cli.print("Type one of the above users names to unblock them");
                String blocked = cli.inputString();
                if (!user.ifInBlacklist(modelBase.getUserByUsername(blocked).getId()) || !modelBase.getUserByUsername(blocked).isActive()) {
                    cli.print(blocked + "Is not from the list above :/");
                    b = true;
                    break;
                }
                User blockedUser = modelBase.getUserByUsername(blocked);
                user.removeFromBlacklist(blockedUser.getId());
                cli.print(blocked + " has been unblocked successfully.");
                b = true;
            }
            if (answer.equals("no")) {
                cli.print("Ok you decided to eat kids, Do it with a silent voice.");
                b = true;
            }
            if (b) {
                break;
            } else {
                cli.print("Oh come on how could you misspell yes or no, just try again...");
            }
        }
    }

    @Override
    public void seeSystemMessages() {
        cli.print("System messages:");
        int counter = 1;
        for (String content : user.getSystemMessages()) {
            cli.print(counter + ")" + content);
            counter++;
        }
        user.getSystemMessages().clear();
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
