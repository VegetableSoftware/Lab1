package Graph;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
//useless change in line 5
//change on branch B1
public class Graph {
	private static class Edge {
		int v1;
		int v2;
		int weight;
		String color = "black";
		Edge toEdge;
		Edge fromEdge;
		
		Edge(int u, int v, int theWeight) {
			v1 = u;
			v2 = v;
			weight = theWeight;
			toEdge = fromEdge = null;
		}
		@Override public boolean equals(Object otherObject) {
			if (this == otherObject)	return true;
			if (otherObject == null)	return false;
			if (getClass() != otherObject.getClass())	return false;
			Edge other = (Edge)otherObject;
			return v1 == other.v1
					&& v2 == other.v2;
		}
		@Override public int hashCode() {
			return Objects.hash(v1, v2);
		}
	}
	private static class Vertex {
		String word;
		int outDegree = 0;
		Edge toEdge;
		Edge fromEdge;
		Vertex(String theWord) {
			word = theWord;
		}
	}
	/**
	 * @deprecated 
	 * @author Vegetable
	 */
	@SuppressWarnings("unused")
	private class RecordInContainer {
		String word;
		int index;
		@Override public boolean equals(Object otherObject) {
			if (this == otherObject)	return true;
			if (otherObject == null) 	return false;
			if (getClass() != otherObject.getClass())	return false;
			RecordInContainer other = (RecordInContainer)otherObject;
			return index == other.index
					&& Objects.equals(word, other.word);
		}
		@Override public int hashCode() {
			return Objects.hash(word, index);
		}
	}
	
	private Vector<Vertex> vertex;
	private HashMap<String, Integer> str2Idx;
	private int size = 0;
	
	public Graph(String filePath) throws IOException {
		vertex = new Vector<Vertex>();
		str2Idx = new HashMap<String, Integer>();
		Scanner in = new Scanner(Paths.get(filePath), "UTF-8");
		in.useDelimiter("[^a-zA-Z]+");
		String word1 = null, word2 = null;
		while (in.hasNext()) {
			word2 = in.next().toLowerCase();
			AddEdge(word1, word2);
			word1 = word2;
		}
		in.close();
	}
	
	private void setColor(LinkedList<Integer> path) {
		for (int i = 0; i < path.size() - 1; ++i) {
			Edge e = vertex.get(path.get(i)).toEdge;
			while (e != null && e.v2 != path.get(i + 1))
				e = e.toEdge;
			if (e == null)
				throw new RuntimeException("Path Error");
			e.color = "red";
		}
	}
	
	private void refreshColor(LinkedList<Integer> path) {
		for (int i = 0; i < path.size() - 1; ++i) {
			Edge e = vertex.get(path.get(i)).toEdge;
			while (e != null && e.v2 != path.get(i + 1))
				e = e.toEdge;
			if (e == null)
				throw new RuntimeException("Path Error");
			e.color = "black";
		}
	}
	
	private String edgeToString(Edge e) {
		return vertex.get(e.v1).word + "->" + vertex.get(e.v2).word
				+ " [label = " + e.weight + " color = " + e.color + "];";
	}
	
	private LinkedList<LinkedList<Integer>> 
		getPathSequence(Vector<Vector<Integer>> parentPath,
			int begin ,int end) {
		LinkedList<LinkedList<Integer>> ret 
			= new LinkedList<LinkedList<Integer>>();
		if (begin == end) {
			ret.add(new LinkedList<Integer>());
			ret.getFirst().addLast(end);
			return ret;
		}
		for (int preVertex : parentPath.get(end)) {
			LinkedList<LinkedList<Integer>> subPathSet
				= getPathSequence(parentPath, begin, preVertex);
			for (LinkedList<Integer> subPath : subPathSet) {
				subPath.addLast(end);
				ret.addLast(subPath);
			}
		}
		return ret;
	}
	
	private Vector<String> getSetOfBridgeWords(String word1, String word2) {
		if (!str2Idx.containsKey(word1) || !str2Idx.containsKey(word2))
			return null;
		HashSet<Integer> set = new HashSet<Integer>();
		int index1 = str2Idx.get(word1);
		int index2 = str2Idx.get(word2);
		Vertex v1 = vertex.get(index1);
		Vertex v2 = vertex.get(index2);
		Edge e = v1.toEdge;
		while (e != null) {
			set.add(e.v2);
			e = e.toEdge;
		}
		Vector<String> bridgeWords = new Vector<String>();
		e = v2.fromEdge;
		while (e != null) {
			if (set.contains(e.v1))
				bridgeWords.add(vertex.get(e.v1).word);
			e = e.fromEdge;
		}
		return bridgeWords;
	}
	
