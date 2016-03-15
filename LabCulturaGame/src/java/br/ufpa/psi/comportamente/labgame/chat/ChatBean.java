package br.ufpa.psi.comportamente.labgame.chat;

import br.ufpa.psi.comportamente.labgame.dao.MensagemDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

@ManagedBean(name = "chatBean")
@SessionScoped
public class ChatBean implements Serializable {

      private String message;
      private String chatMessage;

      private final SimpleDateFormat dateFormat;

      public ChatBean() {
            message = "";
            chatMessage = "";
            dateFormat = new SimpleDateFormat("[HH:mm:ss - dd/MM/yy]");

      }

      /**
       * METODO QUE GERENCIA O RECEBIMENTO DE MENSAGENS DO CHAT
       *
       * É SINCRONIZADO PARA EVITAR PROBLEMAS DE CONCORRENCIA NO ACESSO
       * @param user
       * @param userID
       * @param experimentoID
       */
      public synchronized void sendMessage(String user, Long userID, Long experimentoID) {
            Date currentTime = new Date();

            persisteMensagem(message, userID, experimentoID);

            EventBus eventBus = EventBusFactory.getDefault().eventBus();
            eventBus.publish("/chat", "\n\n" + user + ": " + message + "\n" + "enviado em " + dateFormat.format(currentTime));
            message = "";
      }

      public void receiveMessage(ActionEvent event) {
            String messageChat = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("msg");

            chatMessage = chatMessage + messageChat;
      }

      ///TODO: finalizar isso
      private void persisteMensagem(String mensagem, Long userID, Long experimentoID) {
            Mensagem messagePersist = new Mensagem();

            messagePersist.setConteudoMensagem(mensagem);
            messagePersist.setEmissor(userID);
            messagePersist.setExperimento(experimentoID);

            MensagemDAO dao = new MensagemDAO();

            dao.beginTransaction();
            dao.save(messagePersist);
            dao.stopOperation(true);

      }

      public String getMessage() {
            return message;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public String getChatMessage() {
            return chatMessage;
      }

      public void setChatMessage(String chatMessage) {
            this.chatMessage = chatMessage;
      }

}
