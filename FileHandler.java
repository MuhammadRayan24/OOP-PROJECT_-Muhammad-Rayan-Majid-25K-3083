package BankingManagement;

import java.io.*;

/*
 File work is separated from Bank class so banking
 logic and storage logic do not get mixed together.

 This makes project easier to read and maintain.
*/
public class FileHandler {

    /*
     Static method is used because saving file does
     not require making object every time.
    */
    public static void saveData(String data) {

        try {

            /*
             Full file rewrite is easier here than
             editing single lines one by one.
             Latest data should replace old state.
            */
            FileWriter fw = new FileWriter("accounts.txt");

            fw.write(data);

            /*
             Closing stream is important so data is
             properly pushed into file.
            */
            fw.close();

        } catch (Exception e) {

            /*
             Friendly message shown because project
             user only needs result, not stack trace.
            */
            System.out.println("Saving error.");
        }
    }

    /*
     Loading returns one complete string because
     Bank class can decide later how to split data.
    */
    public static String loadData() {

        StringBuilder sb =
                new StringBuilder();

        try {

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    "accounts.txt"));

            String line;

            /*
             Reading line by line matches how each
             account is stored as separate record.
            */
            while ((line = br.readLine()) != null) {

                sb.append(line)
                        .append("\n");
            }

            br.close();

        } catch (Exception e) {

            /*
             If file does not exist yet, returning
             blank data lets first run continue.
            */
            return "";
        }

        return sb.toString();
    }
}