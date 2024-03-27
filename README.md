# eBanking Application

**Object-Oriented Programming Project**

## Introduction

The purpose of this project is to contribute to the implementation of functionalities for a mobile eBanking application. Users will be able to create an account, deposit money into the account, transfer funds to other users, exchange currencies, buy stocks or cryptocurrencies. Additionally, the application will provide recommendations for stocks that can be advantageously purchased at that moment.

## Functionalities

### Entities

- **User**: Represents the user of the eBanking application, uniquely identified by an email address. Attributes include:
  - Email
  - First name
  - Last name
  - Address
  - User portfolio, which may contain:
    - Accounts in various currencies
    - Stocks
    - Friends

- **Account**:
  - Currency type
  - Amount

- **Stocks**:
  - Company name
  - List of values for the last 10 days

### Commands

#### User and Account Creation/Modification:

- **Create User**: 
CREATE USER <email> <firstname> <lastname> <address>

- **Add Friend**: 
ADD FRIEND <emailUser> <emailFriend>

- **Add Account**: 
ADD ACCOUNT <email> <currency>

- **Deposit Money**: 
ADD MONEY <email> <currency> <amount>

- **Exchange Currency**: 
EXCHANGE MONEY <email> <sourceCurrency> <destinationCurrency> <amount>

- **Transfer to Friend**: 
TRANSFER MONEY <email> <friendEmail> <currency> <amount>

- **Buy Stocks**: 
BUY STOCKS <email> <company> <noOfStocks>

#### Stock Purchase Recommendation:

- **Algorithm**: It will be based on a simplified version of the Simple Moving Averages (SMAs) Crossover Strategy. For details, see [here](https://en.wikipedia.org/wiki/Moving_average_crossover).

- **Command**: 
RECOMMEND STOCKS {"stocksToBuy": ["AAPL", "TSLA"]}

### Listing Commands

- **List User**: 
LIST USER <email>

- **List User Portfolio**: 
LIST PORTFOLIO <email>

### Bonus

- **Purchase Premium Option**: 
BUY PREMIUM <email>


## Notes

- Automated tests for validating the implemented functionality are included.

- Used 4 design patterns and provided detailed explanations for their usage.
