package github.eddy.bigdata.core.hadoop.writable;

import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author edliao
 */
public class LongArrayWritable extends ArrayWritable {

    public LongArrayWritable() {
        super(LongWritable.class);
    }

    public void set(Long... values) {
        Writable[] writables = new Writable[values.length];
        for (int i = 0; i < values.length; i++) {
            writables[i] = new LongWritable(values[i]);
        }
        super.set(writables);
    }

    public List<LongWritable> getLongWritable() {
        Writable[] writables = super.get();
        List<LongWritable> longWritables = new ArrayList<>();
        for (Writable writable : writables) {
            longWritables.add((LongWritable) writable);
        }
        return longWritables;
    }
}
