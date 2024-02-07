package org.poo.cb;

public interface UserDecorator {
    void buyPremiumOption(User user);
    boolean isPremiumUser(String email);
}
