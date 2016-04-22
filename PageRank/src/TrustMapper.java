import java.io.IOException;
import java.util.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

//write out the pagerank;
//write out the edge list in the block;
//write out the boundary condition;
public class TrustMapper extends Mapper<IntWritable, Node, IntWritable, NodeOrDouble> {

    public void map(IntWritable key, Node value, Context context) throws IOException, InterruptedException {

        int blockID = value.getBlockID();
        //get the in block neighbor;
        for(Node InNeighbor : value.getInBlock()) {
            //note that v is the current node, that is the value;
            Edge edge = new Edge(InNeighbor, value);
            //send out the BE;
            context.write(new IntWritable(blockID), new NodeOrBeOrBc(edge));
        }
        //get the BoundaryCondition;
        for(Node outNeighbor : value.getOutBlock()) {
            BoundaryCondition bc = new BoundaryCondition(outNeighbor, value);
            double uPR = outNeighbor.getPageRank();
            double R =uPR / outNeighbor.getDegree();
            bc.setRvalue(R);
            context.write(new IntWritable(blockID), new NodeOrBeOrBc(bc);
        }
        //pass out the old pagerank;
        context.write(new IntWritable(blockID), new NodeOrBeOrBc(value));        
    }
}
