/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.dao.ExperimentoDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Experimento;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Weslley
 */
public class ExperimentosRelatorioLazyDataModel extends LazyDataModel<Experimento> {

    private ExperimentoDAO dao;
    private List<Experimento> experimentos;

    @Override
    public List<Experimento> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        try {
            try {
                dao = new ExperimentoDAO();
                dao.beginTransaction();
                experimentos = dao.findExperimentosConcluido(first, pageSize);

            } finally {
                dao.stopOperation(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (getRowCount() <= 0) {
            dao = new ExperimentoDAO();
            dao.beginTransaction();
            setRowCount(dao.contaExperimentosConcluidoTotal());
            dao.stopOperation(false);
        }

        setPageSize(pageSize);

        return experimentos;
    }

    @Override
    public Experimento getRowData(String rowKey) {
        for (Experimento experimento : experimentos) {
            if (experimento.getId().equals(Long.valueOf(rowKey))) {
                return experimento;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Experimento experimento) {
        return experimento.getId();
    }

}
