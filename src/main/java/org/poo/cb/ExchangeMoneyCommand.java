package org.poo.cb;

public class ExchangeMoneyCommand implements UserCommand {

    User user;
    String sourceCurrency, destinationCurrency;
    Double value;

    //formula
    //sourceDepo = sourceDepo - value * coefficient
    //destDepo = destDepo + value;
    public ExchangeMoneyCommand(User user, String sourceCurrency, String destinationCurrency, Double value) {
        this.user = user;
        this.sourceCurrency = sourceCurrency;
        this.destinationCurrency = destinationCurrency;
        this.value = value;
    }

    @Override
    public void execute() {
        BankAccount sourceAccount = user.findAccountByCurrency(sourceCurrency);
        BankAccount destAccount = user.findAccountByCurrency(destinationCurrency);
        Double rate = BankDatabase.exchangeRates.get(destinationCurrency).get(sourceCurrency);
        user.exchangeMoney(sourceAccount,destAccount,value,rate);
    }
}
