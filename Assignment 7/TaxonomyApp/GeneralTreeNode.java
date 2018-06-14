// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 7
 * Name:
 * Username:
 * ID:
 */

import java.awt.Color;

import ecs100.*;

import java.util.*;

/**
 * GeneralTreeNode represents a simple node in a tree. The node stores references
 * to children and a parent (the latter is null in the case of the root node).
 * <p>
 * For the purposes of one technique for drawing on the screen, there is a location field as well.
 * This is purely for drawing purposes, and is not directly related to the concept of the tree structure.
 * NOTE: There are other techniques for drawing the tree that don't require the location field, so you
 * may decide not to use the location field/methods at all.
 *
 * @author Thomas Kuehne
 *         <p>
 *         Based on code written by Stuart Marshall and Monique Damitio
 */

public class GeneralTreeNode {
    // The name that is stored at the node.
    private String name;

    // A reference to the parent node of this node.  
    private GeneralTreeNode parent;

    // Since this is a general tree, we don't have a limit on the number of children a node may have, so we use a set.
    private Set<GeneralTreeNode> children = new HashSet<GeneralTreeNode>();

    // This field is only needed for one particular technique for drawing the tree on the screen.
    private Location location;  //location of the center of the node on the screen

    /**
     * Creates a new node
     *
     * @param newName the name of the new node
     */
    public GeneralTreeNode(String newName) {
        name = newName;
    }

    // getter and setter methods

    public String getName() {
        return name;
    }

    public GeneralTreeNode getParent() {
        return parent;
    }

    private void setParent(GeneralTreeNode newParent) {
        parent = newParent;
    }

    public Set<GeneralTreeNode> getChildren() {
        return children;
    }

