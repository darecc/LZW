package dc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ByteProgram {
        private static byte[] readFile(String fileName) {
            File file = new File(fileName);
            Path path = Paths.get(fileName);
            try {
                byte[] res = Files.readAllBytes(path);
                return res;
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
            byte[] bajty = readFile(fileName);
            System.out.println("Napis ma " + bajty.length + " znakow");
            File file = new File(fileName);
            long fileSize = file.length();
            System.out.println("Rozmiar pliku: " + fileSize);
            double wspRozmiaru = (double)fileSize/bajty.length;
            LZW lzw = new LZW();
            List<Integer> compressed = lzw.compress(bajty);
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
            System.out.println("Skompresowany plik ma " + compressed.size() + " bajtow");
            double compSize = compressed.size();
            double fileS = fileSize;
            double wspKompresji = compSize/fileS;
            System.out.format("Zmiana rozmiaru pliku: %.2f %n",(1 - wspKompresji) * 100);
            double wspKomp = (1 - wspKompresji)*8/size;
            System.out.format("Wsp. kompresji: %.2f %n", wspKomp*100);
            System.out.println(compressed);
            int[] decompressed = lzw.decompress(compressed);
        }
    }
