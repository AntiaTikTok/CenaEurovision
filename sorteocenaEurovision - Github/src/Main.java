import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmx.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmx.com");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("EMAILQUELOENVÍA@gmx.com", "LA CONTRASEÑA");
                    }
                });

        //Defino paises y divvido

        String[] paises = {"Albania", "Alemania", "Australia", "Armenia", "Austria", "Azerbaiyán", "Bélgica", "Croacia",
                "Chipre", "República Checa", "Dinamarca", "Eslovenia", "España", "Estonia", "Finlandia",
                "Francia", "Georgia", "Grecia", "Islandia", "Irlanda", "Israel", "Italia", "Letonia",
                "Lituania", "Malta", "Moldavia", "Noruega", "Paises Bajos", "Polonia", "Portugal",
                "Reino Unido", "Rumanía", "San Marino", "Serbia", "Suecia", "Suiza", "Ucrania"};


        List<String> listaPaises = Arrays.asList(paises);
        Collections.shuffle(listaPaises);

        /*Divide entre el número de amigas. Ahora que me pongo a comentar me doy cuenta de que hacerlo con
        amigas.length sería más sencillo para otros... Si quieres puedes hacerlo así después de definir amigas*/

        List<List<String>> division = new ArrayList<>();
        int tamanoDivision = listaPaises.size() / 4;

        for (int i = 0; i < listaPaises.size(); i += tamanoDivision) {
            division.add(listaPaises.subList(i, Math.min(i + tamanoDivision, listaPaises.size())));
        }

/*Esto lo hice para comprobar que lo repartía bien xd
        System.out.println(division.get(0));
        System.out.println(division.get(1));
        System.out.println(division.get(2));
        System.out.println(division.get(3));
        */

        // Correos
        String[] amigas = {"amiga1@correo.com", "amiga2@correo.com", "amiga3@correo.com", "amiga4@correo.com"};
        for (int i = 0; i < amigas.length; i++) {

            try {


                Message mensaje = new MimeMessage(session);
                mensaje.setFrom(new InternetAddress("EMAILQUELOENVÍA@gmx.com"));
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(amigas[i]));
                mensaje.setSubject("Paises para la cena de Eurovisión");
                mensaje.setText("¡Estamos a solo a una semana de la final de Eurovisión!\n" +
                        "\n" +
                        "Ya es hora de hacer el sorteo de nuestra cena temática de Eurovisión. Cada una de nosotras llevará  comida de un país que participa en el festival. Debes elegir un país de esta lista (cada una tenemos una lista diferente):\n\n"
                        + String.join("\n", division.get(i))+ "\n\n Puedes elegir cualquier plato típico del país que elijas, o ser creativa y hacer tu propia interpretación! ¡No olvides llevar suficiente comida para que todas podamos probar algo! \n\n :)" +
                        "\n" +
                        "\n" +
                        "¡Será una noche llena de música, comida y diversión!\n" ) ;


                Transport.send(mensaje);
            } catch (AddressException e) {
                System.out.println("Error al enviar el correo electrónico: dirección de correo electrónico incorrecta");
                e.printStackTrace();
            } catch (MessagingException e) {
                System.out.println("Error al enviar el correo electrónico: error en el mensaje");
            }


        }
    }
}