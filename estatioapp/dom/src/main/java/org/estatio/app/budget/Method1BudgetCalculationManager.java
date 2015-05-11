package org.estatio.app.budget;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.RenderType;

import org.estatio.app.EstatioViewModel;
import org.estatio.dom.asset.Property;
import org.estatio.dom.budget.Budget;

/**
 * Created by jodo on 11/05/15.
 */
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class Method1BudgetCalculationManager extends EstatioViewModel {

    public String title() {
        return "Budget Calculation Method1 (by Lease)";
    }

    public Method1BudgetCalculationManager() {}

    public Method1BudgetCalculationManager(final Budget budget) {
        this.budget = budget;
    }

    //region > budget (property)
    private Budget budget;

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(final Budget budget) {
        this.budget = budget;
    }
    //endregion

    public Property getProperty() {
        return this.getBudget().getProperty();
    }

    @CollectionLayout(render = RenderType.EAGERLY)
    public List<Method1BudgetCalculationLeaseItemLine> getMethod1BudgetCalculationLeaseItemLines() {
        return method1BudgetCalculationService.lines();
    }


    @Inject
    Method1BudgetCalculationService method1BudgetCalculationService;
}
