/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.converter;

import br.ufpa.psi.comportamente.labgame.dao.JogadorDAO;
import br.ufpa.psi.comportamente.labgame.entidades.Jogador;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Weslley
 */
@FacesConverter(value = "PrimeFacesPickListConverter")
public class PrimeFacesPickListConverter implements Converter {
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        JogadorDAO jogadorDAO = new JogadorDAO();
        jogadorDAO.beginTransaction();
        Jogador jogador = null;
        if ((value != null) && (!value.equals(""))) {
            
            jogador = jogadorDAO.find(Long.valueOf(value));
        }
        
        jogadorDAO.commitAndCloseTransaction();
        return jogador;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Long retorno = null;
        if (!(value == null)) {
            Jogador jogador = new Jogador();
            jogador = (Jogador) value;
            retorno = jogador.getId();
        }
        return retorno.toString();
    }
    
} 
    

