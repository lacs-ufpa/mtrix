/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.email;

import java.sql.Blob;

public class EnviarEmailThread extends Thread {

	Email email;
	SendMail sender;
	
	public EnviarEmailThread(String remetente, String destinatarios, String assunto, String corpo, Blob anexo) {
		email = new Email();
		email.setRemetente(remetente);
		email.setDestinatarios(destinatarios);
		email.setAssunto(assunto);
		email.setCorpo(corpo);
		email.setAnexo(anexo);
	}
	
	@Override
	public void run() {
		sender = new SendMail();
		sender.sendMail(email);
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
