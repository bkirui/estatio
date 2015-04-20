/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
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

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.RenderType;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;

import org.estatio.dom.EstatioDomainObject;
import org.estatio.dom.asset.Property;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = IdGeneratorStrategy.NATIVE, column = "id")
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@DomainObject(editing = Editing.DISABLED, autoCompleteRepository = BudgetKeyTables.class)
public class BudgetKeyTable extends EstatioDomainObject<Budget> {

    public BudgetKeyTable() {
        super("property, name, startDate, endDate");
    }

    private Property property;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    // //////////////////////////////////////

    private String name;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // //////////////////////////////////////

    private LocalDate startDate;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // //////////////////////////////////////

    private LocalDate endDate;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // //////////////////////////////////////

    private BudgetFoundationValueType foundationValueType;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BudgetFoundationValueType getFoundationValueType() {
        return foundationValueType;
    }

    public void setFoundationValueType(BudgetFoundationValueType foundationValueType) {
        this.foundationValueType = foundationValueType;
    }

    // //////////////////////////////////////

    private BudgetKeyValueMethod keyValueMethod;

    @javax.jdo.annotations.Column(allowsNull = "false")
    public BudgetKeyValueMethod getKeyValueMethod() {
        return keyValueMethod;
    }

    public void setKeyValueMethod(BudgetKeyValueMethod keyValueMethod) {
        this.keyValueMethod = keyValueMethod;
    }

    // //////////////////////////////////////

    private SortedSet<BudgetKeyItem> budgetKeyItems = new TreeSet<BudgetKeyItem>();

    @CollectionLayout(render= RenderType.EAGERLY)
    @Persistent(mappedBy = "budgetKeyTable", dependentElement = "true")
    public SortedSet<BudgetKeyItem> getBudgetKeyItems() {
        return budgetKeyItems;
    }

    public void setBudgetKeyItems(final SortedSet<BudgetKeyItem> budgetKeyItems) {
        this.budgetKeyItems = budgetKeyItems;
    }

    // //////////////////////////////////////

    @Override public ApplicationTenancy getApplicationTenancy() {
        return getProperty().getApplicationTenancy();
    }
}
