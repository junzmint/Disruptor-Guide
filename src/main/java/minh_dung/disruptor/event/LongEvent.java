package minh_dung.disruptor.event;


// Firstly we will define the Event that will carry the data and is common to all following examples
public class LongEvent
{
    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "LongEvent{" + "value=" + value + '}';
    }
}
