package org.mohsinmalik324.cse373;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Graph {
	
	private Map<Integer, ArrayList<Integer>> adjLists = new HashMap<>();
	private int nodes = 0;
	private int edges = 0;
	
	public void loadFromFile(String path) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int lineNum = 0;
			while((line = bufferedReader.readLine()) != null) {
				if(lineNum == 0) {
					edges = Integer.valueOf(line);
				} else if(lineNum == 1) {
					nodes = Integer.valueOf(line);
				} else {
					String[] edgeArray = line.split(" ");
					if(edgeArray.length >= 2) {
						int edge1 = Integer.valueOf(edgeArray[0]);
						int edge2 = Integer.valueOf(edgeArray[1]);
						addEdge(edge1, edge2);
						addEdge(edge2, edge1);
					}
				}
				lineNum++;
			}
			bufferedReader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addEdge(int from, int to) {
		if(!adjLists.containsKey(from)) {
			adjLists.put(from, new ArrayList<>());
		}
		List<Integer> edges = adjLists.get(from);
		if(!edges.contains(to)) {
			edges.add(to);
		}
	}
	
	public void printConnectedComponents() {
		List<Integer> disc = new ArrayList<>();
		int compNum = 1;
		for(int i = 1; i <= nodes; i++) {
			if(!disc.contains(i)) {
				List<Integer> bfsList = bfs(i);
				System.out.print("Component " + compNum + ": ");
				for(int node : bfsList) {
					disc.add(node);
					System.out.print(node + " ");
				}
				System.out.println();
				compNum++;
			}
		}
	}
	
	public List<Integer> bfs(int startingNode) {
		List<Integer> nodes = new ArrayList<>();
		boolean[] visited = new boolean[this.nodes + 1];
		Queue<Integer> queue = new LinkedList<>();
		visited[startingNode] = true;
		queue.add(startingNode);
		while(!queue.isEmpty()) {
			int node = queue.poll();
			nodes.add(node);
			List<Integer> adjList = adjLists.get(node);
			if(adjList != null) {
				for(int adjNode : adjLists.get(node)) {
					if(!visited[adjNode]) {
						visited[adjNode] = true;
						queue.add(adjNode);
					}
				}
			}
		}
		return nodes;
	}
	
}