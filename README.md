
# Seven Segment Displays on Consoles
<p>Only one option is available this week.&nbsp;&nbsp; Make sure you have read and understood</p>
<ul>
<li>both <span style="color: #000080;"><strong><i>m</i></strong></span><i><strong><span style="color: #000080;">odules A</span></strong></i> and <i><strong> <span style="color: #000080;">B</span></strong></i> this week, and</li>
<li><em><strong><span style="color: #000080;">module 2R - Lab Homework Requirements</span></strong></em></li>
</ul>
<p>before submitting this assignment.</p>
<blockquote>
<div style="border-style: solid; border-width: 1px; padding: 1px 4px 1px 4px;"><span style="color: #008080; font-size: x-large;">Even though you learned to do multi-file projects, please continue to submit <i><strong>only one file</strong></i> with a full, working program and run.&nbsp;</span></div>
</blockquote>
<h4 style="color: #ff0000;">OPTION A (Basic):&nbsp; Seven Segment Display for Consoles</h4>
<h3>Understand the Problem</h3>
<p>In the previous assignment we established a good foundation for seven segment displays, and now we complete the problem by providing actual console imagery of the these digits <strong>0 - 9</strong> (and beyond as well: the patterns <strong>A ,b, C, d, E, F</strong> for inputs 10 - 15).&nbsp; We will need two classes:</p>
<ol>
<li>
<strong>SevenSegmentImage - </strong>This class is going to manage an internal 2-D <strong>boolean</strong> array capable of displaying any combination of on or off segments.&nbsp; Since there are seven segments, it should be able to produce the usual 10 digits, for example the digit <strong>4</strong>:
<pre>*   *
*   *
*   *
*****
    *
    *
    *
</pre>
as well as <i>any</i> of the 2<sup>7 </sup>= 128 patters, most of which might seem meaningless to us, like:
<pre>*******
      *
      *
      *
      *




</pre>
or
<pre>*
*
*
*
*******
*
*
*
*
</pre>
In addition, we will enable the client to specify any reasonable size:
<pre>*             *
*             *
*             *
*             *
*             *
*             *
***************
              *
              *
              *
              *
              *
              *
*   *
*   *
*****
    *
    *             
	</pre>
