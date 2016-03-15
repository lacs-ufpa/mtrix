/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.PesquisadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Pesquisador;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class PesquisadoresLazyDataModel extends LazyDataModel<Pesquisador>{
    
    private List<Pesquisador> pesquisadores;
    private PesquisadorDAO dao;
    
    @Override
    public List<Pesquisador> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            try {
                dao = new PesquisadorDAO();
                dao.beginTransaction();
                pesquisadores = dao.findAll(Pesquisador.class);
            } finally {
                dao.stopOperation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getRowCount() <= 0) {
            dao = new PesquisadorDAO();
            dao.beginTransaction();
            setRowCount(dao.contaPesquisadoresTotal());
            dao.stopOperation(false);
        }

        setPageSize(pageSize);

        return pesquisadores;
    }

    @Override
    public Object getRowKey(Pesquisador pesquisador) {
        return pesquisador.getId();
    }

    @Override
    public Pesquisador getRowData(String rowKey) {        
        for (Pesquisador pesquisador : pesquisadores) {
            if (pesquisador.getId().equals(Long.valueOf(rowKey))) {
                return pesquisador;
            }
        }
        return null;
    }
}
