package com.example.hanami.controllers;
/*
@author Dennis
@email mwangedennis05@gmail.com
 */
import com.example.hanami.services.ProductService;
import com.example.hanami.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*
@Contributor  Anyango Tate
For the people realising the system is broken
 */
import java.util.List;
import java.util.logging.Logger;

/*After spring security implementation cors will be like the appendix */
@RestController
@CrossOrigin(origins="http://localhost:3000")
public class App2Controller {
    private Logger logger=Logger.getLogger(App2Controller.class.getName());
    @Autowired
    private final ProductService productService;

    public App2Controller(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/products")
    public List<Product> productsList(){
        return productService.listAll();

    }
    //an processsing
    @GetMapping("products/{id}")
    public Product one(@PathVariable Long id){
            return productService.get(id);

          }
    @PostMapping("/newproduct")
    public void newProduct(@RequestBody Product newproduct){
          productService.save(newproduct);
    }
    @DeleteMapping("/deleteproduct/{id}")
    public void deleteAProduct(@PathVariable Long id){
        productService.delete(id);
    }
    @PutMapping("/edititem/{id}")
    public void editItem(@RequestBody Product product,@PathVariable Long id){
        productService.editProduct(product,id);
    }
}
