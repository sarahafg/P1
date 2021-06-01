//This is the only packages you can import
import java.util.Comparator;
//These are the only packages you can import

/**
   * SimpleLinkedList class.
   * Implementation of a generic double linked list.
   * @author Sarah Fakhry
   */
public class SimpleLinkedList<T>
{
  /**
   * Adds a new record to the end of the list.
   * @param data  new record
   */
  public void add(T data)
  {
    ListItem<T> temp = new ListItem<T>(data);
    // Checks if list is null.
    if(m_tail == null) {
      // Adds record to empty list.
      m_tail = temp;  
      m_head = temp;    
    }  
    else {  
      // Adds record to end of list.
      m_tail.next = temp;   
      temp.pre = m_tail;   
      m_tail = temp;   
    }
    m_tail.next = null;  
  }

  /**
   * Swapping two nodes in linked list.
   * @param n1  first node
   * @param n2  second node
   */
  private void swap(ListItem<T> n1, ListItem<T> n2)
  {
    while (true) { 
      // Checks if 
      if (n1.next == n2 || n2.pre == n1){
        ListItem<T> temp = n1;
        // Swapping nodes.
        n1 = n2;
        n2 = temp;
        // Swapping data in nodes.
        T help = n1.data; 
        n1.data = n2.data; 
        n2.data = help; 
        break;
      }
      else {
        ListItem<T> temp = n1;
        // Swapping nodes.
        n1 = n2;
        n2 = temp;
        // Swapping data in nodes.
        T help = n1.data; 
        n1.data = n2.data; 
        n2.data = help; 
        break;
      }
    }
  }

  /**
   * Reverses the order of the nodes.
   */
  public void reverse()
  {
    ListItem<T> prev = null;
    ListItem<T> current = m_head;
    //Switches the previous and next of each node.
    while (current != null) {
      ListItem<T> next = current.next;
      current.next = prev;
      prev = current;
      current = next;
    }
    //Switches the head and tail.
    if (prev != null) {      
      current = m_head;
      m_head = this.m_tail;
      m_tail = current;
    }
  }

  /**
   * Partitions and returns the newly created parition of the linked 
   * list after sorting them in the order issued by the Comparator passed 
   * as parameter.
   * @param head  head node
   * @param tail  tail node 
   * @param comp  instance to compare the node data
   */
  private ListItem<T> partition(ListItem<T> head, ListItem<T> tail, Comparator<T> comp) {
    // Checks if empty or null node.
    if (head == tail || head == null || tail == null) {
      return head;
    }
    // Set up for the partition using the main head node of the list.
    ListItem<T> previousPivot = head;
    ListItem<T> current = head;
    T pivot = tail.data;
    // Parition and swap data, order stays as is.
    while (head != tail) {
      // Sort in descending order using the comparator issued.
      if (comp.compare(head.data, pivot) > 0) {
        previousPivot = current;
        T temp = current.data;
        current.data = head.data;
        head.data = temp;
        current = current.next;
      }
      head = head.next;
    }
    // Moving the current data to the end of the partition.
    T temp = current.data;
    current.data = pivot;
    tail.data = temp;
    // Returns the reference to the node where our pivot lies.
    return previousPivot;
  }

  /**
   * Partitions and sorts the list.
   * @param head  head node 
   * @param tail  tail node 
   * @param comp  instance to sort with, passed to partiion.
   */
  private void sort(ListItem<T> head, ListItem<T> tail, Comparator<T> comp) {
    // For single node in the list.
    if (head == tail) {
      return;
    }
    // Create parition using the 1st and the last, decending order.
    ListItem<T> previousPivot = partition(head, tail, comp);
    // Sort the list once the partition is created.
    sort(head, previousPivot, comp);
    if (previousPivot != null && previousPivot == head) {
      sort(previousPivot.next, tail, comp);
    } 
    else if (previousPivot != null && previousPivot.next != null) {
      sort(previousPivot.next.next, tail, comp);
    }
  }

  /**
   * Invokes quick sort using the helper function partition and sort, 
   * sets the new head reference with the newly sorted list.
   * @param comp  instance of Comparator of type T
   */
  public void quick_sort(Comparator<T> comp) {
    // Initialize variables necessary for the sort.
    ListItem<T> current = m_head;
    ListItem<T> tail = m_head;
    // Accurately finding the tail.
    while (tail.next != null) {
      tail = tail.next;
    }
    // Invoke sort method using the head, tail, and comparator instance.
    sort(current, tail, comp);
    // Set the current head to the newly sorted list.
    m_head = current;
  }

