package org.estatio.dom.invoice;

import org.estatio.dom.Lockable;
import org.estatio.dom.utils.StringUtils;

public enum InvoiceStatus implements Lockable {

    NEW, 
    APPROVED, 
    COLLECTED, 
    INVOICED;

    public String title() {
        return StringUtils.enumTitle(this.name());
    }

    @Override
    public boolean isUnlocked() {
        return this == NEW;
    }

    @Override
    public boolean isLocked() {
        return this == APPROVED;
    }

}
