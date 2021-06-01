
//
// !!! Do NOT Change anything in this file, really don't
//

//
// this file defines a polygon
// a polygon has a list of points store in SimpleLinkedList that you implemented
// total (0%) in this files
//
public class Polygon
{
  // a 2d point
  public static class Point
  {
    public Point(double _x, double _y)
    {
      x=_x;
      y=_y;
    }
    public double x, y;
  }

  public Polygon()
  {
    R=255;G=0;B=255;
  }

  private SimpleLinkedList<Point> m_vertices;
  private float m_area;
  public String name;
  private int R,G,B; //color

  //methods for building polygons
  //must call in the order of
  //startPolygon->addVertex->...->addVertex->endPolygon()
  public void startPolygon(String _name)
  {
    //your code here
    name=_name;
    m_vertices=new SimpleLinkedList<Point>();
  }

  public boolean addVertex(double x, double y)
  {
    if(m_vertices==null) return false;
    m_vertices.add(new Point(x,y));
    return true;
  }

  public boolean endPolygon()
  {
    if(m_vertices==null) return false;
    if( !m_vertices.is_cicular() ) m_vertices.make_cicular();
    compute_area();
    if(m_area<0)
    {
      m_vertices.reverse();
      m_area=0;
      compute_area();
    }

    return true;
  }

  //added June 8th. 2018
  //return the first vertex of the polygon
  public SimpleLinkedList<Point> getVertices()
  {
    return m_vertices;
  }

  //compute the winding number of a given point at (x,y)
  public int winding_number(double x, double y)
  {
    double winding=0;
    Point q=new Point(x,y);
    ListItem<Point> head=m_vertices.getHead();
    ListItem<Point> ptr=head;
    do{
      ListItem<Point> next=ptr.next;

      Vector u=new Vector(ptr.data,q);
      Vector v=new Vector(next.data,q);
      winding+=u.angle(v);
      ptr=next;
    }
    while(ptr!=head);

    return (int)Math.round(winding/(Math.PI*2));
  }


  //get the area of the polygon
  public float area()
  {
    if(m_area==0) compute_area();
    return m_area;
  }

  //set the color of the polygon
  public void setColor(int _R, int _G, int _B)
  {
    R=_R; G=_G; B=_B;
  }

  //you are allowed to add extra private method
  private void compute_area()
  {
    //compute area of the polygon and save the result to m_area
    m_area=0;
    Point origin=new Point(0,0);
    ListItem<Point> head=m_vertices.getHead();
    ListItem<Point> ptr=head;
    do{
      ListItem<Point> next=ptr.next;
      m_area+=compute_area(origin, ptr.data, next.data);
      ptr=next;
    }
    while(ptr!=head);
  }

  //2D vector
  private class Vector
  {
    public Vector(double _x, double _y)
    {
      x=_x; y=_y;
    }

    //create a vector from a-b
    public Vector(Point a, Point b)
    {
        x=a.x-b.x;
        y=a.y-b.y;
    }

    public double normsqr(){ return x*x+y*y; }
    public double norm(){ return Math.sqrt(normsqr()); }
    public Vector normalize(){
      double n=norm();
      return new Vector(x/n,y/n);
    }

    //dot product and cross product
    public double dot(Vector v){ return x*v.x+y*v.y; }
    public double cross(Vector v){ return x*v.y-y*v.x; }

    //angle between this vector and the given vector
    public double angle(Vector v)
    {
      double d=this.normalize().dot(v.normalize());
      double r=Math.acos(d);
      if( cross(v)<0 ) r=-r;
      return r;
    }

    //private data
    private double x, y;
  }

  //compute signed area of a triangle (a, b, c)
  private double compute_area(Point a, Point b, Point c)
  {
    Vector v=new Vector(b,a);
    Vector u=new Vector(c,a);
    return v.cross(u)/2;
  }

  //output svg code for this polygon
  public String toString()
  {
    String str="<path d=\"";

    ListItem<Point> head=m_vertices.getHead();
    ListItem<Point> ptr=head;
    do{
      Point pt=ptr.data;
      str+=(((ptr==head) ? ("M") : ("L"))+pt.x+","+pt.y+" ");
      ptr=ptr.next;
    }
    while(ptr!=head);
    str+=("L"+ptr.data.x+","+ptr.data.y);

    //color
    str+="\" fill=\"rgb("+this.R+","+this.G+","+this.B+")\" ";
    str+="stroke-width=\"0.2\" stroke=\"rgb("+this.R/3+","+this.G/3+","+this.B/3+")\" ";

    str+="/>\n";
    return str;
  }

  public void updateBoundingBox(double [] bbox)
  {
    ListItem<Point> head=m_vertices.getHead();
    ListItem<Point> ptr=head;
    do{
      Point pt=ptr.data;
      if(pt.x<bbox[0]) bbox[0]=pt.x;
      if(pt.x>bbox[1]) bbox[1]=pt.x;
      if(pt.y<bbox[2]) bbox[2]=pt.y;
      if(pt.y>bbox[3]) bbox[3]=pt.y;
      ptr=ptr.next;
    }
    while(ptr!=head);
  }

  //FC0182435019643AF53744346B4FE8770B811921140611CF7E9BACA04396F2E2
}