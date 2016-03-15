package br.ufpa.psi.comportamente.labgame.resources;

import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import br.ufpa.psi.comportamente.labgame.resources.message.MessageDecoder;
import br.ufpa.psi.comportamente.labgame.resources.message.MessageEncoder;
import javax.faces.application.FacesMessage;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
@PushEndpoint("/chat")
public class ChatResource {
      
      @OnMessage(decoders = {MessageDecoder.class}, encoders = {MessageEncoder.class})
      public Mensagem onMessage(Mensagem message) {
            return message;
      }

}
