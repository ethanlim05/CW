package com.example.demo;


import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.io.*;
public class Account implements Comparable<Account> {
    private String userName;
    private long score = 0;
    private static List<Account> accounts = new ArrayList<>();
    private static Account singleInstance = null;

    public Account(String userName) {
        this.userName = Objects.requireNonNull(userName, "Username cannot be null");
    }

    // Singleton pattern - no parameters
    public static Account getInstance() {
        if (singleInstance == null) {
            singleInstance = new Account("DefaultPlayer");
            accounts.add(singleInstance);  // Add to accounts list
        }
        return singleInstance;
    }

    // Parameterized method for creating specific accounts
    public static Account getInstance(String username) {
        for (Account account : accounts) {
            if (account.getUserName().equals(username)) {
                System.out.println("DEBUG - Found existing account: " + username + " with score: " + account.getScore());
                return account;  // Return existing account
            }
        }
        System.out.println("DEBUG - Creating new account: " + username);
        Account account = new Account(username);
        accounts.add(account);
        return account;
    }

    // NEW METHOD: Check if username already exists
    public static boolean usernameExists(String username) {
        for (Account account : accounts) {
            if (account.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
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

    // New public method to get all accounts
    public static List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);  // Return a copy to maintain encapsulation
    }

    // New method to update username
    public void setUsername(String newUsername) {
        if (newUsername == null || newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // If this is the singleton instance, update it
        if (this == singleInstance) {
            this.userName = newUsername;
        }

        // Update in accounts list
        for (Account account : accounts) {
            if (account == this) {
                account.userName = newUsername;
                break;
            }
        }
    }

    // Save scores to file
    public static void saveScores() {
        try {
            // Get the current directory path
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir + File.separator + "scores.txt";

            File file = new File(filePath);
            System.out.println("Saving scores to: " + file.getAbsolutePath());
            System.out.println("Number of accounts to save: " + accounts.size());

            // Simple approach: just save all accounts without removing duplicates
            try (PrintWriter writer = new PrintWriter(file)) {
                for (Account account : accounts) {
                    System.out.println("Saving: " + account.userName + ":" + account.score);
                    writer.println(account.userName + ":" + account.score);
                }
                System.out.println("Successfully saved " + accounts.size() + " scores");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error saving scores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load scores from file
    public static void loadScores() {
        accounts.clear();
        try {
            // Get the current directory path
            String currentDir = System.getProperty("user.dir");
            String filePath = currentDir + File.separator + "scores.txt";

            File file = new File(filePath);
            System.out.println("Loading scores from: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.out.println("Scores file does not exist, starting fresh");
                // Create a default account for testing
                Account.getInstance("DefaultPlayer").addToScore(0);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int count = 0;
                while ((line = reader.readLine()) != null) {
                    count++;
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String username = parts[0];
                        long score = Long.parseLong(parts[1]);
                        Account account = getInstance(username);
                        account.score = score;
                        System.out.println("Loaded: " + username + ":" + score);
                    }
                }
                System.out.println("Successfully loaded " + count + " scores");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved scores found, starting fresh");
        } catch (IOException e) {
            System.err.println("Error loading scores (IO): " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error loading scores (Number format): " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load scores when class is loaded
    static {
        loadScores();
    }
}
