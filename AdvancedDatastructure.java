package com.example;
import java.util.Map;
import java.util.*;
public class AdvancedDatastructure {
    /*
     * Why trie map?
     * TrieMap is a data structure that is used to store a dynamic set of strings.
     * It is a tree-like structure that is used to store strings in a way that allows for efficient prefix search.
     * It is also known as a prefix tree.
     * It is used in many applications such as spell checking, autocomplete, and routing.
     * It is also used in many databases such as Redis.
     * It is also used in many search engines such as Elasticsearch.
     * It is also used in many routing algorithms such as Dijkstra's algorithm.
     * It is also used in many compression algorithms such as Huffman coding.
     * It is also used in many encryption algorithms such as AES.
     */
    private static void demostrateTrieMap(){
        System.out.println("Demostrating TrieMap");
        TrieMap<Integer> trieMap = new TrieMap<>();
        trieMap.put("apple", 1);
        trieMap.put("banana", 2);
        trieMap.put("orange", 3);
       // System.out.println("TrieMap size: " + trieMap.size());
        System.out.println("TrieMap contains key 'banana': " + trieMap.get("banana"));
        System.out.println("TrieMap contains key 'apple': " + trieMap.get("apple"));
        System.out.println("TrieMap contains key 'orange': " + trieMap.get("orange"));
        System.out.println("TrieMap contains key 'grape': " + trieMap.get("grape"));
        //System.out.println("TrieMap prefix search for 'app': " + trieMap.prefixSearch("app"));
        //System.out.println("TrieMap prefix search for 'ora': " + trieMap.prefixSearch("ora"));
        //System.out.println("TrieMap keys: " + trieMap.keys());
        //System.out.println("TrieMap values: " + trieMap.values());
    }
    public static void main(String[] args) {
    demostrateTrieMap();
    }
    
}
class TrieNode<T> {
    Map<Character, TrieNode<T>> children = new HashMap<>();
    T value;
    boolean isEndOfWord;
}
class TrieMap<T> {
    private TrieNode<T> root =new TrieNode<>();

    public void put(String key, T value){
        TrieNode<T> current = root;
        for(char c : key.toCharArray()){
            current = current.children.computeIfAbsent(c, t -> new TrieNode<>());
        }
        current.value = value;
        current.isEndOfWord = true;
    }
    public T get(String key){
        TrieNode<T> current = findNode(key);
        return current != null && current.isEndOfWord ? current.value : null;
    }
    private TrieNode<T> findNode(String key){
        TrieNode<T> current = root;
        for(char c : key.toCharArray()){
            current = current.children.get(c);
            if(current == null){
                return null;
            }
        }
        return current;
    }
    public boolean containsKey(String key){
        return get(key) != null;
    }
    public boolean hasPrefix(String prefix){
        return findNode(prefix) != null;
    }
    public List<String> getWordsForPrefix(String prefix){
        List<String> results = new ArrayList<>();
        TrieNode<T> node = findNode(prefix);
        if(node != null){
            collectAllWords(node, prefix, results);
        }
        return results;
    }
    private void collectAllWords(TrieNode<T> node, String prefix, List<String> results){
        if(node.isEndOfWord){
            results.add(prefix);
        }
        for(Map.Entry<Character, TrieNode<T>> entry : node.children.entrySet()){
            collectAllWords(entry.getValue(), prefix + entry.getKey(), results);
        }
    }    
}