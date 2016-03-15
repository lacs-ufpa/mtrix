/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.psi.comportamente.labgame.resources;

import javax.faces.application.FacesMessage;
import org.primefaces.push.EventBus;
import org.primefaces.push.RemoteEndpoint;
import org.primefaces.push.annotation.OnClose;
import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.OnOpen;
import org.primefaces.push.annotation.PathParam;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.primefaces.push.impl.JSONEncoder;

/**
 *
 * @author Weslley
 */
@PushEndpoint("/{username}")
@Singleton
public class SaidaJogadorResource {

    @PathParam("username")
    private String username;

    @OnMessage(encoders = {JSONEncoder.class})
    public FacesMessage onMessage(FacesMessage message) {
        return message;
    }

    @OnOpen
    public void onOpen(RemoteEndpoint r, EventBus eventBus) {

    }

    @OnClose
    public void onClose(RemoteEndpoint r, EventBus eventBus) {

    }

}
