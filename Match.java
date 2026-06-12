import java.util.Scanner;

public class Match
{
    private static final int ROUNDS = 6;

    public static boolean playMatch(Scanner scan, Builder myRobot, Bot[] myTeam, Bot[] enemyTeam)
    {
        int enemyBase  = enemyTeam[0].getAvgScore()
            + enemyTeam[1].getAvgScore()
            + enemyTeam[2].getAvgScore();

        int enemyScore = enemyBase + (int)(Math.random() * 61) - 30;

        if (enemyScore < 0)
        {
            enemyScore = 0;
        }

        int playerScore = 0;

        System.out.println("================================");
        System.out.println("           MATCH START          ");
        System.out.println("================================");
        System.out.println("Enemy Alliance:");

        for (Bot b : enemyTeam)
        {
            System.out.println("  " + b.getName() + " (" + b.getNumber() + ")  avg: " + b.getAvgScore() + " pts");
        }

        System.out.println("Enemy starting score: " + enemyScore + " pts");
        System.out.println();
        System.out.println("Your robot performance rating: " + myRobot.getScore());
        System.out.println("Teammates:  " + myTeam[0].getName() + " (" + myTeam[0].getAvgScore() + " pts avg)"
            + "  |  " + myTeam[1].getName() + " (" + myTeam[1].getAvgScore() + " pts avg)");
        System.out.println("Your alliance starts at 0 points.");
        System.out.println();

        for (int round = 1; round <= ROUNDS; round++)
        {
            System.out.println("--- Round " + round + " of " + ROUNDS + " ---");
            System.out.println("Your score: " + playerScore + "  |  Enemy score: " + enemyScore);
            System.out.println();
            System.out.println("Choose your action:");
            System.out.println("1: SHOOT   - Score yourself   (risky, high reward)");
            System.out.println("2: PASS    - Teammate scores  (safe, moderate reward)");
            System.out.println("3: DEFENSE - Block the enemy  (risky, reduces enemy score)");
            System.out.println("4: INTAKE  - Collect pieces   (safe, low guaranteed reward)");
            System.out.println();

            int choice = Input.readInt(scan, 1, 4, "Enter action (1-4):");

            System.out.println();

            switch (choice)
            {
                case 1:
                    playerScore = shoot(playerScore, myRobot, myTeam);
                    break;

                case 2:
                    playerScore = pass(playerScore, myTeam);
                    break;

                case 3:
                    enemyScore = defense(enemyScore, enemyBase, myTeam);
                    break;

                case 4:
                    playerScore = intake(playerScore, myRobot, myTeam);
                    break;
            }
        }

        System.out.println("================================");
        System.out.println("          MATCH RESULT          ");
        System.out.println("================================");
        System.out.println("Final  -  You: " + playerScore + " pts  |  Enemy: " + enemyScore + " pts");
        System.out.println();

        if (playerScore > enemyScore)
        {
            System.out.println("YOU WIN!");
        }
        else
        {
            System.out.println("YOU LOSE.");
        }

        System.out.println("================================");
        System.out.println();

        return playerScore > enemyScore;
    }

    private static int shoot(int currentScore, Builder myRobot, Bot[] myTeam)
    {
        int shooterScore = myRobot.getShooter().getAddScore();
        int robotRating = myRobot.getScore();
        int teammateSupport = getTeammateTotal(myTeam);

        double missChance = 0.20;

        if (Math.random() > missChance)
        {
            int min  = (int)(robotRating * 0.10 + shooterScore * 0.10
                + teammateSupport * 0.04);
            int max  = (int)(robotRating * 0.18 + shooterScore * 0.20
                + teammateSupport * 0.08);
            int gain = min + (int)(Math.random() * (max - min + 1));

            currentScore += gain;

            System.out.println("SHOT! Scored " + gain + " pts.");
        }
        else
        {
            System.out.println("MISSED! Lost Vision. No points scored.");
        }

        return currentScore;
    }

    private static int pass(int currentScore, Bot[] myTeam)
    {
        int teammateTotal = getTeammateTotal(myTeam);
        int min  = (int)(teammateTotal * 0.12);
        int max  = (int)(teammateTotal * 0.22);
        int gain = min + (int)(Math.random() * (max - min + 1));

        currentScore += gain;

        System.out.println("PASS! Teammate scored " + gain + " pts.");

        return currentScore;
    }

    private static int defense(int enemyScore, int enemyBase, Bot[] myTeam)
    {
        int teammateTotal = getTeammateTotal(myTeam);
        double successChance = Math.min(0.80, 0.50 + teammateTotal / 1500.0);

        if (Math.random() < successChance)
        {
            int min = (int)(enemyBase * 0.03 + teammateTotal * 0.03);
            int max = (int)(enemyBase * 0.06 + teammateTotal * 0.06);
            int reduction = min + (int)(Math.random() * (max - min + 1));

            enemyScore -= reduction;

            if (enemyScore < 0)
            {
                enemyScore = 0;
            }

            System.out.println("DEFENSE! Blocked enemy: -" + reduction + " pts from their score.");
        }
        else
        {
            int penalty = 20 + (int)(Math.random() * 26);

            enemyScore += penalty;

            System.out.println("PENALTY! Foul called. Enemy gains +" + penalty + " pts.");
        }

        return enemyScore;
    }

    private static int intake(int currentScore, Builder myRobot, Bot[] myTeam)
    {
        int indexerScore = myRobot.getIndexer().getAddScore();

        double speedBonus = myRobot.getIntake().getSpeed()
            * myRobot.getHopper().getSpeed();

        int intakeScore = myRobot.getIntake().getAddScore();
        int collectionPower = indexerScore + intakeScore;
        int teammateSupport = getTeammateTotal(myTeam);
        int min = (int)(collectionPower * 0.16 * speedBonus
            + teammateSupport * 0.03);
        int max = (int)(collectionPower * 0.28 * speedBonus
            + teammateSupport * 0.06);
        int gain = min + (int)(Math.random() * (max - min + 1));

        currentScore += gain;

        System.out.println("INTAKE! Indexed " + gain + " pts.");

        return currentScore;
    }

    private static int getTeammateTotal(Bot[] myTeam)
    {
        return myTeam[0].getAvgScore() + myTeam[1].getAvgScore();
    }
}
