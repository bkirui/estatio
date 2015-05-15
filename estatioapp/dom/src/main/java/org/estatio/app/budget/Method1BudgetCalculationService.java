package org.estatio.app.budget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;

import org.estatio.dom.budget.Budget;
import org.estatio.dom.budget.BudgetItem;
import org.estatio.dom.budget.Budgets;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.LeaseItem;
import org.estatio.dom.lease.LeaseItemType;
import org.estatio.dom.lease.LeaseTermForServiceCharge;
import org.estatio.dom.lease.Leases;

/**
 * Created by jodo on 11/05/15.
 */
@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.PRIMARY, named = "Budgets")
public class Method1BudgetCalculationService {

    List<Method1BudgetCalculationLeaseItemLine> lines = new ArrayList<Method1BudgetCalculationLeaseItemLine>();

    public Method1BudgetCalculationManager calculateBudgetMethod1(
            final @ParameterLayout(named = "budget") Budget budget){

        Method1BudgetCalculationManager manager = new Method1BudgetCalculationManager(budget);
        List<Method1BudgetCalculationLeaseItemLine> newLines = new ArrayList<Method1BudgetCalculationLeaseItemLine>();

        List<Lease> leasesOnProperty = leases.findLeasesByProperty(budget.getProperty());

        for (Lease l: leasesOnProperty) {

            LeaseItem li = l.findFirstItemOfType(LeaseItemType.SERVICE_CHARGE);
            if (li != null) {

                LeaseTermForServiceCharge lt = (LeaseTermForServiceCharge) li.currentTerm(budget.getStartDate());

                // if no current term, create a new one
                // Is this a possible situation anyway?
                if (lt == null) {
                    lt = new LeaseTermForServiceCharge();
                    lt.setStartDate(budget.getStartDate());
                    lt.setEndDate(budget.getEndDate());
                    lt.setLeaseItem(li);
                    // TODO: etcetera... en persist??
                }

                // set budgeted Value to zero
                lt.setBudgetedValue(new BigDecimal(0));

                for (BudgetItem bi : budget.getBudgetItems()) {

                    // set new budgeted Value (first budgetItem) or add to it (next budgetItem)
                    lt.setBudgetedValue(lt.getBudgetedValue().add(BudgetedValueCalculation.calculateBudgetedValueContribution(bi, lt)));

                    Method1BudgetCalculationLeaseItemLine newLine = new Method1BudgetCalculationLeaseItemLine(
                            bi.toString(),
                            l.toString(),
                            li.toString(),
                            lt.toString(),
                            lt.getBudgetedValue()
                    );
                    newLines.add(newLine);
                }

            }

        }




        this.lines = newLines;
        return manager;
    }


    public List<Budget> autoComplete0CalculateBudgetMethod1(final String search) {
        return budgets.allBudgets();
    }


    //////////////////////////////////

    @Programmatic
    public List<Method1BudgetCalculationLeaseItemLine> lines() {
        return lines;
    }


    @Inject
    Budgets budgets;

    @Inject
    Leases leases;

}
