package observer;

@FunctionalInterface
public interface IAsyncTaskObserver
{
    void handleTaskFinished(String result);
}
