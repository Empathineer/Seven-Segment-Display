package foothill;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.lang.Math;

public class Foothill
{

    public static void main(String[] args)
    {
        //Testing SevenSegmentImage
        SevenSegmentImage ssi = new SevenSegmentImage();
          
        System.out.println("Testing SevenSegmentImage ===================================");

        ssi.setSize( 7, 9 );
        if (ssi.turnOnCellsForSegment( 'a' ))
           ssi.display();
        else
            System.out.println("This segment a is illegal");
        if  (ssi.turnOnCellsForSegment( 'b' ))
               ssi.display();
        else
            System.out.println("This segment b is illegal");
        if (ssi.turnOnCellsForSegment( 'c' ))
            ssi.display();
        else 
            System.out.println("This segment c is illegal");
        if (ssi.turnOnCellsForSegment( 'd' ))
            ssi.display();
        else
            System.out.println("This segment d is illegal");
        ssi.clearImage();
        if  (ssi.turnOnCellsForSegment( 'e' ))
             ssi.display();
        else
            System.out.println("This segment e is illegal");
        if  (ssi.turnOnCellsForSegment( 'f' ))
             ssi.display();
        else
            System.out.println("This segment f is illegal");
        if  (ssi.turnOnCellsForSegment( 'g' ))
             ssi.display();
        else
            System.out.println("This segment g is illegal");

        ssi.clearImage();
        if  (ssi.turnOnCellsForSegment( 'x' ))
            ssi.display();
        else
            System.out.println("This segment x is illegal");
        if  (ssi.turnOnCellsForSegment( '3' ))
            ssi.display();
        else
            System.out.println("This segment 3 is illegal");
        
        //Testing SevenSegmentDisplay
        SevenSegmentDisplay  my7SegForCon = new SevenSegmentDisplay( 15, 13 );
        int j;
        SevenSegmentDisplay  my7SegCopy;
        
        System.out.println(
           "Testing SevenSegmentDisplay ===================================");

        try 
        {
            my7SegCopy = (SevenSegmentDisplay)my7SegForCon.clone();
        }
        catch ( CloneNotSupportedException e)
        {
            System.out.println("** Clone Unsuccessful  **");
            my7SegCopy = new SevenSegmentDisplay(15, 13);
        }
        
        my7SegCopy.setSize( 7, 9 );
        for ( j = 0; j < 16; j++ )
        {
           my7SegCopy.eval( j );
           my7SegCopy.loadConsoleImage();
           my7SegCopy.consoleDisplay();
        }

        System.out.println(
           "Testing Display Size Change ===================================");

        
        for ( j = 5; j < 21; j += 4)
        {
           my7SegCopy.setSize( j, 2*j + 1 );
           my7SegCopy.eval( 5 );
           my7SegCopy.loadConsoleImage();
           my7SegCopy.consoleDisplay();
        }
     }
    }



class BooleanFunc implements Cloneable
{
   public static final int MAX_TABLE_FOR_CLASS = 65536; // that's 16 binary
   // inputs
   public static final int DEFAULT_TABLE_SIZE = 16;

   private int tableSize;
   private boolean[] truthTable;
   private boolean evalReturnIfError;
   private boolean state;

   // constructors
   public BooleanFunc()
   {
      this(DEFAULT_TABLE_SIZE, false);
   }

   public BooleanFunc(int tableSize)
   {
      this(tableSize, false);
   }

   public BooleanFunc(int tableSize, boolean evalReturnIfError)
   {
      // deal with construction errors in a crude but simple fashion
      if (tableSize > MAX_TABLE_FOR_CLASS || tableSize < 1)
         tableSize = DEFAULT_TABLE_SIZE;
      this.tableSize = tableSize;
      truthTable = new boolean[tableSize];
      setTruthTableUsingTrue( new int[0] ); // anon arg; will set all to false
      this.evalReturnIfError = evalReturnIfError;
      this.state = evalReturnIfError;
   }

   // mutators, acessors
   public boolean setTruthTableUsingTrue(int[] inputsThatProduceTrue)
   {
      int k, kTable;

      if ( inputsThatProduceTrue.length > tableSize )
         return false;

      // they are giving us true values, so we init to false then overwrite
      setTableToConstant( false );

      for ( k = 0; k < inputsThatProduceTrue.length; k++ )
      {
         kTable = inputsThatProduceTrue[k];
         if ( kTable >= 0 && kTable < tableSize )
            truthTable[kTable] = true;
      }

      return true;
   }

