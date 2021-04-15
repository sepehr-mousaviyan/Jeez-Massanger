package model;

import com.google.gson.Gson;
import model.texts.Text;
import model.texts.Tweet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.LinkedList;

public class ModelBase {
    private LinkedList<User> users;
    private LinkedList<Tweet> tweets;
    transient private LinkedList<Text> texts;
    private static final Logger logger = LogManager.getLogger(ModelBase.class);

    public ModelBase() {
        users = new LinkedList<>();
        tweets = new LinkedList<>();
        texts = new LinkedList<>();
    }

    public void setUsers(LinkedList<User> users) {
        this.users = users;
    }

    public LinkedList<User> getUsers() {
        return users;
    }

    public void setTweets(LinkedList<Tweet> tweets) {
        this.tweets = tweets;
    }

    public LinkedList<Tweet> getTweets() {
        return tweets;
    }

    public void setTexts(LinkedList<Text> texts) {
        this.texts = texts;
    }

    public LinkedList<Text> getTexts() {
        return texts;
    }

    public void addTweet(Tweet tweet) {
        tweets.add(tweet);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addText(Text text) {
        texts.add(text);
    }

    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean ifUserNameExists(String nickname) {
        for (User user : users) {
            if (user.getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifEmailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifPhonenumberExists(String phonenumber) {
        for (User user : users) {
            if (user.getPhonenumber().equals(phonenumber)) {
                return true;
            }
        }
        return false;
    }

    public Tweet getTweetById(int id) {
        for (Tweet tweet : tweets) {
            if (tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }

    public Text getTextById(int id) {
        for (Text text : texts) {
            if (text.getId() == id) {
                return text;
            }
        }
        return null;
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void removeTweet(Tweet tweet) {
        tweets.remove(tweet);
    }

    public void removeText(Text text) {
        texts.remove(text);
    }

    public void save() {
        String path = "src/main/resources/ModelBase";
        path = path + "/modelBase.json";
        File file = new File(path);
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                logger.fatal("Problem in creating file.", e);
            }
        }
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(file, false));
            printStream.flush();
            printStream.close();
        }
        catch (Exception e) {
            logger.fatal("Problem in writing in file.", e);
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new Gson();
            gson.toJson(this, fileWriter);
            fileWriter.flush();
            fileWriter.close();
            logger.info("ModelBase saved successfully.");
        }
        catch (Exception e) {
            logger.fatal("Problem in saving modelBase", e);
        }
    }

}
