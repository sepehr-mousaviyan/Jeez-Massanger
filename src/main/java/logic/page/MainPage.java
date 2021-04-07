package logic.page;

import logic.Cli;
import logic.PageHandler;
import logic.page.personalPage.PersonalPage;
import model.ModelBase;
import model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPage extends Page {

    private User user;

    MainPage(Cli cli, ModelBase modelBase, PageHandler pageHandler, User user) {
        super(cli, modelBase, pageHandler);
        this.user = user;
    }

    @Override
    public void help() {
        cli.print("Hi " + user.getUsername() + "!\n" +
                "This is main page, enter pages names to enter them:\n" +
                "1)personalPage\n" +
                "2)timelinePage\n" +
                "3)explorerPage\n" +
                "4)settingPage\n" +
                "5)help\n" +
                "6)exit\n");
    }

    @Override
    public void personalPage() {
        PersonalPage personalPage = new PersonalPage(cli, modelBase, pageHandler, user);
        pageHandler.setCurrentPage(personalPage);
    }

    @Override
    public void timelinePage() {
        TimelinePage timelinePage = new TimelinePage(cli, modelBase, pageHandler, user);
        pageHandler.setCurrentPage(timelinePage);
    }

    @Override
    public void explorerPage() {
        ExplorerPage explorerPage = new ExplorerPage(cli, modelBase, pageHandler, user);
        pageHandler.setCurrentPage(explorerPage);
    }

    @Override
    public void settingPage() {
        SettingPage settingPage = new SettingPage(cli, modelBase, pageHandler, user);
        pageHandler.setCurrentPage(settingPage);
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
