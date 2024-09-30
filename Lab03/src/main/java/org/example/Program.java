package org.example;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        testManufacture();
        //testPhone();
    }

    public static void testPhone() {
        PhoneDAO pDAO = new PhoneDAO();
        ManufactureDAO mfDAO = new ManufactureDAO();

        // create a reference
        Manufacture mf = mfDAO.createIfNotExist();

        // add
        Phone p = new Phone();
        p.setName("iPhone 12 Pro");
        p.setColor("Gold");
        p.setCountry("Viet Nam");
        p.setPrice(5000);
        p.setQuantity(100);
        p.setManufacture(mf); // reference
        pDAO.add(p); // insert query

        Phone p2 = new Phone();
        p2.setName("iPhone 15 Pro Max");
        p2.setColor("Natural Titanium");
        p2.setCountry("Viet Nam");
        p2.setPrice(50001000);
        p2.setQuantity(120);
        p2.setManufacture(mf); // reference
        pDAO.add(p2); // insert query

        Phone p3 = new Phone();
        p3.setName("Samsung Galaxy S23 Ultra");
        p3.setColor("Phantom Black");
        p3.setCountry("South Korea");
        p3.setPrice(13000);
        p3.setQuantity(200);
        p3.setManufacture(mf);
        pDAO.add(p3);

        Phone p4 = new Phone();
        p4.setName("Google Pixel 8 Pro");
        p4.setColor("Pink");
        p4.setCountry("USA");
        p4.setPrice(16000000);
        p4.setQuantity(150);
        p4.setManufacture(mf);
        pDAO.add(p4);


        // get the highest selling price phone
        System.out.println("The highest selling price phone:\n" + pDAO.getHighestPrice());

        // get a list of phones sorted by country name
        System.out.println("A list of phones sorted by country name:");
        List<Phone> phones = pDAO.listSortedByCountry();
        phones.forEach(System.out::println);

        // check if there's a phone priced above 50 million VNĐ
        if (pDAO.above50Million())
            System.out.println("There exists a phone priced above 50 million VNĐ");
        else
            System.out.println("There's no phone priced above 50 million VNĐ");

        // get the first phone has the color 'Pink' and costs over 15 million
        p = pDAO.getPinkAndOver15Million();
        if (p != null)
            System.out.println("Pink and Over 15 million:\n" + p);
        else
            System.out.println("No Pink and Over 15 million");

/*
        // get
        p = pDAO.get("P001");
        System.out.println(p);

        //get all
        List<Phone> phones = pDAO.getAll();
        phones.forEach(System.out::println);

        // remove by id
        if (pDAO.remove("P001"))
            System.out.println("Removed P001");
        else
            System.out.println("Failed to remove P001");

        // remove by object
        if (pDAO.remove(p2))
            System.out.println("Removed P002");
        else
            System.out.println("Failed to remove P002");

        // update
        Phone p3 = new Phone();
        p3.setName("Macbook Pro M1");
        p3.setCountry("Viet Nam");
        p3.setPrice(1000);
        p3.setQuantity(100);
        p3.setColor("White");
        p3.setManufacture(mf); // reference
        pDAO.add(p3);

        p3.setName("iPhone XS Max");
        pDAO.update(p3);
        System.out.print(p3);

        phones = pDAO.getAll();
        phones.forEach(System.out::println);

  */
        pDAO.close(); // close session
    }

    public static void testManufacture() {
        ManufactureDAO mfDAO = new ManufactureDAO();

        // add
        Manufacture mf = new Manufacture();
        mf.setName("Apple");
        mf.setLocation("Viet Nam");
        mf.setEmployee(1000);
        mfDAO.add(mf);

        Manufacture mf2 = new Manufacture();
        mf2.setName("Samsung");
        mf2.setLocation("Viet Nam");
        mf2.setEmployee(1000);
        mfDAO.add(mf2);

        Manufacture mf3 = new Manufacture();
        mf3.setName("Goole");
        mf3.setLocation("Viet Nam");
        mf3.setEmployee(10);
        mfDAO.add(mf3);

        // check if all manufactures have more than 100 employees
        if (mfDAO.haveMoreThan100Emp())
            System.out.println("All manufactures have more than 100 employees");
        else
            System.out.println("Not all manufactures have more than 100 employees");

        // sum of all employees of the manufactures
        System.out.println("Sum of all employees of the manufactures: " + mfDAO.sumOfAllEmployees());

        // get the last manufacturer in the list of manufacturers that based in the US
        try {
            System.out.println("The last manufacture based in the US:\n" + mfDAO.lastManufactureBasedInUS());
        } catch (InvalidOperationException e) {
            System.out.println(e.getMessage());
        }
/*
        // get
        mf = mfDAO.get("MF001");
        System.out.println("------------------\n" + mf);

        // get all
        List<Manufacture> mfs = mfDAO.getAll();
        mfs.forEach(System.out::println);

        // remove by id
        if (mfDAO.remove("MF001"))
            System.out.println("Removed MF001");
        else
            System.out.println("Failed to remove MF001");

        // remove by object
        if (mfDAO.remove(mf2))
            System.out.println("Removed MF002");
        else
            System.out.println("Failed to remove MF002");

        // update
        Manufacture mf3 = new Manufacture();
        mf3.setName("Google");
        mf3.setLocation("Viet Nam");
        mf3.setEmployee(1000);
        mfDAO.add(mf3);

        mf3.setEmployee(2000);
        mfDAO.update(mf3);
        System.out.print(mf3);

        mfs = mfDAO.getAll();
        mfs.forEach(System.out::println);

 */

        mfDAO.close(); // close session
    }
}
