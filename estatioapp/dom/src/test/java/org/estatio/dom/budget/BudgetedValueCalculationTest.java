package org.estatio.dom.budget;

import java.math.BigDecimal;

import org.junit.Test;

import org.estatio.app.budget.BudgetedValueCalculation;
import org.estatio.dom.asset.Unit;
import org.estatio.dom.lease.Lease;
import org.estatio.dom.lease.LeaseItem;
import org.estatio.dom.lease.LeaseTermForServiceCharge;
import org.estatio.dom.lease.Occupancy;

import static org.junit.Assert.assertEquals;

/**
 * Created by jodo on 11/05/15.
 */

//TODO: TEST NOT WORKING ...
public class BudgetedValueCalculationTest {

    @Test
    public void calculateBudgetedValueContributionTest() throws Exception {

        //given
        BudgetItem budgetItem = new BudgetItem();
        BudgetKeyTable budgetKeyTable = new BudgetKeyTable();
        BudgetKeyItem budgetKeyItem = new BudgetKeyItem();
        Unit unit = new UnitForTesting();
        LeaseTermForServiceCharge leaseTermForServiceCharge = new LeaseTermForServiceCharge();
        LeaseItem leaseItem = new LeaseItem();
        Lease lease = new Lease();
        Occupancy occupancy = new Occupancy();


        budgetKeyTable.setKeyValueMethod(BudgetKeyValueMethod.PROMILLE);
        budgetKeyItem.setUnit(unit);
        budgetKeyItem.setBudgetKeyTable(budgetKeyTable);
        budgetKeyItem.setKeyValue(new BigDecimal(100.00).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        budgetItem.setBudgetKeyTable(budgetKeyTable);
        budgetItem.setValue(new BigDecimal(1000));

        leaseTermForServiceCharge.setBudgetedValue(new BigDecimal(1111).setScale(2, BigDecimal.ROUND_HALF_DOWN)); //arbitrary value that has to be whiped
        leaseTermForServiceCharge.setLeaseItem(leaseItem);
        leaseItem.setLease(lease);
        occupancy.setLease(lease);
        occupancy.setUnit(unit);

        // when
        assertEquals(leaseTermForServiceCharge.getBudgetedValue(), new BigDecimal(1111.00).setScale(2, BigDecimal.ROUND_HALF_DOWN));
        BudgetedValueCalculation budgetedValueCalculation = new BudgetedValueCalculation();
        BigDecimal outCome = new BigDecimal(0);

        outCome = budgetedValueCalculation.calculateBudgetedValueContribution(budgetItem,leaseTermForServiceCharge);

        //then
//        assertEquals(outCome,new BigDecimal(10.00).setScale(2, BigDecimal.ROUND_HALF_DOWN));


    }

}
