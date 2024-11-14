package lab0910.webservice.Controller;

import lab0910.webservice.Model.Product;
import lab0910.webservice.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET: returns a list of all products in the system
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // POST: Add a new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // GET: Returns detailed information of a product based on id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PUT: Replace the entire product with new data based on its id
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setCode(productDetails.getCode());
                    product.setName(productDetails.getName());
                    product.setPrice(productDetails.getPrice());
                    product.setIllustration(productDetails.getIllustration());
                    product.setDescription(productDetails.getDescription());
                    productRepository.save(product);
                    return ResponseEntity.ok(product);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // PATCH: Update some information of a product based on its id
    @PatchMapping("/{id}")
    public ResponseEntity<Product> partialUpdateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    if (productDetails.getCode() != null) product.setCode(productDetails.getCode());
                    if (productDetails.getName() != null) product.setName(productDetails.getName());
                    if (productDetails.getPrice() != 0) product.setPrice(productDetails.getPrice());
                    if (productDetails.getIllustration() != null) product.setIllustration(productDetails.getIllustration());
                    if (productDetails.getDescription() != null) product.setDescription(productDetails.getDescription());
                    productRepository.save(product);
                    return ResponseEntity.ok(product);
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE: Delete products based on id
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}