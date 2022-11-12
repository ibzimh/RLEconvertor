package app;

import java.util.Scanner;
import java.io.*;

public class RLEconverter {
  private final static int DEFAULT_LEN = 100; // used to create arrays.

  /*
   * This method reads in an uncompressed ascii image file that contains
   * 2 characters. It stores each line of the file in an array.
   * It then calls compressAllLines to get an array that stores the compressed
   * version of each uncompressed line from the file. The compressed array
   * is then passed to the getCompressedFileStr method which returns a String
   * of all compressed lines (the two charcaters are written in the first line)
   * in CSV format. This String is written to a text file with the prefix "RLE_"
   * added to the original, uncompressed file name.
   * Note that dataSize keeps track of the number of lines in the file. The array
   * that holds the lines of the file is initialized to the DEFAULT_LEN, which
   * is assumed to be << the number of lines in the file.
   */
  public void compressFile(String fileName) throws IOException {
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] decompressed = new String[DEFAULT_LEN];
    int dataSize = 0;
    while (scan.hasNext()) {
      line = scan.next();
      if (line != null && line.length() > 0)
        decompressed[dataSize] = line;
      dataSize++;
    }
    scan.close();
    char[] fileChars = discoverAsciiChars(decompressed, dataSize);
    String[] compressed = compressAllLines(decompressed, dataSize, fileChars);
    //writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);
    writeFile(getCompressedFileStr(compressed, fileChars), "C:/Users/ibzim/Downloads/CS 187/RLE_java.txt");

  }

  /*
   * This method implements the RLE compression algorithm. It takes a line of
   * uncompressed data
   * from an ascii file and returns the RLE encoding of that line in CSV format.
   * The two characters that make up the image file are passed in as a char array,
   * where
   * the first cell contains the first character that occurred in the file.
   */
  public String compressLine(String line, char[] fileChars) {
    // TODO: Implement this method
    int currentCharCounter = 0; // counts the total number of variables before the next kind of variable
    StringBuilder output = new StringBuilder(); // the output

    if (line.length() != 0) { // this if is done because of the comparision on line 65
      if (fileChars[0] != line.charAt(0)) { // if there is no character of the first kind
        output.append(String.valueOf(currentCharCounter) + ",");
      }
      currentCharCounter++;
    } else { // if String is empty
      output.append(String.valueOf(0 + "," + 0));
    }

    for (int i = 1; i < line.length(); i++) {
      if (line.charAt(i) == line.charAt(i - 1)) { // if it is the same as the previous character
        currentCharCounter++;
      } else { // if it isn't the same as the previous character
        output.append(String.valueOf(currentCharCounter) + ",");
        currentCharCounter = 1;
      }
    }
    output.append(String.valueOf(currentCharCounter));

    return String.valueOf(output);

  }

  /*
   * This method discovers the two ascii characters that make up the image.
   * It iterates through all of the lines and writes each compressed line
   * to a String array which is returned. The method compressLine is called on
   * each line.
   * The dataSize is the number of lines in the file, which is likely to be << the
   * length of lines.
   */
  public String[] compressAllLines(String[] lines, int dataSize, char[] fileChars) {
    // TODO: Implement this method

    String[] outputArray = new String[dataSize];

    for (int i = 0; i < dataSize; i++) {
      outputArray[i] = compressLine(lines[i], fileChars);
    }

    return outputArray;
  }

  /*
   * This method assembles the lines of compressed data for
   * writing to a file. The first line must be the 2 ascii characters
   * in comma-separated format.
   */
  public String getCompressedFileStr(String[] compressed, char[] fileChars) {
    // TODO: Implement this method
    StringBuilder bld = new StringBuilder();
    bld.append(fileChars[0] + "," + fileChars[1] + "\n");

    for (int i = 0; i < compressed.length - 1; i++) {
      bld.append(compressed[i] + "\n");
    }

    bld.append(compressed[compressed.length - 1]);
    return String.valueOf(bld);
  }

  /*
   * This method reads in an RLE compressed ascii image file that contains
   * 2 characters. It stores each line of the file in an array.
   * It then calls decompressAllLines to get an array that stores the decompressed
   * version of each compressed line from the file. The first row contains the two
   * ascii charcaters used in the original image file. The decompressed array
   * is then passed to the getDecompressedFileStr method which returns a String
   * of all decompressed lines, thus restoring the original, uncompressed image.
   * This String is written to a text file with the prefix "DECOMP_"
   * added to the original, compressed file name.
   * Note that dataSize keeps track of the number of lines in the file. The array
   * that holds the lines of the file is initialized to the DEFAULT_LEN, which
   * is assumed to be << the number of lines in the file.
   */
  public void decompressFile(String fileName) throws IOException {
    Scanner scan = new Scanner(new FileReader(fileName));
    String line = null;
    String[] compressed = new String[DEFAULT_LEN];
    int dataSize = 0;
    while (scan.hasNext()) {
      line = scan.next();
      if (line != null && line.length() > 0)
        compressed[dataSize] = line;
      dataSize++;
    }
    scan.close();
    String[] decompressed = decompressAllLines(compressed, dataSize);
    //writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
    writeFile(getDecompressedFileStr(decompressed), "C:/Users/ibzim/Downloads/CS 187/DECOMP_java.txt");
  }

  /*
   * This method decodes lines that were encoded by the RLE compression algorithm.
   * It takes a line of compressed data and returns the decompressed, or original
   * version
   * of that line. The two characters that make up the image file are passed in as
   * a char array,
   * where the first cell contains the first character that occurred in the file.
   */
  public String decompressLine(String line, char[] fileChars) {

    // TODO: Implement this method
    int currentChar = 0;
    StringBuilder output = new StringBuilder();
    String tempNumberOfChars = "";

    for (int i = 0; i < line.length(); i++) {

      if (line.charAt(i) == ',') { // if character is a comma

        for (int j = 0; j < Integer.parseInt(tempNumberOfChars); j++) { //add the characters
          output.append(String.valueOf(fileChars[currentChar]));
        }

        tempNumberOfChars = ""; //empty it 
        currentChar--;                  //change it to the other variable 
        currentChar = Math.abs(currentChar);
      }

      else {
        tempNumberOfChars += String.valueOf(line.charAt(i));
      }

    }

    for (int j = 0; j < Integer.parseInt(tempNumberOfChars); j++) { //add the characters
      output.append(String.valueOf(fileChars[currentChar]));
    }

    return String.valueOf(output);
  }

  /*
   * This method iterates through all of the compressed lines and writes
   * each decompressed line to a String array which is returned.
   * The method decompressLine is called on each line. The first line in
   * the compressed array passed in are the 2 ascii characters used to make
   * up the image.
   * The dataSize is the number of lines in the file, which is likely to be << the
   * length of lines.
   * The array returned contains only the decompressed lines to be written to the
   * decompressed file.
   */
  public String[] decompressAllLines(String[] lines, int dataSize) {
    // TODO: Implement this method
    String[] outputArray = new String[dataSize - 1];
    char[] fileChars = new char[2];

    fileChars[0] = lines[0].charAt(0);
    fileChars[1] = lines[0].charAt(2);

    for (int i = 1; i < dataSize; i++) {
      outputArray[i - 1] = decompressLine(lines[i], fileChars);
    }

    return outputArray;
  }

  /*
   * This method assembles the lines of decompressed data for
   * writing to a file.
   */
  public String getDecompressedFileStr(String[] decompressed) {
    String data = "";
    // TODO: Implement this method

    StringBuilder bld = new StringBuilder();

    for (int i = 0; i < decompressed.length - 1; i++) {
      bld.append(decompressed[i] + "\n");
    }

    bld.append(decompressed[decompressed.length - 1]);

    data = String.valueOf(bld);
    return data;

  }

  // assume the file contains only 2 different ascii characters.
  public char[] discoverAsciiChars(String[] decompressed, int dataSize) {
    // TODO: Implement this method
    char[] outputArray = new char[2];
    outputArray[0] = decompressed[0].charAt(0);

    outer:
    for (int i = 1; i < decompressed.length; i++) {
      for (int j = 0; j < decompressed[i].length(); j++) {
        if (decompressed[i].charAt(j) != outputArray[0]) {
          outputArray[1] = decompressed[i].charAt(j);
          break outer;
        }
      }
    }

    return outputArray;
  }

  public void writeFile(String data, String fileName) throws IOException {
    PrintWriter pw = new PrintWriter(fileName);
    pw.print(data);
    pw.close();
  }
}