package hardwired;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class AsyncTaskHardwired
{
    public AsyncTaskHardwired(AnalyticsSystem analyticsSystem, DatabaseConnection databaseConnection, View view)
    {
        timer = new Timer();
        result = Optional.empty();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                String finalResultOfAsyncTask = "Final result of async task";
                System.out.printf("Task finished! Result is %s%n", finalResultOfAsyncTask);
                result = Optional.of(finalResultOfAsyncTask);
                if (!analyticsSystem.wasEventHandled(finalResultOfAsyncTask))
                {
                    analyticsSystem.handleAsyncTask(finalResultOfAsyncTask);
                }
                if (view.isRendering())
                {
                    view.handleAsyncTask(finalResultOfAsyncTask);
                }
                databaseConnection.close();

                analyticsSystem.handleAsyncTask(finalResultOfAsyncTask);
                timer.cancel();
            }
        }, 5000);
    }

    public Optional<String> getResult()
    {
        return result;
    }

    public boolean isFinished()
    {
        return result.isPresent();
    }

    private final Timer timer;
    private Optional<String> result;
}
