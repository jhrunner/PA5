package PA5;
import java.util.ArrayList;

public class LinkedOccurrence {
    private Occurrence head;

    public LinkedOccurrence() {
        head = null;
    }

    //Inserts a new Link at the first of the list
    public void insert(String name) {
        Occurrence newOcc = new Occurrence(name);
        newOcc.next = head;
        head = newOcc;
    }

    public void printList() {
        Occurrence temp = head;
        while (head != null) {
            head.printOcc();
            head = head.next;
        }
        head = temp;
    }

    public int getSize() {
        int counter = 0;
        Occurrence temp = head;
        while (head != null) {
            counter++;
            head = head.next;
        }
        head = temp;
        return counter;
    }

    public ArrayList<Occurrence> toArray() {
        ArrayList<Occurrence> array = new ArrayList<Occurrence>();
        Occurrence temp = head;
        while (head != null) {
            array.add(head);
            head = head.next;
        }
        head = temp;
        return array;
    }

    public String toString() {
        String arrayString = "";
        Occurrence temp = head;
        while (head != null) {
            arrayString = head.getDocName() + " " + head.getTermFrequency() + "; " + arrayString;
            head = head.next;
        }
        head = temp;
        return arrayString;
    }
}