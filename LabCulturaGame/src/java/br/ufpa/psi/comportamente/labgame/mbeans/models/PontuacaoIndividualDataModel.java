/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoIndividual;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class PontuacaoIndividualDataModel extends ListDataModel<PontuacaoIndividual> implements SelectableDataModel<PontuacaoIndividual> {

    public PontuacaoIndividualDataModel() {
        
    }

    public PontuacaoIndividualDataModel(List<PontuacaoIndividual> data) {
        super(data);
    }

    @Override
    public Object getRowKey(PontuacaoIndividual pontIndiv) {
        return pontIndiv.getId();
    }

    @Override
    public PontuacaoIndividual getRowData(String rowKey) {
        List<PontuacaoIndividual> listPontIndiv = (List<PontuacaoIndividual>) getWrappedData();

        for (PontuacaoIndividual pontIndiv : listPontIndiv) {
            if (pontIndiv.getId() == Integer.parseInt(rowKey)) {
                return pontIndiv;
            }
        }

        return null;
    }

}
