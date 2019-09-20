package training.supportbank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static boolean userExists( List<Account> accountList, String name ) {
        boolean doesUserExist = false;
        for( int i = 0; i < accountList.size(); i++ ) {
            if( accountList.get(i).name.equals(name) ) {
                doesUserExist = true;
            }
        }
        return doesUserExist;
    }

    public static void main(String[] args) throws IOException {
        List<String> inputFile = Files.readAllLines(Paths.get("Transactions2014.csv"), Charset.forName("windows-1252")); // Reads CSV file

        List<Account> accountList = new ArrayList<Account>();
        List<Transaction> transactionList = new ArrayList<Transaction>();

        // -----------------------

        // LOOPS THROUGH CSV FILE

        for( int i = 1; i < inputFile.size(); i++ ) {
            String[] columns = inputFile.get(i).split(",");

            if( !userExists( accountList, columns[1] ) ) { // Runs if user does not exist already
                Account person = new Account( columns[1] );
                accountList.add(person);
            }

        }

        // -----------------------

        // PRINTS ARRAY

        for(Account s : accountList) {
            System.out.println(s.name + " " + s.balance);
        }
//
//        for(Transaction s : transactionList) {
//            System.out.println(s.fromName + ", " + s.toName + ", " + s.narrative + ", " + s.amountSent);
//        }

    }

}

