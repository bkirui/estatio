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

import org.estatio.dom.asset.Unit;

import java.math.BigDecimal;

public class BudgetKeyItem {

    private BudgetKeyTable budgetKeyTable;

    public BudgetKeyTable getBudgetKeyTable() {
        return budgetKeyTable;
    }

    public void setBudgetKeyTable(BudgetKeyTable budgetKeyTable) {
        this.budgetKeyTable = budgetKeyTable;
    }

    // //////////////////////////////////////

    private Unit unit;

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    // //////////////////////////////////////

    private BigDecimal sourceValue;

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(BigDecimal sourceValue) {
        this.sourceValue = sourceValue;
    }

    // //////////////////////////////////////

    private BigDecimal foundationValue;

    public BigDecimal getFoundationValue() {
        return foundationValue;
    }

    public void setFoundationValue(BigDecimal foundationValue) {
        this.foundationValue = foundationValue;
    }

    // //////////////////////////////////////

    private BigDecimal keyValue;

    public BigDecimal getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(BigDecimal keyValue) {
        this.keyValue = keyValue;
    }
}
