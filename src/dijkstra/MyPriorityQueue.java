package dijkstra;

import main.DataPoint;//used for indexes in the path.
import main.MyArrays; //printing arrays

/**
 * This class is a priority queue with functions for adding, removing paths to the queue.
 * Paths with a shorter distance have a higher priority.
 */
public class MyPriorityQueue {

    private int size = 0;
    private QueueNode front;
    private DataPoint[] vertices; //vertices to refer to with the new paths.

    public MyPriorityQueue(DataPoint[] vertices) {
        this.vertices = vertices;
    }

    public MyPriorityQueue() {
    }

    //returns true if the queue has elements.
    public boolean empty() {
        return size == 0;
    }

    /**
     * Iterates through the queue. Shorter distance is priority, and thus, the path will be inserted accordingly.
     * @param newPath a new path to add to the queue
     */
    public void enqueue(int[] newPath) {
        QueueNode newNode = new QueueNode(newPath);

        if (empty())
            this.front = newNode;
        else {
            double newPathDistance = Dijkstra.calculatePathDistance(newPath, vertices);
            QueueNode currentNode = this.front;
            boolean vertexInserted = false; //determines when to vertexInserted the traversing

            for (int nodeIterator = 0; nodeIterator <= size && !vertexInserted; nodeIterator++) {

                int[] currentNodePath = currentNode.getPath();
                double currentNodePathDistance = Dijkstra.calculatePathDistance(currentNodePath, vertices);

                if (newPathDistance < currentNodePathDistance) { //ensures new node is inserted here if it has a shorter path.

                    if(currentNode.equals(front)) { //ensures the new node is added to the front of the queue
                        addFront(newNode);
                    } else { //insertion is between the first and last node of queue.
                        newNode.setNextNode(currentNode);
                        newNode.setPreviousNode(currentNode.getPreviousNode());
                        currentNode.getPreviousNode().setNextNode(newNode);
                        currentNode.setPreviousNode(newNode);
                    }
                    vertexInserted = true;
                }
                if(!vertexInserted)
                    if (currentNode.getNextNode() == null) { //Ensures node is inserted to the ned of the linked list
                        currentNode.setNextNode(newNode);
                        newNode.setPreviousNode(currentNode);
                        vertexInserted = true;
                    } else
                        currentNode = currentNode.getNextNode(); //continues to traverse if there is a next node.
            }
        }
        size++;
    }

    //Replaces the front of the queue with a new node.
    public void addFront(QueueNode newNode) {
        QueueNode tmp = (QueueNode) front.clone(); //to avoid referencing errors.
        tmp.setPreviousNode(newNode);
        this.front = newNode;
        front.setNextNode(tmp);
    }

    //Dequeues the element at the front of the queue.
    public int[] dequeue() {
        if(front == null)
            return null;
        else {
            QueueNode tmp = (QueueNode) front.clone(); //to avoid referencing errors.
            front = front.getNextNode();
            size--; //there is one less element in the list.
            return tmp.getPath();
        }
    }

    public QueueNode getFront() {
        return front;
    }

    //Prints the doubly linked list priority queue.
    @Override
    public String toString() {
        String doublyLinkedListString = "";
        QueueNode currentNode = front;
        for (int nodeIterator = 0; nodeIterator < size; nodeIterator++) {
            if (currentNode.getPreviousNode() != null)
                doublyLinkedListString += "[" + MyArrays.toString(currentNode.getPreviousNode().getPath()) + ",D:" + Dijkstra.calculatePathDistance(currentNode.getPreviousNode().getPath(), vertices);

            doublyLinkedListString += " ] - [" + MyArrays.toString(currentNode.getPath()) + "" + ",D:" + Dijkstra.calculatePathDistance(currentNode.getPath(), vertices) + "] - [";
            if (currentNode.getNextNode() != null)
                doublyLinkedListString += MyArrays.toString(currentNode.getNextNode().getPath()) + ",D:" + Dijkstra.calculatePathDistance(currentNode.getNextNode().getPath(), vertices) + " ]\n";

            currentNode = currentNode.getNextNode();
        }
        return doublyLinkedListString;
    }
}
