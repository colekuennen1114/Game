public class Component
    {   
        private String category;
        private String type;
        private int cost;
        private double speed;
        private int addScore;
    public Component(String cat, String ty, int c, double s, int aS)
        {
         category = cat;
         type = ty;
         cost = c;
         speed = s;
         addScore = aS;
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
        
    }
