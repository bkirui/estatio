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
import org.estatio.dom.asset.Property;
import org.joda.time.LocalDate;

@DomainService(repositoryFor = Budget.class)
public class Budgets extends UdoDomainRepositoryAndFactory<Budget> {

    public Budgets() {
        super(Budgets.class, Budget.class);
    }

    // //////////////////////////////////////

    public Budget newBudget(
            final Property property,
            final LocalDate startDate,
            final LocalDate endDate) {
        Budget budget = newTransientInstance();
        budget.setProperty(property);
        budget.setStartDate(startDate);
        budget.setEndDate(endDate);
        persistIfNotAlready(budget);

        return budget;
    }
}
