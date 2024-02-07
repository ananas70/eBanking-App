package org.poo.cb;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BankDatabase {
    private static BankDatabase uniqueInstance; //Singleton instance
    public static ArrayList<User> usersArray;
    public static Map<String, Map<String, Double>> exchangeRates;
    public static Map<String, Map<ZonedDateTime, Double>> stockValues;
    public static ArrayList<String> recommendedStocks;
    private BankDatabase(){}
    public static BankDatabase instance() {
        if(uniqueInstance == null) {
            exchangeRates = new HashMap<>();
            usersArray = new ArrayList<>();
            uniqueInstance = new BankDatabase();
        }
        return uniqueInstance;
    }
    public User findUserByEmail(String email) {
        for(User user : usersArray)
            if(user.getEmail().equals(email))
                return user;
        return null;
    }

    public void recommendStocks() {
        recommendedStocks = new ArrayList<>();
        System.out.print("{\"stocksToBuy\":[");

        int shortTerm = 4; // Short-term SMA period
        int longTerm = 9; // Long-term SMA period
        boolean isFirst = true;
        for (String stock : stockValues.keySet()) {
            Map<ZonedDateTime, Double> stockData = stockValues.get(stock);
            double shortTermSMA = calculateSMA(stockData, shortTerm);
            double longTermSMA = calculateSMA(stockData, longTerm);

            if (shortTermSMA > longTermSMA) {
                if(!isFirst)
                    System.out.print(",");
                System.out.print("\"" + stock+"\"");
                recommendedStocks.add(stock);
                isFirst = false;
            }
        }
        System.out.println("]}");
    }

    public static boolean isRecommendedStock(String stockName) {
        for(String stock : recommendedStocks)
            if(stock.equals(stockName))
                return true;
        return false;
    }
    private static double calculateSMA(Map<ZonedDateTime, Double> stockData, int period) {
        double sum = 0;
        int count = 0;
        for (ZonedDateTime date : stockData.keySet()) {
            if (count >= period) {
                break;
            }
            sum += stockData.get(date);
            count++;
        }
        return sum / period;
    }

    public void cleanup() {
        usersArray.clear();
    }


}
