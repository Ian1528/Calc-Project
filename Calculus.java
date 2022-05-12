
import java.util.*;
public class Calculus{
    public static String trigRule(String f){
        ArrayList<String> parts = new ArrayList<String>();
        
        int trigIndex = f.indexOf("sin");
        if(trigIndex == -1) trigIndex = f.indexOf("cos");

        if(trigIndex == 0) parts.add("1");
        parts.add(f.substring(0, trigIndex));
        parts.add(f.substring(trigIndex, trigIndex+3));
        parts.add(f.substring(trigIndex+4, f.length()-1));

        if(parts.get(1).equals("sin")){
            return parts.get(1) + "cos(" + parts.get(2) + ")";
        }
        return "-" + parts.get(1) + "sin(" + parts.get(2) + ")";
    }

    public static String PowerRule(String f){
        String derivative = "";

        ArrayList<String> parts = new ArrayList<String>();
        int xIndex = f.indexOf("x");
        int exponentIndex = f.indexOf("^");


        parts.add(f.substring(0, xIndex));//adds the coefficient
        parts.add(f.substring(xIndex, exponentIndex));//adds x
        parts.add(f.substring(exponentIndex+2, f.length()-1));//adds the value of the exponenets
        double expoNum = Double.parseDouble(parts.get(2));
        double coeff = Double.parseDouble(parts.get(0));

        coeff = coeff*expoNum;
        expoNum--;

        derivative = "" + coeff + "x^(" + expoNum + ")";
        return derivative;
    }


    public static String Derivative(String f){
        ArrayList<String> functions = new ArrayList<String>();
        ArrayList<String> newFunctions = new ArrayList<String>();

        int lastIndex = 0;//keep track of the index of the last + or - we saw
        for(int i = 0; i < f.length(); i++){
            if(f.charAt(i) == '+' || f.charAt(i) == '-'){
                functions.add(f.substring(lastIndex, i)); //add the function
                functions.add(Character.toString(f.charAt(i))); //add the operator
                lastIndex = i; //update the lastIndex
            }
        }

        for(String s : functions){
            if(s.contains("sin") || s.contains("cos")){
                newFunctions.add(trigRule(s));//add the derivative of the trig function
                newFunctions.add("*");
                int index1 = f.indexOf("sin") + 3;
                if(index1 == -1) index1 = f.indexOf("cos");
                newFunctions.add(powerRule())//derivative of inside of trig expression
            }
        }

        /*
        String derivative = "";
        if(f.contains("sin") || f.contains("cos")){
            derivative += trigRule(f);
        }
        int index1 = f.indexOf("sin") + 3;
        int index2 = f.lastIndexOf("")
        derivative += 
        */
        return "";

    }
    
    public static void main(String[] args){
        System.out.println(PowerRule("3x^(2)"));
    }
}
