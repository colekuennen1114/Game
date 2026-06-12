public class Bracket
{
    private static Builder pRobot;
    private static Bot[] pTeam;
    private static int pIdx;
    private static Bot[][] alliances;
    private static String[] names;
    private static int playerLosses;

    public static boolean runBracket(java.util.Scanner scan, Builder myRobot, Bot[] myTeam, Bot[] bots)
    {
        pRobot = myRobot;
        pTeam = myTeam;
        pIdx = myRobot.getAlliance() - 1;
        playerLosses = 0;

        alliances = new Bot[8][];
        names = new String[8];

        for (int i = 0; i < 8; i++)
        {
            if (i == pIdx)
            {
                names[i] = "YOUR ALLIANCE";
            }
            else
            {
                alliances[i] = generateAIAlliance(bots, i + 1);
                names[i] = alliances[i][0].getName() + " / "
                    + alliances[i][1].getName() + " / "
                    + alliances[i][2].getName();
            }
        }

        System.out.println("================================");
        System.out.println("        BRACKET ALLIANCES       ");
        System.out.println("================================");

        for (int i = 0; i < 8; i++)
        {
            String mark = "";

            if (i == pIdx)
            {
                mark = "  ◄ YOU";
            }

            System.out.println("Alliance " + (i + 1) + ": " + names[i] + mark);
        }

        System.out.println("================================");
        System.out.println();

        int round = 1;

        while (playerLosses < 2 && round <= 6)
        {
            int opponent = chooseOpponent();

            System.out.println("================================");
            System.out.println("           PLAYOFF MATCH        ");
            System.out.println("================================");

            if (playerLosses == 0)
            {
                System.out.println("Bracket: Upper Bracket");
                System.out.println("You have 0 losses.");
                System.out.println("If you lose, you will move to the lower bracket.");
            }
            else
            {
                System.out.println("Bracket: Lower Bracket");
                System.out.println("You have 1 loss.");
                System.out.println("THIS IS AN ELIMINATION MATCH.");
                System.out.println("If you lose, your playoffs are over.");
            }

            System.out.println();
            System.out.println("Alliance " + (pIdx + 1) + " vs Alliance " + (opponent + 1));
            System.out.println("Opponent: " + names[opponent]);
            System.out.println("================================");
            System.out.println();

            boolean playerWon = Match.playMatch(scan, pRobot, pTeam, alliances[opponent]);

            if (playerWon)
            {
                System.out.println(">> YOUR ALLIANCE WINS!");
                System.out.println(">> Current losses: " + playerLosses);
                System.out.println(">> You advance to the next playoff match.");
                System.out.println();
            }
            else
            {
                playerLosses++;

                System.out.println(">> YOUR ALLIANCE LOST.");
                System.out.println(">> Current losses: " + playerLosses);

                if (playerLosses == 1)
                {
                    System.out.println(">> You are NOT eliminated.");
                    System.out.println(">> You move to the LOWER BRACKET.");
                    System.out.println(">> Keep playing.");
                }
                else
                {
                    System.out.println(">> You lost twice.");
                    System.out.println(">> You are ELIMINATED.");
                    System.out.println();
                    return false;
                }

                System.out.println();
            }

            round++;
        }

        if (playerLosses < 2)
        {
            System.out.println("================================");
            System.out.println("       CHAMPIONSHIP MATCH       ");
            System.out.println("================================");
            System.out.println("You made it to the championship!");
            System.out.println("Win this match to become champion.");
            System.out.println("================================");
            System.out.println();

            int finalOpponent = chooseOpponent();

            boolean wonFinal = Match.playMatch(scan, pRobot, pTeam, alliances[finalOpponent]);

            if (wonFinal)
            {
                System.out.println("YOU ARE THE CHAMPION!");
                return true;
            }
            else
            {
                playerLosses++;

                if (playerLosses >= 2)
                {
                    System.out.println("You lost in the championship and were eliminated.");
                    return false;
                }
                else
                {
                    System.out.println("You lost the championship match.");
                    return false;
                }
            }
        }

        return false;
    }

    private static int chooseOpponent()
    {
        int opponent = pIdx;

        while (opponent == pIdx)
        {
            opponent = (int)(Math.random() * 8);
        }

        return opponent;
    }

    private static Bot[] generateAIAlliance(Bot[] bots, int allianceNumber)
    {
        Bot[] team = new Bot[3];

        int min = getMinIndexForAlliance(allianceNumber);
        int max = getMaxIndexForAlliance(allianceNumber);

        int count = 0;

        while (count < team.length)
        {
            int index = min + (int)(Math.random() * (max - min + 1));
            Bot selected = bots[index];

            if (!alreadyPicked(team, selected, count))
            {
                team[count] = selected;
                count++;
            }
        }

        return team;
    }

    private static boolean alreadyPicked(Bot[] team, Bot bot, int filledSpots)
    {
        for (int i = 0; i < filledSpots; i++)
        {
            if (team[i] == bot)
            {
                return true;
            }
        }

        return false;
    }

    private static int getMinIndexForAlliance(int allianceNumber)
    {
        if (allianceNumber == 1)
        {
            return 0;
        }
        else if (allianceNumber == 2)
        {
            return 5;
        }
        else if (allianceNumber == 3)
        {
            return 10;
        }
        else if (allianceNumber == 4)
        {
            return 15;
        }
        else if (allianceNumber == 5)
        {
            return 22;
        }
        else if (allianceNumber == 6)
        {
            return 30;
        }
        else if (allianceNumber == 7)
        {
            return 40;
        }
        else
        {
            return 50;
        }
    }

    private static int getMaxIndexForAlliance(int allianceNumber)
    {
        if (allianceNumber == 1)
        {
            return 24;
        }
        else if (allianceNumber == 2)
        {
            return 30;
        }
        else if (allianceNumber == 3)
        {
            return 38;
        }
        else if (allianceNumber == 4)
        {
            return 45;
        }
        else if (allianceNumber == 5)
        {
            return 55;
        }
        else if (allianceNumber == 6)
        {
            return 65;
        }
        else
        {
            return 74;
        }
    }
}