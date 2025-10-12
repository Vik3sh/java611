
package MST;

public class ProdManager {
    public static void main(String[] args){
        Product prod=Product.getProductData();
       if(prod!=null){
        prod.displayProductDetails();
        
       }
       
        

    }
}