  //test function
  //use "java SimpleLinkedList" to call this function
  public static void main(String [] args)
  {
    //TODO: add your test code here
    SimpleLinkedList<String> sLL=new SimpleLinkedList<String>();
    SimpleLinkedList<Integer> iLL=new SimpleLinkedList<Integer>();
    SimpleLinkedList<Integer> aLL=new SimpleLinkedList<Integer>();


    sLL.add("abc");
    sLL.add("ef");
    sLL.add("xyz");
    sLL.add("gmu");

    System.out.println(sLL.toString());
    sLL.swap(sLL.getHead(), sLL.getTail());
    System.out.println(sLL.toString());
    sLL.reverse();
    System.out.println(sLL.toString());
    sLL.swap(sLL.getHead(), sLL.getHead().next);
    System.out.println(sLL.toString());
    sLL.reverse();
    System.out.println(sLL.toString());
    sLL.swap(sLL.getHead(), sLL.getTail().pre);
    System.out.println(sLL.toString());
    sLL.reverse();
    System.out.println(sLL.toString());
    sLL.swap(sLL.getHead().next, sLL.getTail());
    System.out.println(sLL.toString());
    sLL.reverse();
    System.out.println(sLL.toString());
    sLL.swap(sLL.getHead().next, sLL.getTail().pre);
    System.out.println(sLL.toString());
    sLL.reverse();
    System.out.println(sLL.toString());

    // sLL.make_cicular();
    // System.out.println(sLL.is_cicular());
    
    // System.out.println(sLL.toString());
    // sLL.reverse();
    // System.out.println(sLL.toString());

    sLL.reverse();
    System.out.println(sLL.toString());

    iLL.add(-1);
    iLL.add(9);
    iLL.add(21);
    iLL.add(3);
    System.out.println(iLL.toString());
    iLL.quick_sort(new Comparator<Integer>(){
      public int compare(Integer a, Integer b){return a-b;}
    });
    System.out.println("In decending order: "+iLL.toString());

    iLL.add(-16);
    iLL.add(25);
    iLL.add(6);
    iLL.add(-5);
    System.out.println(iLL.toString());
    iLL.quick_sort(new Comparator<Integer>(){
      public int compare(Integer a, Integer b){return a-b;}
    });
    System.out.println("In decending order: "+iLL.toString());

    aLL.add(30);
    aLL.add(-3);
    aLL.add(0);
    aLL.add(12);
    System.out.println(aLL.toString());
    aLL.quick_sort(new Comparator<Integer>(){
      public int compare(Integer a, Integer b){return a-b;}
    });
    System.out.println("In decending order: "+aLL.toString());

    // sLL.quick_sort(new Comparator<String>(){
    // public int compare(String a, String b){return a.compareTo(b);}
    // });
    // System.out.println("In decending order: "+sLL.toString());

    // iLL.add(-1);
    // iLL.add(9);
    // iLL.add(21);
    // iLL.add(3);
    // System.out.println(iLL.toString());
    // iLL.quick_sort(new Comparator<Integer>(){
    //   public int compare(Integer a, Integer b){return a-b;}
    // });
    // System.out.println("In ascending order: "+iLL.toString());
  }

  //---------------------------------------------------------------------------
  //
  // Read the following code but Do Not change.
  //
  //---------------------------------------------------------------------------

  private ListItem<T> m_head, m_tail;
  private int size;

  public SimpleLinkedList()
  {
    size=0;
  }

  public int size()
  {
    return size;
  }

  public void make_cicular()
  {
    if(m_head!=null && m_tail!=null)
    {
      m_head.pre=m_tail;
      m_tail.next=m_head;
    }
  }

  public boolean is_cicular()
  {
    if(m_head!=null && m_tail!=null)
    {
      if(m_head.pre==m_tail && m_tail.next==m_head)
        return true;
    }
    return false;
  }

  public String toString()
  {
    ListItem<T> ptr=m_head;
    String result="";
    while(ptr!=m_tail)
    {
      result+=("["+ptr.data.toString()+"]->");
      ptr=ptr.next;
    }
    result+=("["+m_tail.data.toString()+"]");
    return result;
  }

  public ListItem<T> getHead() { return m_head; }
  public ListItem<T> getTail() { return m_tail; }
}

//FC0182435019643AF53744346B4FE8770B811921140611CF7E9BACA04396F2E2
