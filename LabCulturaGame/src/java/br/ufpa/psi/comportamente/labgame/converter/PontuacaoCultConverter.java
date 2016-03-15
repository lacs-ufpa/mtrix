
package br.ufpa.psi.comportamente.labgame.converter;

import br.ufpa.psi.comportamente.labgame.dao.PontuacaoCulturalDAO;
import br.ufpa.psi.comportamente.labgame.entidades.PontuacaoCultural;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Weslley
 */
@FacesConverter(value = "pontCultural")
public class PontuacaoCultConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {

        PontuacaoCulturalDAO pontCultDAO = new PontuacaoCulturalDAO();
        pontCultDAO.beginTransaction();
        PontuacaoCultural pontCult = null;
        if ((value != null) && (!value.trim().equals(""))) {
            pontCult = pontCultDAO.find(Long.valueOf(value));
        }
        pontCultDAO.commitAndCloseTransaction();

        return pontCult;
    }

    @Override
    @SuppressWarnings("empty-statement")
    public String getAsString(FacesContext fc, UIComponent uic, Object value) {
        String retorno = null;

        if (!(value == null)) {
            if (value instanceof PontuacaoCultural) {
                PontuacaoCultural pontCult = (PontuacaoCultural) value;
                if (pontCult.getId() != null) {
                    retorno = pontCult.getId().toString();
                } else {
                    retorno = "";
                }

            } else {
                return (String) value;
            }
        }

        return retorno;
    }

}
