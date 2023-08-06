package CalcMath;

import java.util.*;
public class Derivatives{
    public static String trigRule(String f){
        ArrayList<String> parts = new ArrayList<String>();
        
        int trigIndex = f.indexOf("sin");
        if(trigIndex == -1) trigIndex = f.indexOf("cos");

        if(trigIndex == 0) parts.add("1");
        else parts.add(f.substring(0, trigIndex));//this might not work
        parts.add(f.substring(trigIndex, trigIndex+3));
        parts.add(f.substring(trigIndex+4, f.length()-1));

        if(parts.get(1).equals("sin")){
            return parts.get(0) + "*cos(" + parts.get(2) + ")";
        }
        return "-" + parts.get(0) + "*sin(" + parts.get(2) + ")";
    }

    public static String powerRule(String f){
        if(!f.contains("x")) return "0";
        if(f.equals("x")) return "1";
        String derivative = "";

        ArrayList<String> parts = new ArrayList<String>();
        int xIndex = f.indexOf("x");
        int exponentIndex = f.indexOf("^");
        
        if(exponentIndex == -1){
            //no exponent, must be something like 2x
            return f.substring(0, xIndex);
        }
        if(xIndex == 0) parts.add("1");
        else parts.add(f.substring(0, xIndex));//adds the coefficient
        parts.add(f.substring(xIndex, exponentIndex));//adds x
        parts.add(f.substring(exponentIndex+2, f.length()-1));//adds the value of the exponenets


        double expoNum = Double.parseDouble(parts.get(2));
        double coeff = Double.parseDouble(parts.get(0));
        coeff = coeff*expoNum;
        expoNum--;
        if(expoNum == 1) derivative = "" + coeff + "x";
        else if(expoNum == 0) derivative = "" + coeff;
        else derivative = "" + coeff + "x^(" + expoNum + ")";
        return derivative;
    }

    public static String chainRule2(String f){
        ArrayList<String> parts = new ArrayList<String>();

        //add the correct coefficient, 1 if not present
        System.out.println("function is " + f);
        if(f.charAt(0) == '(') parts.add("1");

        else parts.add(f.substring(0, f.indexOf("(")));


        int indexOfExponent = f.lastIndexOf("^");//finds the last exponent(aka outermost function)
        String insideTerm = f.substring(f.indexOf("(") + 1, indexOfExponent-1); //get the inside function

        parts.add(insideTerm);//add inside term
        parts.add(f.substring(indexOfExponent+2, f.length()-1));//adds the exponent

        System.out.println("Parts of chain rule are " + parts);

        double newCoeff = Double.parseDouble(parts.get(0)) * Double.parseDouble(parts.get(2));
        double newEx = Double.parseDouble(parts.get(2))-1;

        String ans = "" + newCoeff + "(" + parts.get(1) + ")^(" + newEx + ")*" + Derivative(parts.get(1));
        
        ans = ans.replace("^(1.0)", "");
        return ans;

    }

    public static String naturalLog(String f){
        // format: 1ln(function)
        
        int lnIndex = f.indexOf("ln");
        double coeff=1;
        if(lnIndex>0) coeff= Double.parseDouble(f.substring(0, lnIndex));
        String term = f.substring(lnIndex+3, f.length()-1);
        String deriv = Derivative(term);
        System.out.println(deriv);
        return "("+coeff+"("+deriv+"))/("+term+")";
      }
      
      public static String quotientRule(String f){
        // format: (function1)/(function2)
        String[] functions = f.split("/"); //make sure that this is not within the parentehses (assuming that that matters?)
        String t1 = functions[0].substring(1, functions[0].length()-1);
        String t2 = functions[1].substring(1, functions[1].length()-1);
        String d1 = Derivative(t1);
        String d2 = Derivative(t2);

        String part1 = ""+t1+"("+d2+")-";
        String part2 = ""+t2+"("+d1+")";

        if(part1.contains("(0)") && part2.contains("(0)")) return "0";
        else if(part1.contains("(0)")) return "(" + part2 + ")/(("+t2+")^(2))";
        else if(part2.contains("(0)")) return "(" + part1.substring(0, part1.length()-1) + ")/(("+t2+")^(2))";//return without the + sign
        else return "(" + part1 + part2 + ")" + "/(("+t2+")^(2))";
      }
      
      public static String productRule(String f){
        // format: (function1)*(function2)
        String[] functions = f.split("\\*");//make sure that this is not within the parentheshess (again assuming that matters?)
        String t1 = functions[0].substring(1, functions[0].length()-1);
        String t2 = functions[1].substring(1, functions[1].length()-1);
        String d1 = Derivative(t1);
        String d2 = Derivative(t2);

        String part1 = ""+t1+"("+d2+")+";
        String part2 = ""+t2+"("+d1+")";

        if(part1.contains("(0)") && part2.contains("(0)")) return "0";
        else if(part1.contains("(0)")) return part2;
        else if(part2.contains("(0)")) return part1.substring(0, part1.length()-1);//return without the + sign
        else return part1 + part2;
      }
  
  

