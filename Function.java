package CalcMath;
import java.util.*;
public class Function{
    
    public static ArrayList<String> splitFunction(String f){
        ArrayList<String> functions = new ArrayList<String>();

        int lastIndex = 0;//keep track of the index of the last + or - we saw
        for(int i = 1; i < f.length(); i++){
            if((f.charAt(i) == '+' || f.charAt(i) == '-') && !(parentheses.withinParentheses(i, f))){
                functions.add(f.substring(lastIndex, i)); //add the function
                functions.add(Character.toString(f.charAt(i))); //add the operator
                lastIndex = i+1; //update the lastIndex
            }
            if(i == f.length()-1) functions.add(f.substring(lastIndex));
        }
        if(functions.isEmpty()) functions.add(f);
        return functions;
    }
    public static double solveFunction(String function, String x){
        ArrayList<String> parts = new ArrayList<String>();
        String newFunc = "";
        char prevChar = 'q';

        //replace all x's with either 1*x or *x
        for(int i = 0; i < function.length(); i++){
            //x and previous character is not a digit
            if(function.charAt(i) == 'x' && !Character.isDigit(prevChar)){
                newFunc += "(1*"+x + ")";
            }
            else if(function.charAt(i) == 'x'){
                //find digit start of last coefficient
                int j = i-1;
                while(Character.isDigit(j)){
                    j--;
                    if(j==0)break;
                }
                //j+1 is index of last number
                //newFunc = newFunc.substring(0, j+1) + "(" + newFunc.substring(j+1,i) + "*" + x + ")";
                newFunc += "*" + x;
            }
            else{
                newFunc += function.charAt(i);
            }
            prevChar = function.charAt(i);
        }

        //System.out.println("new function is " + newFunc);

        function = newFunc;
        int prevIndex = 0;
        for(int i = 0; i < function.length(); i++){
            if(function.charAt(i) == '+' || function.charAt(i) == '-' || function.charAt(i) == '*' || function.charAt(i) == '/' || function.charAt(i) == '^' || function.charAt(i) == '(' || function.charAt(i) == ')' || function.charAt(i) == 's' || function.charAt(i) == 'c'){
                if(!function.substring(prevIndex, i).equals("")) parts.add(function.substring(prevIndex, i));//add previous term if it isn't empty
                if((function.charAt(i) != 's') && (function.charAt(i) != 'c')){
                    parts.add(function.substring(i, i+1));//add the operator if it isn't sine
                    prevIndex = i+1;
                }
                else{
                    //operator is sine or cosine, add the entire thing
                    if(function.charAt(i-1) == 'o') continue;
                    else{
                        parts.add(function.substring(i, i+3));
                        prevIndex = i+3;
                        i+=2;
                    }
                }
            }
            else if(i == function.length()-1) parts.add(function.substring(prevIndex));
        }
        System.out.println(parts);
        return Double.parseDouble(solve(parts));
    }

