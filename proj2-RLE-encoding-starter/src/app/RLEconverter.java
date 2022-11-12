package app;

import java.util.Scanner;
import java.io.*;

public class RLEconverter {
  private final static int DEFAULT_LEN = 100; // used to create arrays.

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
    // writeFile(getCompressedFileStr(compressed, fileChars), "RLE_"+fileName);
    writeFile(getCompressedFileStr(compressed, fileChars), "C:/Users/ibzim/Downloads/CS 187/RLE_java.txt");

  }

  public String compressLine(String line, char[] fileChars) {
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

  public String[] compressAllLines(String[] lines, int dataSize, char[] fileChars) {
    String[] outputArray = new String[dataSize];

    for (int i = 0; i < dataSize; i++) {
      outputArray[i] = compressLine(lines[i], fileChars);
    }

    return outputArray;
  }

  public String getCompressedFileStr(String[] compressed, char[] fileChars) {
    StringBuilder bld = new StringBuilder();
    bld.append(fileChars[0] + "," + fileChars[1] + "\n");

    for (int i = 0; i < compressed.length - 1; i++) {
      bld.append(compressed[i] + "\n");
    }

    bld.append(compressed[compressed.length - 1]);
    return String.valueOf(bld);
  }

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
    // writeFile(getDecompressedFileStr(decompressed), "DECOMP_"+fileName);
    writeFile(getDecompressedFileStr(decompressed), "C:/Users/ibzim/Downloads/CS 187/DECOMP_java.txt");
  }

  public String decompressLine(String line, char[] fileChars) {

    int currentChar = 0;
    StringBuilder output = new StringBuilder();
    String tempNumberOfChars = "";

    for (int i = 0; i < line.length(); i++) {

      if (line.charAt(i) == ',') { // if character is a comma

        for (int j = 0; j < Integer.parseInt(tempNumberOfChars); j++) { // add the characters
          output.append(String.valueOf(fileChars[currentChar]));
        }

        tempNumberOfChars = ""; // empty it
        currentChar--; // change it to the other variable
        currentChar = Math.abs(currentChar);
      }

      else {
        tempNumberOfChars += String.valueOf(line.charAt(i));
      }

    }

    for (int j = 0; j < Integer.parseInt(tempNumberOfChars); j++) { // add the characters
      output.append(String.valueOf(fileChars[currentChar]));
    }

    return String.valueOf(output);
  }

  public String[] decompressAllLines(String[] lines, int dataSize) {
    String[] outputArray = new String[dataSize - 1];
    char[] fileChars = new char[2];

    fileChars[0] = lines[0].charAt(0);
    fileChars[1] = lines[0].charAt(2);

    for (int i = 1; i < dataSize; i++) {
      outputArray[i - 1] = decompressLine(lines[i], fileChars);
    }

    return outputArray;
  }

  public String getDecompressedFileStr(String[] decompressed) {
    String data = "";

    StringBuilder bld = new StringBuilder();

    for (int i = 0; i < decompressed.length - 1; i++) {
      bld.append(decompressed[i] + "\n");
    }

    bld.append(decompressed[decompressed.length - 1]);

    data = String.valueOf(bld);
    return data;

  }

  public char[] discoverAsciiChars(String[] decompressed, int dataSize) {
    char[] outputArray = new char[2];
    outputArray[0] = decompressed[0].charAt(0);

    outer: for (int i = 1; i < decompressed.length; i++) {
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