</li>
<li>
<strong>SevenSegmentDisplay</strong> - This class encapsulates the classes <strong> SevenSegmentLogic</strong> and <strong>SevenSegmentImage</strong> as its two private data members, into a single class which can be used to first apply any of 16 inputs to the object (<strong>0 - 15</strong>) and then display the single digit associated with that input (one of <strong>0-9</strong>, <strong>A-F</strong>) on the console in any reasonable size.&nbsp; The "4" above was the output of a <strong>SevenSegmentDisplay</strong> object which took an input <strong>4</strong>, but then displayed it in two different sizes, based on a change in the <strong> setSize()</strong> method of&nbsp; this class <strong>SevenSegmentDisplay</strong>.<br> <br> We will be satisfied to only build-in capability for&nbsp; displaying each object of this class on its own console line(s) with no left-or-right adjacent digits and allow it to always be left justified. This is the easiest problem to solve, and we have enough to handle without trying to break out of this limitation.</li>
</ol>
<h3><u>Phase 1: Class SevenSegmentImage</u></h3>
<h4>Summary</h4>
<p>The class uses a 2-D array to store its image for output.&nbsp; It does not store the input to the seven segment logic that will produce the image - this is purely a display-oriented class&nbsp; When we construct an object, <strong>SevenSegmentImage</strong> allocates a 2-D&nbsp; array of blanks (i.e., an array of filled with<strong> boolean</strong>s<strong> = false</strong>) based on the size (or defaults) passed into the constructor.&nbsp; Later we can re-size the array using a <strong>setSize()</strong> method.&nbsp; Of course, any time it resizes itself, a new <strong><i>blank</i></strong> 2-D array will be generated.&nbsp; If the old array size had some data in it (e.g., the pattern for the digit <strong>4</strong>), that data will be lost after invoking <strong>setSize()</strong>.&nbsp; We would have to re-build the pattern for that <strong>4</strong> by calling this class's one and only pattern building tool:&nbsp; <strong>turnOnCellsForSegment()</strong>.</p>
<p>I'm going to give you the outline for the class, now, so there is no doubt about what it has to be.&nbsp; The helpers are labeled optional and are not required&nbsp; Everything else is required.</p>
<div style="border-style: solid; border-width: 1px; padding: 1px 4px 1px 4px;">
<pre>class SevenSegmentImage implements Cloneable
{

   public static final int MIN_HEIGHT = 5;
   public static final int MIN_WIDTH = 5;
   public static final int MAX_HEIGHT = 65;
   public static final int MAX_WIDTH = 41;
   public static final String DRAW_CHAR = "*";
   public static final String BLANK_CHAR = " ";

   private boolean[][] data;
   private int topRow, midRow, bottomRow, leftCol, rightCol;

   public SevenSegmentImage()
   {
      ...
   }

   public SevenSegmentImage(int width, int height)
   {
      ...
   }

   public void clearImage()
   {
      ...
   }

   public boolean turnOnCellsForSegment(char segment)
   {
       ...
   }

   public boolean setSize(int width, int height)
   {
      ...
   }

   public void display()
   {
      ...
   }

   // deep copy required
   public Object clone() throws CloneNotSupportedException
   {
      ...
   }

   private boolean validateSize(int width, int height)
   {
      ...
   }

   private void allocateCleanArray()
   {
      ...
   }

   // helpers - not required, but used by instructor
   void drawHorizontal(int row)
   {
      ...
   }

   void drawVertical(int col, int startRow, int stopRow)
   {
      ...
   }
}
</pre>
</div>
<h4>Static (Public) Members</h4>
<p>These should be self-explanatory.</p>
<h4>Instance (Private) Members</h4>
<ul>
<li>
<strong>&nbsp;int topRow, midRow, bottomRow, leftCol, rightCol </strong>- A little thought will reveal that we need to know what rows and columns are our "money" groups, since these, and only these, will be potentially populated with *s.&nbsp; For example, in the letter <strong>A</strong> or the number <strong>5</strong>, we will want to "light up" the <strong>topRow</strong> and the <strong>midRow</strong>, but of those two, only the 5 would&nbsp; need to have a lit-up <strong>bottomRow</strong>.&nbsp; The client doesn't need to know about these members, but our internal methods will need them.</li>
<li>
<strong>boolean [][]data </strong>- This is our 2-D array whose size is set by <strong>setSize()</strong>.&nbsp; We won't bother storing <strong>data</strong>'s <strong><i>width</i></strong> or <i><strong>height</strong></i> since that is already encoded in two of our five&nbsp; <strong>int</strong>s in the last bullet.</li>
</ul>
<h4>Instance (Public) Methods</h4>
<ul>
<li>
<strong>Constructors </strong>-&nbsp; Two constructors (default and two-parameter taking <strong>width</strong> and <strong>height</strong>) will set the size by calling the appropriate method(s).</li>
<li>
<strong>void&nbsp; clearImage()</strong> - This will leave the array allocated and the size as-is, but it will clear out all the <strong>boolean</strong>s to <strong>false</strong> (wipe our display clean).</li>
<li>
<strong>boolean setSize( int width, int height ) </strong>- This will validate the parameters, and if both are good, will reallocate the <strong>data</strong> array and compute the five <strong>int</strong> members.&nbsp; Besides range, what is an important requirement of a seven segment image, given that we don't want it to be lopsided (asymmetric)?&nbsp; Figure it out and enforce it.</li>
<li>
<strong>boolean turnOnCellsForSegment( char segment )</strong> - This one takes a char&nbsp; (<strong>'a' - 'g'</strong>, please), allows both upper and lower case, validates and puts all <strong>true</strong>s into the corresponding 2-D array locations for the requested segment.&nbsp; This method, and its recommended helpers are where the fun is this week.</li>
<li>
<strong>Deep memory </strong>- Supply a <strong>clone()</strong>, of course.&nbsp; It should be evident that it is needed.</li>
<li>
<strong>The rest</strong> -&nbsp; The meaning and implementation of the rest should be clear from their names and the summary.</li>
</ul>

