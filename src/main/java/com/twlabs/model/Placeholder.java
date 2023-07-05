package com.twlabs.model;

/**
 * Placeholder
 */
public class Placeholder {
    private String query;
    private String name;


    public Placeholder() {}



    public Placeholder(String query, String name) {
        this.query = query;
        this.setName(name);
    }

    public String getQuery() {
        return query;
    }

    public String getName() {
        return name;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setName(String name) {
        this.name = lowerFirstWord(name, ".");

    }


    public String lowerFirstWord(String input, String separator) {

        if (input.contains(separator)) {
            String parts[] = input.split("\\" + separator);

            String toLower = parts[0].toLowerCase();


            return toLower + input.substring(toLower.length());
        }
        return input;
    }
}
