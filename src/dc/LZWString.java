package dc;

import java.util.*;

public class LZWString {

    private Map<String, Integer> diction;

    public List<Integer> compress(String uncompressed) {
        //Build the dictionary.
        int dictSize = 256;
        if (diction.size() == 0) {
            diction = new HashMap<String, Integer>();
            for (int i = 0; i < 256; i++)
                diction.put("" + (char) i, i);
        }
        Map<String, Integer> dictionary = new HashMap<>();
        for(Map.Entry<String, Integer> entry : diction.entrySet())
            dictionary.put(entry.getKey(), entry.getValue());
        String w = "";
        List<Integer> result = new ArrayList<Integer>();
        for (char c : uncompressed.toCharArray()) {
            String wc = w + c;
            if (dictionary.containsKey(wc))
                w = wc;
            else {
                result.add(dictionary.get(w));
                dictionary.put(wc, dictSize++);
                w = "" + c;
            }
        }

        if (!w.equals(""))
            result.add(dictionary.get(w));
        return result;
    }

    public int setDictionary(String napis) {
        diction = new HashMap<String, Integer>();
        int i = 0;
        for (char c : napis.toCharArray())
            if (diction.containsKey(Character.toString(c)) == false) {
                diction.put(Character.toString(c), i);
                i++;
            }
        return i;
    }

    public String decompress(List<Integer> compressed) {
        //Build the dictionary.
        int dictSize = 256;
        Map<Integer, String> dictionary = new HashMap<Integer, String>();
        Set<Map.Entry<String,Integer>> entrySet = diction.entrySet();
        for (Map.Entry<String, Integer> entry : entrySet)
            dictionary.put(entry.getValue(), entry.getKey());

        String w = "";
        int i = compressed.remove(0);
        w = w + dictionary.get(i);
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

            //Add w+entry[0] to the dictionary.
            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
        }
        return result.toString();
    }
}