import org.apache.hadoop.io.Writable;
import java.io.*;

// Node + Integer (Either Node Integer, in Haskell)
public class NodeOrBeOrBc implements Writable{
    private Node n;
    private BoundaryCondition bc;
    private Edge edge;
    private boolen isnode;

    //*construct a nodeorbeorbc that is a node;
    public NodeOrBeOrBc(Node n) {
        isnode = true;
        this.n = n;
    }
    //*construct a nodeorbeorbc that is a bc;
    public NodeOrBeOrBc(BoundaryCondition bc) {
        isnode = false;
        this.bc = bc;
    }
    //*construct a nodeorbeorbc that is edge
    public NodeOrBeOrBc(Edge edge) {
        isnode = false;
        this.edge = edge;
    }
    //*check if it is a node;
    public boolean isNode() {
        return isnode;
    }

    //If this is a Node, return it.
    //Otherwise, return null
    public Node getNode() {
        if(!isNode()) return null;
        return n;
    }
    
    //If this is a Double, return it.
    //Otherwise, return null
    public Double getDouble() {
        if(isNode()) return null;
        return d;
    }

    //Used for internal Hadoop purposes only
    //Describes how to write NodeOrDouble objects across a network
    public void write(DataOutput out) throws IOException {
        out.writeBoolean(is_node);
        if(is_node) {
            n.write(out);
        }
        else {
            out.writeDouble(d);
        }
    }

    //Used for internal Hadoop purposes only
    //Describes how to read NodeOrDouble objects from across a network
    public void readFields(DataInput in) throws IOException {
        is_node = in.readBoolean();
        if(is_node) {
            n = new Node(-1); //just to avoid errors --- wish this was static
            n.readFields(in);
        } else {
            d = in.readDouble();
        }
    }
}
