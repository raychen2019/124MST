import java.util.*;
import java.io.*;


public class randmst {

	static Random generator;
	static double max = 0.0;

	public static void main(String[] args){

		int seedNum = Integer.parseInt(args[0]) + 3;
		int numPoints = Integer.parseInt(args[1]);
		int numTrials = Integer.parseInt(args[2]);
		int dimension = Integer.parseInt(args[3]);
		
		generator = new Random(seedNum);
		
		float totalWeight = 0;

		for (int i = 0; i < numTrials; i++){
			float MST = FindMST(numPoints, dimension);
			totalWeight += MST;
		}
		System.out.println(max);
//		System.out.println(totalWeight);		

		System.out.print(totalWeight/numTrials);

	}

	

	public static float generateWeight (Node one, Node two){

		float weightTemp = 0;
		for (int i = 0 ; i < one.getDim() ; i++)
			weightTemp += Math.pow((one.getCoords()[i] - two.getCoords()[i]),2);
		return (float) Math.sqrt(weightTemp);
	}

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
	
	public static float FindMST (int numPoints, int numDimensions){
		
		float treeWeight = 0;

		//each vertex can be labeled as an integer

		boolean[] visited = new boolean[numPoints];
		visited[0] = true;
		Node[] nodes = new Node[numPoints];
		for(int i = 0; i < numPoints; i++) {
			nodes[i] = new Node(numDimensions, i);
		}

		PriorityQueue<Edge> heap = new PriorityQueue<Edge>();

		for (int i = 1; i < numPoints; i++)
		{
			heap.add(new Edge (generateWeight (nodes[0], nodes[i]), nodes[i]));
		}

		//the program terminates when we add n-1 edges 
		int edgeCount = 0;
		while (edgeCount < numPoints-1 && !heap.isEmpty()){
			Edge temp = heap.poll();
			if (!visited[temp.getSndVertex().getIndex()]) {
				if (max < temp.getWeight())
						max = temp.getWeight();
				treeWeight += temp.weight;
				visited[temp.getSndVertex().getIndex()] = true;

				for (int i = 1; i < numPoints; i++){
					if (!visited[i])
					{
						float tempWeight = generateWeight(temp.getSndVertex(), nodes[i]);
						if (tempWeight < generateThreshold(numPoints, numDimensions))
							heap.add(new Edge (tempWeight, nodes[i]));
					}
				}
				
				edgeCount++;
			}
			
		}
		
		if (edgeCount < numPoints - 1)
			System.out.println("bound too low");
		
		return treeWeight;
	}
	
	static class Edge implements Comparable{
		float weight;
		Node sndVertex;
		
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
	
	static class Node{
		int dim;
		float[] coords;
		int index;
		
		public Node (int dimensions, int ind){
			index = ind;
			dim = dimensions;
			coords = new float[dim];
			for (int i = 0; i < dim; i++) {
				coords[i] = generator.nextFloat();
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