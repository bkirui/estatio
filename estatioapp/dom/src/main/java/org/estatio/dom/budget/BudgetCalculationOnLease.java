/*
 *  Copyright 2012-2015 Eurocommercial Properties NV
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

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.estatio.app.EstatioViewModel;
import org.estatio.dom.asset.Property;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.LeaseItem;
import org.estatio.dom.lease.LeaseItemType;
import org.estatio.dom.lease.LeaseTermForServiceCharge;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named ="Budget calculation",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class BudgetCalculationOnLease extends EstatioViewModel {

    public BudgetCalculationOnLease(Lease lease){
        this.setLeaseItem(lease.findFirstItemOfType(LeaseItemType.SERVICE_CHARGE));
    }

    public String title() {
        return "Budget calculation";
    }

    //region > property (property)
    private Property property;

    @MemberOrder(sequence = "1")
    public Property getProperty() {
        return property;
    }

    public void setProperty(final Property property) {
        this.property = property;
    }
    //endregion

    //region > startdate (property)
    private LocalDate startdate;

    @MemberOrder(sequence = "2")
    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(final LocalDate startdate) {
        this.startdate = startdate;
    }
    //endregion

    //region > endDate (property)
    private LocalDate endDate;

    @MemberOrder(sequence = "3")
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }
    //endregion

    //region > leaseItem (property)
    private LeaseItem leaseItem;

    @MemberOrder(sequence = "5")
    public LeaseItem getLeaseItem() {
        return leaseItem;
    }

    public void setLeaseItem(final LeaseItem leaseItem) {
        this.leaseItem = leaseItem;
    }
    //endregion

    //region > Term (property)
    private LeaseTermForServiceCharge term;

    @MemberOrder(sequence = "6")
    public LeaseTermForServiceCharge getTerm() {
        return term;
    }

    public void setTerm(final LeaseTermForServiceCharge term) {
        this.term = term;
    }
    //endregion

    public List<BudgetCalculationItem> budgetCalculationItems = new ArrayList<BudgetCalculationItem>();

    @SuppressWarnings("unchecked")
    @Collection
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<BudgetCalculationItem> getBudgetCalculationItems() {
        return budgetCalculationItems;
    }


}
