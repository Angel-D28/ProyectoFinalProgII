package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;
import co.edu.uniquindio.poo.neodelivery.model.Shipment;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ManageReportsController {

    @FXML
    private Button btnGenerateReport;

    @FXML
    private Button btnViewReport;

    @FXML
    private Button btnOpenFolder;

    @FXML
    private TableView<Shipment> tableViewReports;

    @FXML
    private TableColumn<Shipment, String> columnID;

    @FXML
    private TableColumn<Shipment, String> columnClient;

    @FXML
    private TableColumn<Shipment, String> columnAddress;

    @FXML
    private TableColumn<Shipment, String> columnStatus;

    @FXML
    private TableColumn<Shipment, String> columnDriver;

    @FXML
    private TableColumn<Shipment, String> columnDate;

    private AnchorPane mainContent;
    private ObservableList<Shipment> shipmentsList = FXCollections.observableArrayList();
    private static final String REPORTS_DIR = "reports";

    public void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    public void initialize() {

        columnID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnAddress.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getDestination() != null ? 
                cellData.getValue().getDestination().toString() : "N/D"
            )
        );
        columnStatus.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                translateStatus(cellData.getValue().getStatus())
            )
        );
        columnDriver.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getAssignedDriver() != null ? 
                cellData.getValue().getAssignedDriver().getName() : "N/D"
            )
        );
        columnClient.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getOrigin() != null ? 
                cellData.getValue().getOrigin().toString() : "N/D"
            )
        );
        columnDate.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty("N/D")
        );

        tableViewReports.setItems(shipmentsList);
        loadShipmentsFromDatabase();
    }

    private void loadShipmentsFromDatabase() {
        DataBase db = DataBase.getInstance();
        shipmentsList.clear();
        shipmentsList.addAll(db.getListaEnvios());
    }

    private String translateStatus(co.edu.uniquindio.poo.neodelivery.model.Status status) {
        if (status == null) return "N/D";
        return switch(status) {
            case PENDING -> "Pendiente";
            case DELIVERASSIGNED -> "Asignado";
            case DELIVERED -> "Entregado";
            case DELIVERING -> "En Camino";
        };
    }

    /**
     * Genera un reporte PDF con todos los envíos disponibles
     * Guarda el archivo en /reports/Report-<fecha>.pdf
     */
    public void generateDeliveryReportPDF() {
        try {
            // Crear directorio de reportes si no existe
            File reportsDir = new File(REPORTS_DIR);
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = currentDate.format(formatter);
            String fileName = "Report-" + dateString + ".pdf";
            File pdfFile = new File(reportsDir, fileName);

            // Obtener lista de envíos desde la base de datos
            DataBase db = DataBase.getInstance();
            List<Shipment> shipments = db.getListaEnvios();

            if (shipments.isEmpty()) {
                Utils.showAlert("WARNING", "No hay envíos para generar el reporte");
                return;
            }

            // Crear documento PDF
            PDDocument document = new PDDocument();
            PDPageContentStream contentStream = null;
            
            PDImageXObject logoImage = null;
            try {
                // Intentar diferentes rutas posibles para el logo
                java.io.InputStream logoStream = null;
                
                // Intentar desde la raíz de resources
                logoStream = Utils.class.getResourceAsStream("/images/logoCamionReportes.png");
                
                // Si no funciona, intentar desde el paquete
                if (logoStream == null) {
                    logoStream = getClass().getResourceAsStream("/images/logoCamionReportes.png");
                }
                
                // Si aún no funciona, intentar ruta absoluta completa
                if (logoStream == null) {
                    logoStream = ManageReportsController.class.getResourceAsStream("/images/logoCamionReportes.png");
                }
                
                // Si aún no funciona, intentar desde el classloader
                if (logoStream == null) {
                    logoStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("images/logoCamionReportes.png");
                }
                
                if (logoStream != null) {
                    java.awt.image.BufferedImage bufferedImage = javax.imageio.ImageIO.read(logoStream);
                    if (bufferedImage != null) {
                        logoImage = LosslessFactory.createFromImage(document, bufferedImage);
                        System.out.println("Logo cargado exitosamente");
                    } else {
                        System.err.println("No se pudo leer la imagen del logo");
                    }
                    logoStream.close();
                } else {
                    System.err.println("No se encontró el logo en ninguna de las rutas probadas");
                }
            } catch (Exception e) {
                System.err.println("Error al cargar el logo: " + e.getMessage());
                e.printStackTrace();
            }
            
            try {
                float margin = 50;
                float yPosition = 750;
                float lineHeight = 20;
                float[] columnWidths = {80, 120, 150, 100, 120, 100};
                float tableStartX = margin;
                boolean isFirstPage = true;
                int shipmentIndex = 0;

                for (Shipment shipment : shipments) {
                    if (yPosition < 100 || isFirstPage) {
                        if (contentStream != null) {
                            contentStream.close();
                        }
                        PDPage page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = 750;
                        isFirstPage = false;

                        if (shipmentIndex == 0) {
                            // Dibujar el logo primero, antes del título
                            if (logoImage != null) {
                                try {
                                    float logoWidth = 80;
                                    float logoHeight = 60;
                                    float logoX = PDRectangle.A4.getWidth() - margin - logoWidth;
                                    float logoY = yPosition - 5;
                                    // Asegurarse de que el contentStream esté listo
                                    contentStream.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight);
                                    System.out.println("Logo dibujado exitosamente en posición: x=" + logoX + ", y=" + logoY);
                                } catch (Exception e) {
                                    System.err.println("Error al dibujar el logo en el PDF: " + e.getMessage());
                                    e.printStackTrace();
                                }
                            } else {
                                System.err.println("ADVERTENCIA: Logo es null, no se puede dibujar en el PDF");
                            }
                            
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_BOLD, 18);
                            contentStream.newLineAtOffset(margin, yPosition);
                            contentStream.showText("Reporte de Envíos - NeoDelivery");
                            contentStream.endText();

                            yPosition -= 30;

                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
                            contentStream.newLineAtOffset(margin, yPosition);
                            contentStream.showText("Fecha de generación: " + dateString);
                            contentStream.endText();

                            yPosition -= 40;

                            float currentX = tableStartX;

                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("ID");
                            contentStream.endText();
                            currentX += columnWidths[0];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Cliente");
                            contentStream.endText();
                            currentX += columnWidths[1];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Dirección");
                            contentStream.endText();
                            currentX += columnWidths[2];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Estado");
                            contentStream.endText();
                            currentX += columnWidths[3];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Repartidor");
                            contentStream.endText();
                            currentX += columnWidths[4];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Fecha");
                            contentStream.endText();

                            yPosition -= 15;
                            contentStream.moveTo(tableStartX, yPosition);
                            contentStream.lineTo(tableStartX + 670, yPosition);
                            contentStream.stroke();

                            yPosition -= 10;
                        } else {

                            float currentX = tableStartX;
                            contentStream.beginText();
                            contentStream.setFont(PDType1Font.TIMES_BOLD, 10);
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("ID");
                            contentStream.endText();
                            currentX += columnWidths[0];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Cliente");
                            contentStream.endText();
                            currentX += columnWidths[1];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Dirección");
                            contentStream.endText();
                            currentX += columnWidths[2];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Estado");
                            contentStream.endText();
                            currentX += columnWidths[3];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Repartidor");
                            contentStream.endText();
                            currentX += columnWidths[4];

                            contentStream.beginText();
                            contentStream.newLineAtOffset(currentX, yPosition);
                            contentStream.showText("Fecha");
                            contentStream.endText();

                            yPosition -= 15;
                            contentStream.moveTo(tableStartX, yPosition);
                            contentStream.lineTo(tableStartX + 670, yPosition);
                            contentStream.stroke();
                            yPosition -= 10;
                        }
                    }

                    float currentX = tableStartX;

                    contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);

                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(shipment.getId() != null ? shipment.getId() : "N/D");
                    contentStream.endText();
                    currentX += columnWidths[0];

                    String client = shipment.getOrigin() != null ? 
                        shipment.getOrigin().toString() : "N/D";
                    if (client.length() > 15) client = client.substring(0, 15) + "...";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(client);
                    contentStream.endText();
                    currentX += columnWidths[1];

                    String address = shipment.getDestination() != null ? 
                        shipment.getDestination().toString() : "N/D";
                    if (address.length() > 20) address = address.substring(0, 20) + "...";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(address);
                    contentStream.endText();
                    currentX += columnWidths[2];

                    String status = translateStatus(shipment.getStatus());
                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(status);
                    contentStream.endText();
                    currentX += columnWidths[3];

                    String driver = shipment.getAssignedDriver() != null ? 
                        shipment.getAssignedDriver().getName() : "N/D";
                    if (driver.length() > 15) driver = driver.substring(0, 15) + "...";
                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(driver);
                    contentStream.endText();
                    currentX += columnWidths[4];

                    contentStream.beginText();
                    contentStream.newLineAtOffset(currentX, yPosition);
                    contentStream.showText(dateString);
                    contentStream.endText();

                    yPosition -= lineHeight;
                    shipmentIndex++;
                }

                if (contentStream != null) {
                    contentStream.close();
                }

                document.save(pdfFile);
                document.close();

            } catch (IOException e) {
                if (contentStream != null) {
                    try {
                        contentStream.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (document != null) {
                    try {
                        document.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                throw e;
            }

            String message = "Reporte generado exitosamente en:\n" + pdfFile.getAbsolutePath();
            Utils.showAlert("VERIFIED", message);

            loadShipmentsFromDatabase();

        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Error al generar el reporte PDF: " + e.getMessage());
        }
    }

    @FXML
    void generateReport(ActionEvent event) {
        generateDeliveryReportPDF();
    }

    @FXML
    void openReportsFolder(ActionEvent event) {
        try {
            File reportsDir = new File(REPORTS_DIR);
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(reportsDir);
            } else {
                Utils.showAlert("WARNING", "No se puede abrir la carpeta automáticamente");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Utils.showAlert("ERROR", "Error al abrir la carpeta: " + e.getMessage());
        }
    }

    @FXML
    void viewReport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Reporte PDF");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf")
        );
        
        File reportsDir = new File(REPORTS_DIR);
        if (reportsDir.exists()) {
            fileChooser.setInitialDirectory(reportsDir);
        }

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(selectedFile);
                } else {
                    Utils.showAlert("WARNING", "No se puede abrir el archivo PDF automáticamente");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Utils.showAlert("ERROR", "Error al abrir el archivo PDF: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Generando reporte PDF...");
        
        ManageReportsController controller = new ManageReportsController();
        controller.generateDeliveryReportPDF();
        
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);
        String filePath = REPORTS_DIR + "/Report-" + dateString + ".pdf";
        
        System.out.println("Reporte generado en: " + new File(filePath).getAbsolutePath());
    }
}

