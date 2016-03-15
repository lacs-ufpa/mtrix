package br.ufpa.psi.comportamente.labgame.resources.message;

import br.ufpa.psi.comportamente.labgame.entidades.Mensagem;
import org.primefaces.push.Decoder;

/**
 *
 * @author Weslley Tavares <weslleysammyr@gmail.com>
 */
public class MessageDecoder implements Decoder<String, Mensagem> {

      @Override
      public Mensagem decode(String s) {
            String[] userAndMessage = s.split(": ");
            return new Mensagem(userAndMessage[0], userAndMessage[1]);
      }
}
