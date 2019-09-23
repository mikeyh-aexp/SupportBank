package training.supportbank;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private static boolean userExists( List<Account> accountList, String name ) {
        boolean doesUserExist = false;
        for( int i = 0; i < accountList.size(); i++ ) {
            if( accountList.get(i).name.equals(name) ) {
                doesUserExist = true;
            }
        }
        return doesUserExist;
    }

    private static void updateUserBalance(List<Account> accountList, String sender, String receiver, BigDecimal amount) {

        // LOOP THROUGH accountList
        // if sender or receiver matches persons name
            // call required method

        for( int i = 0; i < accountList.size(); i++ ) {
            if(sender.equals(accountList.get(i).name)) {
                accountList.get(i).decreaseBalance(amount);
            } else if(receiver.equals(accountList.get(i).name)) {
                accountList.get(i).increaseBalance(amount);
            }
        }

    }

    private static void createUserInput(List<Account> accountList, List<Transaction> transactionList) {

        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter 'List All' or 'List[Account]'");
        String inputText = userInput.nextLine();

        if(inputText.equals("List All") || inputText.equals("list all")) {
            for(Account s : accountList) {
                if( s.balance.compareTo(BigDecimal.ZERO) > 0 ) {
                    System.out.println(s.name + " is owed £" + s.balance);
                } else {
                    System.out.println(s.name + " is £" + s.balance + " in debt");
                }
            }
        } else {
            for( int i = 0; i < transactionList.size(); i++ ) {
                String fromName = transactionList.get(i).fromName;
                String toName = transactionList.get(i).toName;
                if(inputText.equals("List[" + fromName + "]") || inputText.equals("List[" + toName + "]")) {
                    System.out.println(transactionList.get(i).fromName + " gave " + transactionList.get(i).toName + " £" + transactionList.get(i).amountSent + " for " + transactionList.get(i).narrative);
                }
            }
        }

    }

    private static void loopFile(List<String> input) throws IOException {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<Account> accountList = new ArrayList<Account>();
        List<Transaction> transactionList = new ArrayList<Transaction>();

        for( int i = 1; i < input.size(); i++ ) {

            String[] columns = input.get(i).split(",");
            LocalDate date = LocalDate.parse(columns[0], dateTimeFormatter);
            String sender = columns[1];
            String receiver = columns[2];
            String narrative = columns[3];
            BigDecimal amount = new BigDecimal( columns[4] );

            // Looks through 'from' column
            if( !userExists( accountList, sender ) ) { // Runs if user does not exist already
                Account person = new Account( sender );
                accountList.add(person);
            }
            // Looks through 'To' column
            if( !userExists( accountList, receiver ) ) { // Runs if user does not exist already
                Account person = new Account( receiver );
                accountList.add(person);
            }

            transactionList.add( new Transaction( date, sender, receiver, narrative, amount ) );
            updateUserBalance(accountList, sender, receiver, amount); // runs method which updates balance for each person

            //
        }

        createUserInput(accountList, transactionList);
    }

    public static void main(String[] args) throws IOException {

        List<String> inputFile2014 = Files.readAllLines(Paths.get("Transactions2014.csv"), Charset.forName("windows-1252")); // Reads CSV file
        List<String> inputFileDodgy = Files.readAllLines(Paths.get("DodgyTransactions.csv"), Charset.forName("windows-1252")); // Reads CSV file


        try {
//            loopFile(inputFile2014);
            loopFile(inputFileDodgy);
        } catch(NumberFormatException e) {
            LOGGER.error("CSV file invalid data");
        }
        
    }

}

