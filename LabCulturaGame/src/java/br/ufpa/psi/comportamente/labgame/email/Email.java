/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.email;

import java.io.Serializable;
import java.sql.Blob;

public class Email implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final String SEPARADOR_DESTINATARIOS = ",";
	
	private String remetente;
	private String destinatarios;
	private String assunto;
	private String corpo;
	//private File anexo;
	private Blob anexo;
	
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public Blob getAnexo() {
		return anexo;
	}
	public void setAnexo(Blob anexo) {
		this.anexo = anexo;
	}
	@Override
	public String toString() {
		return String.format("Email [assunto=%s]", assunto);
	}
	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	public String getDestinatarios() {
		return destinatarios;
	}
	public void setRemetente(String remetente) {
		this.remetente = remetente;
	}
	public String getRemetente() {
		return remetente;
	}

}
