package minh_dung.disruptor.legacy_api;

// There is a more "raw" approach that we can use:
import com.lmax.disruptor.RingBuffer;
import minh_dung.disruptor.event.LongEvent;
// import com.lmax.disruptor.examples.longevent.LongEvent;

import java.nio.ByteBuffer;

public class LongEventProducer
{
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb)
    {
        long sequence = ringBuffer.next(); // 1
        try
        {
            LongEvent event = ringBuffer.get(sequence); // 2
            event.set(bb.getLong(0)); // 3
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}

// 1.Grab the next sequence
// 2.Get the entry in the Disruptor for that sequence
// 3.Fill the entry with data

// Specifically, in the multi-producer case, this will result in the consumers stalling and being unable to recover without a restart.
// Therefore, it is recommended that either the lambda or EventTranslator APIs be used.