package RDS;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *The Menu class is responsible for all interaction between the human and the
 * program. It will display an eye-candy 'RDS' message followed by a greeting,
 * some basic usage instructions, and the available options. It is a self-contained
 * class and because of this all methods should be private. Ironically, this is
 * what the user will interact with the most, but shouldn't have any knowledge of
 * it's existence.
 *
 * @author Scott Stevenson
 */
public class Menu {

    private static boolean stillDefault = true;
    private static HashMap map = null;  //the current map (Rowan is default)
    private static int command = -1;    //the current command (-1 is default)
    private static Scanner scanner = new Scanner(System.in);    //The command reader

    /**
     * Menu constructor will take the HashMap map, set it as the map, and then
     * display all welcoming information and options.
     *
     * @param map The map to use.
     */
    public Menu(HashMap map) {
        this.map = map; //set map
        generateWelcome();  //display eye-candy
        intro();    //display basic instructions/welcome
        options();  //display options
    }

    /**
     * The intro() method has a simple purpose: welcome the user and then display
     * some basic instructions to get the user started.
     */
    private static void intro() {
        System.out.println("Welcome to the Rowan Direction System! "
                + "We apologize for and empathize with your lostness!");
        System.out.println("This program allows you to get directions from building A to building B. ");
        System.out.println("These directions will include distance, direction, and "
                + "walking time.");
        System.out.println("The valid options are displayed below.");
    }

    /**
     * The options() method is also very simple: display the valid options, get
     * the user's choice from stdin, and call the appropriate method. A try/catch
     * block is implemented to ensure the user does not crash the program or become
     * lost if they accidentally enter a String or some other data type instead of int
     */
    private static void options() {
        System.out.println("\nTo select an option, simply enter it's corresponding number. Choose wisely.");
        System.out.println("\t1. Get Directions");
        System.out.println("\t2. View Map");
        System.out.println("\t3. Load Map");
        System.out.println("\t4. Help");
        System.out.println("\t5. Quit\n");
        try {
        //A string switch statement would work as well, but only with JDK7 and up.
        //Due to this, I am using an int switch for compatibility.
        command = Integer.parseInt(scanner.nextLine()); //get choice
        switch (command) {
            case 1:
                getDirections();
                break;
            case 2:
                viewMap(false); //false flag shows we are not in a method
                break;
            case 3:
                loadMap();
                break;
            case 4:
                help();
                break;
            case 5:
                quit();
                break;
            default:
                System.out.println(command + " is not a recognized command. "
                        + "Please try again.");
                options();
                break;
        }
        } catch (Exception e) { //user entered a non-int datatype
            System.out.println("Command not recognized.\nInput must be "
                    + "a number between 1 and 5. Please try again.");
            options();  //display options again for continuity
        }
    }

    /**
     * The getDirections() method is the meat of the project. It will get the user's
     * current location, as well as their desired location, and find the shortest route
     * between the 2 (if there is one) and display it in an easy-to-read format to stdout.
     */
    private static void getDirections() {
        boolean found = false;  //way to differentiate between existing/non-existing initial buildings
        Iterator it = map.entrySet().iterator();
        System.out.println("Where are you currently located?");
        String loc = scanner.nextLine();  //get current location
        //Get current location and complete map if it exists
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Building b = (Building) entry.getValue();
            //toLowerCase for ease of use
            if (b.toString().toLowerCase().equals(loc.toLowerCase())) {
                Dijkstras.computeRoutes(b); //calculate routes
                found = true;   //building exists
            }
        }
        if (!found) {   //If not found, current location doesn't exist on map, report
            System.out.println("Sorry, " + loc + " is not a supported building.");
            System.out.println("If you would like to try again, press '1'.\n"
                    + "If you would like to view the map and try again, press '2'.\n"
                    + "If you would like to return to the main menu, press '3'.\n");
            //Again we need a try/catch block to ensure user enters an integer
            try {
            loc = scanner.nextLine();   //get user's new choice
            switch (Integer.parseInt(loc)) {
                case 1:
                    getDirections();
                    break;
                case 2:
                    viewMap(true);  //true parameter indicates we are calling from inside a method
                    getDirections();
                    break;
                case 3:
                    options();
                    break;
                default:
                    System.out.println("Sorry " + loc + "is not a recognized command."
                            + " Please try again.");
                    break;
            }
            } catch (Exception e) { //user entered a non-int datatype
                System.out.println("Command not recognized.\nInput must be "
                        + "a number between 1 and 3. Returning to main menu.");
                options();  //return to main menu
            }
        }
        found = false;  //reset found

