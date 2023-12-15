package shortestPath;

import java.util.LinkedList;
import java.util.List;

class EdgeWeightedDiagraph {

    private int numVertex;
    private LinkedList<DirectedEdge>[] adj;
 
    @SuppressWarnings("unchecked")
	EdgeWeightedDiagraph(int numVertex) {
        this.numVertex = numVertex;
        adj = new LinkedList[numVertex];
        for (int i = 0; i < numVertex; i++) {
            adj[i] = new LinkedList<DirectedEdge>();
        }
    }
 
    //向该city增加以该city为出发点的edge
    void addEdge(DirectedEdge edge) {
    	//city表示出发的城市
        int city = edge.from();
        adj[city].add(edge);
    }
 
    //返回所有该city出发的路径
    Iterable<DirectedEdge> adj(int city) {
        return adj[city];
    }
    
    List<DirectedEdge> getAdj(int city){
    	return adj[city];
    }
 
    //返回city的总数
    int getNumVertex() {
        return numVertex;
    }
 
    //返回edge的总数
    int getNumEdges() {
        int res = 0;
        for (LinkedList<DirectedEdge> e : adj) {
            res += e.size();
        }
        return res;
    }
 
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < adj.length; i++) {
            result += i + ":";
            for (DirectedEdge e : adj[i]) {
                result += String.format(" %s[%s]", e.to(), e.weight());
                //“0: 1[2] 2[3]”
            }
            result += "\n";
        }
        return result;
    }
	
}
