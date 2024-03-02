package minh_dung.disruptor.translators;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import minh_dung.disruptor.event.LongEvent;

import java.nio.ByteBuffer;

public class LongEventProducer
{
    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>()
            {
                @Override
                public void translateTo(LongEvent event, long sequence, ByteBuffer bb)
                {
                    event.set(bb.getLong(0));
                }
            };

    public void onData(ByteBuffer bb)
    {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}

// This approach uses number of extra classes (e.g. handler, translator) that are not explicitly required when using lambdas.
// The advantage of here is that the translator code can be pulled into a separate class and easily unit tested.