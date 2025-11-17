package co.edu.uniquindio.poo.neodelivery.model;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

//contraseña de app: ircn wjqu zhyd gafd
public class EmailService {

    private static final String emailOrigen = "neodelivery123@gmail.com";
    private final String appPassword = "ircn wjqu zhyd gafd";

    private static Session session;

    public EmailService() {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance (props , new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailOrigen, appPassword);
            }
        });
    }



    public static void sendEmail(String destinatario, String asunto, String mensajeTexto) {
        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(emailOrigen));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );
            message.setSubject(asunto);
            message.setText(mensajeTexto);

            Transport.send(message);

            System.out.println("Correo enviado a " + destinatario);

        } catch (MessagingException e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }
    }

}
