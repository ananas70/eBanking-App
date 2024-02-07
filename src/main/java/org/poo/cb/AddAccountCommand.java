package org.poo.cb;

public class AddAccountCommand implements UserCommand{

    User user;
    String currency;
    public AddAccountCommand (User user, String currency) {
        this.currency = currency;
        this.user = user;
    }
    @Override
    public void execute() {
        user.addAccount(currency);
    }
}
