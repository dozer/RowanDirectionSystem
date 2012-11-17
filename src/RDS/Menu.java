package RDS;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author nothing
 */
public class Menu {

    private static Building[] map;
    private static int command = -1;
    private static Scanner scanner = new Scanner(System.in);

    public Menu(Building[] map) {
        this.map = map;
        generateWelcome();
        intro();
        options();
    }

    public static void intro() {
        System.out.println("Welcome to the Rowan Direction System! "
                + "We apologize and empathize with your lostness!");
        System.out.println("This program allows you to get directions from building A to building B. ");
        System.out.println("These directions will include distance, direction, and "
                + "walking time.");
        System.out.println("The valid options are displayed below.");
    }

    public static void options() {
        System.out.println("To select an option, simply enter it's corresponding number. Choose wisely.");
        System.out.println("\t1. Get Directions");
        System.out.println("\t2. View Map");
        System.out.println("\t3. Save Map");
        System.out.println("\t4. Load Map");
        System.out.println("\t5. Help");
        System.out.println("\t6. Quit\n");

        //A string switch statement would work as well, but only with JDK7 and up.
        //Due to this, I am using an int switch for compatibility.
        command = Integer.parseInt(scanner.nextLine());
        switch (command) {
            case 1:
                //get directions
                getDirections();
                break;
            case 2:
                //view map
                viewMap(false);
                break;
            case 3:
                //save map
                saveMap();
                break;
            case 4:
                //load map
                loadMap();
                break;
            case 5:
                //help
                help();
                break;
            case 6:
                //quit
                quit();
                break;
            default:
                System.out.println(command + " is not a recognized command. "
                        + "Please try again.");
                options();
                break;
        }
    }

    public static void getDirections() {
        boolean found = false;

        System.out.println("Where are you currently located?");
        String loc = scanner.nextLine();
        //Get current location and complete map if it exists
        for (Building b : map) {
            if (b.toString().equals(loc)) {
                Dijkstras.computeRoutes(b);
                found = true;   //found it!
            }
        }
        if (!found) {   //Current location doesn't exist on map, report
            System.out.println("Sorry, " + loc + " is not a supported building.");
            System.out.println("If you would like to try again, press '1'.\n" +
                    "If you would like to view the map and try again, press '2'.\n" +
                    "If you would like to return to the main menu, press '3'.\n");
            loc = scanner.nextLine();
            switch (Integer.parseInt(loc))
            {
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
                    System.out.println("Sorry " + loc + "is not a recognized command." +
                            " Please try again.");
                    break;
            }
        }
        found = false;

        System.out.println("What is your desired location?");
        loc = scanner.nextLine();
        //Get desired location and print shortest path if it exists
        for (Building b : map) {
            if (b.toString().equals(loc)) {
                Dijkstras.printRoute(Dijkstras.getShortestRouteTo(b));
                found = true;   //found it!
            }
        }
        if (!found) {   //Destination doesn't exist on map, report
            System.out.println("Sorry, " + loc + " is not a supported building."
                    + " Please try again.");
        }
        found = false;  //Reset found to false to ensure it works in a persistant environment

        options();  //re-display initial menu
    }

    public static void viewMap(boolean inFn) {
        System.out.println("The building(s) that this map supports are:");
        for (Building b : map)
        {
            System.out.println("\t" + b.toString());
        }
        System.out.println();
        if (!inFn)
        {
            options();  //re-display initial menu
        }
    }

    public static void saveMap() {
        System.out.println("In save.");
        options();
    }

    public static void loadMap() {
        System.out.println("In load.");
        options();
    }

    public static void help() {
        System.out.println("-+-+- Get Directions -+-+-");
        System.out.println("\tThis option allows you to input your current"
                + "location as well as a destination.");
        System.out.println("\tIt will calculate the shortest path between"
                + "the two locations (if there is one) and display it.");
        System.out.println("-+-+- View Map -+-+-");
        System.out.println("\tThis option allows you to view all of the"
                + "buildings on the current map");
        System.out.println("-+-+- Load Map -+-+-");
        System.out.println("\tThis option allows you to load a new map"
                + "into the program.");
        System.out.println("\tThis is done through a textfile with the "
                + "following format:");
        System.out.println("\t\t<INSERT FORMAT HERE>");
        System.out.println("-+-+- Help -+-+-");
        System.out.println("\tThis option displays this menu.");
        System.out.println("-+-+- Quit -+-+-");
        System.out.println("\tThis option exits the program.\n");
        options();
    }

    public static void quit() {
        System.out.println("Thank you for using the Rowan Direction System!");
        System.exit(0);
    }

    public static void welcome0() {
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

    public static void welcome1() {
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

    public static void welcome2() {
        System.out.println("   ____     ____    ____     ");
        System.out.println("U |  _\"\\ u |  _\"\\  / __\"| u");
        System.out.println(" \\| |_) |//| | | |<\\___ \\/ ");
        System.out.println("  |  _ <  U| |_| |\\u___) | ");
        System.out.println("  |_| \\_\\  |____/ u|____/>> ");
        System.out.println("  //   \\\\_  |||_    )(  (__) ");
        System.out.println(" (__)  (__)(__)_)  (__)   ");
    }

    public static void generateWelcome() {
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
