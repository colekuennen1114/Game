public class Component
    {   
        private String category;
        private String type;
        private int cost;
        private double speed;
        private int addScore;
        private boolean defense;
    public Component(String cat, String ty, int c, double s, int aS, boolean d)
        {
         category = cat;
         type = ty;
         cost = c;
         speed = s;
         addScore = aS;
         defense = d;
        }
        
        public String getCategory()
        {
            return category;
        }
        
        public String getType()
        {
            return type;
        }
        
        public int getCost()
        {
            return cost;
        }
        
        public double getSpeed()
        {
            return speed;
        }
        
        public int getAddScore()
        {
            return addScore;
        }
        
        public boolean getDefense()
        {
            return defense;
        }
    }