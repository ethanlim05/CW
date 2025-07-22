package com.example.demo;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Account implements Comparable<Account> {
    private long score = 0;
    private String userName;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private static Account singleInstance = null;

    public Account(String userName) {
        this.userName = Objects.requireNonNull(userName, "Username cannot be null");
    }

    // Singleton pattern - no parameters
    public static Account getInstance() {
        if (singleInstance == null) {
            singleInstance = new Account("DefaultPlayer");
        }
        return singleInstance;
    }

    // Parameterized method for creating specific accounts
    public static Account getInstance(String userName) {
        for (Account account : accounts) {
            if (account.getUserName().equals(userName)) {
                return account;
            }
        }
        Account account = new Account(userName);
        accounts.add(account);
        return account;
    }

    @Override
    public int compareTo(Account o) {
        return Long.compare(o.getScore(), score);
    }

    public void addToScore(long score) {
        this.score += score;
    }

    public long getScore() {
        return score;
    }

    public String getUserName() {
        return userName;
    }

    // ... rest of your Account methods
}