    public void setLocation(Location newLocation) {
        location = newLocation;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Adds a child to the receiver node
     *
     * @param newChild the node/subtree to be added as a new child
     */
    public void addChild(GeneralTreeNode newChild) {
        // add to set of children
        children.add(newChild);

        // set new parent reference
        newChild.setParent(this);
    }

    /**
     * Finds the node whose name is equal to the target string
     * <p>
     * CORE.
     * <p>
     * If the target string appears multiple times, then just return the first one encountered.
     * The 'addNode' method from class GeneralTree should guarantee that duplicate strings aren't added anyway.
     * <p>
     * HINT: The most natural implementation of this method is recursive.
     *
     * @param targetName the name of the node we are looking for
     * @return the node that contains the target name, or null if no such node exists.
     */
    public GeneralTreeNode findNode(String targetName) {
        if (name.equals(targetName))
            return this;    //Chicks if the current node is equal to input node name, if so return the current node, checks if the target is null or if the children
        if (targetName == null || children.isEmpty())
            return null;  //set is empty if so return null. Creates a temporary variable then runs a foreach loop setting each value to the curent, and if its
        GeneralTreeNode current;  //not null returns each of the nodes in the children set.
        for (GeneralTreeNode nodes : children) {
            current = nodes.findNode(targetName);
            if (current != null) {
                return current;
            }
        }
        // no node containing the string could be found
        return null;
    }

    /**
     * Removes the receiver node from the list of children of its parent
     * <p>
     * CORE.
     */
    public void remove() {
        parent.addChildrenFromNode(this);
        children.clear();
        parent.getChildren().remove(this);  //Removes the current node.
        setParent(null);
    }

    /**
     * Adds children from another donor node to this node
     * <p>
     * CORE.
     * <p>
     * This method is used to implement method 'moveSubtree' in class General Tree.
     *
     * @param donorNode the node that has the children to be added
     */
    public void addChildrenFromNode(GeneralTreeNode donorNode) {
        for (GeneralTreeNode node : donorNode.getChildren()) {
            node.setParent(this);
        }
        children.addAll(donorNode.children); //Adds all children of the donor node to the children set
    }

    /**
     * Prints the strings of all the nodes under the given target node
     * (including the target node itself)
     * <p>
     * CORE.
     * <p>
     * HINT: The most natural version of this method is recursive.
     */
    public void printSubtree() {
        UI.println(name);   //Prints the current nodes name then operates on the children of that node then recursively calls the method.
        for (GeneralTreeNode child : children) {
            child.printSubtree();
        }
    }

    /**
     * Returns true if the subtree whose root starts with the receiver contains the node of the parameter
     * <p>
     * COMPLETION.
     * <p>
     * This method is used by moveSubtree(...), to ensure that we aren't trying to move a node
     * (and hence the subtree rooted at that node) in a way that makes it become a child of one
     * of it's existing descendants.
     * <p>
     * HINT: The most natural version of this method is recursive.
     *
     * @param node the node to check for
     * @return true if the node is in the subtree, and false otherwise
     */
    public boolean contains(GeneralTreeNode node) {
        if (node == this)
            return true;  //Similar to the BST contains except key difference is the type of tree, as there is an arbitrary amount of children, the left or right is replaced by a for each loop
        if (node == null || children.isEmpty())
            return false; //that iterates through all children, then returns true if it is there.
        for (GeneralTreeNode nodes : children) {
            if (nodes.contains(node)) ;
            return true;
        }
        // this subtree does not contain 'node'
        return false;
    }
    /**
     * Prints the names of all the nodes in the path from the target node to the root of the entire tree
     * <p>
     * COMPLETION.
     */
    public void printPathToRoot() {
        UI.println(name);  //Prints current name then if the parents not null utilizes the print method in GT
        if (parent != null) parent.printPathToRoot();
    }

    /**
     * Prints all the names of all the nodes at the given depth
     * <p>
     * COMPLETION.
     * <p>
     * Prints nothing if there are no nodes at the specified depth.
     * <p>
     * HINT: The most natural version of this method is recursive.
     * <p>
     * //@param depth the depth of the tree whoses nodes are to be listed. The root is at depth 0.
     */

    public void printAllAtDepth(int targetDepth, int currentDepth) {
        if (currentDepth == targetDepth) { //If currentDepth and the TargetDepth is the same, print then current name and return
            UI.println(name);
        }
        for (GeneralTreeNode nodes : children) { //Foreach node in children recurse through the mthod then iterate the current depth
            nodes.printAllAtDepth(targetDepth, currentDepth+1);
        }
    }
    /**
     * Draws all the nodes in the subtree that has the receiver as the root on the Graphics pane
     * <p>
     * CORE.
     * <p>
     * The provided code just draws the tree node; you need to make it draw all the nodes.
     * Make sure that parents and their children are connected by lines .
     * <p>
     * HINT: The most natural version of this method is recursive.
     * <p>
     * HINT: Use UI.drawLine(...) to draw the connecting lines and pay
     * attention in what order you 'paint' on the canvas in order to get a good looking result.
     */
    public void redrawSubtree() {
        double x = location.getX();  //Sets returns the location X and Y to then iterate through the children drawing links to each of the nodes then recurse's.
        double y = location.getY();
        for (GeneralTreeNode node : children) {
            UI.drawLine(x, y, node.location.getX(), node.location.getY());
            node.redrawSubtree();
        }
        this.redrawNode();
    }
    /**
     * Draws a node at the location stored in that node. Drawing the node consists of drawing an oval, and writing the name string
     * out "in" that oval. Note that no consideration is given to the length of the string, so this could look ugly.
     * <p>
     * //@param node  the node to be draw on the canvas. This node should already have had it's location set earlier on.
     */
    private void redrawNode() {
        double x = this.getLocation().getX();
        double y = this.getLocation().getY();

        UI.setColor(Color.white);
        UI.fillOval(x - 2 * GeneralTree.nodeRad, y - GeneralTree.nodeRad, GeneralTree.nodeRad * 4 - 1, GeneralTree.nodeRad * 2 - 1);

        UI.setColor(Color.black);
        UI.drawOval(x - 2 * GeneralTree.nodeRad, y - GeneralTree.nodeRad, GeneralTree.nodeRad * 4 - 1, GeneralTree.nodeRad * 2 - 1);

        UI.drawString(name, x - GeneralTree.nodeRad - 4, y + 5);
    }
}
