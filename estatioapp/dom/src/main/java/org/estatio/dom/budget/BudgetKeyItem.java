/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.dom.budget;

import java.math.BigDecimal;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.estatio.dom.EstatioDomainObject;
import org.estatio.dom.asset.Unit;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.NATIVE, column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@DomainObject(editing = Editing.DISABLED)
public class BudgetKeyItem extends EstatioDomainObject<BudgetKeyItem> {

    public BudgetKeyItem() {
        super("budgetKeyTable,unit");
    }

    private BudgetKeyTable budgetKeyTable;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BudgetKeyTable getBudgetKeyTable() {
        return budgetKeyTable;
    }

    public void setBudgetKeyTable(BudgetKeyTable budgetKeyTable) {
        this.budgetKeyTable = budgetKeyTable;
    }

    // //////////////////////////////////////

    private Unit unit;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    // //////////////////////////////////////

    private BigDecimal sourceValue;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(BigDecimal sourceValue) {
        this.sourceValue = sourceValue;
    }

    // //////////////////////////////////////

    private BigDecimal foundationValue;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BigDecimal getFoundationValue() {
        return foundationValue;
    }

    public void setFoundationValue(BigDecimal foundationValue) {
        this.foundationValue = foundationValue;
    }

    // //////////////////////////////////////

    private BigDecimal keyValue;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BigDecimal getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(BigDecimal keyValue) {
        this.keyValue = keyValue;
    }

    // //////////////////////////////////////

    @Override public ApplicationTenancy getApplicationTenancy() {
        return getBudgetKeyTable().getApplicationTenancy();
    }
}
