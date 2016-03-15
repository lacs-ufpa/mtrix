package br.ufpa.psi.comportamente.labgame.email;

import java.util.HashMap;

import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp
public class SendMail {

    private String mailSMTPServer;
    private String mailSMTPServerPort;
    public static String from = "mtrix.contato@gmail.com";
    public static String passwd = "mtrix2014";

    /*
     * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL 
     * e a porta usada por ele
     */
    public SendMail() { //Para o GMAIL 
        mailSMTPServer = "smtp.gmail.com";
        mailSMTPServerPort = "587"; //25 , 465 , 587
        from = "mtrix.contato@gmail.com";//TODO mudar para um email correto
    }
    /*
     * caso queira mudar o servidor e a porta, so enviar para o contrutor
     * os valor como string
     */

    public SendMail(String mailSMTPServer, String mailSMTPServerPort) { //Para outro Servidor
        this.mailSMTPServer = mailSMTPServer;
        this.mailSMTPServerPort = mailSMTPServerPort;
    }

    public void sendMail(Email email) {
        Properties props = System.getProperties();

		// quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado	
/*    	props.setProperty("proxySet","true");
         props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy
         props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy
         */
        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailSMTPServer); //server SMTP
        props.put("mail.smtp.auth", "true"); //ativa autenticacao
        props.put("mail.smtp.user", from); //usuario ou seja, a conta que esta enviando o email
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", mailSMTPServerPort); //porta
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket
        //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        //Cria um autenticador que sera usado a seguir
        SimpleAuth auth = null;
        auth = new SimpleAuth(from, passwd); //<------ COLOCAR AQUI A SENHA

		//Session - objeto que ira realizar a conex�o com o servidor
		/*Como h� necessidade de autentica��o � criada uma autenticacao que
         * � responsavel por solicitar e retornar o usu�rio e senha para 
         * autentica��o */
        Session session = Session.getInstance(props, auth);
        session.setDebug(false); //Habilita o LOG das operacoes executadas durante o envio do email

        //Objeto que cont�m a mensagem
        Message msg = new MimeMessage(session);

        try {
            //Setando o(s) destinat�rio(s)
            Map<String, InternetAddress> destinatariosMap = new HashMap<String, InternetAddress>();
            String destinatariosString = email.getDestinatarios();
            if (destinatariosString.contains(Email.SEPARADOR_DESTINATARIOS)) {
                for (String dest : destinatariosString.split(Email.SEPARADOR_DESTINATARIOS)) {
                    if (!destinatariosMap.containsKey(dest)) {
                        destinatariosMap.put(dest, new InternetAddress(dest));
                    }
                }
            } else {
                destinatariosMap.put(email.getDestinatarios(), new InternetAddress(email.getDestinatarios()));
            }
            //Setando o destino do email
            msg.setRecipients(Message.RecipientType.BCC, destinatariosMap.values().toArray(new InternetAddress[destinatariosMap.values().size()]));
            //Setando a origem do email
            msg.setFrom(new InternetAddress(from));
            //Setando o assunto
            msg.setSubject(email.getAssunto());
            msg.setContent(email.getCorpo(), "text/plain");

            Transport.send(msg);

            /**
             * **************************************
             */
			//Usado para emails com anexo
			/*if( email.getAnexo() != null ){
             //create and fill the first message part
             MimeBodyPart mbp1 = new MimeBodyPart();

             mbp1.setText(email.getCorpo());

             // create the second message part
             MimeBodyPart mbp2 = new MimeBodyPart();

             // attach the file to the message

             File someFile = new File("termo_de_responsabilidade.pdf");
             FileOutputStream fos = new FileOutputStream(someFile);
             fos.write(converteBlob(email.getAnexo()));
             fos.flush();
             fos.close();

             FileDataSource fds = new FileDataSource(someFile);
             mbp2.setDataHandler(new DataHandler(fds));
             mbp2.setFileName(fds.getName());

             // create the Multipart and add its parts to it
             Multipart mp = new MimeMultipart();
             mp.addBodyPart(mbp1);
             mp.addBodyPart(mbp2);

             // add the Multipart to the message
             msg.setContent(mp);
             }*/
            /**
             * **************************************
             */
        } catch (Exception e) {
            e.printStackTrace();
        }

		//Objeto encarregado de enviar os dados para o email
		/*Transport tr;
         try {
         tr = session.getTransport("smtp"); //define smtp para transporte
			
         *  1 - define o servidor smtp
         *  2 - seu nome de usuario
         *  3 - sua senha
			 
         tr.connect(mailSMTPServer, "sagitta@ufpa.br", "s@g1tt@29831.1223");
         msg.saveChanges(); // don't forget this
         //envio da mensagem
         tr.sendMessage(msg, msg.getAllRecipients());
         tr.close();
         } catch (Exception e) {
         e.printStackTrace();
         }*/
    }

    /*private byte[]  converteBlob(Blob blob){
     BufferedInputStream bis = null;
     try {
     bis = new BufferedInputStream(blob.getBinaryStream());
     } catch (SQLException e) {
     e.printStackTrace();
     }
     ByteArrayOutputStream bo = new ByteArrayOutputStream();
     byte[] buf = new byte[4096];
     int n = 0;

     try {
     while ((n = bis.read(buf, 0, 4096)) != -1) {
     bo.write(buf, 0, n);
     }
     } catch (IOException e) {
     e.printStackTrace();
     }

     try {
     bo.flush();
     } catch (IOException e) {
     e.printStackTrace();
     }
     try {
     bo.close();
     } catch (IOException e1) {
     e1.printStackTrace();
     }
     try {
     bis.close();
     } catch (IOException e) {
     e.printStackTrace();
     }

     buf = null;

     return bo.toByteArray();

     }*/
//	public static void main(String[] args) {
//		SendMail sendMail = new SendMail();
//		Email email = new Email();
//		email.setAssunto("Assunto");
//		email.setCorpo("Corpo");
//		email.setDestinatarios("danieldias@ufpa.br");
//		sendMail.sendMail(email);
//	}
}

class SimpleAuth extends Authenticator {

    public String username = null;
    public String password = null;

    public SimpleAuth(String user, String pwd) {
        username = user;
        password = pwd;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }
}
