package org.poo.cb;

public class UserCommandExecutor {
    //INVOKER
    //knows how to execute a given command but doesn’t know how the command has been implemented
    // It only knows the command’s interface.

    public UserCommandExecutor () {}
    public void executeCommand(UserCommand userCommand) {
        userCommand.execute();
    }
}
