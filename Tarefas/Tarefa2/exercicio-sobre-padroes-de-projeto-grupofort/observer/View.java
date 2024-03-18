package observer;

public class View implements IAsyncTaskObserver
{
    public boolean isRendering()
    {
        return IsRendering;
    }

    public void handleTaskFinished(String finalResultOfAsyncTask)
    {
        if (isRendering())
        {
            System.out.printf("Displaying async task %s%n", finalResultOfAsyncTask);
        }
    }

    private static final boolean IsRendering = true;
}
