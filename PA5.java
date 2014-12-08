package PA5;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PA5 {
    public static final int DEBUG = 0;
    public static int size=0;
    public static String outName;
    public static void main(String[] args) {
        if (args.length < 1 || args.length >1){
            System.out.println("Error: incorrect arguments");
        }
        else {
            
            //Commands from input file
            ArrayList<String> commands = parseInputFile(args[0]);
          //Object to hold word information for given web pages.
            WebPages webPage = new WebPages();
            ArrayList<String> nonEmptyCommands = new ArrayList<String>();
            for(String command: commands){
                if(command.trim().compareTo("") != 0)
                nonEmptyCommands.add(command);
            }
            commands = nonEmptyCommands;

            if (commands.size() == 0) {
                System.out.println("Error: empty file");
            } else {
                //Scan in files until *EOF* command
                boolean reachedEOF = false;
                boolean foundStops = false;
                for (String command : commands) {
                	//System.out.println("command: " + command);
                    if (command.equals("*EOFs*")) {
                        reachedEOF = true;
                        //System.out.println("this line");
                        continue;
                    }
                    else if (!reachedEOF) {
                    	//System.out.println("added page: " + command);
                        webPage.addPage(command);
                    }
                    else if (reachedEOF && !foundStops) { 	
                    	webPage.pruneStopWords(command);
                        //webPage.getTermIndex().get(command, true);
                    }
                    //System.out.println("command: " + command);
                    if (command.equals("*STOPs*")){
                    	//webPage.printTerms();
                    	foundStops = true;
                    	//System.out.println();
                    }
                    else if (foundStops){
                    	//System.out.println("[" + command.toLowerCase() + " ]");
                    	webPage.printQuery(command);
                    	webPage.bestPages(command);
                    }
                }
                webPage.graphMethod(outName);
            }
        }
    }


    public static ArrayList<String> parseInputFile(String filename) {
        ArrayList<String> commands = new ArrayList<String>();
        File input = new File(filename);
        try {
            Scanner scanner = new Scanner(input);
            outName=scanner.nextLine();
            size = scanner.nextInt();
            while (scanner.hasNextLine()) {
                commands.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return commands;
    }
}