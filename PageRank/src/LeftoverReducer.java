import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;


public class LeftoverReducer extends Reducer<IntWritable, Node, IntWritable, Node> {
    public static double alpha = 0.85;
    public void reduce(IntWritable nid, Iterable<Node> Ns, Context context) throws IOException, InterruptedException {
        //Implement
        for(Node n : Ns) {
           long G = context.getConfiguration().getLong("size", 1);
           double m = ((double)
             (context.getConfiguration().getLong("leftover", 0)))/((double)(Integer.MAX_VALUE));
           double prank = alpha / G + (1 - alpha) * (m / G + n.getPageRank());
           n.setPageRank(prank);
           context.write(new IntWritable(n.nodeid), n);
        }
    }
}
