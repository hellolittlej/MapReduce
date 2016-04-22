import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

public class TrustReducer extends Reducer<IntWritable, NodeOrDouble, IntWritable, Node> {
    static final EPS = 0.001;
    
    public void reduce(IntWritable key, Iterable<NodeOrBeOrBc> values, Context context)
        throws IOException, InterruptedException {
        
    }
}
