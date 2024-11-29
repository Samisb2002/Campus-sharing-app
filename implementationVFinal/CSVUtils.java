import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVUtils {

    // Méthode pour ajouter un produit au fichier CSV
    public static void appendProductToCSV(String filename, Product product) {
        try {
            // Vérifie si le fichier existe déjà et s'il n'est pas vide
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            // Ouverture du fichier en mode ajout
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                // Si le fichier n'existe pas ou est vide, on écrit les en-têtes
                if (!fileExists) {
                    bw.write("productId,ownerId,productType,productName," +
                             "productDesc,specificField,isAvailable");
                }

                // Saut de ligne avant d'ajouter le produit
                bw.newLine();

                // Ajout du produit sous forme de chaîne CSV
                bw.write(product.toCSVString());
            }
        } catch (IOException e) {
            // Gestion des erreurs lors de l'écriture dans le fichier
            System.out.println("Error writing to products CSV: " + e.getMessage());
        }
    }

    // Méthode pour ajouter un utilisateur au fichier CSV
    public static void appendUserToCSV(String filename, AuthenticatedStudent user) {
        try {
            // Vérifie si le fichier existe déjà et s'il n'est pas vide
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            // Ouverture du fichier en mode ajout
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                // Si le fichier n'existe pas ou est vide, on écrit les en-têtes
                if (!fileExists) {
                    bw.write("userId,name,email,password,bananaScore");
                }

                // Saut de ligne avant d'ajouter l'utilisateur
                bw.newLine();

                // Ajout de l'utilisateur sous forme de chaîne CSV
                bw.write(user.getUserId() + "," + user.getUserName() + "," +
                         user.getUserEmail() + "," + user.getPassword() + "," +
                         user.scoreManager.getScore());
            }
        } catch (IOException e) {
            // Gestion des erreurs lors de l'écriture dans le fichier
            System.out.println("Error writing to users CSV: " + e.getMessage());
        }
    }

    // Méthode pour ajouter une relation entre un utilisateur et un produit
    public static void appendUserProductToCSV(String filename, int userId, int productId) {
        try {
            // Vérifie si le fichier existe déjà et s'il n'est pas vide
            File file = new File(filename);
            boolean fileExists = file.exists() && file.length() > 0;

            // Ouverture du fichier en mode ajout
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

                // Si le fichier n'existe pas ou est vide, on écrit les en-têtes
                if (!fileExists) {
                    bw.write("userId,productId");
                }

                // Saut de ligne avant d'ajouter la relation
                bw.newLine();

                // Ajout de la relation sous forme de chaîne CSV
                bw.write(userId + "," + productId);
            }
        } catch (IOException e) {
            // Gestion des erreurs lors de l'écriture dans le fichier
            System.out.println("Error writing to " + filename + ": " + e.getMessage());
        }
    }

    // Méthode pour obtenir un produit à partir de son ID
    public static Product getProductById(String filename, int productId) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Ignorer la première ligne (les en-têtes)
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Ignorer les lignes vides
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Séparer les champs de la ligne
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    int currentProductId = Integer.parseInt(fields[0].trim());
                    // Vérifier si l'ID du produit correspond à celui recherché
                    if (currentProductId == productId) {
                        String productType = fields[2].trim();
                        String productName = fields[3].trim();
                        String productDesc = fields[4].trim();
                        String specificField = fields[5].trim();
                        boolean isAvailable = Boolean.parseBoolean(fields[6].trim());

                        // Récupérer le propriétaire du produit
                        AuthenticatedStudent owner = (AuthenticatedStudent) UserManager.getInstance().getUserById(Integer.parseInt(fields[1].trim()));
                        if (owner == null) {
                            return null;
                        }

                        // Créer et retourner le produit
                        Product product = ProductFactory.createProduct(productType, productId, owner, productName, productDesc, specificField);
                        if (product != null) {
                            product.setAvailable(isAvailable);
                            return product;
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Gestion des erreurs lors de la lecture du fichier
            System.out.println("Error reading products CSV: " + e.getMessage());
        }
        return null;
    }

    // Méthode pour obtenir un produit à partir de son nom
    public static Product getProductByName(String filename, String productName) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                // Ignorer la première ligne (les en-têtes)
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Ignorer les lignes vides
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Séparer les champs de la ligne
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    String currentProductName = fields[3].trim();
                    // Vérifier si le nom du produit correspond à celui recherché
                    if (currentProductName.equalsIgnoreCase(productName)) {
                        int productId = Integer.parseInt(fields[0].trim());
                        String productType = fields[2].trim();
                        String productDesc = fields[4].trim();
                        String specificField = fields[5].trim();
                        boolean isAvailable = Boolean.parseBoolean(fields[6].trim());

                        // Récupérer le propriétaire du produit
                        AuthenticatedStudent owner = (AuthenticatedStudent) UserManager.getInstance().getUserById(Integer.parseInt(fields[1].trim()));
                        if (owner == null) {
                            return null;
                        }

                        // Créer et retourner le produit
                        Product product = ProductFactory.createProduct(productType, productId, owner, currentProductName, productDesc, specificField);
                        if (product != null) {
                            product.setAvailable(isAvailable);
                            return product;
                        }
                    }
                }
            }
        } catch (IOException e) {
            // Gestion des erreurs lors de la lecture du fichier
            System.out.println("Error reading products CSV: " + e.getMessage());
        }
        return null;
    }

    // Méthode pour mettre à jour la disponibilité d'un produit dans le fichier CSV
    public static void updateProductAvailabilityInCSV(String filename, int productId, boolean isAvailable) {
        File inputFile = new File(filename);
        File tempFile = new File("products_temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean firstLine = true;

            while ((currentLine = reader.readLine()) != null) {
                // Copie des en-têtes sans modification
                if (firstLine) {
                    writer.write(currentLine);
                    writer.newLine();
                    firstLine = false;
                    continue;
                }

                // Ignorer les lignes vides
                if (currentLine.trim().isEmpty()) {
                    continue;
                }

                // Séparer les champs de la ligne
                String[] fields = currentLine.split(",");

                if (fields.length < 7) {
                    System.out.println("Skipping malformed line: " + currentLine);
                    continue;
                }

                int currentProductId = Integer.parseInt(fields[0].trim());

                // Si l'ID du produit correspond, on met à jour la disponibilité
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
            // Gestion des erreurs lors de la mise à jour du fichier
            System.out.println("Error updating products CSV: " + e.getMessage());
        }

        // Remplacer le fichier d'origine par le fichier temporaire
        if (!inputFile.delete()) {
            System.out.println("Could not delete original products CSV file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temporary products CSV file");
        }
    }
}
