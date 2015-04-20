/*
 * Copyright 2012-2015 Eurocommercial Properties NV
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.estatio.dom.budget;

import java.math.BigDecimal;

import org.apache.isis.applib.AbstractViewModel;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.InvokeOn;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.actinvoc.ActionInvocationContext;

import org.estatio.dom.asset.Unit;

@DomainObject
@DomainObjectLayout(
        named = "Bulk import/export budget key line item",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class BudgetKeyItemImportExportLineItem
        extends AbstractViewModel 
        implements Comparable<BudgetKeyItemImportExportLineItem> {


    public String title() {
        final BudgetKeyItem existingItem = getBudgetKeyItem();
        if(existingItem != null) {
            return "EXISTING: " + getContainer().titleOf(existingItem);
        }
        return "NEW: " + getUnit();
    }
    
    
    // //////////////////////////////////////
    // ViewModel implementation
    // //////////////////////////////////////
    

    @Override
    public String viewModelMemento() {
        return toDoItemExportImportService.mementoFor(this);
    }

    @Override
    public void viewModelInit(final String mementoStr) {
        toDoItemExportImportService.init(mementoStr, this);
    }

    
    // //////////////////////////////////////
    // budgetKeyItem (optional property)
    // //////////////////////////////////////
    
    private BudgetKeyItem budgetKeyItem;

    @MemberOrder(sequence="1")
    public BudgetKeyItem getBudgetKeyItem() {
        return budgetKeyItem;
    }
    public void setBudgetKeyItem(final BudgetKeyItem budgetKeyItem) {
        this.budgetKeyItem = budgetKeyItem;
    }
    public void modifyBudgetKeyItem(final BudgetKeyItem budgetKeyItem) {
        setBudgetKeyItem(budgetKeyItem);
        setBudgetKeyTable(budgetKeyItem.getBudgetKeyTable());
        setUnit(budgetKeyItem.getUnit());
        setSourceValue(budgetKeyItem.getSourceValue());
        setFoundationValue(budgetKeyItem.getFoundationValue());
        setKeyValue(budgetKeyItem.getKeyValue());
    }

    
    // //////////////////////////////////////
    // budgetKeyTable (property)
    // //////////////////////////////////////

    private BudgetKeyTable budgetKeyTable;

    @MemberOrder(sequence="2")
    public BudgetKeyTable getBudgetKeyTable() {
        return budgetKeyTable;
    }

    public void setBudgetKeyTable(final BudgetKeyTable budgetKeyTable) {
        this.budgetKeyTable = budgetKeyTable;
    }

    // //////////////////////////////////////
    // unit (property)
    // //////////////////////////////////////

    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    // //////////////////////////////////////
    // sourceValue (property)
    // //////////////////////////////////////

    private BigDecimal sourceValue;

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(BigDecimal sourceValue) {
        this.sourceValue = sourceValue;
    }


    // //////////////////////////////////////
    // foundationValue (property)
    // //////////////////////////////////////

    private BigDecimal foundationValue;

    public BigDecimal getFoundationValue() {
        return foundationValue;
    }

    public void setFoundationValue(BigDecimal foundationValue) {
        this.foundationValue = foundationValue;
    }


    // //////////////////////////////////////
    // keyValue (property),
    // //////////////////////////////////////

    private BigDecimal keyValue;

    public BigDecimal getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(BigDecimal keyValue) {
        this.keyValue = keyValue;
    }

    // //////////////////////////////////////
    // apply
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            invokeOn = InvokeOn.OBJECT_AND_COLLECTION
    )
    public BudgetKeyItem apply() {
        BudgetKeyItem item = getBudgetKeyItem();
        if(item == null) {

            // create new item
            item = budgetKeyItems.newBudgetKeyItem(getBudgetKeyTable(), getUnit(), getSourceValue(), getFoundationValue(), getKeyValue());

        } else {
            // copy over new values

            item.setBudgetKeyTable(getBudgetKeyTable());
            item.setUnit(getUnit());
            item.setSourceValue(getSourceValue());
            item.setFoundationValue(getFoundationValue());
            item.setKeyValue(getKeyValue());

        }
        return actionInvocationContext.getInvokedOn().isCollection()? null: item;
    }

    
    // //////////////////////////////////////
    // compareTo
    // //////////////////////////////////////

    @Override
    public int compareTo(final BudgetKeyItemImportExportLineItem other) {
        return this.budgetKeyItem.compareTo(other.budgetKeyItem);
    }

    
    // //////////////////////////////////////
    // injected services
    // //////////////////////////////////////
    
    @javax.inject.Inject
    private BudgetKeyItemImportExportService toDoItemExportImportService;
    
    @javax.inject.Inject
    private BudgetKeyItems budgetKeyItems;

    @javax.inject.Inject
    private ActionInvocationContext actionInvocationContext;
}