   public boolean setTruthTableUsingFalse(int[] inputsThatProduceFalse)
   {
      int k, kTable;

      if ( inputsThatProduceFalse.length > tableSize )
         return false;

      // they are giving us false values, so we init to true then overwrite
      setTableToConstant( true );

      for ( k = 0; k < inputsThatProduceFalse.length; k++ )
      {
         kTable = inputsThatProduceFalse[k];
         if ( kTable >= 0 && kTable < tableSize )
            truthTable[kTable] = false;
      }

      return true;
   }

   public boolean eval(int input)
   {
      if ( !inputInRange( input ) )
         return (state = evalReturnIfError);
      return (state = truthTable[input]);
   }

   public boolean getState()
   {
      return state;
   }

   // deep copy required
   public Object clone() throws CloneNotSupportedException
   {
      int k;

      // array will temporarily point to original object
      BooleanFunc newBf = (BooleanFunc) super.clone();

      newBf.truthTable = new boolean[tableSize];
      for (k = 0; k < tableSize; k++)
         newBf.truthTable[k] = this.truthTable[k];
      return newBf;
   }

   // helpers
   private void setTableToConstant(boolean constVal)
   {
      int k;

      for ( k = 0; k < tableSize; k++ )
         truthTable[k] = constVal;
   }

   private boolean inputInRange(int input)
   {
      if ( input < 0 || input >= tableSize )
         return false;
      else
         return true;
   }
};

// class MultiSegmentLogic ----------------------------------------------------
class MultiSegmentLogic  implements Cloneable
{
   protected BooleanFunc[] segs;
   protected int numSegs;

   public MultiSegmentLogic()
   {
      this(0);
   }

   public MultiSegmentLogic( int numSegs )
   {
      if ( !setNumSegs( numSegs ) )
         this.numSegs = 0;
   }

   public boolean setNumSegs( int numSegs )
   {
      int k;

      if ( numSegs < 0 )
         return false;

      this.numSegs = numSegs;

      // allocate new array
      segs = new BooleanFunc[numSegs];
      for (k = 0; k < numSegs; k++)
         segs[k] = new BooleanFunc();

      return true;
   }

   public boolean setSegment( int segNum, BooleanFunc funcForThisSeg )
   {
      if ( !validSeg( segNum ) )
         return false;

      // cloning object so we can pass in anon/temporary BooleanFunc
      try
      {
         segs[segNum] = (BooleanFunc)funcForThisSeg.clone();
      }
      catch ( CloneNotSupportedException e )
      {
         return false;
      }
      return true;
   }

   public void eval( int input )
   {
      int k;
      for ( k = 0; k < numSegs; k++ )
         segs[k].eval( input );  
   }

   // deep copy required
   public Object clone() throws CloneNotSupportedException
   {
      int k;

      // array will temporarily point to original object
      MultiSegmentLogic newMsl = (MultiSegmentLogic) super.clone();

      newMsl.segs = new BooleanFunc[numSegs];
      for (k = 0; k < numSegs; k++)
         newMsl.segs[k] = (BooleanFunc)this.segs[k].clone();
      return newMsl;
   }


   // helpers
   protected boolean validSeg( int seg )
   {
      if ( seg < 0 || seg > numSegs - 1 )
         return false;
      return true;
   }
};

//class SevenSegmentLogic ----------------------------------------------------
class SevenSegmentLogic extends MultiSegmentLogic
{
   public SevenSegmentLogic()
   {
      super.setNumSegs( 7 );  // careful not to call sibling which does nothing
      loadAllFuncs();
   }
   
   // method to prevent the base-class from changing away from 7
   public boolean setNumSegs( int numSegs )
   {
      if (numSegs != 7)
         return false;
      return true;
   }
   
   public boolean getValOfSeg( int seg )
   {
      if ( !validSeg( seg ) )
         return false;
      return segs[seg].getState();
   }

