/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fiware.client.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class ContextResponsesContainer {
    private ContextResponses[] contextResponses;

    /**
     * @return the contextResponses
     */
    public ContextResponses[] getContextResponses() {
        return contextResponses;
    }

    /**
     * @param contextResponses the contextResponses to set
     */
    public void setContextResponses(ContextResponses[] contextResponses) {
        this.contextResponses = contextResponses;
    }
}
