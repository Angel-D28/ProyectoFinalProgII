package co.edu.uniquindio.poo.neodelivery.model.gestores;
import co.edu.uniquindio.poo.neodelivery.model.Repository.DataBase;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ManageReports {
    private final DataBase db = DataBase.getInstance();
    private final ManageAdmin manageAdmin = new ManageAdmin();

    public String buildFullReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("NEO-DELIVERY - REPORTE GENERAL\n");
        sb.append("Generado por ManageReports\n\n");

        appendSection(sb, "USUARIOS", db.getListaUsuarios());
        appendSection(sb, "ENVIOS", db.getListaEnvios());
        appendSection(sb, "REPARTIDORES", db.getListaRepartidores());
        appendSection(sb, "PAGOS", db.getListaPagos());
        appendSection(sb, "ADMINS", manageAdmin.getAllAdmins());

        return sb.toString();
    }

    public void printFullReport() {
        System.out.print(buildFullReport());
    }
    
    public void saveFullReportToTxt(String filePath) throws IOException {
        String report = buildFullReport();
        Path path = Paths.get(filePath);
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }
        Files.writeString(path, report, StandardCharsets.UTF_8);
        System.out.print(report);
    }

    public String loadTxt(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }

    private <T> void appendSection(StringBuilder sb, String title, List<T> list) {
        sb.append("===== ").append(title).append(" (").append(list.size()).append(") =====\n");
        for (T item : list) {
            sb.append(item).append('\n');
        }
        sb.append('\n');
    }
}
