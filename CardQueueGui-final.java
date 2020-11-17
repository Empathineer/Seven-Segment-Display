package foothill;
import java.lang.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import javax.swing.border.TitledBorder;

   
public class Foothill
{
static final int NUM_CARDS_PER_HAND = 10;
    static final int NUM_PLAYERS = 2;
    static JLabel[] playedCardLabels = new JLabel[NUM_CARDS_PER_HAND];
    public static void main(String[] args)
            
    {
      Icon tempIcon;
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CS 1B CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(1500, 300);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      myCardTable.setVisible(true);
      
        //FIRST PART OF MAIN
        Card card1, card2, card3, card4, card5,
                card6, card7, card8;
        card1 = new Card('A', Card.Suit.spades);
        card2 = new Card('K', Card.Suit.spades);
        card3 = new Card('Q', Card.Suit.spades);
        card4 = new Card('J', Card.Suit.spades);
        card5 = new Card('T', Card.Suit.spades);
        card6 = new Card('9', Card.Suit.spades);
        card7 = new Card('8', Card.Suit.spades);
        card8 = new Card('7', Card.Suit.spades);
        
             
        CardNode cardnode1 = new CardNode(card1), 
                 cardnode2 = new CardNode(card2), 
                 cardnode3 = new CardNode(card3),
                 cardnode4 = new CardNode(card4), 
                 cardnode5 = new CardNode(card5),
                 cardnode6 = new CardNode(card6), 
                 cardnode7 = new CardNode(card7),
                 cardnode8 = new CardNode(card8);
        
        cardnode1.next = cardnode2;
        cardnode2.next = cardnode3;
        
        System.out.println("popping out cardnodes\n");
        
        CardNode p_cardnode;
        // show the queue
        for ( p_cardnode = cardnode1; p_cardnode != null;
                p_cardnode = (CardNode)p_cardnode.next )
          {
          p_cardnode.show();
          }
        System.out.println("Done");
        
        //Using a luStack ==========
        CardQueue myqueue = new CardQueue();
        
        Card f;

        System.out.println("Pushing cards to the Queue\n");
        
        myqueue.addCard(card1);
        myqueue.addCard(card2);
        myqueue.addCard(card3);
        myqueue.addCard(card4);
        myqueue.addCard(card5);
        myqueue.addCard(card6);
        myqueue.addCard(card7);
        myqueue.addCard(card8);
                
        System.out.println("Popping cards out of the Queue\n");
        
        for (int k = 0; k < 8; k++)
        { System.out.println(k);
           if ( (f = myqueue.removeCard()) != null)
              System.out.println( f + " ");
           else
              System.out.println("(empty queue) ");
        }
        System.out.println();
        
        
        myqueue = new CardQueue();
        Card random_card;
        int i;
        for (i=0; i< NUM_CARDS_PER_HAND; i++)
          {
           random_card = Card.generateRandomCard();
           myqueue.addCard(random_card);
           playedCardLabels[i] = new JLabel(GUICard.getIcon(random_card));
           myCardTable.pnlplaying_card.add(playedCardLabels[i]);
           try {
           Thread.sleep(800);
}          catch(InterruptedException ex) {
           Thread.currentThread().interrupt();}
           myCardTable.setVisible(true);
          }
         myCardTable.setVisible(true);

         for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
        {  
           if ( (f = myqueue.removeCard()) != null)
           {
              System.out.println( f + " ");
              myCardTable.pnlplaying_card.remove(playedCardLabels[k]);
              try {
              Thread.sleep(800);}
              catch(InterruptedException ex) {
              Thread.currentThread().interrupt();}
              myCardTable.setVisible(true);
           }
           else
              System.out.println("(empty queue) ");
        }
        System.out.println();
      
        
        
    }
}


class CardNode extends Node
   {
      // additional data for subclass
      private Card data; 
      

      public CardNode(Card x)
      {
         super();  // constructor call to base class
         data = x;
      }
      
      // accessor
      public Card getData()
      {
         return data;
      }

      // overriding show()
      public void show()
      {
       System.out.println(data);
      }
   }
 
    