    public static String solve(ArrayList<String> x){
        //System.out.println("Before solving is " + x);
        for(int i = 0; i < x.size(); i++){
            if(x.get(i).contains("_")){
                String tempIntValue = "-" + x.get(i).substring(1);//replace _ with -
                x.set(i, "" + Double.parseDouble(tempIntValue));
            }
        }
        while(x.size() != 1){
            //replace -- with +, and +- with - (this is because of specail negative cases)
            
            /*int index = 0;
            while(index < x.size()-1){
                if(x.get(index).equals("+") && x.get(index+1).equals("-")){
                    x.set(index, "-");
                    x.remove(index+1);
                }
                else if(x.get(index).equals("-") && x.get(index+1).equals("-")){
                    x.remove(index+1);
                }
                else index++;
            }*/

            if(x.contains("(")){
        
                ArrayList<Pair> pairs = parentheses.findParentheses(x);

                while(!pairs.isEmpty()){
                    //System.out.println("Currently checking parenthesses for " + x);
                    pairs = parentheses.findParentheses(x);
                    //System.out.println("Parenthes pairs are " + pairs);
                    Pair currParen = pairs.get(0);
                    int left = currParen.first;
                    int right = currParen.second;
                    pairs.remove(0);//remove this set from all parentheses
                    //System.out.println("currently solving " + x.subList(left, right));
                    
                    String insideSolution = solve(new ArrayList<String>(x.subList(left+1, right)));//solve the part inside parentheses
                    //replace the expression inside parentheses with what the solved value
                    for(int i = left; i < right; i++){
                        x.remove(left);
                    }
                    x.set(left, insideSolution);
                    
                    //System.out.println("AFter removing and paretnhes sis " + x);
                }

            }//parenthese
            
            
            if(x.contains("^") || x.contains("sqrt")){
                int p = x.indexOf("^");
                int s = x.indexOf("sqrt");
            
            if((x.contains("^") && x.contains("sqrt") && s>p) || !x.contains("sqrt")){
                x.set(p,power((String)x.get(p-1), (String)x.get(p+1)));
                x.remove(p+1);
                x.remove(p-1);
            }//power
            else if((x.contains("^") && x.contains("sqrt") && p>s) || !x.contains("^")){
                x.set(s, squareRoot((String)x.get(s+1)));
                x.remove(s+1);
            }       
            }//e
            else if(x.contains("sin")){
                int s = x.indexOf("sin");
                String tempVal = sin(x.get(s+1));//store the value of sin(x)

                x.set(s, (String)sin(x.get(s+1)));//take the sine of the next term in the thing
                x.remove(s+1);
                /*
                //change the negative into a minus sign in separate array entry
                if(x.get(s).charAt(0)=='-'){
                    x.set(s, tempVal.substring(1));
                    x.add(s, "-");
                    x.remove(s+2);
                }
                else x.remove(s+1);*/
            }
            else if(x.contains("cos")){
                int c = x.indexOf("cos");
                String tempVal = cos(x.get(c+1));//store the value of sin(x)

                x.set(c, (String)cos(x.get(c+1)));
                x.remove(c+1);

                /*
                if(x.get(c).charAt(0)=='-'){
                    x.set(c, tempVal.substring(1));
                    x.add(c, "-");
                    x.remove(c+2);
                }
                else x.remove(c+1);*/
            }

            else if(x.contains("*") || x.contains("/")){
                int m = x.indexOf("*");
                int d = x.indexOf("/");
                
                if((x.contains("*") && x.contains("/") && d>m) || !x.contains("/")){
                    x.set(m, mult((String)x.get(m-1), (String)x.get(m+1)));
                    x.remove(m+1);
                    x.remove(m-1);
                }//mult
                else if((x.contains("/") && x.contains("*") && m>d) || !x.contains("*")){
                    x.set(d, div((String)x.get(d-1), (String)x.get(d+1)));
                    x.remove(d+1);
                    x.remove(d-1);
                }//division
            }//md
            else if(x.contains("+") || x.contains("-")){

                int a = x.indexOf("+");
                int s = x.indexOf("-");
            
                if((x.contains("+") && x.contains("-") && s>a) || !x.contains("-")){
                    x.set(a, add((String)x.get(a-1), (String)x.get(a+1)));
                    x.remove(a+1);
                    x.remove(a-1);
                }//add
                else if((x.contains("-") && x.contains("+") && a>s) || !x.contains("+")){
                    x.set(s, sub((String)x.get(s-1), (String)x.get(s+1)));
                    x.remove(s+1);
                    x.remove(s-1);
                }//sub
            }//as
            System.out.println("AFter solving is " + x);
        }//end while loop
        return (String)x.get(0);
    }
     
     
     private static String add(String x, String y){        
        double num1 = Double.parseDouble(x);
        double num2 = Double.parseDouble(y);
        return ""+(num1+num2);
     }//end add
     
     private static String sub(String x, String y){
        double num1 = Double.parseDouble(x);
        double num2 = Double.parseDouble(y);
        return ""+(num1-num2);
     }//end sub
     
     private static String mult(String x, String y){
           double num1 = Double.parseDouble(x);
           double num2 = Double.parseDouble(y);
           return ""+num1*num2;
     }//end mult
     
     private static String div(String x, String y){
           double num1 = Double.parseDouble(x);
           double num2 = Double.parseDouble(y);
           return ""+num1/num2;
     }//end div
     
     private static String power(String x, String y){  
           double num1 = Double.parseDouble(x);
           double num2 = Double.parseDouble(y);
           return ""+Math.pow(num1, num2);
     }//end power
     
     private static String squareRoot(String x){
           double num = Double.parseDouble(x);
           return ""+Math.sqrt(num);
     }//end squareRoot
     private static String sin(String x){
         return ""+Math.sin(Double.parseDouble(x));
     }
     private static String cos(String x){
        return ""+Math.cos(Double.parseDouble(x));
    }
    public static void main(String[] args){

        /*
        ArrayList<String> tester = new ArrayList<String>();
        tester.add("2");
        tester.add("*");
        tester.add("(");
        tester.add("3");
        tester.add("+");
        tester.add("6");
        tester.add(")");


        ArrayList<String> tester2 = new ArrayList<String>();
        String expression = "2+(6/(3-1))";
        for(int i = 0; i < expression.length(); i++){
            tester2.add("" + expression.charAt(i));
        }

        //System.out.println(solve(tester));
        System.out.println(solve(tester2));

        String f = "(2x+3)^(2)+3x^(2)-2^(x)+3";
        //String f2 = "x+2";
        System.out.println(solveFunction(f, 5));
        System.out.println();
        System.out.println(parentheses.withinParentheses(10, f));
        System.out.println(solveFunction("23x^(2)/x", 2));
        System.out.println();*/

        String tester3 = "(3+3*cos(3x)*3)/(2)";
        String tester4 = "3x";
        System.out.println(solveFunction(tester4, "_3"));
        //System.out.println(solveFunction("4x", "_3"));
    }
}
