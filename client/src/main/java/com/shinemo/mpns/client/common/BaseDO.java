package com.shinemo.mpns.client.common;

import java.io.Serializable;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@SuppressWarnings("rawtypes")
public class BaseDO implements Serializable, Cloneable, Comparable {

    protected static final long serialVersionUID = 4753810862868386037L;

    
    /**
     * @see Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Compares the current object with the object passed
     *
     * @param o
     *            object to compare with
     * @return int
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(Object o) {
        return CompareToBuilder.reflectionCompare(this, o);
    }
}
