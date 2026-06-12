public class Bot
{
    private String name;   
    private String number;
    private int avgScore;


public Bot(String n,String num,int avgS)
{
    name = n;
    number = num;
    avgScore = avgS;
}

public String getName()
{
    return name;
}
public String getNumber()
{
    return number;
}

public int getAvgScore()
{
    return avgScore;
}
}

