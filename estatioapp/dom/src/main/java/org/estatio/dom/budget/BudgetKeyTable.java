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

import org.estatio.dom.asset.Property;
import org.joda.time.LocalDate;

public class BudgetKeyTable {

    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    // //////////////////////////////////////

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // //////////////////////////////////////

    private LocalDate startDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // //////////////////////////////////////

    private LocalDate endDate;

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    // //////////////////////////////////////

    private BudgetFoundationValueType foundationValueType;

    public BudgetFoundationValueType getFoundationValueType() {
        return foundationValueType;
    }

    public void setFoundationValueType(BudgetFoundationValueType foundationValueType) {
        this.foundationValueType = foundationValueType;
    }

    // //////////////////////////////////////

    private BudgetKeyValueMethod keyValueMethod;

    public BudgetKeyValueMethod getKeyValueMethod() {
        return keyValueMethod;
    }

    public void setKeyValueMethod(BudgetKeyValueMethod keyValueMethod) {
        this.keyValueMethod = keyValueMethod;
    }
}
