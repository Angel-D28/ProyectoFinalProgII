package co.edu.uniquindio.poo.neodelivery.model;

public class EmailService {
    public static void sendEmail(String to, String subject, String body) {
        // Simulación de envío de correo
        System.out.println("Enviando correo a " + to);
        System.out.println("Asunto: " + subject);
        System.out.println("Contenido: " + body);
        System.out.println("------------------------------------");
    }
}
