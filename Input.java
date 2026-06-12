import java.util.Scanner;

public class Input
{
    public static int readInt(Scanner scan, int min, int max, String prompt)
    {
        while (true)
        {
            System.out.println(prompt);

            if (!scan.hasNextLine())
            {
                throw new IllegalStateException("Input was closed before the game finished.");
            }

            String line = scan.nextLine().trim();

            try
            {
                int value = Integer.parseInt(line);

                if (value >= min && value <= max)
                {
                    return value;
                }
            }
            catch (NumberFormatException ex)
            {
                // The message below handles both text and out-of-range numbers.
            }

            System.out.println("Invalid choice. Enter a number from " + min + " to " + max + ".");
        }
    }
}
