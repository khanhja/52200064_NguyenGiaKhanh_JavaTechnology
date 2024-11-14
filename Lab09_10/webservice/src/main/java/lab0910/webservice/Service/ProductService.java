package lab0910.webservice.Service;

import lab0910.webservice.Model.Product;
import lab0910.webservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product newProduct) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setCode(newProduct.getCode());
                    product.setIllustration(newProduct.getIllustration());
                    product.setDescription(newProduct.getDescription());
                    return productRepository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return productRepository.save(newProduct);
                });
    }

    public Product partialUpdateProduct(Long id, Product partialUpdate) {
        return productRepository.findById(id)
                .map(product -> {
                    if (partialUpdate.getName() != null) product.setName(partialUpdate.getName());
                    if (partialUpdate.getPrice() != 0) product.setPrice(partialUpdate.getPrice());
                    return productRepository.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