   private void loadAllFuncs()
   {
      // we use letters, rather than arrays, to help connect with traditional
      // a, b, ... g segements

      // define all in terms of on/true
      int segA[] = { 0, 2, 3, 5, 6, 7, 8, 9, 10, 12, 14, 15 };
      int segB[] = { 0, 1, 2, 3, 4, 7, 8, 9, 10, 13 };
      int segC[] = { 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13 };
      int segD[] = { 0, 2, 3, 5, 6, 8, 9, 11, 12, 13, 14 };
      int segE[] = { 0, 2, 6, 8, 10, 11, 12, 13, 14, 15 };
      int segF[] = { 0, 4, 5, 6, 8, 9, 10, 11, 12, 14, 15 };
      int segG[] = { 2, 3, 4, 5, 6, 8, 9, 10, 11, 13, 14, 15 };

      // set error pattern to "E" through second parameter
      BooleanFunc a = new BooleanFunc( 16, true );
      a.setTruthTableUsingTrue( segA );
      BooleanFunc b = new BooleanFunc( 16, false );
      b.setTruthTableUsingTrue( segB );
      BooleanFunc c = new BooleanFunc( 16, false );
      c.setTruthTableUsingTrue( segC );
      BooleanFunc d = new BooleanFunc( 16, true );
      d.setTruthTableUsingTrue( segD );
      BooleanFunc e = new BooleanFunc( 16, true );
      e.setTruthTableUsingTrue( segE );
      BooleanFunc f = new BooleanFunc( 16, true );
      f.setTruthTableUsingTrue( segF );
      BooleanFunc g = new BooleanFunc( 16, true );
      g.setTruthTableUsingTrue( segG );

      // this block could be combined with above; done separately for clarity
      setSegment( 0, a );
      setSegment( 1, b );
      setSegment( 2, c );
      setSegment( 3, d );
      setSegment( 4, e );
      setSegment( 5, f );
      setSegment( 6, g );
   }
}

class SevenSegmentImage implements Cloneable
{

   public static final int MIN_HEIGHT = 5;
   public static final int MIN_WIDTH = 5;
   public static final int MAX_HEIGHT = 65;
   public static final int MAX_WIDTH = 41;
   public static final String DRAW_CHAR = "*";
   public static final String BLANK_CHAR = " ";
   public static final int DEFAULT_WIDTH = 10;
   public static final int DEFAULT_HEIGHT = 15;
   

   private boolean[][] data;
   private int topRow, midRow, bottomRow, leftCol, rightCol;

   //instance public methods
   public SevenSegmentImage()
   {
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
   }

   public SevenSegmentImage(int user_width, int user_height)
   {
        setSize(user_width,user_height);
   }

   public void clearImage()
   {
         for (int i=0; i< bottomRow; i++)
             for (int j=0; j< rightCol; j++)
                 data[i][j] = false;
   }

   public boolean turnOnCellsForSegment(char segment)
   {
       
       //convert char to int and validates w/in range
       int c = (int) Character.toLowerCase(segment);
       switch (c) {
           case 'a': drawHorizontal(topRow);
                     break;
           case 'b': drawVertical(rightCol,topRow+1,midRow+1);
                     break;
           case 'c': drawVertical(rightCol,midRow+2,bottomRow-1);
                     break;
           case 'd': drawHorizontal(bottomRow);
                     break;
           case 'e': drawVertical(leftCol,midRow+2,bottomRow-1);
                     break;
           case 'f': drawVertical(leftCol,topRow+1,midRow+1);
                     break;
           case 'g': drawHorizontal(midRow+1);
                     break;
           default: return false;
           }
       return true;
   }

   public boolean setSize(int width, int height)
   {
      if (!validateSize(width,height))
         return false;
      topRow = 1;
      midRow = height / 2;
      bottomRow = height+2;
      rightCol = width+2;
      leftCol = 1;
      allocateCleanArray();
      return true;
   }

   public void display()
   {
      for (int i=0; i < bottomRow; i++)
      {
          for (int j=0; j < rightCol; j++)
          {
              if (data[i][j] == true)
                 System.out.print(DRAW_CHAR);
              else
                 System.out.print(BLANK_CHAR);
           }
          System.out.println();
      }
          
   }

   // deep copy required
   public Object clone() throws CloneNotSupportedException
   {
      SevenSegmentImage new7SegImage = (SevenSegmentImage)super.clone();
      new7SegImage.data = new boolean[this.bottomRow][this.rightCol];
      for (int i=0; i< this.bottomRow; i++)
          for (int j=0; j< this.rightCol; j++)
          {
            new7SegImage.data[i][j] = this.data[i][j];
          }
      return new7SegImage;
   }

   private boolean validateSize(int width, int height)
   {
       if ((width < MIN_WIDTH) || (width > MAX_WIDTH) ||
          (height < MIN_HEIGHT) || (height > MAX_HEIGHT))
           return false;
       
       double hw_ratio = ((double)height) / ((double)width);
       if ( hw_ratio < 0.75 || hw_ratio > 3)
            return false;
       return true;
   }	

   private void allocateCleanArray()

   {  
    
       data = new boolean[bottomRow][rightCol];
       clearimage();
       
   }

   // helpers - not required, but used by instructor
   void drawHorizontal(int row)
   {
      for (int i=1; i< rightCol-1; i++)
          data[row-1][i] = true;
   }

