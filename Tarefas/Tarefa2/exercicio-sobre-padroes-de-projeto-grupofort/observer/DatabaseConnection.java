package observer;

public class DatabaseConnection implements IAsyncTaskObserver
{
    @Override
    public void handleTaskFinished(String result)
    {
        System.out.println("Closing database connection..");
    }
}
