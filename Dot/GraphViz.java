package Dot;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
//useless change in line 9
//change on branch C4
class GraphViz {

	private static String TEMP_DIR = "F:\\code\\java\\SoftwareEngineeringLab\\cache"; // Linux
	 //private static String TEMP_DIR = ""; // Windows

	private static String DOT = "D:\\Graphviz-2.38\\release\\bin\\dot.exe"; // Linux
	//private static String DOT = "D:\\Download\\GraphViz\\bin\\dot.exe";
	// (x86)\\Graphviz2.38\\bin\\dot.exe"; // Windows
	private StringBuilder graph = new StringBuilder();

	public GraphViz() {
	}

	public String getDotSource() {
		return graph.toString();
	}

	public void add(String line) {
		graph.append(line);
	}

	public void addln(String line) {
		graph.append(line + "\n");
	}

	public void addln() {
		graph.append('\n');
	}

	public byte[] getGraph(String dot_source, String type) {
		File dot;
		byte[] img_stream = null;
		try {
			dot = writeDotSourceToFile(dot_source);
			if (dot != null) {
				img_stream = get_img_stream(dot, type);
				//if (dot.delete() == false)
				//	System.err.println("Warning: " + dot.getAbsolutePath() + " could not be deleted!");
				return img_stream;
			}
			return null;
		} catch (java.io.IOException ioe) {
			return null;
		}
	}

	public int writeGraphToFile(byte[] img, String file) {
		return writeGraphToFile(img, new File(file));
	}

	public int writeGraphToFile(byte[] img, File to) {
		try {
			FileOutputStream fos = new FileOutputStream(to);
			fos.write(img);
			fos.close();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
			return -1;
		}
		return 1;
	}

	private byte[] get_img_stream(File dot, String type) {
		File img;
		byte[] img_stream = null;
		try {
			img = File.createTempFile("graph_", "." + type, new File(GraphViz.TEMP_DIR));
			Runtime rt = Runtime.getRuntime();
			String[] args = { DOT, "-T" + type, dot.getAbsolutePath(), "-o", img.getAbsolutePath() };
			Process p = rt.exec(args);
			p.waitFor();
			FileInputStream in = new FileInputStream(img.getAbsolutePath());
			img_stream = new byte[in.available()];
			in.read(img_stream);
			if (in != null) in.close();
			if (img.delete() == false)
				System.err.println("Warning: " + img.getAbsolutePath() + " could not be deleted!");
		} catch (java.io.IOException ioe) {
			System.err.println("Error:    in I/O processing of tempfile in dir " + GraphViz.TEMP_DIR + "\n");
			System.err.println("       or in calling external command");
			ioe.printStackTrace();
		} catch (java.lang.InterruptedException ie) {
			System.err.println("Error: the execution of the external program was interrupted");
			ie.printStackTrace();
		}
		return img_stream;
	}

	public File writeDotSourceToFile(String str) throws java.io.IOException {
		File temp;
		try {
			temp = File.createTempFile("graph_", ".dot", new File(GraphViz.TEMP_DIR));
			FileWriter fout = new FileWriter(temp);
			fout.write(str);
			fout.close();
		} catch (Exception e) {
			System.err.println("Error: I/O error while writing the dot source to temp file!");
			return null;
		}
		return temp;
	}

	public String start_graph() {
		return "digraph G {";
	}
	
	public String end_graph() {
		return "}";
	}

	public void readSource(String input) {
		StringBuilder sb = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(input);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			dis.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		this.graph = sb;
	}
} // end of class GraphViz
