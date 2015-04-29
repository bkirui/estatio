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

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.ParameterLayout;

import org.estatio.dom.EstatioDomainObject;
import org.estatio.dom.apptenancy.WithApplicationTenancyProperty;
import org.estatio.dom.asset.Unit;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.NATIVE, column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@DomainObject(editing = Editing.DISABLED)
public class BudgetKeyItem extends EstatioDomainObject<BudgetKeyItem> implements WithApplicationTenancyProperty {

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

    public BudgetKeyItem changeSourceValue(final @ParameterLayout(named = "Source value") BigDecimal sourceValue) {
        setSourceValue(sourceValue);
        return this;
    }

    public String validateChangeSourceValue(final BigDecimal sourceValue) {
        if (sourceValue.equals(new BigDecimal(0))) {
            return "Source value can't be zero";
        }
        return null;
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

    public BudgetKeyItem changeFoundationValue(final @ParameterLayout(named = "Foundation value") BigDecimal foundationValue) {
        setFoundationValue(foundationValue);
        return this;
    }

    public String validateChangeFoundationValue(final BigDecimal foundationValue) {
        if (foundationValue.equals(new BigDecimal(0))) {
            return "Foundation value can't be zero";
        }
        return null;
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

    public BudgetKeyItem changeKeyValue(final @ParameterLayout(named = "Key value") BigDecimal keyValue) {
        setKeyValue(keyValue);
        return this;
    }

    public String validateChangeKeyValue(final BigDecimal keyValue) {
        if (keyValue.equals(new BigDecimal(0))) {
            return "Key value can't be zero";
        }
        return null;
    }

    // //////////////////////////////////////

    @Override
    public ApplicationTenancy getApplicationTenancy() {
        return getBudgetKeyTable().getApplicationTenancy();
    }
}
