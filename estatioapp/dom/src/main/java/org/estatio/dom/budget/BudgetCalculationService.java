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

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.estatio.dom.asset.Property;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.Leases;
import org.estatio.dom.lease.Occupancy;

@DomainService(nature = NatureOfService.VIEW)
public class BudgetCalculationService {

    public BudgetCalculationOnProperty calculateBudget(final Property property) {

        // init new budgetcalculation view
        BudgetCalculationOnProperty budgetCalculationOnProperty = new BudgetCalculationOnProperty(property);

        // for each lease
        for (Lease lease: leases.findLeasesByProperty(property)) {

            //init new calculation on lease
            BudgetCalculationOnLease budgetCalculationOnLease = new BudgetCalculationOnLease(lease);

            //for every occupancy
            // TODO: refine => only occupances in period
            for (Occupancy occupancy: lease.getOccupancies()){

                //init new budget calulation on occupancy
                BudgetCalculationItem budgetCalculationItem = new BudgetCalculationItem(occupancy);



                budgetCalculationOnLease.budgetCalculationItems.add(budgetCalculationItem);


            }

            //add calculation ons lease to calculation on property
            budgetCalculationOnProperty.budgetCalculationOnLeasesItems.add(budgetCalculationOnLease);

        }

        return budgetCalculationOnProperty;

    }

    @Inject
    Leases leases;

}
