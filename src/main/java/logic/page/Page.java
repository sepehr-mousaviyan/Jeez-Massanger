package logic.page;

import logic.Cli;
import logic.PageHandler;
import model.ModelBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Page {
    protected Cli cli;
    protected ModelBase modelBase;
    protected PageHandler pageHandler;
    private static final Logger logger = LogManager.getLogger(Page.class);

    public Page(Cli cli, ModelBase modelBase, PageHandler pageHandler) {
        this.cli = cli;
        this.modelBase = modelBase;
        this.pageHandler = pageHandler;
    }

    public void help() {
        cli.print("");
    }

    public void exit() {
        modelBase.save();
        cli.print("Program ended successfully.");
        logger.info("Program ended.");
        System.exit(0);
    }

    //login page start
    public void signUp() { cli.print("Command not found"); }

    public void signIn() { cli.print("Command not found"); }
    //login page end

    //main page start
    public void personalPage() {
        cli.print("Command not found");
    }

    public void timelinePage() {
        cli.print("Command not found");
    }

    public void explorerPage() {
        cli.print("Command not found");
    }

    public void chatPage() {
        cli.print("Command not found");
    }

    public void settingPage() {
        cli.print("Command not found");
    }
    //main page end

    //personal page start
    public void makeTweet() {
        cli.print("Command not found");
    }

    public void showMyTweets() {
        cli.print("Command not found");
    }

    public void editProfilePage() {
        cli.print("Command not found");
    }

    public void info() { cli.print("Command not found"); }

    public void handleFollowRequests() {
        cli.print("Command not found");
    }

    public void myFollowRequests() {
        cli.print("Command not found");
    }

    public void seeFollowers() {
        cli.print("Command not found");
    }

    public void seeFollowings() {
        cli.print("Command not found");
    }

    public void seeBlacklist() {
        cli.print("Command not found");
    }

    public void seeSystemMessages() {
        cli.print("Command not found");
    }
    //personalPage end

    //editProfilePage start
    public void firstname() {
        cli.print("Command not found");
    }

    public void familyname() {
        cli.print("Command not found");
    }

    public void username() { cli.print("Command not found"); }

    public void bio() {
        cli.print("Command not found");
    }

    public void email() {
        cli.print("Command not found");
    }

    public void phonenumber() {
        cli.print("Command not found");
    }

    public void birthday() {
        cli.print("Command not found");
    }
    //editProfilePage end

    //seeProfilePage start
    public void seeProfile() {
        cli.print("Command not found");
    }

    public void follow() {
        cli.print("Command not found");
    }

    public void unfollow() {
        cli.print("Command not found");
    }

    public void block() {
        cli.print("Command not found");
    }//Used in TweetShowPage also

    public void unblock() {
        cli.print("Command not found");
    }

    public void report() {
        cli.print("Command not found");
    }//Used in TweetShowPage also
    //SeeProfilePage end

    //TweetShowPage start
    public void tweetContent() {
        cli.print("Command not found");
    }

    public void next() {
        cli.print("Command not found");
    }

    public void prev() {
        cli.print("Command not found");
    }

    public void addToSaveMessage() {
        cli.print("Command not found");
    } //also used in ChatPage

    public void reTweet() {
        cli.print("Command not found");
    }

    public void mute() {
        cli.print("Command not found");
    }

    public void seeAuthorProfile() {
        cli.print("Command not found");
    }

    public void comment() {
        cli.print("Command not found");
    }

    public void like() {
        cli.print("Command not found");
    }

    public void seeComments() {
        cli.print("Command not found");
    }

    //TweetShowPage end

    //TimelinePage start
    public void seeTimelineTweets() {
        cli.print("Command not found");
    }
    //TimelinePage end

    //ExplorerPage start
    public void findUser() {
        cli.print("Command not found");
    }

    public void seeTweets() {
        cli.print("Command not found");
    }
    //ExplorerPage end

    //ChatPage start
    public void seeSavedMessage() {
        cli.print("Command not found");
    }

    public void seeChats() {
        cli.print("Command not found");
    }

    public void messageToAll() {
        cli.print("Command not found");
    }

    public void messageToGroups() {
        cli.print("Command not found.");
    }

    public void addGroup() {
        cli.print("Command not found");
    }

    public void manageGroup() {
        cli.print("Command not found");
    }
    //ChatPage end

    //ChatLogicPage start
    public void seePreviousMessages() {
        cli.print("Command not found");
    }

    public void chat() {
        cli.print("Command not found");
    }
    //ChatLogicPage end

    //GroupPage start
    public void removeGroup() {
        cli.print("Command not found");
    }

    public void addToGroup() {
        cli.print("Command not found");
    }

    public void removeFromGroup() {
        cli.print("Command not found");
    }
    //GroupPage end

    //SettingPage start
    public void setPrivacy() {
        cli.print("Command not found");
    }

    public void setLastSeen() {
        cli.print("Command not found");
    }

    public void setActivity() {
        cli.print("Command not found");
    }

    public void logout() {
        cli.print("Command not found");
    }

    public void delete() {
        cli.print("Command not found");
    }
    //SettingPage end
}
