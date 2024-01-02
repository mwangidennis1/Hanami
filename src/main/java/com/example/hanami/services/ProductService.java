package com.example.hanami.services;

import com.example.hanami.model.Product;
import com.example.hanami.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository){

        this.productRepository=productRepository;
    }
    public List<Product> listAll(){
        return productRepository.findAll();
    }
    public void save(Product product){
        productRepository.save(product);
    }
    public Product get(long id){
        return productRepository.findById(id).get();
    }
    public void delete(long id){
        productRepository.deleteById(id);
    }
    public Product editProduct(Product newProduct,long id){
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setBrand(newProduct.getBrand());
                    product.setMadein(newProduct.getMadein());
                    product.setPrice(newProduct.getPrice());
                    return  productRepository.save(product);
                }).orElseGet(
                        ()->{
                            newProduct.setId(id);
                            return productRepository.save(newProduct);
                        }
                );
    }

}
