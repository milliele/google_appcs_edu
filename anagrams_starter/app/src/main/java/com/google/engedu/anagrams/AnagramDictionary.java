/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            String key = sortLetters(word);
            if (lettersToWord.containsKey(key)) {
                lettersToWord.get(key).add(word);
            } else {
                lettersToWord.put(key,new ArrayList<>(Arrays.asList(word)));
            }
        }
        for(String w:wordSet) {
            if(getAnagramsWithOneMoreLetter(w).size()>=MIN_NUM_ANAGRAMS)
                wordList.add(w);
        }
        for(String w:wordList) {
            int l = w.length();
            if (sizeToWords.containsKey(l)){
                sizeToWords.get(l).add(w);
            } else {
                sizeToWords.put(l,new ArrayList<String>(Arrays.asList(w)));
            }
        }
    }

    private String sortLetters(String word) {
        char[] chs = word.toCharArray();
        Arrays.sort(chs);
        return String.valueOf(chs);
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && !word.startsWith(base) && !word.endsWith(base);
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String temp = sortLetters(targetWord);
        for(String w:wordList) {
            if (sortLetters(w).equals(temp)) {
                result.add(w);
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        int n = word.length();
        for(char c = 'a';c<='z';++c) {
            String after = sortLetters(new StringBuffer(word+c).toString());
//            System.out.println(String.format("after is %s", after));
            if (lettersToWord.containsKey(after)) {
//                System.out.println("have");
                for(String w:lettersToWord.get(after)) {
                    if (!w.startsWith(word) && !w.endsWith(word)){
                        result.add(w);
//                        System.out.println(w);
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        while(!sizeToWords.containsKey(wordLength) && wordLength<MAX_WORD_LENGTH)
            wordLength++;
        if(sizeToWords.containsKey(wordLength)) {
            ArrayList<String> dict = sizeToWords.get(wordLength++);
            return dict.get(new Random().nextInt(dict.size()));
        }
        return "stop";
    }
}
