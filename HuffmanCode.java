import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.PriorityQueue;
import java.util.Scanner;  
//defining a class that creates nodes of the tree  
class Node  
{  
    //storing character in ch variable of type character  
    Character ch;  
    //storing frequency in freq variable of type int  
    Integer freq;  
    //initially both child (left and right) are null  
    Node left = null;   
    Node right = null;   
    //creating a constructor of the Node class  
    Node(Character ch, Integer freq)  
    {  
        this.ch = ch;  
        this.freq = freq;  
    }  
    //creating a constructor of the Node class  
    public Node(Character ch, Integer freq, Node left, Node right)  
    {  
        this.ch = ch;  
        this.freq = freq;  
        this.left = left;  
        this.right = right;  
    }  
}  
//main class  
public class HuffmanCode  
{  
    
    public static void createHuffmanTree(String text)  
    {  
         
        if (text == null || text.length() == 0)   
        {  
            return;  
        }  
           
        Map<Character, Integer> freq = new HashMap<>();  
        //loop iterates over the string and converts the text into character array  
        for (char c: text.toCharArray())   
        {  
            
            freq.put(c, freq.getOrDefault(c, 0) + 1);  
        }  
         
         
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));  
          
        for (var entry: freq.entrySet())   
        {  
            //creates a leaf node and add it to the queue  
            pq.add(new Node(entry.getKey(), entry.getValue()));  
        }  
        //while loop runs until there is more than one node in the queue  
        while (pq.size() != 1)  
        {  
            
            Node left = pq.poll();  
            Node right = pq.poll();  
             
            int sum = left.freq + right.freq;  
            //adding a new internal node (deleted nodes i.e. right and left) to the queue with a frequency that is equal to the sum of both nodes  
            pq.add(new Node(null, sum, left, right));  
        }  
       
        Node root = pq.peek();  
          
        Map<Character, String> huffmanCode = new HashMap<>();  
        encodeData(root, "", huffmanCode);  
         
        System.out.println("Huffman Codes of the characters are: " + huffmanCode);  
        
        System.out.println("The initial string is: " + text);  
           
        StringBuilder sb = new StringBuilder();  
        
        for (char c: text.toCharArray())   
        {  
            //prints encoded string by getting characters   
            sb.append(huffmanCode.get(c));  
        }   
        System.out.println("The encoded string is: " + sb);  
        System.out.print("The decoded string is: ");  
        if (isLeaf(root))  
        {  
            //special case: For input like a, aa, aaa, etc.  
            while (root.freq-- > 0)   
            {  
                System.out.print(root.ch);  
            }  
        }  
        else   
        {  
            //traverse over the Huffman tree again and this time, decode the encoded string  
            int index = -1;  
            while (index < sb.length() - 1)   
            {  
                index = decodeData(root, index, sb);  
            }  
        }  
    }  
 
    public static void encodeData(Node root, String str, Map<Character, String> huffmanCode)  
    {  
        if (root == null)   
        {  
            return;  
        }  
        //checks if the node is a leaf node or not  
        if (isLeaf(root))   
        {  
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");  
        }  
        encodeData(root.left, str + '0', huffmanCode);  
        encodeData(root.right, str + '1', huffmanCode);  
    }  
    
    public static int decodeData(Node root, int index, StringBuilder sb)  
    {  
        //checks if the root node is null or not  
        if (root == null)   
        {  
            return index;  
        }   
        //checks if the node is a leaf node or not  
        if (isLeaf(root))  
        {  
            System.out.print(root.ch);  
            return index;  
        }  
        index++;   
        root = (sb.charAt(index) == '0') ? root.left : root.right;  
        index = decodeData(root, index, sb);  
        return index;  
    }  
    //function to check if the Huffman Tree contains a single node  
    public static boolean isLeaf(Node root)   
    {  
        //returns true if both conditions return ture  
        return root.left == null && root.right == null;  
    }  
    //driver code  
    public static void main(String args[])  
    {  
        String text ;
        System.out.println("Enter the text that you want to compress");
        Scanner scan = new Scanner(System.in);
         text= scan.nextLine(); 
        //function calling  
        createHuffmanTree(text);  
    }  
}  