package logic;

import logic.page.Page;

import java.util.LinkedList;

public class PageHandler {
    private Page currentPage;
    private LinkedList<Page> pages;

    public PageHandler() {
        pages = new LinkedList<>();
    }

    public void setCurrentPage(Page currentPage) {
        this.currentPage = currentPage;
        pages.add(currentPage);
        currentPage.help();
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public LinkedList<Page> getPages() {
        return pages;
    }

    public void back() {
        if (pages.size() <= 2) {
            currentPage.help();
            return;
        }
        Page page = pages.get(pages.size() - 2);
        pages.remove(pages.get(pages.size() - 1));
        this.currentPage = page;
        page.help();
    }

}
