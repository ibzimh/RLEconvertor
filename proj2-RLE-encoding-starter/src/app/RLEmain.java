package app;

public class RLEmain {

   public static void main(String[] args) throws Exception {
      RLEconverter con = new RLEconverter();
      con.compressFile("C:/Users/ibzim/Downloads/CS 187/java.txt");
      con.decompressFile("C:/Users/ibzim/Downloads/CS 187/RLE_java.txt");
   }
}