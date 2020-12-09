import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeNode {

    public String name;
    public int nodeLevel;
    public ArrayList<TreeNode> children = new ArrayList<>();
    public HashMap<String,Integer> childValues = new HashMap<>();

    public TreeNode(){

    }

    public TreeNode(String name){
        this.name = name;
    }

    public TreeNode(String name, int level){
        nodeLevel = level;
        this.name = name;
    }

}
