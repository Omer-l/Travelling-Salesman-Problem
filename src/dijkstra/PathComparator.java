package dijkstra;

import java.util.Comparator;

public class PathComparator implements Comparator<Integer[]> {

	Vertex[] vertices; //vertices refer to.
	
	public PathComparator(Vertex[] vertices) {
		this.vertices = vertices;
	}

	/**
	 * This function determines which path is shorter
	 * @param path1		path 1
	 * @param path2		path 2 (the one to compare to)
	 * @return	1 is path1 is longer, -1 if shorter, 0 if same.
	 */
	@Override
	public int compare(Integer[] path1, Integer[] path2) {
		
		double path1Distance = Application.calculatePathDistance(path1,false);
		double path2Distance = Application.calculatePathDistance(path2,false);
		
		if(path1Distance < path2Distance) 
			return -1;
		else if(path1Distance > path2Distance)
			return 1;
		else 
			return 0;

	}

}
