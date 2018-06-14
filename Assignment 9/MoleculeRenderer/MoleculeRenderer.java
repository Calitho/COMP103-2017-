// This program is copyright VUW.
// You are granted permission to use it to construct your answer to a COMP103 assignment.
// You may not distribute it in any other way without permission.

/* Code for COMP103 - 2017T2, Assignment 9
 * Name:
 * Username:
 * ID:
 */

import ecs100.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

/** 
 *  Renders a molecule on the graphics pane from different positions.
 *  
 *  A molecule consists of a collection of molecule elements, i.e., atoms.
 *  Each atom has a type (eg, Carbon, or Hydrogen, or Oxygen, ..),
 *  and a three dimensional position in the molecule (x, y, z).
 *
 *  Each molecule is described in a file by a list of molecule elements (atoms) and their positions.
 *  The molecule is rendered by drawing a colored circle for each atom.
 *  The size and color of each atom is determined by the type of the atom.
 * 
 *  The description of the size and color for rendering the different types of
 *  atoms is stored in the file "atom-definitions.txt" which should be read and
 *  stored in a Map.  When an atom is rendered, the type should be looked up in
 *  the map to find the size and color to pass to the atom's render() method
 * 
 *  A molecule can be rendered from different perspectives, and the program
 *  provides four buttons to control the perspective of the rendering.
 *  
 *  For interpreting the viewing positions indicated below, image the molecule to be in the
 *  centre of a clock face and the viewer rotating around the perimeter of the clockface.
 *   "Front" renders the molecule from the front (viewing position = 6 o'clock (0 degrees))
 *   "Back" renders the molecule from the back (viewing position = 12 o'clock (180 degrees))
 *   "Left" renders the molecule from the left (viewing position = 9 o'clock (-90 degrees))
 *   "Right" renders the molecule from the right (viewing position = 3 o'clock (90 degrees))
 *   "RotateLeft" rotates the viewing position by -50 seconds (-5 degrees),
 *   "RotateRight" rotates the viewing position by 50 seconds (5 degrees).
 *
 *  To make sure that the nearest atoms appear in front of the furthest atoms,
 *  the atoms must be rendered in order from the furthest away to the nearest.
 *  Each viewing position imposes a different ordering on the atoms.
 */

public class MoleculeRenderer {
    // Map containing the size and color of each atom type.
    private Map<String, AtomInfo> atoms;
    // The collection of the atoms in the current molecule
    private List<MoleculeElement> molecule;  
    private double scaleF = 10.0;
    private double currentAngle = 0.0;    //current viewing angle (in degrees)
    private double rotationStep = 5.0;    // change in ange when rotation around the molecule


    // Constructor:
    /** 
     * [CORE] / [COMPLETION]
     * Sets up the Graphical User Interface and reads the file of element data of
     * each possible type of atom into a Map: where the type is the key
     * and an AtomInfo object is the value (containing size and color).
     */
    public MoleculeRenderer() {
        UI.addButton("Read", () -> {
                String filename = UIFileChooser.open();
                readMoleculeFile(filename);
                view(0, new BackToFrontComparator());
            });
        UI.addButton("From Front", () -> view(0, new BackToFrontComparator()));
        UI.addButton("From Back", () -> view(180, new FrontToBackComparator()));
        UI.addButton("From Left", () -> view(90, new LeftToRightComparator()));
        UI.addButton("From Right", () -> view(-90, new RightToLeftComparator()));
        UI.addButton("Rotate Right", () -> Rotate(-1));
        UI.addButton("Rotate Left", () -> Rotate(1));
        UI.addSlider("Zoom", 1,20, this::zoom);
        readAtomInfos();    //  Read the atom definitions
    }
    public void Rotate(int n){  //Rotate method allows for 2 types of rotation left and right, then increment's the current angle by the rotation step,
        view(currentAngle += (n * rotationStep), new PerspectiveComparator()); // which depending on inputs, can be negative increments or positive, and so changes accordingly.
        render();
    }
    public void zoom (double d) {
        UI.getGraphics().scale(1 + (d- scaleF) / 10, 1 + (d - scaleF) / 10); //Sets up a slider that zooms in and out, however this still has problems, max zoom freezes it,
        scaleF = d;// and you cannot determine where the zoom will happen so you cannot focus on a certain point.
        render();
    }
    /** 
     *  [CORE]
     *  Reads the molecule data from a file containing one line for each atom in the molecule.
     *  Each line contains an atom type and the 3D coordinates of the atom.
     *  For each atom, the method constructs a MoleculeElement object,
     *  and adds it to the List of molecule elements in the molecule.
     *
     *  [COMPLETION]
     *  To obtain the color and the size of each atom, it has to look up the type
     *  of the respective atom in the Map of atoms.
     */
    public void readMoleculeFile(String fname) {
        molecule = new ArrayList<>(); //Sets up a new array list,
        try {
            Scanner sc = new Scanner(new File(fname));  //Sets up a new scanner object and while it has an item to scan, adds a new molecule with values then moves through the txt file
            while (sc.hasNext()){
                AtomInfo current = atoms.get(sc.next());
                molecule.add(new MoleculeElement(sc.nextDouble(),sc.nextDouble(),sc.nextDouble(), current.color,current.radius));
                sc.nextLine();
            }
        }
        catch(IOException ex) {
            UI.println("Reading molecule file " + fname + " failed.");
        }
    }

