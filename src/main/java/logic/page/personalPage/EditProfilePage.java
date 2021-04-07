package logic.page.personalPage;

import logic.Cli;
import logic.PageHandler;
import logic.page.Page;
import model.ModelBase;
import model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class EditProfilePage extends Page {

    private User user;

    public EditProfilePage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print(
                "You can edit your profile in this page\n" +
                "Type one of the items below to edit it\n" +
                "1)firstname\n" +
                "2)familyname\n" +
                "3)username\n" +
                "4)bio\n" +
                "5)email\n" +
                "6)phonenumber\n" +
                "7)birthday\n" +
                "8)back\n" +
                "9)exit.");
    }

    @Override
    public void firstname() {
        cli.print("Enter your firstname:");
        String firstName = cli.inputString();
        user.setFirstName(firstName);
        cli.print("Successfully edited.");
    }

    @Override
    public void familyname() {
        cli.print("Enter your familytname:");
        String familyName = cli.inputString();
        user.setFamilyName(familyName);
        cli.print("Successfully edited.");
    }

    @Override
    public void bio() {
        cli.print("Enter your bio(type none if you don't want to have bio):");
        String bio = cli.inputString();
        user.setBio(bio);
        cli.print("Successfully edited.");
    }

    @Override
    public void username() {
        cli.print("Pick your username:");
        String userName = "";
        while (true) {
            userName = cli.inputString();
            if (!modelBase.ifUserNameExists(userName) || user.getUsername().equals(userName)) {
                break;
            }
            else {
                cli.print("username already exists please pick another one:");
            }
        }
        user.setUsername(userName);
        cli.print("Successfully edited.");
    }

    @Override
    public void email() {
        String email = "";
        cli.print("Enter your email:");
        while (true) {
            email = cli.inputString();
            if (!modelBase.ifEmailExists(email) || user.getEmail().equals(email)) {
                break;
            }
            else {
                cli.print("Email already exists please enter another one:");
            }
        }
        user.setEmail(email);
        cli.print("Successfully edited.");
    }

    @Override
    public void phonenumber() {
        cli.print("Enter your phonenumber:");
        String phonenumber = cli.inputString();
        if (!phonenumber.equals("none") && !(phonenumber.length() == 0)) {
            while (modelBase.ifPhonenumberExists(phonenumber)) {
                if (user.getPhonenumber().equals(phonenumber)) {
                    break;
                }
                cli.print("Phonenumber already exists please enter another one:");
                phonenumber = cli.inputString();
            }
        }
        user.setPhonenumber(phonenumber);
        cli.print("Successfully edited.");
    }

    @Override
    public void birthday() {
        cli.print("Enter your Birthday(optional: type none or your birthday):");
        String birthday = cli.inputString();
        user.setBirthday(birthday);
        cli.print("Successfully edited.");
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
