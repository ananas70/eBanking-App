package org.poo.cb;

public class ListUserCommand implements UserCommand{
    BankDatabase bankDatabase;
    User user;
    public ListUserCommand (User user, BankDatabase bankDatabase) {
        this.user = user;
        this.bankDatabase = bankDatabase;
    }
    @Override
    public void execute() {
        new User().listUser(user);
    }
}
