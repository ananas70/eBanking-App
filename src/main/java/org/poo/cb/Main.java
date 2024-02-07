package org.poo.cb;

import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.ZonedDateTime;

public class Main {
    public static void main(String[] args) {
        //Metoda Main va primi ca parametri 3 fișiere
        //  exchangeRates.csv    stockValues.csv    comenzi.txt
        //      args[0]                 args[1]         args[2]
        if (args == null) {
            System.out.println("Running Main");
        }

        BankDatabase bankDatabase = BankDatabase.instance();
        bankDatabase.cleanup();

        if (args != null) {
            //processing Exchange Rates
            processExchangeRates("src\\main\\resources\\" + args[0]);

            //processing Stock Values
            processStockValues("src\\main\\resources\\" + args[1]);

            //processing Commands
            processCommands(bankDatabase,"src\\main\\resources\\" + args[2]);

        }
    }

    private static void processExchangeRates(String filePath) {
        //processing exchangeRates.csv
        CsvParser<String> stringCsvParser = new ExchangeRatesParser();
        try {
            BankDatabase.exchangeRates = stringCsvParser.parseCsv(filePath);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    private static void processStockValues(String filePath) {
        //processing stockValues.csv
        CsvParser<ZonedDateTime> stringCsvParser = new StockValuesParser();
        try {
            BankDatabase.stockValues = stringCsvParser.parseCsv(filePath);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private static void processCommands(BankDatabase bankDatabase, String filePath) {
        //processing commands.txt
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line (command)
                String command = getCommand(line);
                UserCommandExecutor userExecutor = new UserCommandExecutor();
                int endIndex = command.length();
                User user;
                Double value;
                switch (command) {
                    case "CREATE USER":
                        userExecutor.executeCommand(new CreateUserCommand(line.substring(endIndex + 1)));
                        break;

                    case "LIST USER":
                        User foundUser = bankDatabase.findUserByEmail(line.substring(endIndex + 1));
                        if(foundUser == null)
                            System.out.println("User with email "+line.substring(endIndex + 1) +" doesn’t exist");
                        else
                            userExecutor.executeCommand(new ListUserCommand(foundUser,bankDatabase));
                        break;

                    case "ADD FRIEND":
                        String usersEmails[] = (line.substring(endIndex + 1)).split(" ");
                        User invoker, client;
                        invoker = bankDatabase.findUserByEmail(usersEmails[0]);
                        client = bankDatabase.findUserByEmail(usersEmails[1]);
                        if(invoker == null || client == null) {
                            if(invoker == null)
                                System.out.println("User with "+usersEmails[0]+" doesn’t exist");
                            else
                                System.out.println("User with "+usersEmails[1]+" doesn’t exist");
                        }
                        else
                            userExecutor.executeCommand(new AddFriendCommand(invoker,client));
                        break;

                    case "ADD ACCOUNT":
                        String[] accountData = line.substring(endIndex + 1).split(" ");
                        user = bankDatabase.findUserByEmail(accountData[0]);
                        userExecutor.executeCommand(new AddAccountCommand(user,accountData[1]));
                        break;

                    case "ADD MONEY":
                        String[] moneyData = line.substring(endIndex + 1).split(" ");
                        user = bankDatabase.findUserByEmail(moneyData[0]);
                        value = Double.parseDouble(moneyData[2]);
                        userExecutor.executeCommand(new AddMoneyCommand(user,moneyData[1], value));
                        break;

                    case "LIST PORTFOLIO":
                        user = bankDatabase.findUserByEmail(line.substring(endIndex + 1));
                        userExecutor.executeCommand(new ListPortfolioCommand(user));
                        break;

                    case "EXCHANGE MONEY":
                        //john.doe@email.com USD EUR 100
                        String[] exchangeData = line.substring(endIndex + 1).split(" ");
                        user = bankDatabase.findUserByEmail(exchangeData[0]);
                        value = Double.parseDouble(exchangeData[3]);
                        userExecutor.executeCommand(new ExchangeMoneyCommand(user,exchangeData[1],exchangeData[2],value));
                        break;

                    case "TRANSFER MONEY":
                        //TRANSFER MONEY john.doe@email.com marry.ville@email.com EUR 500
                        String[] transferData = line.substring(endIndex + 1).split(" ");
                        user = bankDatabase.findUserByEmail(transferData[0]);
                        User receiver = bankDatabase.findUserByEmail(transferData[1]);
                        value = Double.parseDouble(transferData[3]);
                        userExecutor.executeCommand(new TransferMoneyCommand(user,receiver,transferData[2],value));
                        break;

                    case "BUY STOCKS":
                        //BUY STOCKS john.doe@email.com TSLA 10
                        String[] stockData = line.substring(endIndex + 1).split(" ");
                        user = bankDatabase.findUserByEmail(stockData[0]);
                        int amount = Integer.parseInt(stockData[2]);
                        userExecutor.executeCommand(new BuyStockCommand(user,stockData[1],amount));
                        break;

                    case "RECOMMEND STOCKS":
                        bankDatabase.recommendStocks();
                        break;

                    case "BUY PREMIUM":
                        user = bankDatabase.findUserByEmail(line.substring(endIndex + 1));
                        if(user == null) {
                            System.out.println("User with email " + line.substring(endIndex + 1) + " doesn’t exist");
                            return;
                        }
                        if(user.isPremium())
                            System.out.println("User is already premium");
                        else
                            user.buyPremiumOption(user);
                        break;

                    default:
                        System.out.print("Unknown command.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCommand(String line) {
        int firstSpaceIndex = line.indexOf(' ');
        int secondSpaceIndex = line.indexOf(' ', firstSpaceIndex + 1);
        if(secondSpaceIndex != -1)
            return line.substring(0, secondSpaceIndex);
        else
            return line; //for RECOMMEND STOCKS
    }
}