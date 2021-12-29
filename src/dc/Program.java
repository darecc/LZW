package dc;
import java.io.*;
import java.util.*;

public class Program {


    private static String readFile(String fileName) {
        File file = new File(fileName);
        try {
            FileReader re = new FileReader(file);
            BufferedReader bu = new BufferedReader(re);
            String result = "";
            String line;
            while ((line = bu.readLine()) != null)
                result = result + line + System.lineSeparator();
            bu.close();
            re.close();
            return result;
        }
        catch(FileNotFoundException fe) {
            System.out.println(fe.getMessage());
        }
        catch(IOException ie) {
            System.out.println(ie.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String fileName = "plik.txt";
        String napis = readFile(fileName);
        System.out.println("Napis ma " + napis.length() + " znakow");
        File file = new File(fileName);
        long fileSize = file.length();
        System.out.println("Rozmiar pliku: " + fileSize);
        double wspRozmiaru = (double)fileSize/napis.length() * 8;
        System.out.println("Przecietnie znak zawiera " + wspRozmiaru + " bitow");
        LZWString lzwString = new LZWString();
        lzwString.setDictionary(napis);
        List<Integer> compressed = lzwString.compress(napis);
        int max = 0;
        for(int i =0 ; i < compressed.size(); i++) {
            if (compressed.get(i) == null) {
                System.out.println(i + " " + compressed.get(i));
                continue;
            }
            int x = compressed.get(i);
            if (x > max)
                max = x;
        }
        double size = Math.log(max) / Math.log(2);
        int sizeInt = (int)Math.ceil(size);
        System.out.println("Skompresowany napis ma " + compressed.size() + " znakow. Kazdy znak to " + sizeInt + " bit(y)");
        double wspKompresji = (double)compressed.size()/fileSize;
        System.out.format("Zmiana rozmiaru pliku: %.2f %n",(1 - wspKompresji) * 100);
        wspKompresji = 1 - wspKompresji*size/wspRozmiaru;
        System.out.format("Wsp. kompresji: %.2f %n", wspKompresji*100);
        System.out.println(compressed);
        String decompressed = lzwString.decompress(compressed);
        System.out.println(decompressed);
    }
}