/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.CondicaoDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Condicao;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class CondicoesNaoCadastradasExperimentoLazyDataModel extends LazyDataModel<Condicao>{
    
    private CondicaoDAO dao;
    private List<Condicao> condicoes;
    private Experimento experimento;
    
    public CondicoesNaoCadastradasExperimentoLazyDataModel(Experimento exp) {
        this.experimento = exp;
    }
    
    @Override
    public List<Condicao> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            try {
                
                dao = new CondicaoDAO();
                dao.beginTransaction();
                condicoes = dao.findCondicoesNaoCadastradasExperimento(first, pageSize, experimento);
            } finally {
                dao.stopOperation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getRowCount() <= 0) {
            dao = new CondicaoDAO();
            dao.beginTransaction();
            setRowCount(dao.contaCondicoesNaoCadastradasExperimento(experimento));
            dao.stopOperation(false);
        }

        setPageSize(pageSize);

        return condicoes;
    }

    @Override
    public Object getRowKey(Condicao condicao) {
        return condicao.getId();
    }

    @Override
    public Condicao getRowData(String rowKey) {        
        for (Condicao condicao : condicoes) {
            if (condicao.getId() == Integer.parseInt(rowKey)) {
                return condicao;
            }
        }
        return null;
    }
    
}
