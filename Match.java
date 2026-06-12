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

        System.out.println("Enemy match score: ~" + enemyScore + " pts");
        System.out.println();
        System.out.println("Your robot output: " + myRobot.getScore() + " pts");
        System.out.println("Teammates:  " + myTeam[0].getName() + " (" + myTeam[0].getAvgScore() + " pts avg)"
            + "  |  " + myTeam[1].getName() + " (" + myTeam[1].getAvgScore() + " pts avg)");
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

            int choice = -1;

            while (choice < 1 || choice > 4)
            {
                choice = scan.nextInt();

                if (choice < 1 || choice > 4)
                {
                    System.out.println("Invalid. Enter 1-4:");
                }
            }

            System.out.println();

            switch (choice)
            {
                case 1:
                    playerScore = shoot(playerScore, myRobot);
                    break;

                case 2:
                    playerScore = pass(playerScore, myTeam);
                    break;

                case 3:
                    enemyScore = defense(enemyScore, enemyBase);
                    break;

                case 4:
                    playerScore = intake(playerScore, myRobot);
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

    private static int shoot(int currentScore, Builder myRobot)
    {
        int shooterScore = myRobot.getShooter().getAddScore();

        double missChance = myRobot.getShooter().getDefense() ? 0.35 : 0.20;

        if (Math.random() > missChance)
        {
            int min  = (int)(shooterScore * 0.50);
            int max  = (int)(shooterScore * 1.00);
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
        int avgTeamScore = (myTeam[0].getAvgScore() + myTeam[1].getAvgScore()) / 2;

        int min  = (int)(avgTeamScore * 0.18);
        int max  = (int)(avgTeamScore * 0.28);
        int gain = min + (int)(Math.random() * (max - min + 1));

        currentScore += gain;

        System.out.println("PASS! Teammate scored " + gain + " pts.");

        return currentScore;
    }

    private static int defense(int enemyScore, int enemyBase)
    {
        if (Math.random() < 0.60)
        {
            int min = (int)(enemyBase * 0.08);
            int max = (int)(enemyBase * 0.14);
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

    private static int intake(int currentScore, Builder myRobot)
    {
        int indexerScore = myRobot.getIndexer().getAddScore();

        double speedBonus = myRobot.getIntake().getSpeed()
            * myRobot.getHopper().getSpeed();

        int min = (int)(indexerScore * 0.30 * speedBonus);
        int max = (int)(indexerScore * 0.55 * speedBonus);
        int gain = min + (int)(Math.random() * (max - min + 1));

        currentScore += gain;

        System.out.println("INTAKE! Indexed " + gain + " pts.");

        return currentScore;
    }
}