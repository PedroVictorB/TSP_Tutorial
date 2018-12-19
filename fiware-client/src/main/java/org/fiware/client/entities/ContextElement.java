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
public class ContextElement
{
    private String id;

    private Attributes[] attributes;

    private String type;

    private String isPattern;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Attributes[] getAttributes ()
    {
        return attributes;
    }

    public void setAttributes (Attributes[] attributes)
    {
        this.attributes = attributes;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getIsPattern ()
    {
        return isPattern;
    }

    public void setIsPattern (String isPattern)
    {
        this.isPattern = isPattern;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", attributes = "+attributes+", type = "+type+", isPattern = "+isPattern+"]";
    }
}
