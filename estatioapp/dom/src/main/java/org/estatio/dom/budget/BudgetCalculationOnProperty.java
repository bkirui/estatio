/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
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

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named ="Budget calculation on property",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class BudgetCalculationOnProperty extends EstatioViewModel {

    public BudgetCalculationOnProperty(final Property property) {
        this.setProperty(property);
    }

    public String title() {
        return "Budget calculation on property";
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

    public List<BudgetCalculationOnLease> budgetCalculationOnLeasesItems = new ArrayList<BudgetCalculationOnLease>();

    @SuppressWarnings("unchecked")
    @Collection
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<BudgetCalculationOnLease> getBudgetCalculationOnLeaseItems() {
        return budgetCalculationOnLeasesItems;
    }


}