        it = map.entrySet().iterator(); //reset the iterator
        System.out.println("What is your desired location?");
        loc = scanner.nextLine();
        //Get desired location and print shortest path if it exists
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();    //get next entry from map
            Building b = (Building) entry.getValue();   //get building (value) from entry
            //toLowerCase for ease of use (corresponds with getting loc above)
            if (b.toString().toLowerCase().equals(loc.toLowerCase())) {
                //at this stage, both buildings are known, find the route
                Dijkstras.printRoute(Dijkstras.getShortestRouteTo(b));
                found = true;   //building exists
            }
        }
        if (!found) {   //Destination doesn't exist on map, report
            System.out.println("Sorry, " + loc + " is not a supported building."
                    + " Please try again.");
        }
        found = false;  //Reset found to false to ensure it works in a persistant environment
        
        //if the map is still default we can recreate it, eliminating the found paths
        //and enabling us to get more directions
        if (stillDefault)
        {
            map = Driver.generateDefaultMap();  //re-generate the map
            options();                          //display options again
        }
        //otherwise we cannot recreate the map and must exit
        else
        {
            quit();
        }
    }

    /**
     * The viewMap() method simply displays the current map on stdout. It takes
     * a boolean argument to allow for use in methods outside of the initial Menu()
     * call, for example in getDirections(). True means we do not need to display
     * the main menu again, false means we do.
     *
     * @param inFn Whether or not to display the main menu
     */
    private static void viewMap(boolean inFn) {
        Iterator it = map.entrySet().iterator();
        System.out.println("The building(s) that this map supports are:");
        //print all the buildings
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();    //get next entry from map
            Building b = (Building) entry.getValue();   //get building (value) from entry
            System.out.println("\t" + b.toString());
        }
        System.out.println();
        //if we're in a function, we don't need to display the main menu again
        if (!inFn) {
            options();  //re-display initial menu
        }
    }

    /**
     *the loadMap() method is responsible for loading user-created maps into the
     * system. It does so by relying on the FileManager() class.
     */
    private static void loadMap() {
        System.out.println("Please enter the file path of the map you would "
                + "like to load:\n");
        String path = scanner.nextLine();   //get the filepath
        map = FileManager.loadMap(path);    //set the map
        stillDefault = false;               //the map is no longer the default map
        options();                          //display main menu
    }

    /**
     * The help() method's job is to display a brief description of all available
     * options within the main menu.
     */
    private static void help() {
        System.out.println("1. Get Directions");
        System.out.println("\tThis option allows you to input your current"
                + " location as well as a destination.");
        System.out.println("\tIt will calculate the shortest path between"
                + " the two locations (if there is one) and display it.");
        System.out.println("2. View Map");
        System.out.println("\tThis option allows you to view all of the"
                + " buildings on the current map");
        System.out.println("3. Load Map");
        System.out.println("\tThis option allows you to load a new map"
                + " into the program.");
        System.out.println("\tThis is done through a textfile with the "
                + "following format:");
        System.out.println("\t\t<key reference> <name of building>");
        System.out.println("\t\t#");
        System.out.println("\t\t<from key> <to key> <distance>");
        System.out.println("4. Help");
        System.out.println("\tThis option displays this menu.");
        System.out.println("5. Quit");
        System.out.println("\tThis option exits the program.\n");
        options();
    }

    /**
     * The quit() method thanks the user for using the system and exits without error.
     */
    private static void quit() {
        System.out.println("Thank you for using the Rowan Direction System!");
        System.exit(0);
    }

    /**
     * The welcome0() method is simply an eye-candy user-experience booster.
     */
    private static void welcome0() {
        System.out.println("    .:'/*/'`:,·:~·–:.,              ,._., ._                                        __  '        ");
        System.out.println("   /::/:/:::/:::;::::::/`':.,'       /::::::::::'/:/:~-.,                        ,·:'´/::::/'`;·.,   ");
        System.out.println("    /·*'`·´¯'`^·-~·:–-'::;:::'`;     /:-·:;:-·~·';/:::::::::`·-.              .:´::::/::::/:::::::`;");
        System.out.println(" '                        '`;::'i‘   ';           '`~-:;:::::::::'`,          /:;:· '´ ¯¯'`^·-;::::");
        System.out.println("   '`;        ,– .,        'i:'/     ',.                 '`·-:;:::::'i'‘      /·´           _   '`;/‘  ");
        System.out.println("     i       i':/:::';       ;/'        `'i      ,_            '`;:::'¦‘     'i            ;::::'`;*    ");
        System.out.println("     i       i/:·'´       ,:''           'i      ;::/`:,          i'::/       `;           '`;:::::'`:, ");
        System.out.println("     '; '    ,:,\\     ~;'´:::'`:,       _;     ;:/;;;;:';        ¦'/           `·,           '`·;:::::'");
        System.out.println("     'i      i:/\\       `;::::/:'`;'   /::';   ,':/::::::;'       ,´          ,~:-'`·,           `:;::/");
        System.out.println("      ;     ;/   \\       '`:/::::/',/-:;_i  ,'/::::;·´        ,'´          /:::::::::';           ';/ ");
        System.out.println("      ';   ,'       \\         '`;/' '`·.     `'¯¯     '   , ·'´           ,:~·- . -·'´          ,'´  ");
        System.out.println("       `'*´          '`~·-·^'´        `' ~·- .,. -·~ ´                '`·,               , ·'´    ");
        System.out.println("                                             '                               '`*^·–·^*'´'           ‘");
    }

    /**
     * The welcome1() method is simply an eye-candy user-experience booster.
     */
    private static void welcome1() {
        System.out.println("RRRRRRRRRRRRRRRRR   DDDDDDDDDDDDD           SSSSSSSSSSSSSSS ");
        System.out.println("R::::::::::::::::R  D::::::::::::DDD      SS:::::::::::::::S");
        System.out.println("R::::::RRRRRR:::::R D:::::::::::::::DD   S:::::SSSSSS::::::S");
        System.out.println("RR:::::R     R:::::RDDD:::::DDDDD:::::D  S:::::S     SSSSSSS");
        System.out.println("  R::::R     R:::::R  D:::::D    D:::::D S:::::S ");
        System.out.println("  R::::R     R:::::R  D:::::D     D:::::DS:::::S ");
        System.out.println("  R::::RRRRRR:::::R   D:::::D     D:::::D S::::SSSS ");
        System.out.println("  R:::::::::::::RR    D:::::D     D:::::D  SS::::::SSSSS");
        System.out.println("  R::::RRRRRR:::::R   D:::::D     D:::::D    SSS::::::::SS ");
        System.out.println("  R::::R     R:::::R  D:::::D     D:::::D       SSSSSS::::S ");
        System.out.println("  R::::R     R:::::R  D:::::D     D:::::D            S:::::S");
        System.out.println("  R::::R     R:::::R  D:::::D    D:::::D             S:::::S");
        System.out.println("RR:::::R     R:::::RDDD:::::DDDDD:::::D  SSSSSSS     S:::::S");
        System.out.println("R::::::R     R:::::RD:::::::::::::::DD   S::::::SSSSSS:::::S");
        System.out.println("R::::::R     R:::::RD::::::::::::DDD     S:::::::::::::::SS ");
        System.out.println("RRRRRRRR     RRRRRRRDDDDDDDDDDDDD         SSSSSSSSSSSSSSS ");
    }

    /**
     * The welcome2() method is simply an eye-candy user-experience booster.
     */
    private static void welcome2() {
        System.out.println("   ____     ____    ____     ");
        System.out.println("U |  _\"\\ u |  _\"\\  / __\"| u");
        System.out.println(" \\| |_) |//| | | |<\\___ \\/ ");
        System.out.println("  |  _ <  U| |_| |\\u___) | ");
        System.out.println("  |_| \\_\\  |____/ u|____/>> ");
        System.out.println("  //   \\\\_  |||_    )(  (__) ");
        System.out.println(" (__)  (__)(__)_)  (__)   ");
    }

    /**
     * The generateWelcome() method spawns a random number between 0 and 2 and
     * then displays the corresponding eye-candy message. This ensures that
     * user-experience is a bit different each time the program is run.
     */
    private static void generateWelcome() {
        Random rand = new Random();
        int which = rand.nextInt(3);
        switch (which) {
            case 0:
                welcome0();
                break;
            case 1:
                welcome1();
                break;
            case 2:
                welcome2();
                break;
            default:
                System.out.print("");
        }
    }
}
