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

import org.estatio.dom.charge.Charge;
import org.estatio.dom.currency.Currency;

import java.math.BigDecimal;

public class BudgetItem {

    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    // //////////////////////////////////////

    private BudgetKeyTable budgetKeyTable;

    public BudgetKeyTable getBudgetKeyTable() {
        return budgetKeyTable;
    }

    public void setBudgetKeyTable(BudgetKeyTable budgetKeyTable) {
        this.budgetKeyTable = budgetKeyTable;
    }

    // //////////////////////////////////////

    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    // //////////////////////////////////////

    private Currency currency;

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    // //////////////////////////////////////

    private Charge charge;

    public Charge getCharge() {
        return charge;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

}