class Node
{
   // data (protected allows only certain other classes to 
   // access "next" directly)
  
   protected Node next;
    
   // constructor

   public Node()
   {
      next = null;
   }
   
   // console display
   public void show()
   {
      System.out.print( "(generic node) ");
   }
}

class Queue
{
   
   private int total=0;
   // pointer to first node in stack
   private Node head, tail;
   
   // constructor
   public Queue()
   {
      head = tail = null;
   }
   
   public Node remove()
   {
      if (total == 0) throw new java.util.NoSuchElementException();
      Node tNode = head;
      head = head.next;
      if (--total == 0) tail=null;
      return tNode;
   }
   
   public void add(Node newNode)
   {
       Node current = tail;
       tail = newNode;
       if (total++ == 0) 
           head = tail;
       else 
           current.next = newNode;
   }
   
}

class CardQueue extends Queue
{
   public static final Card QUEUE_EMPTY = null;
   public void addCard(Card newCard)
   {   
      CardNode lp = new CardNode(newCard);
      super.add(lp);
   }  
   
   public Card removeCard()
   {
      CardNode lp = (CardNode)remove();
      if (lp == null)
          return QUEUE_EMPTY;
      else
          return lp.getData();
   }

}

class Card
{   
   // type and constants
   public enum Suit { clubs, diamonds, hearts, spades }
   
   static char DEFAULT_VAL = 'A';
   static Suit DEFAULT_SUIT = Suit.spades;
 
   // private data
   private char value;
   private Suit suit;
   private boolean errorFlag;
 
   // 4 overloaded constructors
   public Card(char value, Suit suit)
   {   // because mutator sets errorFlag, we don't have to test
      set(value, suit);
   }
 
   public Card(char value)
   {
      this(value, DEFAULT_SUIT);
   }
   
   public Card()
   {
      this(DEFAULT_VAL, DEFAULT_SUIT);
   }
   
   // copy constructor
   public Card(Card card)
   {
      this(card.value, card.suit);
   }
 
   // mutators
   public boolean set(char value, Suit suit)
   {
      char upVal;            // for upcasing char
 
      // convert to uppercase to simplify
      upVal = Character.toUpperCase(value);
 
      if ( !isValid(upVal, suit))
      {
         errorFlag = true;
         return false;
      }
      
      // else implied
      errorFlag = false;
      this.value = upVal;
      this.suit = suit;
      return true;
   }
 
   // accessors
   public char getVal()
   {
      return value;
   }
 
   public Suit getSuit()
   {
      return suit;
   }
 
   public boolean getErrorFlag()
   {
      return errorFlag;
   }
   
   // stringizer
   public String toString()
   {
      String retVal;
 
      if (errorFlag)
         return "** illegal **";
 
      // else implied
      retVal =  String.valueOf(value);
      retVal += " of ";
      retVal += String.valueOf(suit);
 
      return retVal;
   }
 
   // helper
   private static boolean isValid(char value, Suit suit)
   {
      // don't need to test suit (enum), but leave in for clarity
      char upVal;  // string to hold the 1-char value
      String legalVals = "23456789TJQKA";
      
      // convert to uppercase to simplify (need #include <cctype>)
      upVal = Character.toUpperCase(value);
 
      // check for validity
      if ( legalVals.indexOf(upVal) >= 0 )
         return true;
      else
         return false;
   }
   
   public boolean equals(Card card)
   {
      if (this.value != card.value)
         return false;
      if (this.suit != card.suit)
         return false;
      if (this.errorFlag != card.errorFlag)
         return false;
      return true;
   }
   
    static public Card generateRandomCard()
   {
       int value_index = (int)(Math.random() * 52) /4 ;
       int suit_index = (int) (Math.random() * 4);
       System.out.println(value_index + "  " + suit_index);
       return(new Card(GUICard.turnIntIntoCardValueChar(value_index),
               Card.Suit.values()[suit_index]));
       
   }
}

class CardTable extends JFrame
{
    static final int MAX_CARDS_PER_HAND = 57;
    static final int MAX_PLAYERS = 2;  // for now, we only allow 2 person games
    
