import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;
import org.w3c.dom.NamedNodeMap;

import javax.lang.model.element.NestingKind;

/**
 * Created by Rafael on 4/15/2016.
 */

class FamilyTree{
    private class Node{
        String name;
        Node father;
        Node mother;
        public Node(String name, Node father,Node mother){
            this.name = name;
            this.father = father;
            this.mother = mother;
        }
    }
    private Node root;
    public FamilyTree(String ego){
        root = new Node(ego,null,null);
    }
    private Node find(String name){
        return find(name,root);
    }
    private Node find(String name, Node root){
        if(root!=null){
            if(root.name.equals(name)){
                return root; //preorder traversal visits the root first so we must check if we found the name we are looking for.
            }
            Node fatherResult = find(name,root.father); //goes down the father side of the tree
            if(fatherResult != null) {
                return fatherResult;
            }
            Node motherResult = find(name,root.mother); // goes down the mother side of the tree
            return motherResult;
        }
        return null;
    }
    public void addParents(String ego, String father, String mother){
        Node temp = find(ego);
        if (temp==null){
            System.out.println("*");
            throw new IllegalArgumentException("Kid " + ego + " doesn't exist");
        }
        else{
            temp.father = new Node(father,null,null);
            temp.mother = new Node(mother,null,null);
        }

    }
    public boolean isDescendant(String ego,String ancestor){
        Node temp1 = find(ego); // for node of name ego
        Node temp2 = find(ancestor); // for node of name ancestor
        if (temp1 == null || temp2 == null){
            return false;
        }
        return isDescendant(temp1,temp2);
    }
    private boolean isDescendant(Node root, Node ancestor){
        if(root!=null){
            if(root==ancestor){
                return true;
            }
            return isDescendant(root.father,ancestor)|| isDescendant(root.mother,ancestor); //because we are moving down from root to see if we get to ancestor
        }
        return false;
    }
}

public class Pottery
{
    public static void main(String [] args)
    {
        FamilyTree family = new FamilyTree("Al");
        family.addParents("Al", "Harry", "Ginny");
        family.addParents("Harry", "James", "Lily");
        family.addParents("Ginny", "Arthur", "Molly");

        System.out.println(family.isDescendant("Al", "Molly"));        //  true
        System.out.println(family.isDescendant("Ginny", "James"));    //  false
        System.out.println(family.isDescendant("Harry", "Salazar")); //  false
        System.out.println(family.isDescendant("Harry", "James"));  //  true
        System.out.println(family.isDescendant("Harry", null));    //  false
    }
}
