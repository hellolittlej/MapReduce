import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class LeftoverReducer extends Reducer<IntWritable, Node, IntWritable, Node> {
    public static double alpha = 0.85;
    public void reduce(IntWritable key, Iterable<NodeOrDouble> values, Context context) throws IOException, InterruptedException {
        //Implement;
        double pagerank = 0.0;
        Node n = new Node();
        for(NodeOrDouble val : values) {
            if(val.isNode()) {
                n = val; 
            }
            else {
                pagerank += val;
            }
        }
        double error = Math.abs(n.getPageRank() - pagerank) / pagerank;
        //the counter of the context can only be the long value;
        context.getCounter(LossCounter.counter.SimpleError.increment((long)(Integer.MAX_VALUE * error));
        n.setPageRank(pagerank);
        context.write(new IntWritable(n.nodeID), n);
    }
}