    private int numCardsPerHand;
    private int numPlayers;
    
    JPanel  pnlcomputer_top, pnlplayer_bottom, pnlplaying_card; 
    //JLabel  lblInscomputer_top, lblInsplayer_bottom, lblInsplaying_card;

 
    //public methods
    public CardTable(String title, int nCPerHand, int nPlayers)
    {
        super(title);
        
        //filters input
        numCardsPerHand = nCPerHand;
        numPlayers = nPlayers;
        //pnlcomputer_top = new JPanel(new GridLayout(1,numCardsPerHand,10,10));
        //pnlplayer_bottom = new JPanel(new GridLayout(1,numCardsPerHand,10,10));
        pnlplaying_card = new JPanel(new GridLayout(1,numPlayers,5,5));
        pnlplaying_card.setLayout(new FlowLayout(FlowLayout.LEFT));
        //setLayout ( new BorderLayout(5,5));
       // add(pnlcomputer_top, BorderLayout.NORTH);
       // add(pnlplayer_bottom, BorderLayout.SOUTH);
        add(pnlplaying_card, BorderLayout.CENTER);
       // pnlcomputer_top.setBorder(new TitledBorder("Computer Hand"));
       // pnlplayer_bottom.setBorder(new TitledBorder("Your Hand"));
        pnlplaying_card.setBorder(new TitledBorder("Playing Area"));
        
        GUICard.loadCardIcons();
    }
    
    public int getNumCardsPerHand()
    {
        return numCardsPerHand;
    }
    
    public int getNumPlayers()
    {
        return numPlayers;
    }
}

class GUICard
{
    //static members
    private static Icon[][] iconCards = new ImageIcon[14][4]; // 14 = A thru K (+ joker optional)
    private static Icon iconBack;
    private static boolean iconsLoaded = false;
    
    //helper static arrays
    private static String cardlValsConvertAssist = "A23456789TJQKX";
    private static String suitValsConvertAssist  = "CDHS";
    private static Card.Suit suitConvertAssist[] =
    {
       Card.Suit.clubs,
       Card.Suit.diamonds,
       Card.Suit.hearts,
       Card.Suit.spades
    };
    
    //static methods
    
    static void loadCardIcons()
       {
        String imageFileName;
        int intSuit, intVal;
      
        if (!iconsLoaded)
          {
            for (intSuit = 0; intSuit < 4; intSuit++)
               for (intVal = 0; intVal < 14; intVal++ )
                 {
            // card image files stored in Foothill/images folder with names like
            // "AC.gif", "3H.gif","XD.gif", etc.
                  imageFileName = "C:/foothilljava/denis/lab5/Gui Cards/images/"
                  + turnIntIntoCardValueChar(intVal) 
                  + turnIntIntoCardSuitChar(intSuit)
                  + ".gif";
                  iconCards[intVal][intSuit] = new ImageIcon(imageFileName);
                 }
            iconBack = new ImageIcon("C:/foothilljava/denis/lab5/"
                + "Gui Cards/images/BK.gif");
            iconsLoaded = true;
          }
       }    
   
   // turns 0 - 13 into 'A', '2', '3', ... 'Q', 'K', 'X'
   static public char turnIntIntoCardValueChar(int k)
   {
   
      if ( k < 0 || k > 13)
         return '?'; 
      return cardlValsConvertAssist.charAt(k);
   }
   
   // turns 0 - 3 into 'C', 'D', 'H', 'S'
   static public char turnIntIntoCardSuitChar(int k)
   {
      if ( k < 0 || k > 3)
         return '?'; 
      return suitValsConvertAssist.charAt(k);
   }
        
   static public int valueAsInt(Card card)
   {
       return cardlValsConvertAssist.indexOf(card.getVal());
   }
   
   static public int suitAsInt(Card card)
   {
       return card.getSuit().ordinal();
   }
   
   static public Icon getIcon(Card card)
   {
       loadCardIcons();
       return iconCards[valueAsInt(card)][suitAsInt(card)];
   }
   
  
   static public Icon getBackCardIcon()
    {
        return iconBack;
       
    }
    
}