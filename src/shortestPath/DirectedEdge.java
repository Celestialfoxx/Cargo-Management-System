package shortestPath;

class DirectedEdge {
	
	private int from;
    private int to;
    private double weight;
	 
    DirectedEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
	 
    int from() {
        return from;
    }
	 
    int to() {
        return to;
    }
 
    double weight() {
        return this.weight;
    }
 
    @Override
    public String toString() {
        return String.format("%s->%s[%s]", from, to, weight);
    }
	

}
