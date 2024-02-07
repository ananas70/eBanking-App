package org.poo.cb;

public class AddFriendCommand implements UserCommand{
    User invoker, client;
    public AddFriendCommand(User invoker, User client) {
        this.invoker = invoker;
        this.client = client;
    }
    @Override
    public void execute() {
        invoker.addFriend(client);
    }
}
