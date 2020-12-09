
public class Edge{

    private String from;
    private int weight;
    private String to;

    public Edge(){

    }

    public Edge(String from, String to, int utility){
        this.from = from;
        this.to = to;
        this.weight = utility;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Edge copy(){
        return new Edge(from, to, weight);
    }

    public boolean connectedTo(String name){
        if(to.equals(name) || from.equals(name)){
            return true;
        } else {
            return false;
        }
    }

    public boolean checkContent(String to, String from){
        if(this.to.equals(to) && this.from.equals(from)){
            return true;
        } else if(this.to.equals(from) && this.from.equals(to)){
            return true;
        }
        return false;
    }
}
