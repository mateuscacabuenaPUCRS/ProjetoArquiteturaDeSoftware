package hardwired;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsSystem
{
    public boolean wasEventHandled(String taskResult)
    {
        return !handledTasks.contains(taskResult);
    }

    public void handleAsyncTask(String finalResultOfAsyncTask)
    {
        System.out.printf("Analytics system reported the following task result %s%n", finalResultOfAsyncTask);
        handledTasks.add(finalResultOfAsyncTask);
    }

    private List<String> handledTasks = new ArrayList<>();
}