   void drawVertical(int col, int startRow, int stopRow)
   {
      for (int i=startRow; i<=stopRow; i++)
          data[i-1][col-1]=true;
   }
}

class SevenSegmentDisplay  implements Cloneable
{
   private SevenSegmentImage theImage;
   private SevenSegmentLogic theDisplay;

   public SevenSegmentDisplay()
   {
       this(SevenSegmentImage.DEFAULT_WIDTH,
               SevenSegmentImage.DEFAULT_HEIGHT);
   }
   
   public SevenSegmentDisplay( int width, int height )
   {
       theDisplay = new SevenSegmentLogic();
       theImage = new SevenSegmentImage(width,height);
      // setSize(width, height);
   }
   
   public boolean setSize( int width, int height )
   {
       return theImage.setSize(width,height);
      
   }
   
   public void loadConsoleImage()
   {
       
      theImage.clearImage();
      for (int i=0; i< theDisplay.numSegs; i++)
      {
         char seg = (char)(i+97);
         if (theDisplay.getValOfSeg(i))
             theImage.turnOnCellsForSegment(seg);
      }
   }
   
   public void consoleDisplay()
   {
      theImage.display();
   }
   
   public void eval( int input )
   {
      for (int i=0; i<7; i++)
          theDisplay.eval(input);
   }

   public Object clone() throws CloneNotSupportedException
   {
         SevenSegmentDisplay  new7SegDisplay = (SevenSegmentDisplay) super.clone();
         new7SegDisplay.theImage = (SevenSegmentImage)theImage.clone();
         new7SegDisplay.theDisplay = (SevenSegmentLogic)theDisplay.clone();
      return new7SegDisplay;
   }
}


run:
Testing SevenSegmentImage ===================================
 ******* 
         
         
         
         
         
         
         
         
         
         
 ******* 
        *
        *
        *
        *
         
         
         
         
         
         
 ******* 
        *
        *
        *
        *
        *
        *
        *
        *
        *
         
 ******* 
        *
        *
        *
        *
        *
        *
        *
        *
        *
 ******* 
         
         
         
         
         
*        
*        
*        
*        
*        
         
         
*        
*        
*        
*        
*        
*        
*        
*        
*        
         
         
*        
*        
*        
******** 
*        
*        
*        
*        
*        
         
This segment x is illegal
This segment 3 is illegal
Testing SevenSegmentDisplay ===================================
 ******* 
*       *
*       *
*       *
*       *
*       *
*       *
*       *
*       *
*       *
 ******* 
         
        *
        *
        *
        *
        *
        *
        *
        *
        *
         
 ******* 
        *
        *
        *
 ********
*        
*        
*        
*        
*        
 ******* 
 ******* 
        *
        *
        *
 ********
        *
        *
        *
        *
        *
 ******* 
         
*       *
*       *
*       *
*********
        *
        *
        *
        *
        *
         
 ******* 
*        
*        
*        
******** 
        *
        *
        *
        *
        *
 ******* 
 ******* 
*        
*        
*        
******** 
*       *
*       *
*       *
*       *
*       *
 ******* 
 ******* 
        *
        *
        *
        *
        *
        *
        *
        *
        *
         
 ******* 
*       *
*       *
*       *
*********
*       *
*       *
*       *
*       *
*       *
 ******* 
 ******* 
*       *
*       *
*       *
*********
        *
        *
        *
        *
        *
 ******* 
 ******* 
*       *
*       *
*       *
*********
*       *
*       *
*       *
*       *
*       *
         
         
*        
*        
*        
******** 
*       *
*       *
*       *
*       *
*       *
 ******* 
 ******* 
*        
*        
*        
*        
*        
*        
*        
*        
*        
 ******* 
         
        *
        *
        *
 ********
*       *
*       *
*       *
*       *
*       *
 ******* 
 ******* 
*        
*        
*        
******** 
*        
*        
*        
*        
*        
 ******* 
 ******* 
*        
*        
*        
******** 
*        
*        
*        
*        
*        
         
Testing Display Size Change ===================================
 ***** 
*      
*      
*      
*      
****** 
      *
      *
      *
      *
      *
      *
 ***** 
 ********* 
*          
*          
*          
*          
*          
*          
*          
*          
********** 
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
 ********* 
 ************* 
*              
*              
*              
*              
*              
*              
*              
*              
*              
*              
*              
*              
************** 
              *
              *
              *
              *
              *
              *
              *
              *
              *
              *
              *
              *
              *
              *
 ************* 
 ***************** 
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
*                  
****************** 
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
                  *
 ***************** 
BUILD SUCCESSFUL (total time: 0 seconds)

