package com.chl.campussecondhandtradingsystem.websocket;

import org.apache.catalina.session.StandardSessionFacade;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        StandardSessionFacade ssf = (StandardSessionFacade) request.getHttpSession();
        if(ssf != null){
            HttpSession session = (HttpSession) request.getHttpSession();
            sec.getUserProperties().put(HttpSession.class.getName(), session);
        }
    }
}