    /** 
     *  [COMPLETION]
     *  Reads a file containing radius and color information about each type of
     *  atom and stores the info in a Map, using the atom type as a key.
     */
    private void readAtomInfos() {
        UI.println("Reading the atom definitions...");
        atoms = new HashMap<String, AtomInfo>(); //initializes a new Hash map of Strings and atom info, so of atoms....
        try {
            Scanner sc = new Scanner(new File("atom-definitions.txt")); //New scanner scanning from txt file, reads the name and adds a new atom to the Hashmap
            while (sc.hasNext()) {
                String name = sc.next();
                atoms.put(name, new AtomInfo(name,sc.nextInt(),new Color(sc.nextInt(), sc.nextInt(), sc.nextInt())));
            }
        }
        catch (IOException ex) {
            UI.println("Reading atom definitions FAILED.");
        }
    }

    public void view (double viewingAngle, Comparator<MoleculeElement> sortingCriterion) {
        currentAngle = viewingAngle;

        if (molecule == null)
            return;
        Collections.sort(molecule, sortingCriterion);

        render(); // render the molecule according to the current ordering
    }

    /**
     *  Renders the molecule, according the the current ordering of Atoms in the List.
     *  The Atom's render() method needs the current perspective angle.
     */
    public void render() {
        UI.clearGraphics();

        for(MoleculeElement moleculeElement : molecule) {
            moleculeElement.render(currentAngle);
        }

    }

    /**    
     * 
     * Private comparator classes.
     * 
     * You will need a comparator class for each different direction
     *
     * Each comparator class should be a Comparator of MoleculeElement, and will define
     * a compare method that compares two atoms.
     * Each comparator must have a compare method.
     * Most of the comparators do not need an explicit constructor and have no fields.
     * However, the comparator for rotating the viewer by an angle may need a field and a constructor.
     */

    /** 
     * [CORE]
     * Comparator that defines the ordering to be from back to front.
     * 
     * Uses the z coordinates of the two atoms;
     * larger z means towards the back,
     * smaller z means towards the front
     * @return
     *  negative if element1 is more to the back than element2,
     *  0 if they are in the same plane,
     *  positive if element1 is more to the front than element2.
     */
    private class BackToFrontComparator implements Comparator<MoleculeElement> {
        //takes the Zpos of element 1 and subtracts element 2 Zpos to change the view to the front
        public int compare(MoleculeElement element1, MoleculeElement element2) {
          return (int) (element1.getZ()-element2.getZ());
        }
    }

    /** 
     * Comparator that defines the ordering to be from front to back.
     * 
     * Uses the z coordinates of the two atoms
     * larger z means towards the back,
     * smaller z means towards the front
     * @return
     *  negative if element1 is more to the front than element2, (
     *  0 if they are in the same plane,
     *  a positive number if element1 is more to the back than element2.
     */
    private class FrontToBackComparator implements Comparator<MoleculeElement> {
        //takes the Zpos of element 2 and subtracts element 1 Zpos to change the view to the back
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            return (int) (element2.getZ()-element1.getZ());
        }
    }

    /** 
     * Comparator that defines the ordering to be from left to right 
     * 
     *  uses the x coordinates of the two atom
     *  larger x means more towards the right,
     *  smaller x means more towards the left.
     *  @return
     *   negative if element1 is left of element2, 
     *   positive if atom 1 is right of element2.
     *   0 if they are vertically aligned, 
     */
    private class LeftToRightComparator implements Comparator<MoleculeElement> {
        //takes the Xpos of element 1 and subtracts element 2 Xpos to change the view to the left
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            return (int) (element1.getX()-element2.getX());
        }
    }

    /** 
     * Comparator that defines the ordering to be from right to left 
     * 
     * Uses the x coordinates of the two atoms
     * larger x means more towards the right,
     * smaller x means more towards the left.
     * @return
     *  negative if element1 is more to the right than element2,
     *  0 if they are aligned,
     *  positive if atom 1 is more to the left than element2.       
     */
    private class RightToLeftComparator implements Comparator<MoleculeElement> {
        //takes the Xpos of element 2 and subtracts element 1 Xpos to change the view to the right
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            return (int) (element2.getX()-element1.getX());
        }
    }

    /** Comparator that defines the ordering to be from further to nearer when viewed from
     *  a given perspective.
     *  Needs:
     *  - a constructor that stores the perspective angle,
     *  - a compare method.
     *  
     *  @return
     *  a negative number if element1 is further than element2 from this angle
     *  0 if they are at the same distance,
     *  a positive number if element1 is nearer than element2 from this angle
     */
    private class PerspectiveComparator implements Comparator<MoleculeElement> {
        /** 
         * You can give this constructor a field to hold the currentAngle,
         * but it can also access the field of the enclosing class directly.
         */
        //Makes use of the further than method t aide in the rotation method.
        public int compare(MoleculeElement element1, MoleculeElement element2) {
            return element1.furtherThan(element2, currentAngle);
        }
    }

    public static void main(String args[]) {
        new MoleculeRenderer();
    }
}
