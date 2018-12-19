/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fiware.client.entities;

/**
 *
 * @author Pedro
 */
public class ContextResponsesSubscriptionContainer {
    private String subscriptionId;
    private String originator;
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

    /**
     * @return the subscriptionId
     */
    public String getSubscriptionId() {
        return subscriptionId;
    }

    /**
     * @param subscriptionId the subscriptionId to set
     */
    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * @return the originator
     */
    public String getOriginator() {
        return originator;
    }

    /**
     * @param originator the originator to set
     */
    public void setOriginator(String originator) {
        this.originator = originator;
    }
}
