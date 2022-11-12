package app;

public class RLEmain {

   public static void main(String[] args) throws Exception{
   
      RLEconverter con = new RLEconverter ();
      /* run main to call compress to generate RLE_java.txt  */
      con.compressFile("C:/Users/ibzim/Downloads/CS 187/java.txt");
      /* then comment out the call to compress, uncomment the call to decompress.
         then run main to generate RLE_java.txt  */
      con.decompressFile("C:/Users/ibzim/Downloads/CS 187/RLE_java.txt");
   }
}