	private void AddEdge(String word1, String word2) {
		if (word1 == null || word2 == null || Objects.equals(word1, word2))	
			return;
		if (!str2Idx.containsKey(word1)) {
			str2Idx.put(word1, size++);
			vertex.add(new Vertex(word1));
		}
		if (!str2Idx.containsKey(word2)) {
			str2Idx.put(word2, size++);
			vertex.add(new Vertex(word2));
		}
		int index1 = str2Idx.get(word1);
		int index2 = str2Idx.get(word2);
		Vertex v1 = vertex.get(index1);
		Vertex v2 = vertex.get(index2);
		Edge e = v1.toEdge;
		Edge newEdge = new Edge(index1, index2, 1);
		while (e != null && !e.equals(newEdge))
			e = e.toEdge;
		if (e != null)
			e.weight++;
		else {
			v1.outDegree++;
			newEdge.toEdge = v1.toEdge;
			newEdge.fromEdge = v2.fromEdge;
			v1.toEdge = v2.fromEdge = newEdge;
		}
	}

	public String queryBridgeWords(String word1, String word2) {
		Vector<String> bridgeWords = getSetOfBridgeWords(word1, word2);
		if (bridgeWords == null)
			return "No \"" + word1 + "\" or \"" + word2 + "\" in the graph!";
		
		if (bridgeWords.size() == 0)
			return "No bridge words from \"" + word1 + "\" to \"" + word2 
					+ "\"!";
		else if (bridgeWords.size() == 1)
			return "The bridge word from \"" + word1 + "\" to \"" + word2 
					+ "\" is: \"" + bridgeWords.get(0)
					+ "\".";
		else {
			StringBuilder ret = new StringBuilder("The bridge words from \""
					+ word1 + "\" to \"" + word2 + "\" are: ");
			int i;
			for (i = 0; i < bridgeWords.size() - 1; ++i) {
				ret.append("\"");
				ret.append(bridgeWords.get(i));
				ret.append("\", ");
			}
			ret.append("and \"");
			ret.append(bridgeWords.get(i));
			ret.append("\".");
			return ret.toString();
		}
	}

	public String generateNewText(String inputText) {
		Scanner in = new Scanner(inputText);
		Vector<String> wordSet = new Vector<String>();
		in.useDelimiter("[^a-zA-Z]+");
		while (in.hasNext()) {
			String word = in.next();
			wordSet.add(word.toLowerCase());
		}
		in.close();
		if (wordSet.isEmpty())
			return new String();
		StringBuilder ret = new StringBuilder(wordSet.get(0));
		System.out.println(wordSet.toString());
		for (int i = 1; i < wordSet.size(); ++i) {
			String word1 = wordSet.get(i - 1);
			String word2 = wordSet.get(i);
			Vector<String> bridgeWords = getSetOfBridgeWords(word1, word2);
			if (bridgeWords != null) {
				int index = (int)(Math.random() * bridgeWords.size());
				if (bridgeWords.size() != 0)
					ret.append(" " + bridgeWords.get(index));
			}
			ret.append(" " + word2);
		}
		return ret.toString();
	}

	public String randomWalk() {
		if (size == 0)
			return new String();
		HashSet<Edge> traversed = new HashSet<Edge>();
		int vertexIndex = (int)(Math.random() * size);
		Vertex v = vertex.get(vertexIndex);
		Edge e = v.toEdge;
		int edgeIndex = (int)(Math.random() * v.outDegree);
		StringBuilder ret = new StringBuilder(v.word);
		
		while (edgeIndex-- != 0)
			e = e.toEdge;
		
		while (e != null && !traversed.contains(e)) {
			traversed.add(e);
			v = vertex.get(e.v2);
			e = v.toEdge;
			ret.append(" " + v.word);
			edgeIndex = (int)(Math.random() * v.outDegree);
			while (edgeIndex-- != 0)
				e = e.toEdge;
		}
		return ret.toString();
	}

	private LinkedList<LinkedList<Integer>> 
		getShortestPath(String word1, String word2) {
		Vector<Integer> distance = new Vector<Integer>(size);
		Vector<Vector<Integer>> parent = new Vector<Vector<Integer>>(size);
		LinkedList<Integer> nextDistance = new LinkedList<Integer>();
		for (int i = 0; i < size; ++i) {
			distance.add(Integer.MAX_VALUE);
			parent.add(null);
		}
		int index1 = str2Idx.get(word1), index2 = str2Idx.get(word2);
		int currentDistance = 0;
		int currentIndex = index1;
		nextDistance.add(currentIndex);
		distance.set(currentIndex, currentDistance);
		while (parent.get(index2) == null && !nextDistance.isEmpty()) {
			LinkedList<Integer> newDistance = new LinkedList<Integer>();
			for (int vIndex : nextDistance) {
				Edge e = vertex.get(vIndex).toEdge;
				while (e != null) {
					if (distance.get(e.v2) > currentDistance) {
						if (distance.get(e.v2) > currentDistance + 1)
							newDistance.add(e.v2);
						distance.set(e.v2, currentDistance + 1);
						if (parent.get(e.v2) == null)
							parent.set(e.v2, new Vector<Integer>(size));
						parent.get(e.v2).add(e.v1);
					}
					e = e.toEdge;
				}
			}
			nextDistance = newDistance;
			currentDistance++;
		}
		if (parent.get(index2) == null)
			return new LinkedList<LinkedList<Integer>>();
		return getPathSequence(parent, index1, index2);
	}
	
