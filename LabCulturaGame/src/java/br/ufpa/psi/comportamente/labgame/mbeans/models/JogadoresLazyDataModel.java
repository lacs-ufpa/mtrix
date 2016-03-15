/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class JogadoresLazyDataModel extends LazyDataModel<Jogador> {

      private List<Jogador> jogadores;
      private JogadorDAO dao;

      @Override
      public List<Jogador> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            try {
                  try {
                        dao = new JogadorDAO();
                        dao.beginTransaction();
                        jogadores = dao.encontraJogadorPorExperimentoNulo(first, pageSize);
                  } finally {
                        dao.stopOperation(false);
                  }
            } catch (Exception e) {

            }

            if (getRowCount() <= 0) {
                  dao = new JogadorDAO();
                  dao.beginTransaction();
                  setRowCount(dao.contaJogadoresExperimentoNulo());
                  dao.stopOperation(false);
            }

            setPageSize(pageSize);

            return jogadores;
      }

      @Override
      public Object getRowKey(Jogador jogador) {
            return jogador.getId();
      }

      @Override
      public Jogador getRowData(String rowKey) {
            for (Jogador jogador : jogadores) {
                  if (jogador.getId().equals(Long.valueOf(rowKey))) {
                        return jogador;
                  }
            }
            return null;
      }

}
