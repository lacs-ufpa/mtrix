/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoIndividualDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class PontuacaoIndivLazyDataModel extends LazyDataModel<PontuacaoIndividual> {

    private PontuacaoIndividualDAO dao;
    private List<PontuacaoIndividual> pontuacoes;
    
    @Override
    public List<PontuacaoIndividual> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            try {
                dao = new PontuacaoIndividualDAO();
                dao.beginTransaction();
                pontuacoes = dao.findAll(first, pageSize);
            } finally {
                dao.stopOperation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getRowCount() <= 0) {
            dao = new PontuacaoIndividualDAO();
            dao.beginTransaction();
            setRowCount(dao.contaPontuacoesTotal());
            dao.stopOperation(false);
        }

        setPageSize(pageSize);

        return pontuacoes;
    }

    @Override
    public Object getRowKey(PontuacaoIndividual pontuacao) {
        return pontuacao.getId();
    }

    @Override
    public PontuacaoIndividual getRowData(String rowKey) {
        
        for (PontuacaoIndividual pontuacao : pontuacoes) {
            if (pontuacao.getId().equals(Long.valueOf(rowKey))) {
                return pontuacao;
            }
        }

        return null;
    }
}
