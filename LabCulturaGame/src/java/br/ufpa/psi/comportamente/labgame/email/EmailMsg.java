/*~
M-Trix - Social Experiments Application.
Copyright (C) 2015-2016 The M-Trix authors.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
~*/
package br.ufpa.psi.comportamente.labgame.email;

import br.ufpa.psi.comportamente.labgame.idioma.MensagemUtil;

public class EmailMsg {

    public static String getMensagemCadatroUsuario(String login, String senha) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MensagemUtil.getMensagem("siglaSistema") + " - " + MensagemUtil.getMensagem("tituloSistema") + " \n \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_sucessoCadastro"));
        buffer.append(MensagemUtil.getMensagem("enviarEmail_login_e_senha"));
        buffer.append("Login: ").append(login).append(" \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_senha")).append(senha).append(" \n \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_obs"));
        buffer.append(MensagemUtil.getMensagem("enviarEmail_saudacao"));
        buffer.append(MensagemUtil.getMensagem("enviarEmail_maisInformacoes"));
        buffer.append("Email: fundacaoguama@fundacaoguama.org.br \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_telefone") + " (91) 3321-8900 \n");
        return buffer.toString();
    }

    public static String getMensagemRecuperarSenha(String login, String senha) {
        StringBuffer buffer = new StringBuffer();
        /*
         buffer.append(MensagemUtil.getMensagem("siglaSistema")+" - " + MensagemUtil.getMensagem("tituloSistema") +" \n \n");
         buffer.append(MensagemUtil.getMensagem("enviarEmail_login_e_senha"));
         buffer.append("Login: ").append(login).append(" \n");
         buffer.append(MensagemUtil.getMensagem("enviarEmail_senha")).append(senha).append(" \n \n");
         buffer.append(MensagemUtil.getMensagem("enviarEmail_saudacao"));
         buffer.append(MensagemUtil.getMensagem("enviarEmail_maisInformacoes"));
         buffer.append("Email: fundacaoguama@fundacaoguama.org.br \n");
         buffer.append(MensagemUtil.getMensagem("enviarEmail_telefone") + " (91) 3321-8900 \n");
         */
        buffer.append("Olá ").append(login).append(", você solicitou uma nova senha de acesso ao M-trix.").append(" \n \n");
        buffer.append("Nova Senha: ").append(senha).append(" \n\n");
        buffer.append("Atenciosamente,").append(" \n\n");
        buffer.append("Equipe M-trix");
        return buffer.toString();
    }

    public static String getMensagemConfirmacaoAlteracaoSenha(String nome, String senha) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(MensagemUtil.getMensagem("siglaSistema") + " - " + MensagemUtil.getMensagem("tituloSistema") + " \n \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_alteracaoSenha1")).append(nome).append(MensagemUtil.getMensagem("enviarEmail_alteracaoSenha2"));
        buffer.append(MensagemUtil.getMensagem("enviarEmail_novaSenha")).append(senha).append(" \n \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_saudacao"));
        buffer.append(MensagemUtil.getMensagem("enviarEmail_maisInformacoes"));
        buffer.append("Email: fundacaoguama@fundacaoguama.org.br \n");
        buffer.append(MensagemUtil.getMensagem("enviarEmail_telefone") + " (91) 3321-8900 \n");
        return buffer.toString();
    }

}
