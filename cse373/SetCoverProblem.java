package org.mohsinmalik324.cse373;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetCoverProblem {
	
	private Set<Integer> universalSet = new HashSet<>();
	private List<Set<Integer>> subsets = new ArrayList<>();
	private List<Set<Integer>> minSetCover;
	private int min = -1;
	
	public SetCoverProblem(String fileName) {
		loadFromFile(fileName);
	}
	
	public void loadFromFile(String fileName) {
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int lineNum = 0;
			while((line = bufferedReader.readLine()) != null) {
				if(lineNum == 0) {
					int u = Integer.valueOf(line);
					for(int i = 1; i <= u; i++) {
						universalSet.add(i);
					}
				} else if(lineNum > 1) {
					Set<Integer> subset = new HashSet<>();
					for(String stringNum : line.split(" ")) {
						try {
							int num = Integer.valueOf(stringNum);
							subset.add(num);
						} catch(NumberFormatException e) {
							
						}
					}
					subsets.add(subset);
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
	
	public Set<Integer> getUniversalSet() {
		return universalSet;
	}
	
	public List<Set<Integer>> getSubsets() {
		return subsets;
	}
	
	public List<Set<Integer>> solve() {
		List<Set<Integer>> a = new ArrayList<>();
		min = -1;
		solveBacktrack(a);
		return minSetCover;
	}
	
	private void solveBacktrack(List<Set<Integer>> a) {
		if(isASolution(a)) {
			processSolution(a);
		} else {
			if(min != -1 && a.size() + 1 >= min) {
				return;
			}
			List<Set<Integer>> c = constructCandidates(a);
			for(Set<Integer> subset : c) {
				a.add(subset);
				solveBacktrack(a);
				a.remove(subset);
			}
		}
	}
	
	private boolean isASolution(List<Set<Integer>> a) {
		boolean check = check(a);
		if(min == -1) {
			return check;
		} else {
			return a.size() < min && check;
		}
	}
	
	private List<Set<Integer>> constructCandidates(List<Set<Integer>> a) {
		List<Set<Integer>> c = new ArrayList<>();
		if(a.size() == 0) {
			c.addAll(subsets);
			return c;
		}
		Set<Integer> lastSet = a.get(a.size() - 1);
		int index = subsets.indexOf(lastSet);
		for(int i = index + 1; i < subsets.size(); i++) {
			Set<Integer> subset = subsets.get(i);
			if(!a.contains(subset)) {
				c.add(subset);
			}
		}
		return c;
	}
	
	private void processSolution(List<Set<Integer>> a) {
		min = a.size();
		minSetCover = new ArrayList<>();
		minSetCover.addAll(a);
	}
	
	private boolean check(List<Set<Integer>> subsets) {
		Set<Integer> combinedSet = new HashSet<>();
		for(Set<Integer> subset : subsets) {
			combinedSet.addAll(subset);
		}
		return combinedSet.containsAll(universalSet);
	}
	
}