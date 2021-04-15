package logic.page;

import logic.Cli;
import logic.HashGenerator;
import logic.PageHandler;
import model.ModelBase;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginPage extends Page {

    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public LoginPage(Cli cli, ModelBase modelBase, PageHandler pageHandler) {
        super(cli, modelBase, pageHandler);
    }

    @Override
    public void help() {
        cli.print(
                "This is login page, enter commands names to use them.\n" +
                "Commands to be used:\n" +
                "1)signUp\n" +
                "2)signIn\n" +
                "3)help\n" +
                "4)exit\n");
    }

    @Override
    public void signUp() {
        cli.print("Enter your firstname:");
        String firstName = cli.inputString();
        cli.print("Enter your familyName:");
        String familyName = cli.inputString();
        cli.print("Pick your username:");
        String nickName = "";
        while (true) {
            nickName = cli.inputString();
            if (!modelBase.ifUserNameExists(nickName)) {
                break;
            }
            else {
                cli.print("username already exists please pick another one:");
            }
        }
        String email = "";
        cli.print("Enter your email:");
        while (true) {
            email = cli.inputString();
            if (!modelBase.ifEmailExists(email)) {
                break;
            }
            else {
                cli.print("Email already exists please enter another one:");
            }
        }
        cli.print("Enter your password:");
        String password = HashGenerator.generate(cli.inputString());
        cli.print("Enter your Birthday(optional: type none or your birthday):");
        String birthday = cli.inputString();
        cli.print("Enter your Phonenumber(optional: type none or your phonenumber):");
        String phonenumber = cli.inputString();
        if (!phonenumber.equals("none")) {
            while (modelBase.ifPhonenumberExists(phonenumber)) {
                cli.print("Phonenumber already exists please enter another one:");
                phonenumber = cli.inputString();
            }
        }
        cli.print("Enter your Bio(optional: type none or your biography):");
        String bio = cli.inputString();
        User user = new User(firstName, familyName, nickName, bio, password, birthday, email, phonenumber);
        logger.info(user.getUsername() + " signed up.");
        modelBase.addUser(user);
        cli.print("You registered successfully you can sign in now(type signIn if you want to)");
    }

    @Override
    public void signIn() {

        cli.print("Please enter your username:");
        String nickname = "";
        while (true) {
            nickname = cli.inputString();
            if (!modelBase.ifUserNameExists(nickname)) {
                cli.print("We can not find this username please enter another one:");
            }
            else {
                break;
            }
        }
        User user = modelBase.getUserByUsername(nickname);
        cli.print("Enter your password:");
        String password = "";
        while (true) {
            password = HashGenerator.generate(cli.inputString());
            if (password.equals(user.getPassword())) {
                break;
            }
            else {
                cli.print("Password does not match enter the right one:");
            }
        }
        cli.print("You signed in successfully.");
        user.setActive(true);
        MainPage mainPage = new MainPage(cli, modelBase, pageHandler, user);
        logger.info(user.getUsername() + " signed in.");
        pageHandler.setCurrentPage(mainPage);
    }

}
