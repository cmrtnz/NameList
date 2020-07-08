import java.util.NoSuchElementException;

public class NameList {

    private Node head;

    public static class Node{ //static since Nodes don't need to know their list, but list needs to know their nodes
        String name;
        Node next;
        Node(String a, Node nextNode){
            name = a;
            next = nextNode;

        }
    }

    public void add(String toAdd){
        Node nodeToAdd = new Node(toAdd, null);

        if(firstLetterPresent(Character.toString(toAdd.charAt(0))))
            addNode(nodeToAdd); //just need to add the node here
        else {
            addNode(new Node(Character.toString(toAdd.charAt(0)), null)); //need to add letter node first
            addNode(nodeToAdd);
        }
    }

    private boolean firstLetterPresent(String letter) {
        Node current = head;
        while (current != null) {
            if (current.name.compareTo(letter) == 0)
                return true;
            current = current.next;
        }
        return false;
    }


    private void addNode(Node nodeToAdd) {
        if(head == null) {  //empty list, node becomes head
            head = nodeToAdd;
            return;
        }

        if(nodeToAdd.name.compareTo(head.name) < 0) { //if node is before a non-null head
            nodeToAdd.next = head;
            head = nodeToAdd;
            return;
        }

        Node beforeToCompare = head;
        Node toCompare = head.next; //to traverse through the linked list

        while(toCompare != null) {
            if(nodeToAdd.name.compareTo(toCompare.name) < 0) { //if compareTo is negative then we know we need to add the node before that one
                nodeToAdd.next = toCompare;
                beforeToCompare.next = nodeToAdd;
                return;
            }
            beforeToCompare = beforeToCompare.next;
            toCompare = toCompare.next;
        }

        beforeToCompare.next = nodeToAdd; /*If run to this point, the new node must be "greater" than
                                        everything else in the list so it's inserted at the end*/
        return;
    }
    public void remove(String toRemove) {
        if(!find(toRemove)) //check if the element is present in the list so that we can remove it
            throw new NoSuchElementException();

        if(head.name.compareTo(toRemove) == 0) {
            head = head.next;
        }
        if(head.name.compareTo(toRemove) == 0){
            head.next = head.next.next;
            if(head.name.compareTo(Character.toString(toRemove.charAt(0)))==0)
                head = head.next;
        }
        /*manually checking for the first 2 positions of the linked list since the loop that follows starts
        checking at head.next.next in its design
         */

        Node beforeBeforeCurrent = head;
        Node beforeCurrent = head.next;
        Node current = head.next.next;
        while(current != null) {
            if(current.name.compareTo(toRemove)==0) { //searching for the string we're removing
                beforeCurrent.next = current.next;  //removing the node
                if(beforeCurrent.name.compareTo(Character.toString(toRemove.charAt(0)))==0) //If that node was the last word of that letter group then the node
                    beforeBeforeCurrent.next = beforeCurrent.next; //before that is the letter itself so we know to take it out
                break;
            }
            beforeBeforeCurrent = beforeCurrent;
            beforeCurrent = current;
            current = current.next;
        }


    }
    public void removeLetter(String letter) {
        if(!find(letter)) //making sure the letter is present in the list
            throw new NoSuchElementException();

        /*Through the while loop, I find the last node before the section of the to be removed letter
        and corresponding words are and the first node after. I set the last node before's next pointer
        to the first node after to effectively erase that entire section easily
         */
        Node current = head;
        Node beforeLetterNode = new Node(letter, null);
        while(current.next != null) {
            if(current.next.name.compareTo(letter)==0) {
                beforeLetterNode = current;
            }
            if(Character.toString(current.name.charAt(0)).compareTo(letter) == 0 && Character.toString(current.next.name.charAt(0)).compareTo(letter)!=0) {
                beforeLetterNode.next = current.next;
            }
            current = current.next;
        }

    }

    public boolean find(String toFind) {
        boolean found = false;
        Node current = head;
        while(current != null) {
            if(current.name.compareTo(toFind)==0) { //go through the list and exit as soon as you find the string
                found = true;
                break;
            }
            current = current.next;
        }

        return found;
    }

    public String toString() {
        if(head == null) {
            System.out.println("Empty list");
            return null;
        }
        Node current = head;
        while(current != null) {
            if(current.name.length() == 1) //check if the current node is a letter node
                System.out.println(current.name);
            else //if it's not a letter node then we need space before it to make it look nice like in the example
                System.out.println("  " + current.name);
            current = current.next;
        }
        return null;
    }

    public static void main(String[] args) {
        NameList test = new NameList();
        //testing add function
        test.add("Nancy");
        test.add("Brian");
        test.add("Michael");
        test.add("Mitchell");
        test.add("Sarah");
        test.add("Joey");
        test.add("Jacob");
        test.add("Kevin");
        test.toString();

        //testing remove function
        test.remove("Sarah");
        System.out.println("-------------------------\nNow after removing Sarah:");
        test.toString();

        //testing removeLetter function
        test.removeLetter("M");
        System.out.println("-------------------------\nAfter removing all 'M's (Michael & Mitchell):");
        test.toString();

        //testing find function
        System.out.println("-------------------------\nChecking for Michael(not in list) & Brian(in list):");
        if(test.find("Michael"))
            System.out.println("Michael was found in the list.");
        else
            System.out.println("Michael was NOT found in the list.");

        if(test.find("Brian"))
            System.out.println("Brian was found in the list.");
        else
            System.out.println("Brian was NOT found in the list.");

    }

}
