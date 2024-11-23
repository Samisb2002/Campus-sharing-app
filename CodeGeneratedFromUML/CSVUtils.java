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

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file, true))) {

                if (!fileExists) {
                    // Write header if file doesn't exist or is empty
                    bw.write("productId,ownerId,productType,productName," +
                             "productDesc,specificField,isAvailable");
                }

                // Always add a newline before writing data
                bw.newLine();

                StringBuilder sb = new StringBuilder();
                sb.append(product.getProductId()).append(",");
                sb.append(product.getOwner().getUserId()).append(",");
                String productType = product.getClass()
                                            .getSimpleName().toLowerCase();
                sb.append(productType).append(",");
                sb.append(product.getName()).append(",");
                sb.append(product.getDescription()).append(",");

                if (product instanceof Service) {
                    Service service = (Service) product;
                    sb.append(service.getDuration());
                } else if (product instanceof Loan) {
                    Loan loan = (Loan) product;
                    sb.append(loan.getReturnDate());
                } else if (product instanceof Donation) {
                    Donation donation = (Donation) product;
                    sb.append(donation.getPickupLocation());
                }

                sb.append(",").append(product.isAvailable());

                bw.write(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to products CSV: " +
                               e.getMessage());
        }
    }

    public static void appendUserToCSV(String filename,
                                       AuthenticatedStudent user) {
        try {
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file, true))) {

                if (!fileExists) {
                    // Write header if file doesn't exist or is empty
                    bw.write("userId,name,email,password,bananaScore");
                }

                // Always add a newline before writing data
                bw.newLine();

                StringBuilder sb = new StringBuilder();
                sb.append(user.getUserId()).append(",");
                sb.append(user.getUserName()).append(",");
                sb.append(user.getUserEmail()).append(",");
                sb.append(user.getPassword()).append(",");
                sb.append(user.scoreManager.getScore());

                bw.write(sb.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to users CSV: " +
                               e.getMessage());
        }
    }

    public static void appendUserProductToCSV(String filename, int userId,
                                              int productId) {
        try {
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            try (BufferedWriter bw = new BufferedWriter(
                    new FileWriter(file, true))) {

                if (!fileExists) {
                    // Write header if file doesn't exist or is empty
                    bw.write("userId,productId");
                }

                // Always add a newline before writing data
                bw.newLine();

                bw.write(userId + "," + productId);
            }
        } catch (IOException e) {
            System.out.println("Error writing to " + filename + ": " +
                               e.getMessage());
        }
    }

    public static void updateProductAvailabilityInCSV(String filename,
                                                      int productId,
                                                      boolean isAvailable) {
        File inputFile = new File(filename);
        File tempFile = new File("products_temp.csv");

        try (BufferedReader reader = new BufferedReader(
                 new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(
                 new FileWriter(tempFile))) {

            String currentLine;
            boolean firstLine = true;

            while ((currentLine = reader.readLine()) != null) {
                // Skip empty lines
                if (currentLine.trim().isEmpty()) {
                    continue;
                }

                if (firstLine) {
                    writer.write(currentLine);
                    writer.newLine();
                    firstLine = false;
                    continue;
                }

                String[] fields = currentLine.split(",");

                // Check if the line has at least 7 fields
                if (fields.length < 7) {
                    System.out.println("Skipping malformed line: " +
                                       currentLine);
                    continue;
                }

                String productIdStr = fields[0].trim();

                if (productIdStr.isEmpty()) {
                    System.out.println("Skipping line with empty productId: " +
                                       currentLine);
                    continue;
                }

                int currentProductId;
                try {
                    currentProductId = Integer.parseInt(productIdStr);
                } catch (NumberFormatException e) {
                    System.out.println("Skipping line with invalid productId: " +
                                       currentLine);
                    continue;
                }

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
            System.out.println("Error updating products CSV: " +
                               e.getMessage());
        }

        // Replace original file with the updated file
        if (!inputFile.delete()) {
            System.out.println("Could not delete original products CSV file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temporary products CSV file");
        }
    }
}
