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
public class Method2BudgetCalculationManager extends EstatioViewModel {

    public String title() {
        return "Budget Calculation Method2 (by BudgetKeyItem)";
    }

    public Method2BudgetCalculationManager() {}

    public Method2BudgetCalculationManager(final Budget budget) {
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
    public List<Method2BudgetCalculationLeaseItemLine> getMethod2BudgetCalculationLeaseItemLines() {
        return method2BudgetCalculationService.lines();
    }


    @Inject
    Method2BudgetCalculationService method2BudgetCalculationService;
}
