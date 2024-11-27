import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVUtils {

    public static void appendProductToCSV(String filename, Product product) {
        try {
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                if (!fileExists) {
                    bw.write("productId,ownerId,productType,productName," +
                             "productDesc,specificField,isAvailable");
                }

                bw.newLine();

                bw.write(product.toCSVString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to products CSV: " + e.getMessage());
        }
    }

    public static void appendUserToCSV(String filename, AuthenticatedStudent user) {
        try {
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                if (!fileExists) {
                    bw.write("userId,name,email,password,bananaScore");
                }

                bw.newLine();

                bw.write(user.getUserId() + "," + user.getUserName() + "," +
                         user.getUserEmail() + "," + user.getPassword() + "," +
                         user.scoreManager.getScore());
            }
        } catch (IOException e) {
            System.out.println("Error writing to users CSV: " + e.getMessage());
        }
    }

    public static void appendUserProductToCSV(String filename, int userId, int productId) {
        try {
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                if (!fileExists) {
                    bw.write("userId,productId");
                }

                bw.newLine();

                bw.write(userId + "," + productId);
            }
        } catch (IOException e) {
            System.out.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }

    public static Product getProductById(String filename, int productId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    int currentProductId = Integer.parseInt(fields[0].trim());
                    if (currentProductId == productId) {
                        String productType = fields[2].trim();
                        String productName = fields[3].trim();
                        String productDesc = fields[4].trim();
                        String specificField = fields[5].trim();
                        boolean isAvailable = Boolean.parseBoolean(fields[6].trim());

                        AuthenticatedStudent owner = (AuthenticatedStudent) UserManager.getInstance().getUserById(Integer.parseInt(fields[1].trim()));
                        if (owner == null) {
                            return null;
                        }

                        Product product = ProductFactory.createProduct(productType, productId, owner, productName, productDesc, specificField);
                        if (product != null) {
                            product.setAvailable(isAvailable);
                            return product;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products CSV: " + e.getMessage());
        }
        return null;
    }

    public static Product getProductByName(String filename, String productName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    String currentProductName = fields[3].trim();
                    if (currentProductName.equalsIgnoreCase(productName)) {
                        int productId = Integer.parseInt(fields[0].trim());
                        String productType = fields[2].trim();
                        String productDesc = fields[4].trim();
                        String specificField = fields[5].trim();
                        boolean isAvailable = Boolean.parseBoolean(fields[6].trim());

                        AuthenticatedStudent owner = (AuthenticatedStudent) UserManager.getInstance().getUserById(Integer.parseInt(fields[1].trim()));
                        if (owner == null) {
                            return null;
                        }

                        Product product = ProductFactory.createProduct(productType, productId, owner, currentProductName, productDesc, specificField);
                        if (product != null) {
                            product.setAvailable(isAvailable);
                            return product;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading products CSV: " + e.getMessage());
        }
        return null;
    }

    public static void updateProductAvailabilityInCSV(String filename, int productId, boolean isAvailable) {
        File inputFile = new File(filename);
        File tempFile = new File("products_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean firstLine = true;

            while ((currentLine = reader.readLine()) != null) {
                if (firstLine) {
                    writer.write(currentLine);
                    writer.newLine();
                    firstLine = false;
                    continue;
                }

                if (currentLine.trim().isEmpty()) {
                    continue;
                }

                String[] fields = currentLine.split(",");

                if (fields.length < 7) {
                    System.out.println("Skipping malformed line: " + currentLine);
                    continue;
                }

                int currentProductId = Integer.parseInt(fields[0].trim());

                if (currentProductId == productId) {
                    fields[6] = String.valueOf(isAvailable);
                    String updatedLine = String.join(",", fields);
                    writer.write(updatedLine);
                } else {
                    writer.write(currentLine);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating products CSV: " + e.getMessage());
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete original products CSV file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temporary products CSV file");
        }
    }
}
