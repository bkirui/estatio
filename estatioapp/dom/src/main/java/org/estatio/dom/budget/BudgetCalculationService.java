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
import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.memento.MementoService;

import org.estatio.dom.asset.Property;
import org.estatio.dom.asset.Unit;
import org.estatio.dom.charge.Charge;
import org.estatio.dom.lease.Occupancy;

@DomainService(nature = NatureOfService.VIEW)
public class BudgetCalculationService {

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @MemberOrder(name="Budgets", sequence="90.1")
    public BudgetCalculation calculateBudget(final Budget budget) {

        // init new budgetcalculation view
        BudgetCalculation budgetCalculation = new BudgetCalculation();
        budgetCalculation.setProperty(budget.getProperty());
        budgetCalculation.setStartDate(budget.getStartDate());
        budgetCalculation.setEndDate(budget.getEndDate());

        // for each budgetItem on budget TODO: make query
        for (BudgetItem budgetItem: budgetItems.allBudgetItems()) {

            //init new BudgetCalculationItem
            BudgetCalculationItem budgetCalculationItem = new BudgetCalculationItem();
            budgetCalculationItem.setBudgetCalculation(budgetCalculation);


        }

        return budgetCalculation;

    }

    public List<Budget> autoComplete0CalculateBudget(String string) {
        return budgets.allBudgets();
    }

    // //////////////////////////////////////
    // memento for BudgetCalculation
    // //////////////////////////////////////

    String mementoFor(final BudgetCalculation budgetCalculation) {
        final MementoService.Memento memento = mementoService.create();
        memento.set("property", bookmarkService.bookmarkFor(budgetCalculation.getProperty()));
        memento.set("startDate", budgetCalculation.getStartDate());
        memento.set("endDate", budgetCalculation.getEndDate());
        memento.set("charge", bookmarkService.bookmarkFor(budgetCalculation.getCharge()));
        return memento.asString();
    }

    void initOf(final String mementoStr, final BudgetCalculation budgetCalculation) {
        final MementoService.Memento memento = mementoService.parse(mementoStr);
        budgetCalculation.setProperty(bookmarkService.lookup(memento.get("property", Bookmark.class), Property.class));
        budgetCalculation.setStartDate(memento.get("startDate", LocalDate.class));
        budgetCalculation.setStartDate(memento.get("endDate", LocalDate.class));
        budgetCalculation.setCharge(bookmarkService.lookup(memento.get("charge", Bookmark.class), Charge.class));
    }

    BudgetCalculation newBudgetCalculation(BudgetCalculation budgetCalculation) {
        return container.newViewModelInstance(BudgetCalculation.class, mementoFor(budgetCalculation));
    }

    // //////////////////////////////////////
    // memento for BudgetCalculationItem
    // //////////////////////////////////////

    String mementoFor(final BudgetCalculationItem budgetCalculationItem) {
        final MementoService.Memento memento = mementoService.create();
        memento.set("occupancy", bookmarkService.bookmarkFor(budgetCalculationItem.getOccupancy()));
        memento.set("unit", bookmarkService.bookmarkFor(budgetCalculationItem.getUnit()));
        memento.set("keyValue", budgetCalculationItem.getKeyValue());
        memento.set("calculatedValue", bookmarkService.bookmarkFor(budgetCalculationItem.getCalculatedValue()));
        return memento.asString();
    }

    void initOf(final String mementoStr, final BudgetCalculationItem budgetCalculationItem) {
        final MementoService.Memento memento = mementoService.parse(mementoStr);
        budgetCalculationItem.setOccupancy(bookmarkService.lookup(memento.get("occupancy", Bookmark.class), Occupancy.class));
        budgetCalculationItem.setUnit(bookmarkService.lookup(memento.get("unit", Bookmark.class), Unit.class));
        budgetCalculationItem.setKeyValue(memento.get("keyValue", BigDecimal.class));
        budgetCalculationItem.setCalculatedValue(memento.get("calculatedValue", BigDecimal.class));
    }

    BudgetCalculationItem newBudgetCalculationItem(BudgetCalculationItem budgetCalculationItem) {
        return container.newViewModelInstance(BudgetCalculationItem.class, mementoFor(budgetCalculationItem));
    }

    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private MementoService mementoService;

    @javax.inject.Inject
    private Budgets budgets;

    @javax.inject.Inject
    private BudgetItems budgetItems;


}
