package org.estatio.app.budget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.ParameterLayout;

import org.estatio.dom.asset.Unit;
import org.estatio.dom.budget.Budget;
import org.estatio.dom.budget.BudgetItem;
import org.estatio.dom.budget.BudgetKeyItem;
import org.estatio.dom.budget.BudgetKeyTable;
import org.estatio.dom.budget.Budgets;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.LeaseItemType;
import org.estatio.dom.lease.LeaseTermForServiceCharge;
import org.estatio.dom.lease.Leases;
import org.estatio.dom.lease.Occupancies;
import org.estatio.dom.lease.Occupancy;

/**
 * Created by jodo on 11/05/15.
 */
@DomainService()
@DomainServiceLayout(menuBar = DomainServiceLayout.MenuBar.PRIMARY, named = "Budgets")
public class Method2BudgetCalculationService {

    List<Method2BudgetCalculationLeaseItemLine> lines = new ArrayList<Method2BudgetCalculationLeaseItemLine>();

    public Method2BudgetCalculationManager calculateBudgetMethod2(
            final @ParameterLayout(named = "budget") Budget budget) {

        Method2BudgetCalculationManager manager = new Method2BudgetCalculationManager(budget);
        List<Method2BudgetCalculationLeaseItemLine> newLines = new ArrayList<Method2BudgetCalculationLeaseItemLine>();

        List<Lease> leasesOnProperty = leases.findLeasesByProperty(budget.getProperty());

        //INIT: Clear all budgetedValues on current leaseTermForServiceCharges
        for (Lease l : leasesOnProperty) {
            LeaseTermForServiceCharge leaseTermForServiceCharge = (LeaseTermForServiceCharge) l.findFirstItemOfType(LeaseItemType.SERVICE_CHARGE).currentTerm(budget.getStartDate());
            leaseTermForServiceCharge.setBudgetedValue(new BigDecimal(0));
        }

        for (BudgetItem bi : budget.getBudgetItems()) {

            BudgetKeyTable budgetKeyTable = bi.getBudgetKeyTable();
            for (Iterator<BudgetKeyItem> it = budgetKeyTable.getBudgetKeyItems().iterator(); it.hasNext(); ) {

                Unit u = it.next().getUnit();
                Boolean unitFound = false;
                Lease leaseFound = null;
                Unit foundUnit = null;
                LeaseTermForServiceCharge foundTerm = null;
                //find term for this unit
                for (Lease l : leasesOnProperty) {
                    for (Occupancy o : l.getOccupancies()) {

                        if (u.equals(o.getUnit())) {

                            unitFound = true;
                            leaseFound = l;
                            foundUnit = u;
                            //TODO: other lease Item Types depending on BudgetItem charge
                            foundTerm = (LeaseTermForServiceCharge) o.getLease().findFirstItemOfType(LeaseItemType.SERVICE_CHARGE).currentTerm(budget.getStartDate());
                            foundTerm.setBudgetedValue(foundTerm.getBudgetedValue().add(BudgetedValueCalculation.calculateBudgetedValueContribution(bi, foundTerm)));

                        }

                    }
                }

                if (unitFound) {
                    Method2BudgetCalculationLeaseItemLine newLine = new Method2BudgetCalculationLeaseItemLine(
                            bi.toString(),
                            leaseFound.toString(),
                            foundUnit.toString(),
                            foundTerm.getBudgetedValue()
                    );
                    newLines.add(newLine);
                } else {
                    Method2BudgetCalculationLeaseItemLine newLine = new Method2BudgetCalculationLeaseItemLine(
                            bi.toString(),
                            "unit not found on a lease: ",
                            u.toString(),
                            new BigDecimal(0)
                    );
                    newLines.add(newLine);
                }

            }

        }

        this.lines = newLines;
        return manager;
    }


    public List<Budget> autoComplete0CalculateBudgetMethod2(final String search) {
        return budgets.allBudgets();
    }

    //////////////////////////////////


    public List<Method2BudgetCalculationLeaseItemLine> lines() {
        return lines;
    }


    @Inject
    Budgets budgets;

    @Inject
    Leases leases;

    @Inject
    Occupancies occupancies;

}
