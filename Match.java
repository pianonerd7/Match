import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Random;

/**
 * Developed to efficiently match pals from a given text file. Scan file, split names into array, and match
 * pals based on randomly generated number, and then printed to appointed file in the same file.
 * @author Jiaxin He
 *
 */
public class Match {

 /**
  * Scans the name of the textfile, and stores into a string.
  * @param fileName The name of the input file
  * @return The string representation of the content of text file.
  * @throws IOException 
  */
 public String readFileToString(String fileName) throws IOException {

  BufferedReader reader = new BufferedReader(new FileReader(fileName));

  try {
   StringBuilder builder = new StringBuilder();
   String everyLine = reader.readLine();

   while (everyLine != null) {
    builder.append(everyLine + "\n");
    everyLine = reader.readLine();
   }
   return builder.toString();
  }
  finally {
   reader.close();
  }
 }

 /**
  * Split words at punctuation
  * @param text The string performing splitting on
  * @return String array containing each name in every index
  */
 public String[] splitWords(String text) {
  return text.split("\\W");
 }

 /**
  * Converts a string array to an array list.
  * @param array The string array
  * @return An array list with same content as the string array.
  */
 public ArrayList<String> arrayToList(String[] array) {
  return new ArrayList<String>(Arrays.asList(array));
 }

 /**
  * Randomly generates a number within the given range.
  * @param min The minimum number this number could be
  * @param max The maximum number this number could be
  * @return The randomly generated number within the max and min range
  */
 public int randomNumGen(int min, int max) {
  Random coefficient = new Random();

  int randomNum = coefficient.nextInt((max-min) + 1) + min;

  return randomNum;
 }

 /**
  * Assigns 2 pal in this method, and deletes from the list of names. 
  * @param names The arraylist of names of everyone to be paired
  * @return The arrayList of the paired up pals.
  */
 public ArrayList<String> palAssignment(ArrayList<String> names) {

  ArrayList<String> finalList = new ArrayList<String>();

  String firstName;
  String secondName;
  String finalName;

  while(names.size() > 1) {
   int size = names.size();

   firstName = names.get(randomNumGen(0, size-1));
   secondName = names.get(randomNumGen(0, size-1));

   while (firstName.equals(secondName)) {
    firstName = names.get(randomNumGen(0, size-1)); 
   }

   finalName = firstName + "," + secondName;

   finalList.add(finalName);

   names.remove(firstName);
   names.remove(secondName);
  }

  //Odd case
  if (names.size() == 1) {
   String lastPair = finalList.get(finalList.size()-1);
   finalList.remove(lastPair);
   finalList.add(lastPair + " , " + names.get(0));
  }

  return finalList;
 }

 /**
  * Prints the names of the pals to an output file.
  * @param fileName The file name of the output file.
  * @param pals The arraylist of pals already paired up
  * @throws IOException
  */
 public void printTable(String fileName, ArrayList<String> pals) throws IOException{

  Formatter formatter = new Formatter();

  PrintStream outputFile = new PrintStream(fileName);//makes new outputfile;

  DateFormat date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  Calendar cal = Calendar.getInstance();

  outputFile.print("Pearl Pals for the week of " + date.format(cal.getTime()));
  outputFile.print("\n");//enter;
  outputFile.print("\n");//enter;

  outputFile.print(formatter.format("%30s %10s %30s", "PAL ONE", "WITH", "PAL TWO"));
  outputFile.print("\n");
  outputFile.print("\n");

  for (int i = 0; i < pals.size(); i++){//loops through all of the list;
   formatter = new Formatter();

   String[] ice_cream = splitWords(pals.get(i));

   String rowData1 = ice_cream[0];
   String rowData2 = ice_cream[1];
   String with = "with";

   outputFile.print(formatter.format("%30s %10s %30s", rowData1, with, rowData2));
   outputFile.print("\n");

  }
  outputFile.close();
  formatter.close();
 }
 
 /**
  * Scan text file and save into string, splits names into array, and matches pals based
  * on arraylist. The prints out files to file name "Pals".
  * @param args
  * @throws IOException
  */
 public static void main (String args[]) throws IOException {
  Match match = new Match();
  String string = match.readFileToString("Names.txt");
  String[] wordArray = match.splitWords(string);
  ArrayList<String> arrayNames = match.arrayToList(wordArray);
  ArrayList<String> arrayPairs = match.palAssignment(arrayNames);
  match.printTable("Pals", arrayPairs);
 }



}
