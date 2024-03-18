package observer;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class AsyncTaskObservable
{
    public AsyncTaskObservable()
    {
        timer = new Timer();
        result = Optional.empty();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                broadcastResult("Final result of async task");
                timer.cancel();
            }
        }, 5000);
    }

    private void broadcastResult(String result)
    {
        this.result = Optional.of(result);
        ArrayList<IAsyncTaskObserver> subscribersCopy = (ArrayList<IAsyncTaskObserver>) subscribers.clone();
        for (IAsyncTaskObserver observer : subscribersCopy)
        {
            observer.handleTaskFinished(result);
        }
    }

    public boolean subscribe(IAsyncTaskObserver observer)
    {
        return subscribers.add(observer);
    }

    public boolean unsubscribe(IAsyncTaskObserver observer)
    {
        return subscribers.remove(observer);
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

    private final ArrayList<IAsyncTaskObserver> subscribers = new ArrayList<>();
}
