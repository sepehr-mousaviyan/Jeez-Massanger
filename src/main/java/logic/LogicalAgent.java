package logic;

import com.google.gson.Gson;
import logic.page.LoginPage;
import logic.page.Page;
import model.ModelBase;
import model.texts.Tweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;

public class LogicalAgent {
    Cli cli;
    ModelBase modelBase;
    PageHandler pageHandler;
    private static final Logger logger = LogManager.getLogger(LogicalAgent.class);

    public LogicalAgent() {
        this.cli = new Cli();
        modelBase = loadModelBase();
        pageHandler = new PageHandler();
        LoginPage loginPage = new LoginPage(cli, modelBase, pageHandler);
        pageHandler.setCurrentPage(loginPage);
    }

    public ModelBase loadModelBase() {
        String path = "src/main/resources/ModelBase";
        path = path + "/modelBase.json";
        File file = new File(path);
        if (!file.exists()) {
            logger.info("ModelBase loaded.");
            return new ModelBase();
        }
        else {
            try {
                FileReader fileReader = new FileReader(file);
                Gson gson = new Gson();
                ModelBase modelBase1 = gson.fromJson(fileReader, ModelBase.class);
                for (Tweet tweet : modelBase1.getTweets()) {
                    modelBase1.addText(tweet);
                }
                fileReader.close();
                logger.info("ModelBase loaded.");
                return modelBase1;
            }
            catch (Exception e) {
                logger.fatal("Problem with loading modelBase.", e);
            }
        }
        return new ModelBase();
    }

    public void run() {
        cli.print("Enter your command:");
        String command = cli.inputString();
        Page page = pageHandler.getCurrentPage();
        if (command.equals("back")) {
            pageHandler.back();
        }
        if (command.equals("help")) {
            page.help();
        }
        if (command.equals("signUp")) {
            page.signUp();
        }
        if (command.equals("signIn")) {
            page.signIn();
        }
        if (command.equals("personalPage")) {
            page.personalPage();
        }
        if (command.equals("timelinePage")) {
            page.timelinePage();
        }
        if (command.equals("explorerPage")) {
            page.explorerPage();
        }
        if (command.equals("chatPage")) {
            page.chatPage();
        }
        if (command.equals("settingPage")) {
            page.settingPage();
        }
        if (command.equals("makeTweet")) {
            page.makeTweet();
        }
        if (command.equals("showMyTweets")) {
            page.showMyTweets();
        }
        if (command.equals("editProfilePage")) {
            page.editProfilePage();
        }
        if (command.equals("info")) {
            page.info();
        }
        if (command.equals("handleFollowRequests")) {
            page.handleFollowRequests();
        }
        if (command.equals("myFollowRequests")) {
            page.myFollowRequests();
        }
        if (command.equals("seeFollowers")) {
            page.seeFollowers();
        }
        if (command.equals("seeFollowings")) {
            page.seeFollowings();
        }
        if (command.equals("seeBlacklist")) {
            page.seeBlacklist();
        }
        if (command.equals("seeSystemMessages")) {
            page.seeSystemMessages();
        }
        if (command.equals("firstname")) {
            page.firstname();
        }
        if (command.equals("familyname")) {
            page.familyname();
        }
        if (command.equals("username")) {
            page.username();
        }
        if (command.equals("bio")) {
            page.bio();
        }
        if (command.equals("email")) {
            page.email();
        }
        if (command.equals("phonenumber")) {
            page.phonenumber();
        }
        if (command.equals("birthday")) {
            page.birthday();
        }
        if (command.equals("seeProfile")) {
            page.seeProfile();
        }
        if (command.equals("follow")) {
            page.follow();
        }
        if (command.equals("unfollow")) {
            page.unfollow();
        }
        if (command.equals("block")) {
            page.block();
        }
        if (command.equals("unblock")) {
            page.unblock();
        }
        if (command.equals("report")) {
            page.report();
        }
        if (command.equals("tweetContent")) {
            page.tweetContent();
        }
        if (command.equals("next")) {
            page.next();
        }
        if (command.equals("prev")) {
            page.prev();
        }
        if (command.equals("addToSaveMessage")) {
            page.addToSaveMessage();
        }
        if (command.equals("reTweet")) {
            page.reTweet();
        }
        if (command.equals("mute")) {
            page.mute();
        }
        if (command.equals("seeAuthorProfile")) {
            page.seeAuthorProfile();
        }
        if (command.equals("comment")) {
            page.comment();
        }
        if (command.equals("like")) {
            page.like();
        }
        if (command.equals("seeComments")) {
            page.seeComments();
        }
        if (command.equals("seeTimelineTweets")) {
            page.seeTimelineTweets();
        }
        if (command.equals("findUser")) {
            page.findUser();
        }
        if (command.equals("seeTweets")) {
            page.seeTweets();
        }
        if (command.equals("seeSavedMessage")) {
            page.seeSavedMessage();
        }
        if (command.equals("setPrivacy")) {
            page.setPrivacy();
        }
        if (command.equals("setLastSeen")) {
            page.setLastSeen();
        }
        if (command.equals("setActivity")) {
            page.setActivity();
        }
        if (command.equals("logout")) {
            page.logout();
        }
        if (command.equals("delete")) {
            page.delete();
        }
        if (command.equals("exit")) {
            page.exit();
        }
        modelBase.save();
        this.run();
    }

}
