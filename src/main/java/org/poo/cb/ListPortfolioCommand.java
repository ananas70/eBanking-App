package org.poo.cb;

public class ListPortfolioCommand implements UserCommand{

    User user;
    public ListPortfolioCommand (User user) {
        this.user = user;
    }
    @Override
    public void execute() {
        user.listPortfolio();
    }
}
