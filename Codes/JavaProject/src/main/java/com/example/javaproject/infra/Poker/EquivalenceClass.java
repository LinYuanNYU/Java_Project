package com.example.javaproject.infra.Poker;


import java.util.List;

public abstract class EquivalenceClass {
    private final Integer number1, number2;

    public EquivalenceClass(final Integer number1, final Integer number2) {
        this.number1 = number1;
        this.number2 = number2;
    }

    public Integer getNumber1() {
        return number1;
    }

    public Integer getNumber2() {
        return number2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((number1 == null) ? 0 : number1.hashCode());
        result = prime * result + ((number2 == null) ? 0 : number2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EquivalenceClass other = (EquivalenceClass) obj;
        if (number1 != other.number1)
            return false;
        if (number2 != other.number2)
            return false;
        return (number1 == other.number1 && number2 == other.number2)
                || (number1 == other.number2 && number2 == other.number1);
    }

    public abstract List<Card> equivalence2cards();

    public abstract String getType();

    @Override
    public String toString() {
        return "Equivalence Class (" + getNumber1() + ","
                + getNumber2() + ")";
    }

}