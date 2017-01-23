package com.example.a3_pc.complex_calculator;
import java.lang.Math;

public class complex {
    public double real;
    public double imaginary;
    public double modulus;
    public double angle;

    // Default constructor of a complex number
    complex() {
        this.real = Double.NaN;
        this.imaginary = Double.NaN;
        this.modulus = Double.NaN;
        this.angle = Double.NaN;
    }

    // Determines the string's form and constructs a
    //  complex number based on the string's form
    // Valid string format:
    // x+yi
    // yi
    // x
    // xe^yi
    // e^yi
    // e^i
    // r(cos(x) + isin(x))
    complex(String str) {
        str = str.replaceAll(" ", "");
        if(str.contains("e^")) { // If in exponential form
            complex exp = splitE(str);
            this.real = exp.real;
            this.imaginary = exp.imaginary;
            this.angle = exp.angle;
            this.modulus = exp.modulus;
        }
        else if (str.contains("sin")) {
            complex polar = splitP(str);
            this.real = polar.real;
            this.imaginary = polar.imaginary;
            this.angle = polar.angle;
            this.modulus = polar.modulus;
        }
        else { // If in rectangular form
            complex rec = splitC(str);
            this.real = rec.real;
            this.imaginary = rec.imaginary;
            this.modulus = Math.sqrt(rec.real*rec.real + rec.imaginary*rec.imaginary);
            this.angle = Math.asin(rec.imaginary/rec.modulus);
        }
    }

    // Calculates the index of the last operator in the string to find operator
    //  in between the real and imaginary value
    private static int lastOPindex(String str) {
        int i = str.indexOf('+');
        int s = str.indexOf('-');
        while(i >= 0) {
            if(-1 == str.indexOf('+', i+1))
                break;
            else
                i = str.indexOf('+', i+1);
        }
        while(s >= 0) {
            if(-1 == str.indexOf('-', s+1))
                break;
            else
                s = str.indexOf('-', s+1);
        }
        int middle = Math.max(i, s);
        return middle;
    }

    // Checks if string in rectangular form contains both
    //  real and imaginary values
    private static boolean isBinary(String str) {
        if(str.contains("+") || str.contains("-")) {
            int middle = lastOPindex(str);
            if(str.substring(0, middle).matches("-?\\d+(\\.\\d+)?")
                    && str.substring(middle+1).contains("i"))
                return true;
        }
        return false;
    }

    // Splits the real and imaginary values of the string in rectangular form
    public static complex splitC(String str) {
        complex num = new complex();
        String real = "";
        String imaginary = "";
        if(isBinary(str)) {
            int i = lastOPindex(str);
            real = str.substring(0, i);
            imaginary = str.substring(i).replaceAll("i", "");
        }
        else if(!str.contains("i")) {
            real = str;
            imaginary = "0";
        }
        else if(!str.contains("+") && str.contains("i")) {
            real = "0";
            str = str.replaceAll("i", "");
            if(str.isEmpty())
                imaginary = "1";
            else
                imaginary = str;
        }
        if(imaginary.equals("-"))
            imaginary = "-1";
        else if(imaginary.equals("+"))
            imaginary = "1";
        num.real = Double.parseDouble(real);
        num.imaginary = Double.parseDouble(imaginary);
        return num;
    }

    // Splits the real and imaginary values of the string in exponential form
    public static complex splitE(String str) {
        int i = str.indexOf("e^");
        String r = str.substring(0, i);
        double modulus;
        if(r.isEmpty())
            modulus = 1;
        else
            modulus = Double.parseDouble(r);
        String angle = str.substring(i+2, str.length()-1);
        double ang;
        if(angle.isEmpty())
            ang = 1;
        else
            ang = Double.parseDouble(angle);
        complex ans = new complex();
        double length = Double.parseDouble(r);
        ans.real = modulus * Math.cos(ang);
        ans.imaginary = modulus * Math.sin(ang);
        ans.angle = ang;
        ans.modulus = length;
        return ans;
    }

    // Splits the real and imaginary values of the string in polar form
    public static complex splitP(String str) {
        int i = str.indexOf("(");
        String r = str.substring(0, i);
        complex ans = new complex();
        ans.modulus = Double.parseDouble(r);
        int open = str.indexOf("cos(");
        int closing = str.indexOf(")");
        double angle = Double.parseDouble(str.substring(open+4, closing));
        ans.angle = angle;
        ans.real = Math.cos(angle) * ans.modulus;
        ans.imaginary = Math.sin(angle) * ans.modulus;
        return ans;
    }

    // The Addition method for complex numbers
    public static complex add(complex v1, complex v2) {
        complex ans = new complex();
        ans.real = v1.real + v2.real;
        ans.imaginary = v1.imaginary + v2.imaginary;
        ans.modulus = Math.sqrt(ans.real*ans.real + ans.imaginary*ans.imaginary);
        ans.angle = Math.asin(ans.imaginary/ans.modulus);
        return ans;
    }

    // The Subtraction method for complex numbers
    public static complex subtract(complex v1, complex v2) {
        complex ans = new complex();
        ans.real = v1.real - v2.real;
        ans.imaginary = v1.imaginary - v2.imaginary;
        ans.modulus = Math.sqrt(ans.real*ans.real + ans.imaginary*ans.imaginary);
        ans.angle = Math.asin(ans.imaginary/ans.modulus);
        return ans;
    }

    // The Multiplication method for complex numbers
    public static complex multiply(complex v1, complex v2) {
        complex ans = new complex();
        ans.real = (v1.real * v2.real) - (v1.imaginary * v2.imaginary);
        ans.imaginary = (v1.real * v2.imaginary) + (v2.real * v1.imaginary);
        ans.modulus = Math.sqrt(ans.real*ans.real + ans.imaginary*ans.imaginary);
        ans.angle = Math.asin(ans.imaginary/ans.modulus);
        return ans;
    }

    // The Division method for complex numbers
    public static complex divide(complex v1, complex v2) {
        complex ans = new complex();
        complex numerator = new complex();
        complex conjugate = v2;
        conjugate.imaginary *= -1;
        numerator = multiply(v1, conjugate);
        ans.real = numerator.real / (v2.real * v2.real + v2.imaginary * v2.imaginary);
        ans.imaginary = numerator.imaginary / (v2.real * v2.real + v2.imaginary * v2.imaginary);
        ans.modulus = Math.sqrt(ans.real*ans.real + ans.imaginary*ans.imaginary);
        ans.angle = Math.asin(ans.imaginary/ans.modulus);
        return ans;
    }

    // Converts a complex number to a string based on the given form
    public static String to_String(complex v, String form) {
        String ans = "";
        if(form.equals("Rectangular")) {
            if(v.imaginary >= 0)
                ans = String.valueOf(v.real) + "+" + String.valueOf(v.imaginary) + "i";
            else if(v.imaginary < 0)
                ans = String.valueOf(v.real) + String.valueOf(v.imaginary) + "i";
        }
        else if(form.equals("Exponential")) {
            ans = String.valueOf(v.modulus) + "e^(" + String.valueOf(v.angle) + "i)";
        }
        else if(form.equals("Polar")) {
            ans = String.valueOf(v.modulus) + "(cos(" + String.valueOf(v.angle) + ")"
                    + " + isin(" + String.valueOf(v.angle) + "))";
        }
        else
            ans = "ERROR: Complex Form not selected";
        return ans;

    }
}