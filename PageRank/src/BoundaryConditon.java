import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapreduce.Counter;


public class BoundaryCondition {
    private Node OutBound;
    private Node InBound;
    private Double R;
    
    public BoundaryCondition(Node out, Node in) {
        OutBoudn = out;
        InBound = in;
        R = 0.0;
    }

    public void setRvalue(double R) {
        this.R = R;
    }
}

