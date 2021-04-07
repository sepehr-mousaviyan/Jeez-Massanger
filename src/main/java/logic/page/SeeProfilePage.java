package logic.page;

import logic.Cli;
import logic.PageHandler;
import model.ModelBase;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeeProfilePage extends Page {
    private User profileWatcher;
    private User profileOwner;
    private static final Logger logger = LogManager.getLogger(SeeProfilePage.class);

    public SeeProfilePage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User profileWatcher, User profileOwner) {
        super(cli, modelBase, pageHandler);
        this.profileWatcher = profileWatcher;
        this.profileOwner = profileOwner;
    }

    @Override
    public void help() {
        cli.print("This is " + profileOwner.getUsername() + "'s profile page.");
        cli.print(
                "Type commands name to use them.\n" +
                "Commands to be used:\n" +
                "1)seeProfile\n" +
                "2)follow\n" +
                "3)unfollow\n" +
                "4)block\n" +
                "5)unblock\n" +
                "6)report\n" +
                "7)help\n" +
                "8)back\n" +
                "9)exit\n");
    }

    @Override
    public void seeProfile() {
        cli.print("Firstname: " + profileOwner.getFirstName());
        cli.print("Familyname: " + profileOwner.getFamilyName());
        cli.print("Nickname: " + profileOwner.getUsername());
        cli.print(profileOwner.showLastSeen(profileWatcher.getId()));
        if (profileOwner.ifInFollowers(profileWatcher.getId())) {
            cli.print("You've already followed " + profileOwner.getUsername() + ".");
        }

        else {
            cli.print("You have not followed " + profileOwner.getUsername() + " yet.");
        }
    }

    @Override
    public void follow() {
        if (profileOwner.ifInFollowers(profileWatcher.getId())) {
            cli.print("You've already followed " + profileOwner.getUsername());
            return;
        }
        if (!profileOwner.isPrivate()) {
            profileOwner.addFollower(profileWatcher.getId());
            profileWatcher.addFollowing(profileOwner.getId());
            cli.print("You followed " + profileOwner.getUsername() + " successfully.");
            profileOwner.addSystemMessage(profileWatcher.getUsername() + " started to follow you.");
            logger.info(profileWatcher.getUsername() + " followed " + profileOwner.getUsername());
        } else {
            profileOwner.addFollowRequest(profileWatcher.getId());
            cli.print("Your follow request has been received successfully.");
            profileWatcher.addPendingId(profileOwner.getId());
            logger.info(profileWatcher.getUsername() + " requested to follow " + profileOwner.getUsername());
        }
    }

    @Override
    public void unfollow() {
        if (!profileOwner.ifInFollowers(profileWatcher.getId())) {
            cli.print("You are not following " + profileOwner.getUsername() + ".");
            return;
        }
        profileOwner.removeFromFollowers(profileWatcher.getId());
        profileWatcher.removeFromFollowings(profileOwner.getId());
        profileOwner.addSystemMessage(profileWatcher.getUsername() + " unfollowed you.");
        logger.info(profileWatcher.getUsername() + " unfollowed " + profileOwner.getUsername());
    }

    @Override
    public void block() {
        profileWatcher.addToBlacklist(profileOwner.getId());
        cli.print("You blocked " + profileOwner.getUsername() + " successfully.");
        logger.info(profileWatcher.getUsername() + " blocked " + profileOwner.getUsername());
    }

    @Override
    public void unblock() {
        if (!profileWatcher.ifInBlacklist(profileOwner.getId())) {
            cli.print("You have not blocked " + profileOwner.getUsername() + " yet.");
            return;
        }
        profileWatcher.removeFromBlacklist(profileOwner.getId());
        cli.print("You unblocked " + profileOwner.getUsername() + " successfully.");
        logger.info(profileWatcher.getUsername() + " unblocked " + profileOwner.getUsername());
    }

    @Override
    public void report() {
        //:D
        profileWatcher.addSystemMessage("Thank you for reporting " + profileOwner.getUsername() + ".");
        profileOwner.addSystemMessage("You got reported by " + profileWatcher.getUsername() + ".\nBe nice please or we will ban you!");
        cli.print("You reported " + profileOwner.getUsername() + " successfully.");
    }
}
