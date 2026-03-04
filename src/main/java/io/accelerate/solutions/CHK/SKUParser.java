package io.accelerate.solutions.CHK;

import java.util.HashMap;
import java.util.Map;

public class SKUParser {


    //This method parses a string of SKUs (product codes) and counts the frequency of each character.
    //It creates a HashMap to store each SKU as a key with its count as the value.
    // Then it iterates through each character in the input string:
    //For each character, it converts it to a string and uses getOrDefault() to retrieve the current count (defaulting to 0 if not present),
    // increments it by 1, and stores it back in the map.
    public Map<String, Integer> parse(String skus) {

        Map<String, Integer> quantities = new HashMap<>();

        for (char c : skus.toCharArray()) {
            String sku = String.valueOf(c);
            quantities.put(sku, quantities.getOrDefault(sku, 0) + 1);
        }
        return quantities;
    }
}

