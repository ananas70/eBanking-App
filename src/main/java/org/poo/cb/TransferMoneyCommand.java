package org.poo.cb;

public class TransferMoneyCommand implements UserCommand{
    User invoker, receiver;
    String currency;
    Double value;

    public TransferMoneyCommand(User invoker, User receiver, String currency, Double value) {
        this.invoker = invoker;
        this.receiver = receiver;
        this.currency = currency;
        this.value = value;
    }


    @Override
    public void execute() {

        invoker.transferMoney(receiver,currency, value);
    }
}
