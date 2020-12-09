import java.util.HashMap;
import java.util.ArrayList;

public class Day13 {

    private Data data = new Data();
    private HashMap<String, HashMap<String, Integer>> people = new HashMap<>();
    private int maxUtility = Integer.MIN_VALUE;

    /**
     * Parses a String of data and generates the relation for the first person in the line.
     * @param line current line to parse
     */
    private void parseInputLine(String line){
        int space = line.indexOf(" ");
        int utility = 1;
        String name = line.substring(0, space); // name one is the first word

        if(!people.containsKey(name)){
            people.put(name, new HashMap<String, Integer>());
        }

        space = line.indexOf(" ", space + 1) + 1;
        String direction = line.substring(space, line.indexOf(" ", space + 1)); // third word is gain/lose
        space = line.indexOf(" ", space) + 1;

        if(direction.equals("lose")){
            utility = -1;
        }

        utility *= Integer.parseInt(
                line.substring(space, line.indexOf(" ", space + 1))); // fourth word is the utility level

        people.get(name).put(line.substring(line.lastIndexOf(" ") + 1), utility); // last word is the adjacent person
    }

    private void part1(){
        String[] inputData = data.getDataPart1();

        for(int i = 0; i < inputData.length; i++){
            parseInputLine(inputData[i]);
        }

        ArrayList<Edge> edges = generateEdges(people);
        TreeNode root = generateTree(edges);
        System.out.println("The maximum value for part 1 is: " + findGreatestBranch(root));
    }

    private void part2(){
        String[] inputData = data.addDataPart2(people.size(), people);

        for(int i = 0; i < inputData.length; i++){
            parseInputLine(inputData[i]);
        }

        ArrayList<Edge> edges = generateEdges(people);
        TreeNode root = generateTree(edges);
        System.out.println("The maximum value for part 2 is: " + findGreatestBranch(root));
    }

    /**
     * Generate edges for all possible pairs.
     * @param people HashMap of all people and their respective utility values of adjacency.
     * @return ArrayList of Edges
     */
    private ArrayList<Edge> generateEdges(HashMap<String, HashMap<String, Integer>> people){
        ArrayList<Edge> edges = new ArrayList<>();

        people.forEach((name, nodeRelations) -> {
            // nodeRelations is a HashMap<String, int> for the person's relations
            nodeRelations.forEach((otherPerson, utility) -> {
               int personUtility = nodeRelations.get(otherPerson);
               int otherPersonUtility = people.get(otherPerson).get(name);

                if(!checkEdgeExists(name, otherPerson, edges)){
                    edges.add(new Edge(name, otherPerson, personUtility + otherPersonUtility));
                }
            });
        });

        return edges;
    }

    /**
     * Generates a tree based on the edges and currently defined people.
     * @param edges ArrayList of Edges
     * @return TreeNode the root node of the tree
     */
    private TreeNode generateTree(ArrayList<Edge> edges){
        int branchLevel = 0;
        ArrayList<String> peopleArray = new ArrayList<>(people.keySet());
        TreeNode rootNode = new TreeNode(peopleArray.get(branchLevel), branchLevel);
        peopleArray.remove(rootNode.name);
        addChildren(branchLevel, rootNode, peopleArray, edges);
        return rootNode;
    }

    /**
     * Recursive method to add children to the current node.
     * @param branchLevel the current branch level
     * @param parentNode the parent node
     * @param remainingPeople ArrayList of remaining people
     * @param edges ArrayList of Edges
     */
    void addChildren(int branchLevel, TreeNode parentNode, ArrayList<String> remainingPeople, ArrayList<Edge> edges){
        parentNode.nodeLevel = branchLevel; // necessary for the recursive increment

        for(int i = 0; i < remainingPeople.size(); i++){
            TreeNode newNode = new TreeNode(remainingPeople.get(i));
            parentNode.children.add(newNode);
            parentNode.childValues.put(newNode.name, findEdgeWeight(parentNode.name, newNode.name, edges));
            ArrayList<String> remPeople = (ArrayList<String>) remainingPeople.clone();
            remPeople.remove(newNode.name);
            addChildren(branchLevel + 1, newNode, remPeople, edges);
        }

        branchLevel++;
        if(branchLevel == (people.size())){
            // add the first node as the leaf to calculate the correct utility value
            TreeNode newNode = new TreeNode(new ArrayList<>(people.keySet()).get(0), branchLevel);
            parentNode.children.add(newNode);
            parentNode.childValues.put(newNode.name, findEdgeWeight(parentNode.name, newNode.name, edges));
        }
    }

    /**
     * Find the maximum value from root to leaf.
     * @param root the start node
     * @return the maximum value
     */
    private int findGreatestBranch(TreeNode root){
        int currentSum = 0;
        maxUtility = Integer.MIN_VALUE; // set to minimum value before starting evaluation
        evaluateChildren(root, currentSum);
        return maxUtility;
    }

    /**
     * Recursive method to evaluate the remaining nodes before reaching the leaf.
     * maxUtility is managed on the instance level.
     * @param node the current parent node
     * @param currentSum the utility sum so far
     */
    private void evaluateChildren(TreeNode node, int currentSum){

        for(int i = 0; i < node.children.size(); i++){
            String childName = node.children.get(i).name;
            int newCurrentSum = currentSum + node.childValues.get(childName); // avoids keeping track of currentSum
            evaluateChildren(node.children.get(i), newCurrentSum);
        }

        if(node.nodeLevel == people.size()){
            if(currentSum > maxUtility){
                maxUtility = currentSum;
            }
        }
    }

    /**
     * Utility method to simplify relation existence check
     * @param to the second person
     * @param from the first person
     * @param edges ArrayList of relations
     * @return true if edge exists
     */
    private boolean checkEdgeExists(String to, String from, ArrayList<Edge> edges){
        int i = 0;

        while(i < edges.size()){
            if(edges.get(i).checkContent(to, from)) return true;
            i++;
        }

        return false;
    }

    /**
     * Utility method to simplify finding the weight of two adjacent people
     * @param name first person
     * @param otherName second person
     * @param edges ArrayList of relations
     * @return the edge weight, all edges are assumed to be defined (non-existence is not handled).
     */
    private int findEdgeWeight(String name, String otherName, ArrayList<Edge> edges){

        for(int i =0; i < edges.size(); i++){
            if(edges.get(i).checkContent(name, otherName)) return edges.get(i).getWeight();
        }

        return 0;
    }

    public static void main(String[] args){
        Day13 day = new Day13();
        day.part1();
        day.part2();
    }
}

