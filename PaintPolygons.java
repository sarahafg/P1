import java.util.Comparator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
   * PaintPolygons class.
   * Defines a polygon painter class.
   * Maintains a list of sorted polygons.
   * @author Sarah Fakhry
   */
public class PaintPolygons
{
  private class PolygonComparator implements Comparator<Polygon>
  {
    /**
   * Compare two polygons using winding number and areas.
   * @param p1 type Polygon
   * @param p2 type Polygon
   */   
    public int compare(Polygon p1, Polygon p2)
    {
      // Pointer to current node in both polygons.
      ListItem<Polygon.Point> current = p1.getVertices().getHead();
      ListItem<Polygon.Point> current1 = p2.getVertices().getHead();
      // Loops through every vertice in both polygons.
      while (current != null){
        while (current1 != null){
          // Checks if p2 is inside of p1.
          if (p1.winding_number(current1.data.x, current1.data.y) > 0){  
            return 1;
          }
          // Checks if p1 is inside of p2.
          else if (p2.winding_number(current.data.x, current.data.y) < 0){
            return -1;
          }
          // Checks if any vertice is 0 in p1 or p2.
          else if (p2.winding_number(current.data.x, current.data.y) == 0 || 
          p1.winding_number(current1.data.x, current1.data.y) == 0){
            // Compares areas.
            if(p1.area() > p2.area()) {
              return 1;
            }
            return 0;
          }
          // Updates next vertice for p1 and p2
          current = current.next;
          current1 = current1.next;
        }
      }
      return 0;
    }
  }

  //---------------------------------------------------------------------------
  //
  // Read the following code but Do Not change.
  //
  //---------------------------------------------------------------------------
  private SimpleLinkedList<Polygon> m_polys;

  public PaintPolygons()
  {
    m_polys=new SimpleLinkedList<Polygon>();
  }

  public void addPolygon(Polygon p)
  {
      m_polys.add(p);
  }

  //sort the polygons from outside to inside
  //if two polygons are in the same level, sort by areas in ascending order
  //(i.e., small to large)
  public void sortPolygons()
  {
      m_polys.quick_sort(new PolygonComparator());
  }

  //
  // print the polygons to screen
  //
  public void print()
  {
    ListItem<Polygon> ptr=m_polys.getHead();
    while(ptr!=null)
    {
      System.out.println("polygon "+ptr.data.name+" area= "+ptr.data.area());
      ptr=ptr.next;
    }
  }

  //save the polygons to output.svg in sorted order
  public void save2SVG(String filename)
  {
    try
    {
      FileWriter fileWriter = new FileWriter(new File(filename));

      //write svg header
      {
        double [] bbox={Double.MAX_VALUE,-Double.MAX_VALUE,Double.MAX_VALUE,-Double.MAX_VALUE};
        ListItem<Polygon> ptr=m_polys.getHead();
        while(ptr!=null)
        {
          ptr.data.updateBoundingBox(bbox);
          ptr=ptr.next;
        }
        //padding
        double width=(bbox[1]-bbox[0]);
        double height=(bbox[3]-bbox[2]);
        double pad=Math.min(width,height)/50;
        bbox[0]-=pad; bbox[1]+=pad; bbox[2]-=pad; bbox[3]+=pad;
        width=(bbox[1]-bbox[0]);
        height=(bbox[3]-bbox[2]);
        //
        String header="<?xml version=\"1.0\" standalone=\"no\" ?>\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg ";
        header+=(" width=\""+ width +"px\"");
        header+=(" height=\""+ height +"px\"");
        header+=(" viewBox=\""+bbox[0]+" "+bbox[2]+" "+width+" "+height+" \"");
        header+=(" xmlns=\"http://www.w3.org/2000/svg\"");
        header+=(" version=\"1.1\""+">\n");
        fileWriter.write(header);
      }

      //write each polygon
      ListItem<Polygon> ptr=m_polys.getHead();
      while(ptr!=null)
      {
        fileWriter.write(ptr.data.toString()+"\n");
        ptr=ptr.next;
      }

      //write svg footer
      {
        String footer="</svg>\n";
        fileWriter.write(footer);
      }

      //done
      fileWriter.flush();
      fileWriter.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println("- Saved results to "+filename);
  }
  //FC0182435019643AF53744346B4FE8770B811921140611CF7E9BACA04396F2E2
}
