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