<h4>Sample Client</h4>

<div style="border-style: solid; border-width: 1px; padding: 1px 4px 1px 4px;">
<pre>      SevenSegmentImage ssi = new SevenSegmentImage();

      System.out.println(
         "Testing SevenSegmentImage ===================================");

      ssi.setSize( 7, 9 );
      ssi.turnOnCellsForSegment( 'a' );
      ssi.display();
      ssi.turnOnCellsForSegment( 'b' );
      ssi.display();
      ssi.turnOnCellsForSegment( 'c' );
      ssi.display();
      ssi.turnOnCellsForSegment( 'd' );
      ssi.display();

      ssi.clearImage();
      ssi.turnOnCellsForSegment( 'e' );
      ssi.display();
      ssi.turnOnCellsForSegment( 'f' );
      ssi.display();
      ssi.turnOnCellsForSegment( 'g' );
      ssi.display();

      ssi.clearImage();
      ssi.turnOnCellsForSegment( 'x' );
      ssi.display();
      ssi.turnOnCellsForSegment( '3' );
      ssi.display();
</pre>
</div>
<h3><u>Phase 2: SevenSegmentDisplay</u></h3>
<h4>Summary</h4>
<p>This is our pièce de résistance.&nbsp; It wraps a <strong>SevenSegmentLogic</strong> object and a <strong>SevenSegmentImage</strong> object together into a single, functioning console solution.&nbsp; Here's the prototype:</p>
<div style="border-style: solid; border-width: 1px; padding: 1px 4px 1px 4px;">
<pre>class SevenSegmentDisplay  implements Cloneable
{
   private SevenSegmentImage theImage;
   private SevenSegmentLogic theDisplay;

   public SevenSegmentDisplay()
   {
      ...
   }
   
   public SevenSegmentDisplay( int width, int height )
   {
      ...
   }
   
   public boolean setSize( int width, int height )
   {
      ...
   }
   
   public void loadConsoleImage()
   {
      ...
   }
   
   public void consoleDisplay()
   {
      ...
   }
   
   public void eval( int input )
   {
      ...
   }

   public Object clone() throws CloneNotSupportedException
   {
      ...
   }
}
</pre>
</div>
<p>Notice that the constructor only establishes the size and does not assume anything about the value to be represented, internally.</p>
<h4>Helper Functions</h4>
<ul>
<li>
<strong>void eval( int input )</strong> - This is the input to the display in:&nbsp; an int from <strong>0 - 15</strong>.&nbsp;<strong> 0-9</strong> are the numerals, and <strong>10-15</strong> are the letters <strong>A-F</strong>.&nbsp; </li>
<li>
<strong>void loadConsoleImage()</strong> - &nbsp; This clears the data in <strong>theImage</strong> (to all <strong>false</strong>).&nbsp; Then, it looks at the <strong>SevenSegmentLogic</strong> member, one segment-at-a-time, and if it sees a <strong>true</strong> lights up the corresponding segment in the <strong>SevenSegementImage</strong> member.&nbsp; There is almost (but not quite) nothing else that this method has to do. It's short and makes good use of all the hard word already done in the previous classes.</li>
</ul>
<h4>Sample Client</h4>

<div style="border-style: solid; border-width: 1px; padding: 1px 4px 1px 4px;">
<pre>      SevenSegmentDisplay  my7SegForCon = new SevenSegmentDisplay( 15, 13 );
      int j;
      
      System.out.println(
         "Testing SevenSegmentDisplay ===================================");

      my7SegForCon.setSize( 7, 9 );
      for ( j = 0; j &lt; 16; j++ )
      {
         my7SegForCon.eval( j );
         my7SegForCon.loadConsoleImage();
         my7SegForCon.consoleDisplay();
      }

      for ( j = 5; j &lt; 21; j += 4)
      {
         my7SegForCon.setSize( j, 2*j + 1 );
         my7SegForCon.eval( 5 );
         my7SegForCon.loadConsoleImage();
         my7SegForCon.consoleDisplay();
      }
   }
</pre>
</div>
