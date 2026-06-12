import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bracket
{
    private static class Alliance
    {
        private int seed;
        private Bot[] bots;
        private boolean player;

        Alliance(int allianceSeed, Bot[] allianceBots, boolean isPlayer)
        {
            seed = allianceSeed;
            bots = allianceBots;
            player = isPlayer;
        }
    }

    private static class MatchResult
    {
        private Alliance winner;
        private Alliance loser;

        MatchResult(Alliance matchWinner, Alliance matchLoser)
        {
            winner = matchWinner;
            loser = matchLoser;
        }
    }

    public static boolean runBracket(Scanner scan, Builder myRobot, Bot[] myTeam, Bot[] bots)
    {
        Alliance[] seeds = createAlliances(myRobot, myTeam, bots);
        printAlliances(seeds);

        MatchResult m1 = play(scan, myRobot, myTeam, seeds[0], seeds[7], "Upper Round 1");
        MatchResult m2 = play(scan, myRobot, myTeam, seeds[3], seeds[4], "Upper Round 1");
        MatchResult m3 = play(scan, myRobot, myTeam, seeds[1], seeds[6], "Upper Round 1");
        MatchResult m4 = play(scan, myRobot, myTeam, seeds[2], seeds[5], "Upper Round 1");

        MatchResult m5 = play(scan, myRobot, myTeam, m1.winner, m2.winner, "Upper Round 2");
        MatchResult m6 = play(scan, myRobot, myTeam, m3.winner, m4.winner, "Upper Round 2");
        announceSimulationBreak(m5, m6);

        MatchResult m7 = play(scan, myRobot, myTeam, m1.loser, m4.loser, "Lower Round 1 - Elimination");
        if (m7.loser.player)
        {
            return false;
        }

        MatchResult m8 = play(scan, myRobot, myTeam, m2.loser, m3.loser, "Lower Round 1 - Elimination");
        if (m8.loser.player)
        {
            return false;
        }

        announceNextPlayerMatch(m5, m6, "Upper Final");
        MatchResult m9 = play(scan, myRobot, myTeam, m5.winner, m6.winner, "Upper Final");
        MatchResult m10 = play(scan, myRobot, myTeam, m5.loser, m8.winner, "Lower Round 2 - Elimination");
        if (m10.loser.player)
        {
            return false;
        }

        MatchResult m11 = play(scan, myRobot, myTeam, m6.loser, m7.winner, "Lower Round 2 - Elimination");
        if (m11.loser.player)
        {
            return false;
        }

        MatchResult m12 = play(scan, myRobot, myTeam, m10.winner, m11.winner, "Lower Round 3 - Elimination");
        if (m12.loser.player)
        {
            return false;
        }

        MatchResult m13 = play(scan, myRobot, myTeam, m9.loser, m12.winner, "Lower Final - Elimination");
        if (m13.loser.player)
        {
            return false;
        }

        return playFinals(scan, myRobot, myTeam, m9.winner, m13.winner);
    }

    private static Alliance[] createAlliances(Builder myRobot, Bot[] myTeam, Bot[] bots)
    {
        Alliance[] seeds = new Alliance[8];
        List<Bot> available = new ArrayList<Bot>();

        for (Bot bot : bots)
        {
            if (bot != myTeam[0] && bot != myTeam[1])
            {
                available.add(bot);
            }
        }

        for (int seed = 1; seed <= 8; seed++)
        {
            if (seed == myRobot.getAlliance())
            {
                seeds[seed - 1] = new Alliance(seed, myTeam, true);
            }
            else
            {
                seeds[seed - 1] = new Alliance(seed, generateAIAlliance(available, seed), false);
            }
        }

        return seeds;
    }

    private static Bot[] generateAIAlliance(List<Bot> available, int seed)
    {
        Bot[] team = new Bot[3];
        int target = 280 - ((seed - 1) * 30);

        for (int i = 0; i < team.length; i++)
        {
            int bestIndex = 0;
            int bestDistance = Integer.MAX_VALUE;

            for (int j = 0; j < available.size(); j++)
            {
                int distance = Math.abs(available.get(j).getAvgScore() - target);

                if (distance < bestDistance)
                {
                    bestDistance = distance;
                    bestIndex = j;
                }
            }

            team[i] = available.remove(bestIndex);
            target -= 12;
        }

        return team;
    }

    private static MatchResult play(Scanner scan, Builder myRobot, Bot[] myTeam,
        Alliance first, Alliance second, String roundName)
    {
        System.out.println("================================");
        System.out.println(" " + roundName);
        System.out.println(" Alliance " + first.seed + " vs Alliance " + second.seed);
        System.out.println("================================");

        Alliance winner;

        if (first.player || second.player)
        {
            Alliance opponent = first.player ? second : first;
            boolean playerWon = Match.playMatch(scan, myRobot, myTeam, opponent.bots);
            winner = playerWon ? (first.player ? first : second) : opponent;
        }
        else
        {
            winner = simulateAI(first, second);
        }

        Alliance loser = winner == first ? second : first;
        System.out.println("Alliance " + winner.seed + " advances.");

        if (winner.player)
        {
            System.out.println("Your alliance is still alive in the playoffs.");
        }
        else if (loser.player)
        {
            System.out.println("Your alliance lost this match.");
        }

        System.out.println();
        return new MatchResult(winner, loser);
    }

    private static void announceSimulationBreak(MatchResult first, MatchResult second)
    {
        if (first.winner.player || second.winner.player)
        {
            System.out.println("================================");
            System.out.println("       BRACKET UPDATE           ");
            System.out.println("================================");
            System.out.println("You won your first two upper-bracket matches.");
            System.out.println("The remaining lower-bracket matches will now be simulated.");
            System.out.println("Your next playable match is the Upper Final.");
            System.out.println("================================");
            System.out.println();
        }
    }

    private static void announceNextPlayerMatch(MatchResult first, MatchResult second,
        String roundName)
    {
        if (first.winner.player || second.winner.player)
        {
            System.out.println("AI bracket simulation complete.");
            System.out.println("Starting your next match: " + roundName + ".");
            System.out.println();
        }
    }

    private static Alliance simulateAI(Alliance first, Alliance second)
    {
        int firstScore = allianceScore(first.bots);
        int secondScore = allianceScore(second.bots);

        Alliance winner = firstScore >= secondScore ? first : second;
        System.out.println("AI result: Alliance " + first.seed + " " + firstScore
            + " - Alliance " + second.seed + " " + secondScore);
        return winner;
    }

    private static int allianceScore(Bot[] bots)
    {
        int base = bots[0].getAvgScore() + bots[1].getAvgScore() + bots[2].getAvgScore();
        return base + (int)(Math.random() * 61) - 30;
    }

    private static boolean playFinals(Scanner scan, Builder myRobot, Bot[] myTeam,
        Alliance upperChampion, Alliance lowerChampion)
    {
        int upperWins = 0;
        int lowerWins = 0;
        int matchNumber = 1;

        while (upperWins < 2 && lowerWins < 2)
        {
            MatchResult result = play(scan, myRobot, myTeam, upperChampion,
                lowerChampion, "Championship Final " + matchNumber);

            if (result.winner == upperChampion)
            {
                upperWins++;
            }
            else
            {
                lowerWins++;
            }

            System.out.println("Finals series: Alliance " + upperChampion.seed + " " + upperWins
                + " - Alliance " + lowerChampion.seed + " " + lowerWins);
            System.out.println();
            matchNumber++;
        }

        Alliance champion = upperWins == 2 ? upperChampion : lowerChampion;
        return champion.player;
    }

    private static void printAlliances(Alliance[] seeds)
    {
        System.out.println("================================");
        System.out.println("        BRACKET ALLIANCES       ");
        System.out.println("================================");

        for (Alliance alliance : seeds)
        {
            String label = alliance.player
                ? "YOUR ALLIANCE"
                : alliance.bots[0].getName() + " / "
                    + alliance.bots[1].getName() + " / "
                    + alliance.bots[2].getName();

            System.out.println("Alliance " + alliance.seed + ": " + label);
        }

        System.out.println("================================");
        System.out.println();
    }
}
