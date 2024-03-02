package minh_dung.disruptor.event;

import com.lmax.disruptor.EventHandler;

// Once we have the event defined, we need to create a consumer that will handle these events. As an example, we will create an EventHandler that will print the value out to the console.
public class LongEventHandler implements EventHandler<LongEvent>
{
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event);
    }
}
