package main.java.com.focus.Commands;

import main.java.com.focus.Focus;
import main.java.com.focus.Timers.TimerInterface;

public class CommandFactory {

    public CommandInterface createCommand(String[] command, TimerInterface currentTimer) {
        if (command == null || command.length == 0) return null;
        
        switch (command[0].toLowerCase()) {
            
            case "start":
                if (Focus.getCurrentTimer() != null) {
                    System.out.println("An session is already in process.");
                    break;
                }

                if (command.length >= 5 && command[1].equals("-work") && command[3].equals("-rest")) {
                    
                    try {
                        int workTime = Integer.parseInt(command[2]);
                        int restTime = Integer.parseInt(command[4]);
                        
                        if (command.length >= 7 && command[5].equals("-repeat")) {
                            int repeat = Integer.parseInt(command[6]);
                            return new StartRepeatCommand(workTime, restTime, repeat);
                        }
                        return new StartCommand(workTime, restTime);

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid time format");
                    }

                } else {
                    System.out.println("Usage: start -work [minutes] -rest [minutes] -repeat [amount]");
                }
                break;
                
            case "stop":
                return new StopCommand(currentTimer);

            case "pause":
                return new PauseCommand(currentTimer);
            
            case "resume":
                return new ResumeCommand(currentTimer);
                
            default:
                System.out.println("Unknown command");
        }
        
        return null;
    }
}