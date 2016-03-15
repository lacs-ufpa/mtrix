/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.teste;

import br.ufpa.psi.comportamente.labgame.dao.PesquisadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Weslley
 */
public class TestePesquisador {

      public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

            Pesquisador pesquisador = new Pesquisador();
            PesquisadorDAO dao = new PesquisadorDAO();

            pesquisador.setNome("Admin");
            pesquisador.setUsername("admin");

            String passwd = "123";

            pesquisador.setPassword(passwd);
            pesquisador.setCurso("SI");
            pesquisador.setLattes("www.lattes.com");

            dao.beginTransaction();
            dao.save(pesquisador);
            dao.stopOperation(true);

      }
}
