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

import java.util.List;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.estatio.dom.UdoDomainRepositoryAndFactory;
import org.estatio.dom.asset.Property;
import org.estatio.dom.valuetypes.LocalDateInterval;

@DomainService(repositoryFor = BudgetKeyTable.class, nature = NatureOfService.VIEW)
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.PRIMARY, named = "Budgets")
public class BudgetKeyTableContributions extends UdoDomainRepositoryAndFactory<BudgetKeyTable> {

    public BudgetKeyTableContributions() {
        super(BudgetKeyTableContributions.class, BudgetKeyTable.class);
    }

    // //////////////////////////////////////

    public BudgetKeyTable newBudgetKeyTable(
            final @ParameterLayout(named = "Property") Property property,
            final @ParameterLayout(named = "Name") String name,
            final @ParameterLayout(named = "Start Date") LocalDate startDate,
            final @ParameterLayout(named = "End Date") LocalDate endDate,
            final @ParameterLayout(named = "Foundation Value Type") BudgetFoundationValueType foundationValueType,
            final @ParameterLayout(named = "Key Value Method") BudgetKeyValueMethod keyValueMethod) {
        BudgetKeyTable budgetKeyTable = newTransientInstance();
        budgetKeyTable.setProperty(property);
        budgetKeyTable.setName(name);
        budgetKeyTable.setStartDate(startDate);
        budgetKeyTable.setEndDate(endDate);
        budgetKeyTable.setFoundationValueType(foundationValueType);
        budgetKeyTable.setKeyValueMethod(keyValueMethod);
        persistIfNotAlready(budgetKeyTable);

        return budgetKeyTable;
    }

    public String validateNewBudgetKeyTable(
            final Property property,
            final String name,
            final LocalDate startDate,
            final LocalDate endDate,
            final BudgetFoundationValueType foundationValueType,
            final BudgetKeyValueMethod keyValueMethod) {
        if (!new LocalDateInterval(startDate, endDate).isValid()) {
            return "End date can not be before start date";
        }

        return null;
    }

    // //////////////////////////////////////

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(contributed = Contributed.AS_ASSOCIATION)
    @CollectionLayout(render = RenderType.LAZILY)
    public List<BudgetKeyTable> budgetKeyTables(Property property){
        return budgetKeyTables.findBudgetKeyTableByProperty(property);
    }


    // //////////////////////////////////////

    @Inject
    BudgetKeyTables budgetKeyTables;

}
