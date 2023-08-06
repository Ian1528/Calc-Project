package CalcMath;
import java.util.*;
public class Integrals {
    public static String powerRuleIntegral(String f){
        String antiderivative = "";
        ArrayList<String> parts = new ArrayList<String>();
        int xIndex = f.indexOf("x");
        int exponentIndex = f.indexOf("^");

        if(xIndex == -1){
            return f+"x";
        }
        if(exponentIndex == -1){
            f += "^(1)";
            exponentIndex = f.indexOf("^");
        }
        if(xIndex == 0) parts.add("1");
        else parts.add(f.substring(0, xIndex));//adds the coefficient
        parts.add(f.substring(xIndex, exponentIndex));//adds x
        parts.add(f.substring(exponentIndex+2, f.length()-1));//adds the value of the exponenets
        double expoNum = Double.parseDouble(parts.get(2));
        double coeff = Double.parseDouble(parts.get(0));

        coeff = coeff/(expoNum+1);
        expoNum++;

        antiderivative = "" + coeff + "x^(" + expoNum + ")";
        return antiderivative;
    }
    public static String trigRuleIntegral(String f){
        ArrayList<String> parts = new ArrayList<String>();
        
        int trigIndex = f.indexOf("sin");
        if(trigIndex == -1) trigIndex = f.indexOf("cos");

        if(trigIndex == 0) parts.add("1");
        else parts.add(f.substring(0, trigIndex));
        parts.add(f.substring(trigIndex, trigIndex+3));//add the actual sin and cosine
        parts.add(f.substring(trigIndex+4, f.length()-1));//add expression inside sine and cosine
        double coeff = Double.parseDouble(parts.get(0));
        int indexOfX = parts.get(2).indexOf("x");
        coeff /= Double.parseDouble(parts.get(2).substring(0, indexOfX));//divide the coefficient by coeffeiceint of x


        if(parts.get(1).equals("sin")){
            return "-" + coeff + "*cos(" + parts.get(2) + ")";
        }
        return "" + coeff + "*sin(" + parts.get(2) + ")";
    }
    public static String indefiniteIntegral(String f){
        
        ArrayList<String> parts = Function.splitFunction(f);
        System.out.println(parts);
        String integral = "";
        for(String s : parts){
            if(s.contains("sin") || s.contains("cos")) integral += trigRuleIntegral(s);//trig
            else if(s.equals("+") || s.equals("-")) integral += s;
            else integral += powerRuleIntegral(s);//power

        }
        integral = integral.replace("+0", "+");
        integral = integral.replace("*1", "");
        integral = integral.replace("^(1.0)", "");
        integral = integral.replace("*-1", "*-");
        integral = integral.replace("--", "+");
        integral = integral.replace("+-", "-");

        return integral + "+C";
    }
    public static double definiteIntegral(String f, double a, double b){
        String antiderivative = indefiniteIntegral(f).substring(0, indefiniteIntegral(f).length()-2);
        System.out.println(antiderivative);
        return Function.solveFunction(antiderivative, "" + b) - Function.solveFunction(antiderivative, "" + a);
    }
    public static void main(String[] args){
        //System.out.println(powerRuleIntegral("-3x^(2)"));
        //System.out.println(trigRuleIntegral("cos(2x)"));
        //System.out.println(indefiniteIntegral("x^(2)+cos(2x)-3x^(2)"));
        //System.out.println(definiteIntegral("x^(2)+cos(2x)-3x^(2)", 2, 6));
        System.out.println(definiteIntegral("3x^(2)-sin(2x)", 1, 100));
    }
}
