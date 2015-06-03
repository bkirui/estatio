package org.estatio.app.budget;

import java.math.BigDecimal;

import org.estatio.dom.asset.Unit;
import org.estatio.dom.budget.BudgetItem;
import org.estatio.dom.budget.BudgetKeyItem;
import org.estatio.dom.budget.BudgetKeyValueMethod;
import org.estatio.dom.lease.LeaseTermForServiceCharge;
import org.estatio.dom.lease.Occupancy;

/**
 * Created by jodo on 11/05/15.
 */
public class BudgetedValueCalculation {


    public static BigDecimal calculateBudgetedValueContribution(BudgetItem bi, LeaseTermForServiceCharge lt) {
        BigDecimal budgetedValueContrib = new BigDecimal(0);
        for (Occupancy o : lt.getLeaseItem().getLease().getOccupancies()){
            Unit u = o.getUnit();
            for (BudgetKeyItem budgetKeyItem : bi.getBudgetKeyTable().getBudgetKeyItems()){
                if (budgetKeyItem.getUnit().equals(u)){
                    // check Key Value Method
                    if (budgetKeyItem.getBudgetKeyTable().getKeyValueMethod() == BudgetKeyValueMethod.PROMILLE) {
                        budgetedValueContrib = budgetedValueContrib.add(budgetKeyItem.getKeyValue().setScale(2, BigDecimal.ROUND_HALF_DOWN).multiply(bi.getValue().setScale(2, BigDecimal.ROUND_HALF_DOWN).divide(new BigDecimal(1000))));
                    }
                    //TODO: other BudgetKeyValue methods...
                }
            }
        }
        return budgetedValueContrib.setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

}
