public class ComponentData
{
    public static Component[] createShooters()
    {
        Component[] shooters = new Component[6];

        shooters[0] = new Component("Shooter", "Fixed Single", 150, 1, 75, true);
        shooters[1] = new Component("Shooter", "Fixed Double", 250, 1, 100, true);
        shooters[2] = new Component("Shooter", "Fixed Triple", 350, 1, 150, true);
        shooters[3] = new Component("Shooter", "Single Turret", 450, 1, 175, false);
        shooters[4] = new Component("Shooter", "Double Turret", 550, 1, 200, false);
        shooters[5] = new Component("Shooter", "Drum", 300, 1, 180, true);
        
        return shooters;
    }
    
    public static Component[] createIntakes()
    {
        Component[] intakes = new Component[6];
        
        intakes[0] = new Component("Intake", "Over the Bumper x1", 200, 1, 50, false);
        intakes[1] = new Component("Intake", "Over the Bumper x1.25", 200, 1.25, 50, false);
        intakes[2] = new Component("Intake", "Over the Bumper x1.5", 200, 1.5, 50, false);
        intakes[3] = new Component("Intake", "Through the Bumper", 100, 1, 25, false);
        intakes[4] = new Component("Intake", "Through the Bumper", 100, 1.25, 25, false);
        intakes[5] = new Component("Intake", "Through the Bumper", 100, 1.5, 25, false);
        
        return intakes;
    }
    
    public static Component[] createIndexers()
    {
        Component[] indexers = new Component[4];
        
        indexers[0] = new Component("Indexer", "Spindexer", 150, 1, 100, false);
        indexers[1] = new Component("Indexer", "Belts", 125, 1, 75, false);
        indexers[2] = new Component("Indexer", "Rollers", 100, 1, 50, false);
        indexers[3] = new Component("Indexer", "Dye Rotor", 250, 1, 150, false);
        
        return indexers;
    }
    
    public static Component[] createHoppers()
    {
        Component[] hoppers = new Component[4];
        
        hoppers[0] = new Component("Hopper", "0 Fuel", 0, 1, 0, false);
        hoppers[1] = new Component("Hopper", "20 Fuel", 75, 1.2, 0, false);
        hoppers[2] = new Component("Hopper", "40 Fuel", 150, 1.35, 0, false);
        hoppers[3] = new Component("Hopper", "60 Fuel", 200, 1.5, 0, false);
        
        return hoppers;
    }
}
    

