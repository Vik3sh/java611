// Problem Statement 
// You are developing a system for a store where products are stored with numeric codes for name 
// and ID. The goal is to create a Product class that can initialize and display product details using 
// numeric input only 
// Create a Java class named Product with the following numeric attributes: 
// id (int) unique identifier for the product 
// nameCode (int) numeric representation of the product name 
// price (double) product price 
// Implement a constructor to initialize these attributes, and a method displayProductDetails() to 
// print them. 
 
// Input Format 
// Product ID (integer) 
// Product Name Code (integer) 
// Product Price (double) 
// Output Format 
// Print the product details: 
// Product ID 
// Product Name Code 
// Product Price 
// Constraints 
// ID >0 
// Price ≥ 0 
// Name Code is a numeric representation (simulated name lookup) 
// Sample Test Case 1 
// Input: 
// 101 
// 2001 
// 55000 
// Output: 
// Product ID: 101 
// Product Name Code: 2001 
// Product Price: ₹55000.0 
// Explanation: 
// Product ID: 101- This is the unique ID assigned to the product. 
// Product Name Code: 2001- This numeric code acts as a placeholder for the product's name in 
// the system (like a code for "Laptop" or "TV"). 
// Product Price: ₹55000.0 - This shows the product's price; printed as a double, so it includes a 
// decimal point. 
package MST;
import java.util.*;


public class Product {
    private int productId;
    private int nameCode;
    private double price;

    public Product(int productId, int nameCode, double price){
        this.productId = productId;
        this.nameCode= nameCode;
        this.price=price;

    }
    
    public static Product getProductData(){
        Scanner sc= new Scanner(System.in);
        System.out.println("enter the product id: ");
        int productId=sc.nextInt();sc.nextLine();
        if(productId<=0){
            System.out.println("invalid product id");
            sc.close();
            return null;
        }

        System.out.println("enter the product name code: ");
        int nameCode=sc.nextInt();sc.nextLine();
        System.out.println("enter price of product: ");
        double price=sc.nextDouble();sc.nextLine();
        if(price<0){
            System.out.println("invalid price");
            sc.close();
            return null;
        }
        sc.close();
        return new Product(productId, nameCode, price);
    }

    
    public void displayProductDetails(){
        System.out.println("Product ID: "+productId);
        System.out.println("Product Name Code: "+nameCode);
        System.out.println("Product Price: "+"₹"+price);
        
    }


    

}

