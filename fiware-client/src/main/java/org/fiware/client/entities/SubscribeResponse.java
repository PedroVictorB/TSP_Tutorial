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
public class SubscribeResponse {
    private String duration;
    private String subscriptionId;
    private String throttling;

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
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
     * @return the throttling
     */
    public String getThrottling() {
        return throttling;
    }

    /**
     * @param throttling the throttling to set
     */
    public void setThrottling(String throttling) {
        this.throttling = throttling;
    }
}
