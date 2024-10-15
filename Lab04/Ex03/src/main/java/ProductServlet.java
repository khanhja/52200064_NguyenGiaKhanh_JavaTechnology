import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private List<Product> productList = new ArrayList<>();

    @Override
    public void init() throws ServletException {
        // Create sample products
        productList.add(new Product(1, "iPhone 12 Pro", 12500));
        productList.add(new Product(2, "Macbook Air M1", 23640));
        productList.add(new Product(3, "Macbook Pro M1", 32000));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            // Return list of all products
            response.getWriter().write(createJsonResponse(0, "Danh sách sản phẩm", productList));
        } else {
            // Return specific product with ID in argument
            try {
                int id = Integer.parseInt(idParam);
                Product foundProduct = productList.stream()
                        .filter(product -> product.getId() == id)
                        .findFirst()
                        .orElse(null);

                if (foundProduct != null) {
                    response.getWriter().write(createJsonResponse(0, "Sản phẩm tìm thấy", foundProduct));
                } else {
                    response.getWriter().write(createJsonResponse(1, "Không tìm thấy sản phẩm với mã " + id, null));
                }
            } catch (NumberFormatException e) {
                response.getWriter().write(createJsonResponse(1, "ID không hợp lệ", null));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Get new product from client
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        Product newProduct = parseJsonToProduct(jsonString);

        // Validate data
        if (isProductValid(newProduct)) {
            // Check if ID does exist
            if (productList.stream().noneMatch(product -> product.getId() == newProduct.getId())) {
                productList.add(newProduct);
                response.getWriter().write(createJsonResponse(0, "Sản phẩm đã được thêm", null));
            } else {
                response.getWriter().write(createJsonResponse(1, "ID sản phẩm đã tồn tại", null));
            }
        } else {
            response.getWriter().write(createJsonResponse(1, "Dữ liệu sản phẩm không hợp lệ", null));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = request.getReader().readLine()) != null) {
            sb.append(line);
        }
        String jsonString = sb.toString();
        Product updatedProduct = parseJsonToProduct(jsonString);

        // Validate data
        if (isProductValid(updatedProduct)) {
            boolean found = false;
            for (Product product : productList) {
                if (product.getId() == updatedProduct.getId()) {
                    product.setName(updatedProduct.getName());
                    product.setPrice(updatedProduct.getPrice());
                    response.getWriter().write(createJsonResponse(0, "Sản phẩm đã được cập nhật", null));
                    found = true;
                    break;
                }
            }
            if (!found) {
                response.getWriter().write(createJsonResponse(1, "Không tìm thấy sản phẩm với mã " + updatedProduct.getId(), null));
            }
        } else {
            response.getWriter().write(createJsonResponse(1, "Dữ liệu sản phẩm không hợp lệ", null));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        String idParam = request.getParameter("id");

        // Check if ID is not be specified
        if (idParam == null || idParam.isEmpty()) {
            response.getWriter().write(createJsonResponse(1, "Cần chỉ định ID sản phẩm", null));
            return;
        }

        try {
            int id = Integer.parseInt(idParam);
            boolean removed = productList.removeIf(product -> product.getId() == id);

            if (removed) {
                response.getWriter().write(createJsonResponse(0, "Sản phẩm đã được xóa", null));
            } else {
                response.getWriter().write(createJsonResponse(1, "Không tìm thấy sản phẩm với mã " + id, null));
            }
        } catch (NumberFormatException e) {
            response.getWriter().write(createJsonResponse(1, "ID không hợp lệ", null));
        }
    }

    private boolean isProductValid(Product product) {
        return product.getId() > 0 && product.getName() != null && !product.getName().isEmpty() && product.getPrice() >= 0;
    }

    private String createJsonResponse(int errorCode, String message, Object data) {
        StringBuilder jsonResponse = new StringBuilder();
        jsonResponse.append("{\n");
        jsonResponse.append("  \"id\": ").append(errorCode).append(",\n");
        jsonResponse.append("  \"message\": \"").append(message).append("\"");
        if (data != null) {
            jsonResponse.append(",\n  \"data\": ").append(dataToJson(data));
        }
        jsonResponse.append("\n}");
        return jsonResponse.toString();
    }

    private String dataToJson(Object data) {
        if (data instanceof Product) {
            Product product = (Product) data;
            return String.format("{\n    \"id\": %d,\n    \"name\": \"%s\",\n    \"price\": %.2f\n  }",
                    product.getId(), product.getName(), product.getPrice());
        } else if (data instanceof List) {
            List<Product> products = (List<Product>) data;
            StringBuilder jsonList = new StringBuilder("[\n");
            for (int i = 0; i < products.size(); i++) {
                jsonList.append("  ").append(dataToJson(products.get(i)));
                if (i < products.size() - 1) {
                    jsonList.append(",\n");
                }
            }
            jsonList.append("\n]");
            return jsonList.toString();
        }
        return null;
    }

    private Product parseJsonToProduct(String jsonString) {
        String[] parts = jsonString.replaceAll("[{}\"]", "").split(",");
        int id = 0;
        String name = null;
        double price = 0.0;

        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2) {
                switch (keyValue[0].trim()) {
                    case "id":
                        id = Integer.parseInt(keyValue[1].trim());
                        break;
                    case "name":
                        name = keyValue[1].trim();
                        break;
                    case "price":
                        price = Double.parseDouble(keyValue[1].trim());
                        break;
                }
            }
        }
        return new Product(id, name, price);
    }
}