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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.estatio.dom.UdoDomainRepositoryAndFactory;
import org.estatio.dom.charge.Charge;
import org.estatio.dom.currency.Currency;

import java.math.BigDecimal;
import java.util.List;

@DomainService(repositoryFor = BudgetItem.class, nature = NatureOfService.VIEW)
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.PRIMARY, named = "Budgets")
public class BudgetItems extends UdoDomainRepositoryAndFactory<BudgetItem> {

    public BudgetItems() {
        super(BudgetItems.class, BudgetItem.class);
    }

    // //////////////////////////////////////

    public BudgetItem newBudgetItem(
            final @ParameterLayout(named = "Budget") Budget budget,
            final @ParameterLayout(named = "Budget Key Table") BudgetKeyTable budgetKeyTable,
            final @ParameterLayout(named = "Value") BigDecimal value,
            final @ParameterLayout(named = "Currency") Currency currency,
            final @ParameterLayout(named = "Charge") Charge charge,
            final @ParameterLayout(named = "Budget Cost Group") BudgetCostGroup budgetCostGroup) {
        BudgetItem budgetItem = newTransientInstance();
        budgetItem.setBudget(budget);
        budgetItem.setBudgetKeyTable(budgetKeyTable);
        budgetItem.setValue(value);
        budgetItem.setCurrency(currency);
        budgetItem.setCharge(charge);
        budgetItem.setBudgetCostGroup(budgetCostGroup);

        persistIfNotAlready(budgetItem);

        return budgetItem;
    }

    public String validateNewBudgetItem(
            final Budget budget,
            final BudgetKeyTable budgetKeyTable,
            final BigDecimal value,
            final Currency currency,
            final Charge charge,
            final BudgetCostGroup budgetCostGroup) {
        if (value.equals(new BigDecimal(0))) {
            return "Value can't be zero";
        }

        return null;
    }

    // //////////////////////////////////////

    public List<BudgetItem> allBudgetItems() {
        return allInstances();
    }
}
