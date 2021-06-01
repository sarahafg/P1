
//
// !!! Do NOT Change anything in this file
//
// total (0%) in this files
//

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class cs310pa1
{
    public static void main(String[] args)
    {
        if(args.length==0)
        {
            System.err.println("Usage: cs310pa1 polygon_file");
            return;
        }

        PaintPolygons pp=new PaintPolygons();

        //read and build polygons from files
        for(int i=0;i<args.length;i++)
        {
          pp.addPolygon(readPolygon(args[i]));
        }

        //sort polygons
        pp.sortPolygons();

        //output polygons to terminal
        pp.print();

        //output polygons to svg file
        pp.save2SVG("output.svg");
    }


    //read a polygon from file
    private static Polygon readPolygon(String filename)
    {
      Polygon poly=new Polygon();
      poly.startPolygon(filename);
      int R=0,G=0,B=0; //color
      try {
          Scanner scan = new Scanner(new File(filename));

          //read color
          if(scan.hasNextInt()) R=scan.nextInt(); else throw new IOException("R is not given");
          if(scan.hasNextInt()) G=scan.nextInt(); else throw new IOException("G is not given");
          if(scan.hasNextInt()) B=scan.nextInt(); else throw new IOException("B is not given");

          //read points
          while(scan.hasNextDouble())
          {
            double x, y;
            x=scan.nextDouble();
            if(scan.hasNextDouble()) y=scan.nextDouble();
            else throw new IOException("Y value is not given");
            poly.addVertex(x,y);
          }
      }
      catch (FileNotFoundException e) {
        e.printStackTrace();
      }
      catch (IOException e){
        e.printStackTrace();
      }

      poly.endPolygon();
      poly.setColor(R,G,B);
      return poly;
    }
}

//FC0182435019643AF53744346B4FE8770B811921140611CF7E9BACA04396F2E2
