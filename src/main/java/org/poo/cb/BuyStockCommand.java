package org.poo.cb;

import java.time.ZonedDateTime;
import java.util.Map;

public class BuyStockCommand implements UserCommand{
    User user;
    String stockName;
    int amount;
    public BuyStockCommand (User user, String stockName, int amount) {
        this.user = user;
        this.stockName = stockName;
        this.amount = amount;

    }
    public static Double getMostRecentValueForStock(String stockName) {
        Map<ZonedDateTime, Double> stockData = BankDatabase.stockValues.get(stockName);
        ZonedDateTime mostRecentDate = null;
        for (ZonedDateTime date : stockData.keySet()) {
            if (mostRecentDate == null || date.isAfter(mostRecentDate)) {
                mostRecentDate = date;
            }
        }
            return stockData.get(mostRecentDate);
    }
    @Override
    public void execute() {
        Double stockPrice = getMostRecentValueForStock(stockName);
        user.buyStock(stockName,amount,stockPrice);
    }
}
