package org.poo.cb;


import java.util.ArrayList;

public class User implements UserDecorator{
    //RECEIVER
    private Name name;
    private String email, address;
    private ArrayList<User> friends;
    private ArrayList<BankAccount> accounts;
    private ArrayList<Stock> stocks;
    private TransferFeeStrategy feeStrategy;
    private boolean premium;

    public User(){}
    public User(Name name, String email, String address) {
        this.friends = new ArrayList<>();
        this.name= name;
        this.email = email;
        this.address = address;
        this.premium = false;
        this.feeStrategy = new NormalFeeStrategy();
    }
    public boolean isPremium() {
        return premium;
    }
    public double calculateFee(double amount) {
        //  strategy pattern
        return feeStrategy.calculateFee(amount);
    }
    public Name getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    private boolean isDuplicatedEmail(String email){
        for(User user : BankDatabase.usersArray)
            if(user.email.equals(email))
                return true;
        return false;
    }
    public void createUser(String userData) {
        //john.doe@email.com John Doe 1401 Stoltenberg Islands Geralynstad SD 36945
        String[] credentials = userData.split(" ");
        Name name = new Name(credentials[1], credentials[2]);
        String userAddress = "";
        for (int i = 3; i < credentials.length; i++) {
            userAddress = userAddress + credentials[i];
            userAddress = userAddress + " ";
        }
        userAddress = userAddress.trim();
        if (!isDuplicatedEmail(credentials[0])) {
            User newUser = new User(name, credentials[0], userAddress);
            newUser.premium = false;
            newUser.friends = new ArrayList<>();
            newUser.accounts = new ArrayList<>();
            newUser.stocks = new ArrayList<>();
            BankDatabase.usersArray.add(newUser);
        }
        else
            System.out.println("User with "+ credentials[0]+" already exists");
    }
    public void listUser(User user) {
        System.out.print("{\"email\":");
        System.out.print("\"" + user.getEmail() + "\"");
        System.out.print(",\"firstname\":\"");
        System.out.print(user.getName().firstName);
        System.out.print("\",\"lastname\":\"");
        System.out.print(user.getName().lastName);
        System.out.print("\",\"address\":\"");
        System.out.print(" " + user.getAddress());
        System.out.print("\",\"friends\":[");
        if(!user.friends.isEmpty()) {
            boolean isFirst = true;
            for (User friend : user.friends) {
                if (!isFirst) {
                    System.out.print(",");
                }
                System.out.print("\"" + friend.email + "\"");
                isFirst = false;
            }
        }
        System.out.println("]}");
    }
    public ArrayList<User> getFriends() {
        return friends;
    }
    public void addFriend(User friend) {
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.friends.add(this);
        }
        else
            System.out.println("User with "+ friend.email +" is already a friend");
    }
    private boolean isDuplicatedAccount(String currency) {
        for(BankAccount account : this.accounts)
            if(account.getCurrency().equals(currency))
                return true;
        return false;
    }
    public void addAccount (String currency) {
        if(this.isDuplicatedAccount(currency)) {
            System.out.println("Account in currency " + currency + " already exists for user");
            return;
        }
        this.accounts.add(new BankAccount(currency,0.0));
    }
    public BankAccount findAccountByCurrency(String currency) {
        for(BankAccount account : this.accounts) {
            if(account.getCurrency().equals(currency))
                return account;
        }
        return null;
    }
    public void addMoney(String currency, Double value) {
        BankAccount bankAccount = this.findAccountByCurrency(currency);
        bankAccount.addValue(value);
    }
    public void exchangeMoney(BankAccount source, BankAccount dest, Double value, Double rate) {
        double fee = 0;
        if(value * rate > source.getValue()/2)
            fee = feeStrategy.calculateFee(value * rate);
        if(value * rate > source.getValue())
            System.out.println("Insufficient amount in account "+ source.getCurrency() +" for exchange");
        source.subtractValue(value * rate + fee);
        dest.addValue(value);
    }
    public void listPortfolio(){
        //{"stocks":[{"stockName":"TSLA","amount":10}],"accounts":[{"currencyName":"USD","amount":"1000.00"},{"currencyName":"EUR","amount":"0.00"}]}
        System.out.print("{\"stocks\":[");
        boolean isFirst = true;
        for (Stock stock: this.stocks) {
            if(!isFirst)
                System.out.print(",");
            System.out.print("{\"stockName\":\""+stock.getName() +"\",\"amount\":"+ stock.getAmount() +"}");
            isFirst = false;
        }
        System.out.print("],\"accounts\":[");
        isFirst = true;
        for(BankAccount account : this.accounts) {
            if(!isFirst)
                System.out.print(",");
            String formattedValue = String.format("%.2f", account.getValue());
            System.out.print("{\"currencyName\":\"" + account.getCurrency() + "\",\"amount\":\"" + formattedValue + "\"}");
            isFirst = false;
        }
        System.out.println("]}");
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
        if (premium) {
            this.feeStrategy = new PremiumFeeStrategy(); // Switch to premium fee strategy
        } else {
            this.feeStrategy = new NormalFeeStrategy(); // Switch to normal fee strategy
        }
    }
    public void transferMoney(User receiver, String currency, Double value) {
        BankAccount sourceAccount = this.findAccountByCurrency(currency);
        BankAccount destAccount = receiver.findAccountByCurrency(currency);
        if(!this.isFriend(receiver)){
            System.out.println("You are not allowed to transfer money to " + receiver.getEmail());
            return;
        }
        if(value > sourceAccount.getValue()) {
            System.out.println("Insufficient amount in account " + currency + " for transfer\n");
            return;
        }
        sourceAccount.subtractValue(value);
        destAccount.addValue(value);
    }

    @Override
    public void buyPremiumOption(User user) {
        new PremiumUserDecorator(this).buyPremiumOption(user);
        user.premium = true;
        user.feeStrategy = new PremiumFeeStrategy();
    }

    @Override
    public boolean isPremiumUser(String email) {
        return premium;
    }
    public boolean isFriend(User user) {
        return friends.contains(user);
    }

    public void buyStock(String stockName, int amount, Double stockPrice) {
        BankAccount account = this.findAccountByCurrency("USD");
        Double buyPrice = amount * stockPrice;
        if(isPremium())
            if (BankDatabase.isRecommendedStock(stockName))
                buyPrice = buyPrice - 0.05 * buyPrice;  //Premium

        if(account.getValue() < buyPrice) {
            System.out.println("Insufficient amount in account for buying stock");
            return;
        }
        account.subtractValue(buyPrice);
        Stock stock = new Stock(stockName,amount);
        this.stocks.add(stock);
    }

}
