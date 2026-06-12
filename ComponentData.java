public class ComponentData
{
    public static Component[] createShooters()
    {
        Component[] shooters = new Component[6];

        shooters[0] = new Component("Shooter", "Fixed Single", 150, 1, 75);
        shooters[1] = new Component("Shooter", "Fixed Double", 250, 1, 100);
        shooters[2] = new Component("Shooter", "Fixed Triple", 350, 1, 150);
        shooters[3] = new Component("Shooter", "Single Turret", 450, 1, 175);
        shooters[4] = new Component("Shooter", "Double Turret", 550, 1, 200);
        shooters[5] = new Component("Shooter", "Drum", 300, 1, 180);
        
        return shooters;
    }
    
    public static Component[] createIntakes()
    {
        Component[] intakes = new Component[6];
        
        intakes[0] = new Component("Intake", "Over the Bumper x1", 200, 1, 50);
        intakes[1] = new Component("Intake", "Over the Bumper x1.25", 250, 1.25, 50);
        intakes[2] = new Component("Intake", "Over the Bumper x1.5", 300, 1.5, 50);
        intakes[3] = new Component("Intake", "Through the Bumper x1", 100, 1, 25);
        intakes[4] = new Component("Intake", "Through the Bumper x1.25", 150, 1.25, 25);
        intakes[5] = new Component("Intake", "Through the Bumper x1.5", 200, 1.5, 25);
        
        return intakes;
    }
    
    public static Component[] createIndexers()
    {
        Component[] indexers = new Component[4];
        
        indexers[0] = new Component("Indexer", "Spindexer", 150, 1, 100);
        indexers[1] = new Component("Indexer", "Belts", 125, 1, 75);
        indexers[2] = new Component("Indexer", "Rollers", 100, 1, 50);
        indexers[3] = new Component("Indexer", "Dye Rotor", 250, 1, 150);
        
        return indexers;
    }
    
    public static Component[] createHoppers()
    {
        Component[] hoppers = new Component[4];
        
        hoppers[0] = new Component("Hopper", "0 Fuel", 0, 1, 0);
        hoppers[1] = new Component("Hopper", "20 Fuel", 75, 1.2, 0);
        hoppers[2] = new Component("Hopper", "40 Fuel", 150, 1.35, 0);
        hoppers[3] = new Component("Hopper", "60 Fuel", 200, 1.5, 0);
        
        return hoppers;
    }
}
    
