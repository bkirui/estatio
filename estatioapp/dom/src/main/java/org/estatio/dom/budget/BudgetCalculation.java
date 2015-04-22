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

import java.util.List;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.estatio.app.EstatioViewModel;
import org.estatio.dom.asset.Property;
import org.estatio.dom.charge.Charge;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named ="Budget calculation on property",
        bookmarking = BookmarkPolicy.AS_ROOT
)
public class BudgetCalculation extends EstatioViewModel {

    public BudgetCalculation() {

    }

    public String title() {
        return "Budget calculation";
    }

    // //////////////////////////////////////
    // ViewModel implementation
    // //////////////////////////////////////

    public String viewModelMemento() {
        return budgetCalculationService.mementoFor(this);
    }

    public void viewModelInit(final String mementoStr) {
        budgetCalculationService.initOf(mementoStr, this);
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

    //region > startDate (property)
    private LocalDate startDate;

    @MemberOrder(sequence = "2")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
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

    //region > charge (property)
    private Charge charge;

    @MemberOrder(sequence = "1")
    public Charge getCharge() {
        return charge;
    }

    public void setCharge(final Charge charge) {
        this.charge = charge;
    }
    //endregion

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<BudgetCalculationItem> getBudgetCalculationItems(){
        return null;
    }

    // //////////////////////////////////////
    // injected services
    // //////////////////////////////////////

    @javax.inject.Inject
    private BudgetCalculationService budgetCalculationService;


}
