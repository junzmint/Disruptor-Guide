package minh_dung.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
// import com.lmax.disruptor.examples.longevent.LongEvent;
import minh_dung.disruptor.LongEvent;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;

public class LongEventMainUsingLambdas
{
    public static void main(String[] args) throws Exception
    {
        int bufferSize = 1024; // 1

        Disruptor<LongEvent> disruptor = // 2
                new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event, sequence, endOfBatch) ->
                System.out.println("Event: " + event)); // 3
        disruptor.start(); // 4


        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer(); // 5
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
            Thread.sleep(1000);
        }
    }
}
// 1. Specify the size of the ring buffer, must be power of 2.
// 2. Construct the Disruptor
// 3. Connect the handler
// 4. Start the Disruptor, starts all threads running
// 5. Get the ring buffer from the Disruptor to be used for publishing.