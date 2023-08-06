package CalcMath;
import java.util.*;
public class parentheses {
    //with a string array parameter
    public static boolean withinParentheses(int index, String f){
        ArrayList<Pair> pairs = findParentheses(f);
        for(Pair p : pairs){
            if(p.first < index && index < p.second){
                return true;
            }
        }
        return false;
    }
    public static ArrayList<Pair> findParentheses(ArrayList<String> s){

        Stack<Integer> left = new Stack<Integer>();
        Queue<Integer> right = new LinkedList<Integer>();
        
        ArrayList<Pair> pairs = new ArrayList<Pair>();

        for(int i = 0; i < s.size(); i++){
            if(s.get(i).equals("(")){
                left.push(i);
            }
            else if(s.get(i).equals(")")){
                right.add(i);
            }
            if(left.size()==right.size() && left.size() != 0){
                int l = left.pop();
                int r = right.remove();

                pairs.add(new Pair(l, r));
            }
        }
        while(left.size()==right.size() && left.size() != 0){
            int l = left.pop();
            int r = right.remove();

            pairs.add(new Pair(l, r));
        }

        return pairs;
    }

    //wiht a string parameter
    public static ArrayList<Pair> findParentheses(String function){
        char[] s = function.toCharArray();

        Stack<Integer> left = new Stack<Integer>();
        Queue<Integer> right = new LinkedList<Integer>();
        
        ArrayList<Pair> pairs = new ArrayList<Pair>();

        for(int i = 0; i < s.length; i++){

            if(s[i] == '('){
                left.push(i);
            }
            else if(s[i] == ')'){
                right.add(i);
            }
           
            if(left.size()==right.size() && left.size() != 0){
                int l = left.pop();
                int r = right.remove();

                pairs.add(new Pair(l, r));
            }
        }

        while(left.size()==right.size() && left.size() != 0){
            int l = left.pop();
            int r = right.remove();

            pairs.add(new Pair(l, r));
        }

        return pairs;
    }
    public static void main(String[] args){
        String test = "2+(2x+(3y+2))+3x(2)";
        ArrayList<Pair> arr = findParentheses(test);
        for(Pair p : arr){
            System.out.println(p);
        }
        System.out.println();

        ArrayList<String> test2 = new ArrayList<String>();
        for(int i = 0; i < test.length(); i++){
            test2.add("" + test.charAt(i));
        }

        ArrayList<Pair> arr2 = findParentheses(test);
        for(Pair p : arr2){
            System.out.println(p);
        }
        System.out.println();
        String test3 = "(3x+2x^(2)+x^(3))/(2x+x^(2))";//left at 0, 4, 7
        //right at 9, 10, 11
        for(Pair p : findParentheses(test3)){
            System.out.println(p);
        }
    }
}
