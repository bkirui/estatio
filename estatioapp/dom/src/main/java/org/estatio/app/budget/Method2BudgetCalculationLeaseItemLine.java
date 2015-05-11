package org.estatio.app.budget;

import java.math.BigDecimal;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.util.ObjectContracts;

/**
 * Created by jodo on 11/05/15.
 */
@DomainObject(
        nature = Nature.VIEW_MODEL
)
public class Method2BudgetCalculationLeaseItemLine implements  Comparable<Method2BudgetCalculationLeaseItemLine> {

    public Method2BudgetCalculationLeaseItemLine() {}

    public Method2BudgetCalculationLeaseItemLine(
            final String budgetItemToString,
            final String leaseToString,
            final String unitToString,
            final BigDecimal budgetedValue
    ) {
        this.budgetItemToString = budgetItemToString;
        this.leaseToString = leaseToString;
        this.unitToString = unitToString;
        this.budgetedValue = budgetedValue;
    }

    //region > test (property)
    private String budgetItemToString;

    @MemberOrder(sequence = "1")
    public String getBudgetItemToString() {
        return budgetItemToString;
    }

    public void setBudgetItemToString(final String budgetItemToString) {
        this.budgetItemToString = budgetItemToString;
    }
    //endregion

    //region > leaseToString (property)
    private String leaseToString;

    @MemberOrder(sequence = "2")
    public String getLeaseToString() {
        return leaseToString;
    }

    public void setLeaseToString(final String leaseToString) {
        this.leaseToString = leaseToString;
    }
    //endregion

    //region > leaseItemToString (property)
    private String unitToString;

    @MemberOrder(sequence = "3")
    public String getUnitToString() {
        return unitToString;
    }

    public void setUnitToString(final String unitToString) {
        this.unitToString = unitToString;
    }
    //endregion

    //region > budgetedValue (property)
    private BigDecimal budgetedValue;

    @MemberOrder(sequence = "5")
    public BigDecimal getBudgetedValue() {
        return budgetedValue;
    }

    public void setBudgetedValue(final BigDecimal budgetedValue) {
        this.budgetedValue = budgetedValue;
    }
    //endregion




    @Override public int compareTo(final Method2BudgetCalculationLeaseItemLine o) {
        return ObjectContracts.compare(this, o, "budgetToString, leaseToString, unitToString");
    }
}
