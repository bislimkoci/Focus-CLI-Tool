package main.java.com.focus.Parsers;


public class Parser implements ParserCommands{

    @Override
    public String[] parse(String line) {
        String[] st = line.strip().toLowerCase().split(" ");
        return st;
    }

}
