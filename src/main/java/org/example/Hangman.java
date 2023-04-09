package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    public static Player player;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean ExitGame = false;


        System.out.println("Welcome to the HangMan Game!");
        System.out.println("Select a choice:  ");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit Game");
        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {

            case 1:
                while (true) {
                    Scanner f = new Scanner(new File("New folder\\RegisteredUsers.txt"));

                    System.out.println("Please enter your username: ");
                    String username = scanner.nextLine();
                    System.out.println("Please enter your password: ");
                    String password = scanner.nextLine();
                    boolean found = false;
                    boolean usernameFound = false;
                    while (f.hasNext()) {
                        String line = f.nextLine();
                        String[] parts = line.split(" ");
                        if (parts[0].equals(username)) {  //if username is correct
                            usernameFound = true;
                            if (parts[1].equals(password)) {    //if password is correct
                                found = true;
                                System.out.println("Welcome " + username);
                                player = new Player(username, password);
                                break;
                            }
                        }
                    }
                    if (!usernameFound && !found) {
                        System.out.println("404 Not found ");
                    } else if (!found) {
                        System.out.println("401 INVALID PASSWORD");
                    } else {
                        break;
                    }
                }
                break;
            case 2:
                while (true) {
                    Scanner f = new Scanner(new File("New folder\\RegisteredUsers.txt"));

                    System.out.println("Please enter your username: ");
                    String username = scanner.nextLine();
                    System.out.println("Please enter your password: ");
                    String password = scanner.nextLine();
                    boolean found = false;
                    while (f.hasNext()) {
                        String line = f.nextLine();
                        String[] parts = line.split(" ");
                        if (parts[0].equals(username)) {
                            found = true;
                            System.out.println("Username already exists");
                        }
                    }
                    if (!found) {
                        try {
                            FileWriter myWriter = new FileWriter("New folder\\RegisteredUsers.txt", true);
                            myWriter.write(username + " " + password + "\n");
                            myWriter.close();
                            System.out.println("Successfully registered");
                            System.out.println("Welcome " + username);
                            player = new Player(username, password);
                            break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        }


        Scanner keyborad = new Scanner(System.in);   //get the player input

        System.out.println("1.Single player 2.Multi players 3.Score History");
        String players = keyborad.nextLine();
        String word;
        if (players.equals("1")) {
            Scanner file = new Scanner(new File("New folder\\New Text Document.txt"));

            List<String> words = new ArrayList<>(); // list of words
            while (file.hasNext()) { // while there is a line to read
                words.add(file.nextLine());    //add the word to the list
            }
            Random rand = new Random();

            word = words.get(rand.nextInt(words.size()));  //random word

        } else if (players.equals("2")) {
            System.out.println("player 1 , please enter your word  :");
            word = keyborad.nextLine();     //get the word from the player
            System.out.println(" \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n ");        //clear the screen
            System.out.println("Player 2 turn, please enter your word");
        } else if (players.equals("3")) {
            ArrayList<Integer> scores = getScores();
            if (scores.size() == 0) {
                System.out.println("No scores found");
                return;
            }
            System.out.println("Your scores are: ");
            for (int i = 0; i < scores.size(); i++) {
                System.out.println(scores.get(i));
            }
            return;
        } else {
            System.out.println("Invalid input");
            return;
        }


        // System.out.println(word);   //print the word random word


        List<Character> playerGuesses = new ArrayList<>();//player guess


        int wrongGuess = 6;   //wrong guess
        int correctGuess = 0; //correct guess
        while (true) {

            System.out.println("you have " + wrongGuess + " wrong guesses left");
            if (wrongGuess <= 0) {
                System.out.println("you lose");
                System.out.println("The word was: " + word);    //print the word
                break;
            }

            printword(word, playerGuesses); //print the word
            if (!getPlayerGuess(keyborad, word, playerGuesses)) {
                wrongGuess--;
            }  //get the player guess
            else {
                correctGuess++;
            }

            if (printwordstate(word, playerGuesses)) { //print the word state with the real word letter and the player guess letter
                System.out.println("you win");
                System.out.println("Your score is: " + correctGuess);
                break;
            }
        }
        saveScore(correctGuess);
    }

    public static void saveScore(int score) {
        try {
            FileWriter myWriter = new FileWriter("New folder\\ScoreKeeper.txt", true);
            myWriter.write(player.getUsername() + " " + score + "\n");
            myWriter.close();
            System.out.println("Successfully saved score");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Integer> getScores() throws FileNotFoundException {
        ArrayList<Integer> scores = new ArrayList<>();
        Scanner f = new Scanner(new File("New folder\\ScoreKeeper.txt"));
        while (f.hasNext()) {
            String line = f.nextLine();
            String[] parts = line.split(" ");
            if (parts[0].equals(player.getUsername())) {
                scores.add(Integer.parseInt(parts[1]));
            }
        }
        return scores;
    }


    private static boolean getPlayerGuess(Scanner keyborad, String word, List<Character> playerGuesses) {
        System.out.println("please enter a letter");    //ask the player to enter a letter


        String letterGuess = keyborad.nextLine(); //get the player guess
        playerGuesses.add(letterGuess.charAt(0)); //add the letter to the player guess

        return word.contains(letterGuess);  //return true if the player guess contains the word
    }

    private static void printword(String word, List<Character> playerGuesses) {
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {        //if the player guess contains the word
                System.out.print(word.charAt(i));   //print the word
            } else {
                System.out.print("_");
            }
        }
        System.out.println();
    }

    private static boolean printwordstate(String word, List<Character> playerGuesses) {
        int correctCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (playerGuesses.contains(word.charAt(i))) {        //if the player guess contains the word
                System.out.print(word.charAt(i));   //print the word
                correctCount++;     //count the correct letter

            } else {
                System.out.print("_");
            }
        }
        System.out.println();
        return (word.length() == correctCount); //return true if the player guess all the letter
    }

}



