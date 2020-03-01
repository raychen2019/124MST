import java.util.*;
import java.io.*;


public class randmst {

	// Using a generator to determine the coordinates of our points
	static Random generator;
	static double max = 0.0; // the maximum edge weight for the last added edge in the MST

	public static void main(String[] args){

		int seedNum = Integer.parseInt(args[0]) + 3; // seed for generator
		int numPoints = Integer.parseInt(args[1]);   // number of nodes in t
		int numTrials = Integer.parseInt(args[2]);	 // the number of trials ran
		int dimension = Integer.parseInt(args[3]);	 // the dimension of the coordinate point
		
		// treat a 0 dimension case as 1 dimension case
		if (dimension == 0)
			dimension = 1;
		
		// seeding the generator
		generator = new Random(seedNum);
		
		// total weight of all MST
		float totalWeight = 0;

		// Summing the weights of all MSTs
		for (int i = 0; i < numTrials; i++){
			float MST = FindMST(numPoints, dimension);
			totalWeight += MST;
		}
		System.out.println(max);
		
		// average weight per tree
		System.out.print(totalWeight/numTrials);

	}

	
	// determining the weight between two ndoes
	public static float generateWeight (Node one, Node two){

		float weightTemp = 0;
		for (int i = 0 ; i < one.getDim() ; i++)
			weightTemp += Math.pow((one.getCoords()[i] - two.getCoords()[i]),2); //compare coordinatess
		return (float) Math.sqrt(weightTemp);
	}

	// determining the cut off for edges that we are not going to include
	public static float generateThreshold (int nodeNum, int dimension){
		if (dimension == 1)
			return (float) (4.91*Math.pow(nodeNum,-0.905)+.05);
		else if (dimension == 2)
			return (float) (2.24*Math.pow(nodeNum,-0.506)+.05);
		else if (dimension == 3)
			return (float) (1.49*Math.pow(nodeNum,-0.318)+.05);
		else if (dimension == 4)
			return (float) (1.54*Math.pow(nodeNum,-0.25)+.05);
		
		return (float) (Math.sqrt(dimension)/2);
	}
	
	// finding the MST for a completely connected graph with n number of points and with d dimensions
	public static float FindMST (int numPoints, int numDimensions){

		//weight of the tree
		float treeWeight = 0;

		//each vertex can be labeled as an integer
		boolean[] visited = new boolean[numPoints];
		visited[0] = true;
		Node[] nodes = new Node[numPoints]; //storing all nodes
		for(int i = 0; i < numPoints; i++) { 
			nodes[i] = new Node(numDimensions, i); //creating each node
		}

		//Representing our heap as a priority queue of edges
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>();

		//Upon visiting the first node, add all of its outgoing edges
		for (int i = 1; i < numPoints; i++)
		{
			heap.add(new Edge (generateWeight (nodes[0], nodes[i]), nodes[i]));
		}

		//the program terminates when we add n-1 edges or when the heap is empty
		int edgeCount = 0;
		while (edgeCount < numPoints-1 && !heap.isEmpty()){
			Edge temp = heap.poll(); // look at the first edge
			if (!visited[temp.getSndVertex().getIndex()]) { // if the edge would just go to an unexplored node
				if (max < temp.getWeight()) // record the newest max edge that's valid
						max = temp.getWeight();
				treeWeight += temp.weight; // add newly added edge to the tree weight
				visited[temp.getSndVertex().getIndex()] = true; // the vertex has now been visited

				//now add all of the outgoing edges from the newly included node that goes to unexplored nodes
				for (int i = 1; i < numPoints; i++){ 
					if (!visited[i]) // if the outgoing edge goes to an unexplored node
					{
						float tempWeight = generateWeight(temp.getSndVertex(), nodes[i]); //determine weight of node
						if (tempWeight < generateThreshold(numPoints, numDimensions)) //if the weight is small enough to be relevant
							heap.add(new Edge (tempWeight, nodes[i])); //add the edge to the heap
					}
				}
				
				edgeCount++; // record the added edge
			}
			
		}
		
		// if there are less edges then there should be
		if (edgeCount < numPoints - 1)
			System.out.println("bound too low");
		
		return treeWeight; // weight of the tree
	}
	
	//an edge class for the queue 
	static class Edge implements Comparable{
		float weight;	//weight of the edge
		Node sndVertex; //the name of the unexplored node the edge goes to
		
		public Edge (float weightParam, Node sndVertexParam){
			weight = weightParam;
			sndVertex = sndVertexParam;
		}
		
		public float getWeight(){
			return weight;
		}
		
		public Node getSndVertex(){
			return sndVertex;
		}

		public int compareTo(Object o) {
			if (weight > ((Edge) o).weight)
				return 1;
			else 
				return -1;
		}

	}
	
	//a node that stores coordinate points
	static class Node{
		int dim;
		float[] coords;
		int index;
		
		public Node (int dimensions, int ind){
			index = ind;
			dim = dimensions;
			coords = new float[dim];
			for (int i = 0; i < dim; i++) {
				coords[i] = generator.nextFloat(); //determining the coordinates of the point
			}
		}
		
		public int getIndex(){
			return index;
		}
		
		public int getDim(){
			return dim;
		}
		
		public float[] getCoords(){
			return coords;
		}

	}
	
}