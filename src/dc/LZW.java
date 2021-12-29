package dc;

import java.util.*;

public class LZW {
    private Map<String, Integer> diction;

    public List<Integer> compress(byte[] uncompressed) {
        int dictSize = 256;
        diction = new HashMap<String, Integer>();
        for (int i = 0; i < dictSize; i++)
                diction.put("" + (char)i, (int)i);
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (byte c : uncompressed) {
            int x = (int)c & 0xff;
            char ch = (char)x;
            String wc = w + ch;
            if (diction.containsKey(wc))
                w = wc;
            else {
                result.add(diction.get(w));
                diction.put(wc, dictSize++);
                w = "" + ch;
            }
        }
        if (!w.equals(""))
            result.add(diction.get(w));
        return result;
    }

    public int[] decompress(List<Integer> compressed) {
        int dictSize = 256;
        Map<Integer, String> dictionary = new HashMap<Integer, String>();
        Set<Map.Entry<String, Integer>> entrySet = diction.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet)
            dictionary.put(entry.getValue(), entry.getKey());

        String w = "";
        int number = 0;
        for (int i = 0; i < compressed.size(); i++)
            if (compressed.get(i) == 0) {
                number = i;
                break;
            }
        w = w + dictionary.get(number);
        StringBuffer result = new StringBuffer(w);
        for (Integer k : compressed) {
            if (k == null)
                continue;
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);
            result.append(entry);
            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
        }
        int[] numbers = new int[result.length()];
        for(int j = 0; j < result.length(); j++) {
            String s = result.substring(j, j+1);
            char c = s.charAt(0);
            numbers[j] = c;
        }
        return numbers;
    }
}
