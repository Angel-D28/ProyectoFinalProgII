package co.edu.uniquindio.poo.neodelivery.controllers;

import co.edu.uniquindio.poo.neodelivery.model.ActivityLogEntry;
import co.edu.uniquindio.poo.neodelivery.model.ActivityLogService;
import co.edu.uniquindio.poo.neodelivery.model.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ActivityLogController {

    @FXML private TableView<ActivityLogEntry> tableLog;
    @FXML private TableColumn<ActivityLogEntry, String> colDate;
    @FXML private TableColumn<ActivityLogEntry, String> colUser;
    @FXML private TableColumn<ActivityLogEntry, String> colAction;

    @FXML
    private Button btnGeneratePDF;

    private AnchorPane mainContent;

    void setMainContent(AnchorPane mainContent) {
        this.mainContent = mainContent;
    }

    @FXML
    public void initialize() {

        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("user"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("action"));

        tableLog.setItems(ActivityLogService.getLogs());
    }

    @FXML
    void generatePDF(ActionEvent event) {
        try {
            String userHome = System.getProperty("user.home");
            String filePath = userHome + "/Downloads/ActivityLog.pdf";

            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, new java.io.FileOutputStream(filePath));

            document.open();

            com.lowagie.text.Font titleFont = new com.lowagie.text.Font(com.lowagie.text.Font.HELVETICA, 18, com.lowagie.text.Font.BOLD);
            com.lowagie.text.Paragraph title = new com.lowagie.text.Paragraph("Activity Log Report", titleFont);
            title.setAlignment(com.lowagie.text.Element.ALIGN_CENTER);
            document.add(title);

            document.add(new com.lowagie.text.Paragraph("\n"));

            com.lowagie.text.pdf.PdfPTable pdfTable = new com.lowagie.text.pdf.PdfPTable(3);
            pdfTable.setWidthPercentage(100);

            pdfTable.addCell("Fecha");
            pdfTable.addCell("Usuario");
            pdfTable.addCell("Acción");

            for (ActivityLogEntry entry : tableLog.getItems()) {
                pdfTable.addCell(entry.getDate());
                pdfTable.addCell(entry.getUser());
                pdfTable.addCell(entry.getAction());
            }

            document.add(pdfTable);
            document.close();

            Utils.showAlert("VERIFIED", "PDF generado en: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}