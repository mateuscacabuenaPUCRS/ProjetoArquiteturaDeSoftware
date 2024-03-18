package hardwired;

public class Hardwired
{
    public static void main(String[] args)
    {
        AsyncTaskHardwired asyncTaskHardwired = new AsyncTaskHardwired(new AnalyticsSystem(), new DatabaseConnection(), new View());
    }

    private Hardwired() {}
}
