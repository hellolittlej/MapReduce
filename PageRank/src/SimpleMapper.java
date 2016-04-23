import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

public class SimpleMapper extends Mapper<IntWritable, Node, IntWritable, Node> {

    public void map(IntWritable nid, Node N, Context context) throws IOException, InterruptedException {
        //pass along the structure;
        context.getCounter(LossCounter.counter.SIZE).increment(1);
        int n = N.getOutSize();
        context.write(nid, new NodeOrDouble(N));
        if(n != 0) {
            double Prank = N.getPageRank() / n;
            for(int i = 0; i < n; i ++) {
                context.write(new IntWritable(N.Outgoing[i]), new NodeOrDouble(Prank));
            }
        }
    }
}
