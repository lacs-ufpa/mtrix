/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpa.psi.comportamente.labgame.mbeans.models;

import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Weslley
 */
public class PontuacaoCulturalDataModel extends ListDataModel<PontuacaoCultural> implements SelectableDataModel<PontuacaoCultural> {

    public PontuacaoCulturalDataModel() {
        
    }

    public PontuacaoCulturalDataModel(List<PontuacaoCultural> data) {
        super(data);
    }

    @Override
    public Object getRowKey(PontuacaoCultural pontCult) {
        return pontCult.getId();
    }

    @Override
    public PontuacaoCultural getRowData(String rowKey) {
        List<PontuacaoCultural> listPontCult = (List<PontuacaoCultural>) getWrappedData();

        for (PontuacaoCultural pontCult : listPontCult) {
            if (pontCult.getId() == Integer.parseInt(rowKey)) {
                return pontCult;
            }
        }

        return null;
    }

}
