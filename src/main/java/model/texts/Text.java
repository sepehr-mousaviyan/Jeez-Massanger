package model.texts;

import logic.IdGenerator;
import model.ModelBase;
import model.User;

public class Text {
    protected String content;
    protected int authorId;
    protected int id;

    public Text(String content, int authorId) {
        this.content = content;
        this.authorId = authorId;
        id = IdGenerator.generate();
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public User getAuthor(ModelBase modelBase) {
        return modelBase.getUserById(authorId);
    }

    public String show(ModelBase modelBase) {
        return getAuthor(modelBase).getUsername() + ":\n" + content;
    }

}
