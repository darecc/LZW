package dc;

public class Huffman {
    /**
     * Konwersja elementów tablicy o rozmiarze 'size' bitów do tablicy bajtów
     * @param tab
     * @param size
     * @return
     */
    public byte[] convertIntToByte(int[] tab, int size) {
        int byteSize = tab.length * size /8 + 1;
        byte[] byteTable = new byte[byteSize];
        int count = 0;
        int bytes = size;
        for (int i = 0; i < tab.length; i++) {
            if (bytes >= 16) {
                byte x = (byte)tab[i];
                byteTable[count] = (byte)((byteTable[count] << 8)  + x);
                tab[i] = tab[i] >> 8;
                bytes -= 8;
                count++;
            }
            if (bytes >= 8) {
                byte x = (byte)tab[i];
                byteTable[count] = (byte)((byteTable[count] << 8) + x);
                count++;
                tab[i] = tab[i] >> 8;
                bytes -= 8;
            }
            if (bytes >= 0) {
                byteTable[count] = (byte)((byteTable[count] << bytes) + tab[i]);
                bytes = bytes - 8;
            }
            if (bytes < 0) {
                bytes = bytes + size;
            }
        }
        return byteTable;
    }

    /**
     * Obliczenie jakie bajty wystepowaly najczesciej w formie histogramu
     * @param table
     * @return
     */
    public int[][] calculateHistogram(byte[] table) {
        int[][] histogram = new int[256][2];
        for (int i = 0; i < 256; i++)
            histogram[i][1] = i;
        for(int x : table)
            histogram[x + 128][0]++;
        for (int i = 0; i < 255; i++) {
            int p = 0;
            int max = 0;
            for (int j = i+1; j < 256; j++)
                if (histogram[j][0] > max) {
                    max = histogram[j][0];
                    p = j;
                }
            int p1 = histogram[i][0];
            int p2 = histogram[i][1];
            histogram[i][0] = histogram[p][0];
            histogram[i][1] = histogram[p][1];
            histogram[p][0] = p1;
            histogram[p][1] = p2;
        }
        return histogram;
    }
    public int calculateCompressedSize(int[][] histogram) {
        int count = 0;
        for (int i = 0; i < 256; i++) {
            if (i == 0)
                count += 3 * histogram[i][0];
            if (i > 0 && i < 3)
                count += 4 *histogram[i][0];
            if (i > 2 && i < 6)
                count += 5 * histogram[i][0];
            if (i > 5 && i < 11)
                count += 6 * histogram[i][0];
            if (i > 10 && i < 19)
                count += 7 * histogram[i][0];
            if (i > 18 && i < 32)
                count += 8 * histogram[i][0];
            if (i > 31 && i < 53)
                count += 9 * histogram[i][0];
            if (i > 52 && i < 87)
                count += 10 * histogram[i][0];
            if (i > 86 && i < 142)
                count += 11 * histogram[i][0];
            if (i > 141 && i < 231)
                count += 12 * histogram[i][0];
            if (i > 230)
                count += 13 * histogram[i][0];
        }
        if (count % 8 == 0)
            return count /8;
        else
            return count/8 + 1;
    }
}
