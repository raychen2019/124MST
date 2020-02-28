import java.util.*;
import java.io.*;


public class RamaswamyChenAssign1 {

	static Random generator;
	static double max = 0.0;
	
	public static void main(String[] args){
		
		int seedNum = Integer.parseInt(args[0]);
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
	
	public static float generateWeight (int dimension){
		
		float weightTemp = 0;
		for (int i = 0 ; i < dimension ; i++)
			weightTemp += Math.pow((generator.nextFloat() - generator.nextFloat()),2);
		
		return (float) Math.sqrt(weightTemp); 
	}
	public static float FindMST (int numPoints, int numDimensions){

		float treeWeight = 0;
		
		//each vertex can be labeled as an integer
		boolean[] visited = new boolean[numPoints];
		visited[0] = true;
		
		PriorityQueue<Edge> heap = new PriorityQueue<Edge>();
		
		
		for (int i = 1; i < numPoints; i++)
		{
			heap.add(new Edge (generateWeight (numDimensions), i));
		}
		
		
		//the program terminates when we add n-1 edges 
		int edgeCount = 0;
		while (edgeCount < numPoints-1){
			Edge temp = heap.poll();
			
			if (!visited[temp.getSndVertex()]) {
				if (max < temp.getWeight())
					max = temp.getWeight();
				treeWeight += temp.weight;
				visited[temp.getSndVertex()] = true;
				
				for (int i = 1; i < numPoints; i++){
					if (!visited[i])
					{
						float tempWeight = generateWeight(numDimensions);
						if (tempWeight < 0.0065)
							heap.add(new Edge (tempWeight, i));
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
		int sndVertex;
		
		public Edge (float weightParam, int sndVertexParam){
			weight = weightParam;
			sndVertex = sndVertexParam;
		}
		
		public float getWeight(){
			return weight;
		}
		
		public int getSndVertex(){
			return sndVertex;
		}

		public int compareTo(Object o) {
			if (weight > ((Edge) o).weight)
				return 1;
			else 
				return -1;
		}

	}
}


