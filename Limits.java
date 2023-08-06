package CalcMath;

//limits to infinity
import java.util.*;

public class Limits{
    public static String limitToInfinity(String f){
        String[] fraction = f.split("/");//separates numerator and denominator
        fraction[0] = fraction[0].substring(1, fraction[0].length()-1);
        fraction[1] = fraction[1].substring(1, fraction[1].length()-1);

        ArrayList<String> num = new ArrayList<String>();
        ArrayList<String> den = new ArrayList<String>();
        
        int lastIndex=-1;
        for(int i=0; i<fraction[0].length(); i++){

            //just one expression, no addition or subtraction
            if(!fraction[0].contains("+") && !fraction[0].contains("-")){
                num.add(fraction[0]);
                break;
            }

            //add the previous term and the operator
            if(fraction[0].charAt(i) == '+' || fraction[0].charAt(i) == '-'){
                num.add(fraction[0].substring(lastIndex+1, i));
                num.add(Character.toString(fraction[0].charAt(i)));
                lastIndex=i;
            }
            if(i==fraction[0].length()-1) num.add(fraction[0].substring(lastIndex+1));
            System.out.println(num);
        }//puts individual terms into ArrayList (num)
        
        int lastIndexDen=-1;
        for(int i=0; i<fraction[1].length(); i++){
            if(!fraction[1].contains("+") && !fraction[1].contains("-")){
                den.add(fraction[1]);
                break;
            }
            if(fraction[1].charAt(i) == '+' || fraction[1].charAt(i) == '-'){
                den.add(fraction[1].substring(lastIndexDen+1, i));
                den.add(Character.toString(fraction[1].charAt(i)));
                lastIndexDen=i;
            }
            if(i==fraction[1].length()-1) den.add(fraction[1].substring(lastIndexDen+1));
        }//puts individual terms into ArrayList (den)
        
        String numHighEx="1";//highset exponeent
        String termHigh=""; //highest term
        for(String s : num){
            String tempEx="";//exponent of the current term
            if(s.contains("^")) tempEx=s.substring(s.indexOf("^")+2, s.length()-1);//store the exponent value
            else tempEx="1";
            
            //if only one term
            if(num.size()==1){
                if(tempEx.indexOf("x")==0) tempEx="1x";//need to add the coefficient for later algorithm
                numHighEx=tempEx;
                termHigh=num.get(0);
                break;
            }
            
            //exponential, which beats power
            if(tempEx.contains("x")){
                if(tempEx.indexOf("x")==0) tempEx="1x";
                if(numHighEx.contains("x")){
                    if(Double.parseDouble(tempEx.substring(0,tempEx.indexOf("x"))) > Double.parseDouble(numHighEx.substring(0,numHighEx.indexOf("x")))){
                        //coefficient is bigger than the old high
                        numHighEx=tempEx;
                        termHigh=s;
                    }
                }
                else{
                    numHighEx=tempEx;
                    termHigh=s;
                }
            }//if the exponent contains an x
            
            //not exponential, just power
            else{
                if(Double.parseDouble(tempEx) > Double.parseDouble(numHighEx)){
                    numHighEx=tempEx;
                    termHigh=s;
                }
            }//if the exponent is just a number
        }//finds the highest term in the numerator
        
        String denHighEx="1";
        String denTerm="";
        for(String s : den){
            String tempEx="";
            if(s.contains("^")) tempEx=s.substring(s.indexOf("^")+2, s.length()-1);
            else tempEx="1";
            
            if(den.size()==1){
                if(tempEx.indexOf("x")==0) tempEx="1x";
                denHighEx=tempEx;
                denTerm=den.get(0);
                break;
            }
                    
            if(tempEx.contains("x")){
                if(tempEx.indexOf("x")==0) tempEx="1x";
                if(denHighEx.contains("x")){
                    if(Double.parseDouble(tempEx.substring(0,tempEx.indexOf("x"))) > Double.parseDouble(denHighEx.substring(0,denHighEx.indexOf("x")))){
                        denHighEx=tempEx;
                        denTerm=s;
                    }
                }
                else{
                    denHighEx=tempEx;
                    denTerm=s;
                }
            }//if the exponent contains an x
            
            else{
                if(Double.parseDouble(tempEx) > Double.parseDouble(denHighEx)){
                    denHighEx=tempEx;
                    denTerm=s;
                }         
            }//if the exponent is just a number
        }//finds the highest term in the denominator
        
        if(fraction[0].indexOf(termHigh)>0){
            if(fraction[0].charAt(fraction[0].indexOf(termHigh)-1) == '-') termHigh="-"+termHigh;
        }//if the highest term in the numerator is negative, a negative sign is added
        if(fraction[1].indexOf(denTerm)>0){
            if(fraction[1].charAt(fraction[1].indexOf(denTerm)-1) == '-') denTerm="-"+denTerm;
        }//if the highest term in the denominator is negative, a negative sign is added
        System.out.println("highest numerator term is " + termHigh);
        System.out.println("highest denominator term is " + denTerm);

        //compare highest terms
        String numBase = termHigh.substring(0, termHigh.indexOf("^"));
        String denBase = denTerm.substring(0, denTerm.indexOf("^"));
        if(numBase.contains("x") && !denBase.contains("x")){
            //numerator is power, denom is exponential
            return "0";
        }
        else if(!numBase.contains("x") && denBase.contains("x")){
            //numerator is exponential, denom is power
            if(numBase.contains("-") && denBase.contains("-")) return "infinity";//both negative
            if(numBase.contains("-") || denBase.contains("-")) return "negative infinity";//numerator is negative, denom is pos
            return "infinity";//both pos

        }

        //both power, compare exponents then bases
        else if(numBase.contains("x") && denBase.contains("x")){

            //numerator is greater than denominator
            if(Double.parseDouble(numHighEx) > Double.parseDouble(denHighEx)){
                if(numBase.contains("-") && denBase.contains("-")) return "infinity";//both negative
                if(numBase.contains("-") || denBase.contains("-")) return "negative infinity";//numerator is negative, denom is pos
                return "infinity";//both pos
            }
            //denominator is greater than numerator
            else if(Double.parseDouble(numHighEx) < Double.parseDouble(denHighEx)){
                return "0";
            }
            //equal powers, compare base
            else{
                //return the quotient of coefficients
                return "" + Double.parseDouble(numBase)/Double.parseDouble(denBase);
            }
        }
        
        //both exponential, compare bases then exponents
        else{
            System.out.println("numex is " + numHighEx + " and denheigh ex is " + denHighEx);
            double numBaseUpdated = Math.pow(Double.parseDouble(numBase), Double.parseDouble(numHighEx.substring(0, numHighEx.length()-1)));
            double denBaseUpdated = Math.pow(Double.parseDouble(denBase), Double.parseDouble(denHighEx.substring(0, denHighEx.length()-1)));
            
            //numerator is greater than denominator
            if(Math.abs(numBaseUpdated) > Math.abs(denBaseUpdated)){
                if(numBase.contains("-") && denBase.contains("-")) return "infinity";//both negative
                if(numBase.contains("-") || denBase.contains("-")) return "negative infinity";//numerator is negative, denom is pos
                return "infinity";//both pos
            }

            //numerator is less than denominator
            else if(Math.abs(numBaseUpdated) < Math.abs(denBaseUpdated)){
                return "0";
            }
            //they are equal, compare exponents
            else{
                double numExCoeff = Double.parseDouble(numHighEx.substring(0, numHighEx.indexOf("x")));
                double denExCoeff = Double.parseDouble(denHighEx.substring(0, denHighEx.indexOf("x")));
                if(numExCoeff > denExCoeff){
                    //compare base signs again
                    if(numBase.contains("-") && denBase.contains("-")) return "infinity";//both negative
                    if(numBase.contains("-") || denBase.contains("-")) return "negative infinity";//numerator is negative, denom is pos
                    return "infinity";//both pos
                }
                else if(numExCoeff < denExCoeff){
                    return "0";
                }
                else{
                    return "" + Double.parseDouble(numBase)/Double.parseDouble(denBase); 
                }
            }
        }
    }//limitToInfinity
    
    
    public static double limitAtValue(String function, String value){
        double ans = Double.NaN;
        while(Double.isNaN(ans)){

            ans = Function.solveFunction(function, "" + value);

            ArrayList<Pair> pairs = parentheses.findParentheses(function);

            int slashIndex = function.indexOf("/");
            boolean numTrue = false, denTrue = false;

            //check to see whether l'hospitals is valid
            for(Pair p : pairs){
                //set of parentheses that encompasses the numerator
                if(p.second == slashIndex-1 && p.first == 0){
                    numTrue = true;
                }
                //set of parentheses that ecompasses the denominator
                if(p.first == slashIndex+1 && p.second == function.length()-1){
                    denTrue = true;
                }
            }

            //L'Hospitals is valid
            if(denTrue && numTrue){
                String[] fraction = function.split("/");//separates numerator and denominator
                fraction[0] = Derivatives.Derivative(fraction[0].substring(1, fraction[0].length()-1));
                fraction[1] = Derivatives.Derivative(fraction[1].substring(1, fraction[1].length()-1));
                function = "(" + fraction[0] + ")/(" + fraction[1] + ")";
            }
        }
        return ans;
    }
    public static String limit(String f, String value){
        if(value.equals("infinity")){
            return limitToInfinity(f);
        }
        else{
            return "" + limitAtValue(f, value);
        }

    }
    public static void main(String[] args){
        //System.out.println(limitAtValue("(3x+2x^(2))/(x^(2))", 0));   //parentehs must be added when solveFunction replaces x with *x
        /*System.out.println(limit("3x", "2"));
        System.out.println(limit("(3x+sin(3x))/(2x)", "0"));
        System.out.println(limit("(3x+3)/(2x)", "_2"));
        System.out.println(limit("_3+3*sin(x)", "3.1415/2"));*/
        //System.out.println(limit("(2x^(1))/(x^(2))", "infinity"));
        System.out.println(limit("(x^(2))/(7x^(3))", "0"));
        //when entering negatives at the start, like -3+3x, the negative must be written as _ instaed of -
    }
}