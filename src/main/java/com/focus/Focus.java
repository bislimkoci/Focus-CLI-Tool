package main.java.com.focus;
import java.util.Scanner;

import main.java.com.focus.Commands.CommandFactory;
import main.java.com.focus.Commands.CommandInterface;
import main.java.com.focus.Parsers.Parser;
import main.java.com.focus.Parsers.ParserCommands;
import main.java.com.focus.Timers.TimerInterface;

public class Focus{
    private static TimerInterface currentTimer;

    public static void main(String[] args) { 
        System.out.println("Focus with commands: start -work[mins] -rest [mins] -repeat [amount] | stop | stats | exit");
        
        //Works only with start command and exit command

        Scanner scanner = new Scanner(System.in);    

        ParserCommands parser = new Parser();
        
        CommandFactory facotry = new CommandFactory();

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("quit")) {
                break;
            }

            String[] commandParts = parser.parse(input);
            if (commandParts == null || commandParts.length == 0) {
                continue;
            }
            
            CommandInterface command = facotry.createCommand(commandParts, currentTimer);

            if (command != null) {
                command.execute();
            }
        }
        scanner.close();
        
    }

    public static void setCurrentTimer(TimerInterface timer) {
        currentTimer = timer;
    }

    public static TimerInterface getCurrentTimer(){
        return currentTimer;
    }
}