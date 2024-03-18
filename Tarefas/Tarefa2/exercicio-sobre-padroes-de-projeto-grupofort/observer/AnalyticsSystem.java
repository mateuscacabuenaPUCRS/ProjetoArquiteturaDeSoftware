package observer;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsSystem implements IAsyncTaskObserver
{
	public AnalyticsSystem(AsyncTaskObservable asyncTaskObservable)
	{
		this.asyncTaskObservable = asyncTaskObservable;
	}

	public boolean wasEventHandled(String taskResult)
    {
        return !handledTasks.contains(taskResult);
    }

    public void handleTaskFinished(String finalResultOfAsyncTask)
    {
        System.out.printf("Analytics system reported the following task result %s%n", finalResultOfAsyncTask);
        if (!wasEventHandled(finalResultOfAsyncTask))
        {
            handledTasks.add(finalResultOfAsyncTask);
        }
		asyncTaskObservable.unsubscribe(this);
    }

    private final List<String> handledTasks = new ArrayList<>();
	private final AsyncTaskObservable asyncTaskObservable;
}
