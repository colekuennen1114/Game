import java.util.Scanner;

public class Builder
{
    private Component shooter;
    private Component intake;
    private Component indexer;
    private Component hopper;
    private int alliance;

    public static final int BUDGET = 1000;

    public static final int[] ALLIANCE_COSTS = {500, 425, 350, 275, 200, 125, 50, 0};

    public Builder(Component sh, Component in, Component idx, Component hop, int a)
    {
        shooter = sh;
        intake = in;
        indexer = idx;
        hopper = hop;
        alliance = a;
    }

    public static Builder build(Scanner scan)
    {
        Component[] shooters = ComponentData.createShooters();
        Component[] intakes  = ComponentData.createIntakes();
        Component[] indexers = ComponentData.createIndexers();
        Component[] hoppers  = ComponentData.createHoppers();

        int budget = BUDGET;

        System.out.println("================================");
        System.out.println("         ROBOT BUILDER          ");
        System.out.println("================================");
        System.out.println("Total Budget: $" + budget);
        System.out.println("Build your robot and choose your alliance.");
        System.out.println("Higher alliance = harder bracket path, but cheaper.");
        System.out.println();

        Component chosenShooter = chooseComponent(scan, shooters, budget, "SHOOTER");
        budget -= chosenShooter.getCost();

        Component chosenIntake = chooseComponent(scan, intakes, budget, "INTAKE");
        budget -= chosenIntake.getCost();

        Component chosenIndexer = chooseIndexer(scan, indexers, budget, chosenShooter);
        budget -= chosenIndexer.getCost();

        Component chosenHopper = chooseComponent(scan, hoppers, budget, "HOPPER");
        budget -= chosenHopper.getCost();

        int chosenAlliance = chooseAlliance(scan, budget);
        budget -= ALLIANCE_COSTS[chosenAlliance - 1];

        System.out.println();
        System.out.println("Remaining budget: $" + budget);
        System.out.println("Build complete!");
        System.out.println();

        Builder result = new Builder(chosenShooter, chosenIntake, chosenIndexer, chosenHopper, chosenAlliance);
        result.displayBuild();
        return result;
    }

    private static Component chooseComponent(Scanner scan, Component[] components, int budget, String category)
    {
        System.out.println("--- Choose your " + category + " ---");
        System.out.println("Remaining budget: $" + budget);
        System.out.println();

        for (int i = 0; i < components.length; i++)
        {
            Component c = components[i];
            String tag = c.getCost() <= budget ? "" : "  [CANT AFFORD]";

            System.out.println(i + ": " + c.getType()
                + " | Cost: $" + c.getCost()
                + " | Score: +" + c.getAddScore()
                + " | Speed: x" + c.getSpeed()
                + tag);
        }

        System.out.println();

        while (true)
        {
            int choice = Input.readInt(scan, 0, components.length - 1, "Enter choice:");

            if (components[choice].getCost() <= budget)
            {
                System.out.println("Selected: " + components[choice].getType());
                System.out.println();
                return components[choice];
            }

            System.out.println("Can't afford that! Try again:");
        }
    }

    private static Component chooseIndexer(Scanner scan, Component[] indexers, int budget, Component shooter)
    {
        System.out.println("--- Choose your INDEXER ---");
        System.out.println("Remaining budget: $" + budget);
        System.out.println("Some indexers only work with certain shooters.");
        System.out.println();

        for (int i = 0; i < indexers.length; i++)
        {
            Component c = indexers[i];

            String tag = "";

            if (c.getCost() > budget)
            {
                tag += "  [CANT AFFORD]";
            }

            if (!isCompatible(shooter, c))
            {
                tag += "  [NOT COMPATIBLE]";
            }

            System.out.println(i + ": " + c.getType()
                + " | Cost: $" + c.getCost()
                + " | Score: +" + c.getAddScore()
                + " | Speed: x" + c.getSpeed()
                + tag);
        }

        System.out.println();

        while (true)
        {
            int choice = Input.readInt(scan, 0, indexers.length - 1, "Enter choice:");

            if (indexers[choice].getCost() > budget)
            {
                System.out.println("Can't afford that! Try again:");
            }
            else if (!isCompatible(shooter, indexers[choice]))
            {
                System.out.println("That indexer does not work with your shooter. Try again:");
            }
            else
            {
                System.out.println("Selected: " + indexers[choice].getType());
                System.out.println();
                return indexers[choice];
            }
        }
    }

    private static boolean isCompatible(Component shooter, Component indexer)
    {
        String shooterType = shooter.getType();
        String indexerType = indexer.getType();

        if (indexerType.equals("Dye Rotor"))
        {
            return shooterType.equals("Fixed Single")
                || shooterType.equals("Single Turret");
        }

        if (indexerType.equals("Spindexer"))
        {
            return shooterType.equals("Fixed Single")
                || shooterType.equals("Single Turret")
                || shooterType.equals("Double Turret");
        }

        return true;
    }

    private static int chooseAlliance(Scanner scan, int budget)
    {
        System.out.println("--- Choose your Alliance ---");
        System.out.println("Remaining budget: $" + budget);
        System.out.println();

        for (int i = 0; i < ALLIANCE_COSTS.length; i++)
        {
            String tag = ALLIANCE_COSTS[i] <= budget ? "" : "  [CANT AFFORD]";
            System.out.println((i + 1) + ": Alliance " + (i + 1) + " | Cost: $" + ALLIANCE_COSTS[i] + tag);
        }

        System.out.println();

        while (true)
        {
            int choice = Input.readInt(scan, 1, 8, "Enter alliance number (1-8):");

            if (ALLIANCE_COSTS[choice - 1] <= budget)
            {
                System.out.println("Selected: Alliance " + choice);
                System.out.println();
                return choice;
            }

            System.out.println("Can't afford that alliance! Try again:");
        }
    }

    public int getScore()
    {
        double speedMultiplier = shooter.getSpeed() * intake.getSpeed() * indexer.getSpeed() * hopper.getSpeed();
        int baseScore = shooter.getAddScore() + intake.getAddScore() + indexer.getAddScore() + hopper.getAddScore();

        return (int)(baseScore * speedMultiplier);
    }

    public void displayBuild()
    {
        System.out.println("================================");
        System.out.println("          YOUR ROBOT            ");
        System.out.println("================================");
        System.out.println("Shooter:   " + shooter.getType()  + "  (+" + shooter.getAddScore()  + " pts)");
        System.out.println("Intake:    " + intake.getType()   + "  (x"  + intake.getSpeed()   + " speed)");
        System.out.println("Indexer:   " + indexer.getType()  + "  (+" + indexer.getAddScore()  + " pts)");
        System.out.println("Hopper:    " + hopper.getType()   + "  (x"  + hopper.getSpeed()   + " speed)");
        System.out.println("Alliance:  Alliance " + alliance);
        System.out.println("Robot performance rating: " + getScore());
        System.out.println("================================");
    }

    public Component getShooter()
    {
        return shooter;
    }

    public Component getIntake()
    {
        return intake;
    }

    public Component getIndexer()
    {
        return indexer;
    }

    public Component getHopper()
    {
        return hopper;
    }

    public int getAlliance()
    {
        return alliance;
    }
}
