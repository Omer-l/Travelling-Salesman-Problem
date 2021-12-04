package dijkstra;
/**
 * This class contains a path to cities and a next and previous node, for which, the next and previous nodes also
 * contain paths and next and previous nodes.
 * This will make the queue mutable as nodes will be added and removed at run time.
 */
public class QueueNode {
    private final int[] path;
    private QueueNode previousNode;
    private  QueueNode nextNode;

    public QueueNode(int[] path, QueueNode previousNode, QueueNode nextNode) {
        this.path = path;
        this.previousNode = previousNode;
        this.nextNode = nextNode;
    }

    public QueueNode(int[] path) {
        this.path = path;
    }

    public QueueNode getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(QueueNode previousNode) {
        this.previousNode = previousNode;
    }

    public QueueNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(QueueNode nextNode) {
        this.nextNode = nextNode;
    }

    public int[] getPath() {
        return path;
    }
}
