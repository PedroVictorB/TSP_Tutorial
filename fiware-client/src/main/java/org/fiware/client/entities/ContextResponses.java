/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fiware.client.entities;

import com.google.gson.annotations.SerializedName;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class ContextResponses
{
    private StatusCode statusCode;

    private ContextElement contextElement;

    public StatusCode getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (StatusCode statusCode)
    {
        this.statusCode = statusCode;
    }

    public ContextElement getContextElement ()
    {
        return contextElement;
    }

    public void setContextElement (ContextElement contextElement)
    {
        this.contextElement = contextElement;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [statusCode = "+statusCode+", contextElement = "+contextElement.toString()+"]";
    }
}
