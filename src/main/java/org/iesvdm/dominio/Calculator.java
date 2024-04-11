package org.iesvdm.dominio;

public class Calculator {

    public int add(int a, int b) {
        return a+b;
    }

    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public long squareLong(long l) {
        return l*l;
    }
}
