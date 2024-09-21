import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        if (args.length < 3)
            System.out.print("Please follow this format of argument: <connection url> <username> <password>");
            // Sample: "jdbc:mysql://localhost:3306/" root 1234567890
        else {
            String url = args[0];
            String username = args[1];
            String password = args[2];

            ProductDAO dao = new ProductDAO(url, username, password);

            String menu = "1. Read all products.\n" +
                          "2. Read detail of a product by id.\n" +
                          "3. Add a new product.\n" +
                          "4. Update a product.\n" +
                          "5. Delete a product by id.\n" +
                          "6. Exit.\n" +
                          "Enter your choice: ";
            Scanner sc = new Scanner(System.in);
            int num;

            do {
                System.out.print(menu);
                num = sc.nextInt();

                switch (num) {
                    case 1:
                        List<Product> productList = dao.readAll();
                        String table = String.format("\n%-10s %-20s %-15s %-15s %-30s", "ID", "Name", "Price", "Quantity", "Description");
                        System.out.println(table);
                        productList.forEach(System.out::println);
                        System.out.println();
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.print("Enter product ID: ");
                        String id = sc.nextLine();
                        Product p = dao.read(id);
                        if (p == null)
                            System.out.println("\n------------ Product with ID " + id + " does not exist! -------------\n");
                        else {
                            table = String.format("\n%-10s %-20s %-15s %-15s %-30s", "ID", "Name", "Price", "Quantity", "Description");
                            System.out.println(table);
                            System.out.println(p + "\n");
                        }
                        break;
                    case 3:
                        sc.nextLine();
                        System.out.print("Enter product name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter product price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter product quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter product description: ");
                        String description = sc.nextLine();

                        String pID = dao.add(new Product("", name, price, quantity, description));
                        System.out.println("\n------------ A new product has been added with ID: " + pID + " ------------\n");
                        break;
                    case 4:
                        sc.nextLine();
                        System.out.print("Enter product ID: ");
                        id = sc.nextLine();

                        if (dao.read(id) == null)
                            System.out.println("\n------------ The product with ID " + id + " does not exist! ------------\n");
                        else {
                            System.out.print("Enter product name: ");
                            name = sc.nextLine();
                            System.out.print("Enter product price: ");
                            price = sc.nextDouble();
                            System.out.print("Enter product quantity: ");
                            quantity = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter product description: ");
                            description = sc.nextLine();

                            p = new Product(id, name, price, quantity, description);
                            if(dao.update(p)) {
                                System.out.println("\n------------ The product has been updated! ------------\n");
                                table = String.format("%-10s %-20s %-15s %-15s %-30s", "ID", "Name", "Price", "Quantity", "Description");
                                System.out.println(table);
                                System.out.println(p + "\n");
                            }
                        }
                        break;
                    case 5:
                        sc.nextLine();
                        System.out.print("Enter product ID: ");
                        id = sc.nextLine();
                        if (dao.read(id) == null)
                            System.out.println("\n------------ The product with ID " + id + " does not exist! ------------\n");
                        else
                            if(dao.delete(id))
                                System.out.println("\n------------ The product with ID " + id + " has been deleted ------------\n");
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        break;
                }
            } while (num != 6);
        }
    }
}