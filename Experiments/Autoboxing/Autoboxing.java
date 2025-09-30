package Experiments.Autoboxing;


public class Autoboxing {
    public static void main(String[] args){
        
        // normal int
        int primitiveNumber = 10;
        System.out.println("Primitive int: "+primitiveNumber);
        
        // autoboxing
       Integer wrapperNumber=primitiveNumber; // Java does this automatically!
        System.out.println("Wrapper Integer: " + wrapperNumber);
        
        // unboxing
        int backToPrimitive = wrapperNumber; // Java does this automatically!
        System.out.println("Back to primitive: " + backToPrimitive);
        
        // using both together
        int sum = primitiveNumber + wrapperNumber;
        System.out.println("Sum of both: " + sum);
        
        // show if they are the same value
        System.out.println("Are they equal? " + (primitiveNumber == wrapperNumber));
    }   
}
