namespace Dr._Variyam_Research_C_Implementation;
using CardinalityEstimation;

class Program
{
    static void Main(string[] args)
    {
        ICardinalityEstimator<string> estimator = new CardinalityEstimator();

    try
        {
            using (StreamReader reader = new StreamReader("Hamlet.txt"))
            {
                string line;
                while ((line = reader.ReadLine()) != null)
                {
                    estimator.Add(line);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            Console.WriteLine("File not found: " + e.FileName);
        }
        catch (IOException e)
        {
            Console.WriteLine("Error reading file: " + e.Message);
        }
        ulong numberOfuniqueElements = estimator.Count(); // will be 3
        Console.WriteLine(numberOfuniqueElements);
    }

}