    public static String Derivative(String f){
        ArrayList<String> functions = Function.splitFunction(f);
        ArrayList<String> newFunctions = new ArrayList<String>();

        
        System.out.println(functions);
        for(String s : functions){
            //if there is a ) in front of the carrot
            int indexOfLastCarrot = s.lastIndexOf("^");
            if(indexOfLastCarrot > 0 && s.charAt(indexOfLastCarrot-1) == ')'){
                newFunctions.add(chainRule2(s));
            }
            else if(s.contains("*")){
                System.out.println("Power ruling " + s);
                newFunctions.add(productRule(s));
            }
            else if(s.contains("/")){
                newFunctions.add(quotientRule(s));
            }
            else if(s.contains("sin") || s.contains("cos")){
                System.out.println("Working on trig rule for " + functions);
                newFunctions.add(trigRule(s));//add the derivative of the trig function
                newFunctions.add("*");
                int index1 = s.indexOf("sin") + 4;
                if(index1 == 3) index1 = s.indexOf("cos")+4;
                newFunctions.add(Derivative(s.substring(index1, s.length()-1)));//derivative of inside of trig expression
            }
            else if(s.contains("ln")){
                newFunctions.add(naturalLog(s));
            }
            else if(s.equals("+"))newFunctions.add("+");
            else if(s.equals("-"))newFunctions.add("-");
            else{
                newFunctions.add(powerRule(s));
            }
        }
        System.out.println("newfunctions is " + newFunctions);
        String newDerivative = "";
        for(String s : newFunctions){
            if(s.contains("(0)")) newDerivative += "0";
            else newDerivative += s;
        }

        newDerivative = newDerivative.replace("+0", "+");
        newDerivative = newDerivative.replace("*1", "");
        newDerivative = newDerivative.replace("1*", "");
        newDerivative = newDerivative.replace("-1*", "-");
        newDerivative = newDerivative.replace("^(1.0)", "");
        newDerivative = newDerivative.replace("*-1", "*-");
        newDerivative = newDerivative.replace("--", "+");
        newDerivative = newDerivative.replace("+-", "-");
        newDerivative = newDerivative.replace("(1)", "");
        if(newDerivative.length() > 1 && newDerivative.substring(0, 2).equals("(\\*")){
            newDerivative = newDerivative.substring(2);
        }
        
        if(newDerivative.charAt(newDerivative.length()-1) == '+' || newDerivative.charAt(newDerivative.length()-1) == '-'){
            newDerivative = newDerivative.substring(0, newDerivative.length()-1);
        }

        return newDerivative;

        /*
        String derivative = "";
        if(f.contains("sin") || f.contains("cos")){
            derivative += trigRule(f);
        }
        int index1 = f.indexOf("sin") + 3;
        int index2 = f.lastIndexOf("")
        derivative += 
        */

    }
    public static void main(String[] args){
        //System.out.println(powerRule("2x^(1)"));
        //System.out.println(trigRule("sin(3x)"));
        //System.out.println(Derivative("3x^(2)+sin(x^(2))"));
        //System.out.println(Derivative("x^(2)+cos(2x)-3x^(2)"));
        //System.out.println(Derivative("(sin(3x^(2)))^(2)"));
        //System.out.println(Derivative("x"));
        //System.out.println(Derivative("(x+3)^(2)+3x^(2)"));

        //System.out.println(Derivative("(sin(cos(x^(2))))^(2)"));
        //System.out.println(Derivative("(-2sin(x))^(2)"));
        
        System.out.println(Derivative("(sin(cos(x^(2))))^(2)"));
        
        //System.out.println(Derivative("(sin(-cos(x^(2))))"));
        //System.out.println(Derivative("(sin(-cos(x^(2))))^(2)"));
        //System.out.println(Function.splitFunction(("(sin(x^(2)+2x))^(2)")));

        //System.out.println(Derivative("(sin(cos(x^(2)))"));
        //System.out.println(Derivative("x+2"));

        //System.out.println(Derivative("((x^(0.5))/(2))*(3x^(2))"));
        //System.out.println(Derivative("(x^(0.5))/(2)"));
        //System.out.println(Derivative("ln(2)+x^(2)"));
        //System.out.println(Derivative("(x^(2))/(ln(2))"));
        System.out.println(Derivative("ln(x)"));

        
    }

}