	private LinkedList<LinkedList<LinkedList<Integer>>>
		getShortestPath(String word) {
		LinkedList<LinkedList<LinkedList<Integer>>> ret = 
				new LinkedList<LinkedList<LinkedList<Integer>>>();
		for (Vertex v : vertex) {
			String word2 = v.word;
			if (Objects.equals(word, word2) == false)
				ret.addLast(getShortestPath(word, word2));
		}
		return ret;
	}

	public boolean exist(String word) {
		if (word == null || !str2Idx.containsKey(word.toLowerCase()))
			return false;
		return true;
	}
	
	public String toDot() {
		StringBuilder ret = new StringBuilder();
		ret.append("resolution = 960;");
		for (Vertex v : vertex) {
			Edge e = v.toEdge;
			while (e != null) {
				ret.append(edgeToString(e));
				e = e.toEdge;
			}
		}
		return ret.toString();
	}

	public LinkedList<String> toDot(String word1, String word2) {
		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();
		if (!str2Idx.containsKey(word1) || !str2Idx.containsKey(word2))
			return null;
		if (Objects.equals(word1, word2))
			return null;
		LinkedList<String> ret = new LinkedList<String>();
		LinkedList<LinkedList<Integer>> pathSet 
			= getShortestPath(word1, word2);
		for (LinkedList<Integer> path : pathSet) {
			setColor(path);
			ret.add(toDot());
			refreshColor(path);
		}
		return ret;
	}

	public LinkedList<LinkedList<String>> toDot(String word) {
		word.toLowerCase();
		LinkedList<LinkedList<String>> ret = new LinkedList<>();
		LinkedList<LinkedList<LinkedList<Integer>>> toOthersPathSet
			= getShortestPath(word);
		for (LinkedList<LinkedList<Integer>> pathSet : toOthersPathSet) {
			LinkedList<String> toCertainWord = new LinkedList<String>();
			for (LinkedList<Integer> path : pathSet) {
				setColor(path);
				toCertainWord.add(toDot());
				refreshColor(path);
			}
			ret.add(toCertainWord);
		}
		return ret;
	}
	
	public static class TestBed {
		public static void main(String args[]) throws IOException {
			Graph g = new Graph("test.txt");
			System.out.println("***********************************");
			System.out.println("Query bridge words (quit to quit) :");
			Scanner in = new Scanner(System.in);
			String word1 = null, word2 = null;
			while (!Objects.equals(word1 = in.next(), "quit")) {
				word2 = in.next();
				System.out.println(g.queryBridgeWords(word1, word2));
			}
			
			in.nextLine();
			System.out.println("***********************************");
			System.out.println("Generate new text (quit to quit) :");
			while (!Objects.equals(word1 = in.nextLine(), "quit")) {
				System.out.println(g.generateNewText(word1));
			}
			for (int i = 0; i < 10; ++i)
				System.out.println(g.randomWalk());
			
			System.out.println("***********************************");
			System.out.println("Calculate the path (quit to quit) :");
			String line = null;
			while (!Objects.equals(line = in.nextLine(), "quit")) {
				Scanner lines = new Scanner(line);
				try {
					word1 = lines.next();
				} catch (NoSuchElementException e) {
					System.out.println(g.toDot());
					lines.close();
					continue;
				}
				try {
					word2 = lines.next();
				} catch (NoSuchElementException e) {
					System.out.println(g.toDot(word1));
					lines.close();
					continue;
				}
				lines.close();
				System.out.println(g.toDot(word1, word2));
			}
			LinkedList<LinkedList<String>> dotSource = g.toDot("to");
			LinkedList<LinkedList<String>> filePath = new LinkedList<>();
			int i = 0, j = 0;
			for (LinkedList<String> toCertainWordSource : dotSource) {
				j = 0;
				LinkedList<String> toCertainWordFilePath = new LinkedList<>();
				for (String source : toCertainWordSource) {
					String filename = Dot.DotCompiler.saveTempFile("tempfile " + i
							+ "-" + (j++), source);
					toCertainWordFilePath.addLast(filename);
				}
				++i;
			}
			Dot.DotCompiler.clearCache();
			in.close();
		}
	}
}
