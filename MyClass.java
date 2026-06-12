import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class MyClass
{
    public static Bot[] enemyAlliance(Bot[] bots)
    {
        Bot[] enAlliance = new Bot[3];

        int enFirst  = 0;
        int enSecond = 0;
        int enThird  = 0;

        while ((enFirst == enSecond) || (enSecond == enThird) || (enThird == enFirst))
        {
            enFirst  = (int)(Math.random() * bots.length);
            enSecond = (int)(Math.random() * bots.length);
            enThird  = (int)(Math.random() * bots.length);
        }

        enAlliance[0] = bots[enFirst];
        enAlliance[1] = bots[enSecond];
        enAlliance[2] = bots[enThird];

        return enAlliance;
    }

    public static Bot[] myAlliance(Scanner scan, Bot[] bots, int allianceNumber)
    {
        Bot[] myAlliance = new Bot[2];

        System.out.println("================================");
        System.out.println("       ALLIANCE SELECTION       ");
        System.out.println("================================");
        System.out.println("You are Alliance " + allianceNumber + ".");
        System.out.println("You will get 5 options for your first pick.");
        System.out.println("Then you will get 5 options for your second pick.");
        System.out.println("Lower alliances get weaker pick options.");
        System.out.println();

        Bot[] firstBoard = createPickBoard(bots, allianceNumber, null);

        System.out.println("--- PICK 1 OPTIONS ---");
        printBoard(firstBoard);

        int firstChoice = chooseFromBoard(scan, firstBoard);
        myAlliance[0] = firstBoard[firstChoice];

        System.out.println("Selected: " + myAlliance[0].getName()
            + " (" + myAlliance[0].getNumber() + ")");
        System.out.println();

        Bot[] secondBoard = createPickBoard(bots, allianceNumber, myAlliance[0]);

        System.out.println("--- PICK 2 OPTIONS ---");
        printBoard(secondBoard);

        int secondChoice = chooseFromBoard(scan, secondBoard);
        myAlliance[1] = secondBoard[secondChoice];

        System.out.println("Selected: " + myAlliance[1].getName()
            + " (" + myAlliance[1].getNumber() + ")");
        System.out.println();

        System.out.println("================================");
        System.out.println("          YOUR ALLIANCE         ");
        System.out.println("================================");
        System.out.println("Teammate 1: " + myAlliance[0].getName()
            + " (" + myAlliance[0].getNumber() + ")"
            + "  Avg Score: " + myAlliance[0].getAvgScore());
        System.out.println("Teammate 2: " + myAlliance[1].getName()
            + " (" + myAlliance[1].getNumber() + ")"
            + "  Avg Score: " + myAlliance[1].getAvgScore());
        System.out.println("================================");
        System.out.println();

        return myAlliance;
    }

    private static Bot[] createPickBoard(Bot[] bots, int allianceNumber, Bot alreadyPicked)
    {
        Bot[] board = new Bot[5];

        int minScore = getMinScoreForAlliance(allianceNumber);
        int maxScore = getMaxScoreForAlliance(allianceNumber);
        List<Bot> eligible = new ArrayList<Bot>();

        for (Bot bot : bots)
        {
            if (bot != alreadyPicked
                && bot.getAvgScore() >= minScore
                && bot.getAvgScore() <= maxScore)
            {
                eligible.add(bot);
            }
        }

        for (int i = 0; i < board.length; i++)
        {
            int randomIndex = (int)(Math.random() * eligible.size());
            board[i] = eligible.remove(randomIndex);
        }

        return board;
    }

    private static int getMinScoreForAlliance(int allianceNumber)
    {
        if (allianceNumber == 1)
        {
            return 180;
        }
        else if (allianceNumber == 2)
        {
            return 160;
        }
        else if (allianceNumber == 3)
        {
            return 140;
        }
        else if (allianceNumber == 4)
        {
            return 120;
        }
        else if (allianceNumber == 5)
        {
            return 100;
        }
        else if (allianceNumber == 6)
        {
            return 75;
        }
        else if (allianceNumber == 7)
        {
            return 40;
        }
        else
        {
            return 0;
        }
    }

    private static int getMaxScoreForAlliance(int allianceNumber)
    {
        if (allianceNumber == 1)
        {
            return Integer.MAX_VALUE;
        }
        else if (allianceNumber == 2)
        {
            return 240;
        }
        else if (allianceNumber == 3)
        {
            return 220;
        }
        else if (allianceNumber == 4)
        {
            return 200;
        }
        else if (allianceNumber == 5)
        {
            return 180;
        }
        else if (allianceNumber == 6)
        {
            return 155;
        }
        else if (allianceNumber == 7)
        {
            return 130;
        }
        else
        {
            return 110;
        }
    }

    private static void printBoard(Bot[] board)
    {
        for (int i = 0; i < board.length; i++)
        {
            System.out.println(i + ": " + board[i].getName()
                + " (" + board[i].getNumber() + ")"
                + "  Avg Score: " + board[i].getAvgScore());
        }

        System.out.println();
        System.out.println("Choose one bot from this list, 0-" + (board.length - 1) + ":");
    }

    private static int chooseFromBoard(Scanner scan, Bot[] board)
    {
        return Input.readInt(scan, 0, board.length - 1,
            "Enter bot choice (0-" + (board.length - 1) + "):");
    }

    public static void main(String args[])
    {
        Scanner scan = new Scanner(System.in);

        Bot[] bots = BotData.createBots();

        // Phase 1: Build robot and choose alliance
        Builder myRobot = Builder.build(scan);

        // Phase 2: Pick 2 alliance partners
        Bot[] alliance = myAlliance(scan, bots, myRobot.getAlliance());

        // Alliance summary
        int totalAvgScore = myRobot.getScore()
            + alliance[0].getAvgScore()
            + alliance[1].getAvgScore();

        System.out.println("================================");
        System.out.println("        ALLIANCE SUMMARY        ");
        System.out.println("================================");
        System.out.println("Your robot score:   " + myRobot.getScore()        + " pts");
        System.out.println("Teammate 1 score:   " + alliance[0].getAvgScore() + " pts");
        System.out.println("Teammate 2 score:   " + alliance[1].getAvgScore() + " pts");
        System.out.println("Total alliance avg: " + totalAvgScore             + " pts");
        System.out.println("Alliance number:    Alliance " + myRobot.getAlliance());
        System.out.println("================================\n");

        // Phase 3: Double elimination bracket
        boolean won = Bracket.runBracket(scan, myRobot, alliance, bots);

        // Phase 4: Win / lose screen
        if (won)
        {
            System.out.println("████████████████████████████████");
            System.out.println("█                              █");
            System.out.println("█    CHAMPIONSHIP WINNERS!     █");
            System.out.println("█                              █");
            System.out.println("████████████████████████████████");
            System.out.println();
            System.out.println("  Alliance " + myRobot.getAlliance() + " — WORLD CHAMPIONS");
            System.out.println();
            System.out.println("  Your Robot");
            System.out.println("  ───────────────────────────────");
            myRobot.displayBuild();
            System.out.println();
            System.out.println("  Your Alliance");
            System.out.println("  ───────────────────────────────");
            System.out.println("  " + alliance[0].getName()
                + " (" + alliance[0].getNumber() + ")"
                + "  —  " + alliance[0].getAvgScore() + " pts avg");
            System.out.println("  " + alliance[1].getName()
                + " (" + alliance[1].getNumber() + ")"
                + "  —  " + alliance[1].getAvgScore() + " pts avg");
            System.out.println();
            System.out.println("  Combined alliance avg: " + totalAvgScore + " pts");
            System.out.println();
            System.out.println("████████████████████████████████");
            System.out.println("  Congratulations, you're the");
            System.out.println("  FRC World Champion!");
            System.out.println("████████████████████████████████");
        }
        else
        {
            System.out.println("================================");
            System.out.println("            GAME OVER           ");
            System.out.println("================================");
            System.out.println("Your alliance was eliminated from playoffs.");
            System.out.println("Try building a stronger robot or choosing a better alliance number.");
            System.out.println("================================");
        }
    }
}
