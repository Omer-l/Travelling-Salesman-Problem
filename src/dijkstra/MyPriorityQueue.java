package dijkstra;

import main.DataPoint;//used for indexes in the a path.
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
            boolean exit = false; //determines when to exit the traversing

            for (int nodeIterator = 0; nodeIterator <= size && !exit; nodeIterator++) {

                int[] currentNodePath = currentNode.getPath();
                double currentNodePathDistance = Dijkstra.calculatePathDistance(currentNodePath, vertices);

                if (newPathDistance < currentNodePathDistance) {

                    if(currentNode.equals(front)) {
                        addFront(newNode);
                    } else {
                        newNode.setNextNode(currentNode);
                        newNode.setPreviousNode(currentNode.getPreviousNode());
                        currentNode.getPreviousNode().setNextNode(newNode);
                        currentNode.setPreviousNode(newNode);
                    }
                    exit = true;
                }
                if(!exit)
                    if (currentNode.getNextNode() == null) { //end of queue
                        currentNode.setNextNode(newNode);
                        newNode.setPreviousNode(currentNode);
                        exit = true;
                    } else
                        currentNode = currentNode.getNextNode();
            }
        }
        size++;
    }

    //Replaces the front of the queue with a new node.
    public void addFront(QueueNode newNode) {
        QueueNode tmp = (QueueNode) front.clone();
        tmp.setPreviousNode(newNode);
        this.front = newNode;
        front.setNextNode(tmp);
    }

    //Dequeues the element at the front of the queue.
    public int[] dequeue() {
        if(front == null)
            return null;
        else {
            QueueNode tmp = (QueueNode) front.clone();
            front = front.getNextNode();
            size--;
            return tmp.getPath();
        }
    }

    //	/**
//	 * Iterates through the queue. Shorter distance is priority, and thus, the path will be inserted accordingly.
//	 * @param newPath	a new path to add to the queue
//	 */
//	public void enqueue(int[] newPath) {
//
//		if (rear == size)
//			resize();
//
//		double newPathDistance = Dijkstra.calculatePathDistance(newPath, vertices);
//
//		for(int vertexIndexIterator = 0; vertexIndexIterator < arrayOfPaths.length; vertexIndexIterator++) {
//			if(arrayOfPaths[vertexIndexIterator] == null) {
//				arrayOfPaths[rear] = newPath;
//				break;
//			}
//			int[] currentPath = arrayOfPaths[vertexIndexIterator];
//			double currentPathDistance = Dijkstra.calculatePathDistance(currentPath, vertices);
//
//			if(newPathDistance < currentPathDistance) {
//				int[][] newArray = new int[arrayOfPaths.length + 1][];
//				newArray[vertexIndexIterator] = newPath;
//
//				System.arraycopy(arrayOfPaths, vertexIndexIterator, newArray, vertexIndexIterator+1, arrayOfPaths.length - vertexIndexIterator);
//
//				arrayOfPaths = newArray;
//				break;
//			}
//		}
//		rear++;
//	}

//	//Gets the element at the front of the queue
//	public int[] peek() {
//		return arrayOfPaths[front];
//	}
//
//	//returns true if the queue has no elements.
//	public boolean notEmpty() {
//		return rear != 0;
//	}
//
//	//gets the size of the queue
//	public int getSize() {
//		return rear;
//	}
//
//	/**
//	 * Iterates through the queue. Shorter distance is priority, and thus, the path will be inserted accordingly.
//	 * @param newPath	a new path to add to the queue
//	 */
//	public void enqueue(int[] newPath) {
//
//		if (rear == size)
//			resize();
//
//		double newPathDistance = Dijkstra.calculatePathDistance(newPath, vertices);
//
//		for(int vertexIndexIterator = 0; vertexIndexIterator < arrayOfPaths.length; vertexIndexIterator++) {
//			if(arrayOfPaths[vertexIndexIterator] == null) {
//				arrayOfPaths[rear] = newPath;
//				break;
//			}
//			int[] currentPath = arrayOfPaths[vertexIndexIterator];
//			double currentPathDistance = Dijkstra.calculatePathDistance(currentPath, vertices);
//
//			if(newPathDistance < currentPathDistance) {
//				int[][] newArray = new int[arrayOfPaths.length + 1][];
//				newArray[vertexIndexIterator] = newPath;
//
//				System.arraycopy(arrayOfPaths, vertexIndexIterator, newArray, vertexIndexIterator+1, arrayOfPaths.length - vertexIndexIterator);
//
//				arrayOfPaths = newArray;
//				break;
//			}
//		}
//		rear++;
//	}
//
//	/**
//	 * Removes the path at the front of the queue.
//	 * @return	the path removed from the front of the queue.
//	 */
//	public int[] dequeue() {
//		if(front == rear) { //reset
//			size = 1;
//			arrayOfPaths = new int[size][];
//			front = 0;
//			rear = 0;
//			int[] emptyPath = {};
//			return emptyPath;
//		} else {
//			return arrayOfPaths[front++];
//		}
//	}

    public QueueNode getFront() {
        return front;
    }

    @Override
    public String toString() {
        String s = "";
        QueueNode currentNode = front;
        for (int nodeIterator = 0; nodeIterator < size; nodeIterator++) {
            if (currentNode.getPreviousNode() != null)
                s += "[" + MyArrays.toString(currentNode.getPreviousNode().getPath()) + ",D:" + Dijkstra.calculatePathDistance(currentNode.getPreviousNode().getPath(), vertices);

            s += " ] - [" + MyArrays.toString(currentNode.getPath()) + "" + ",D:" + Dijkstra.calculatePathDistance(currentNode.getPath(), vertices) + "] - [";
            if (currentNode.getNextNode() != null)
                s += MyArrays.toString(currentNode.getNextNode().getPath()) + ",D:" + Dijkstra.calculatePathDistance(currentNode.getNextNode().getPath(), vertices) + " ]\n";

            currentNode = currentNode.getNextNode();
        }
        return s;
    }
//	@Override
//	public String toString() {
//		String s = "";
//		QueueNode currentNode = front;
//		for(int nodeIterator = 0; nodeIterator < size; nodeIterator++) {
//			s += + "\n";
//			currentNode = currentNode.getNextNode();
//		}
//		return MyArrays.toString(arrayOfPaths);
//	}
}
