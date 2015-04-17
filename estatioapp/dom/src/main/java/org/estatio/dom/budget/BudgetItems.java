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

import org.apache.isis.applib.annotation.DomainService;
import org.estatio.dom.UdoDomainRepositoryAndFactory;
import org.estatio.dom.charge.Charge;
import org.estatio.dom.currency.Currency;

import java.math.BigDecimal;

@DomainService(repositoryFor = BudgetItem.class)
public class BudgetItems extends UdoDomainRepositoryAndFactory<BudgetItem> {

    public BudgetItems() {
        super(BudgetItems.class, BudgetItem.class);
    }

    // //////////////////////////////////////

    public BudgetItem newBudgetItem(
            final Budget budget,
            final BudgetKeyTable budgetKeyTable,
            final BigDecimal value,
            final Currency currency,
            final Charge charge) {
        BudgetItem budgetItem = newTransientInstance();
        budgetItem.setBudget(budget);
        budgetItem.setBudgetKeyTable(budgetKeyTable);
        budgetItem.setValue(value);
        budgetItem.setCurrency(currency);
        budgetItem.setCharge(charge);
        persistIfNotAlready(budgetItem);

        return budgetItem;
    }
}
