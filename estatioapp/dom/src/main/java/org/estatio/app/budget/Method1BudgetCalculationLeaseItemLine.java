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
public class Method1BudgetCalculationLeaseItemLine implements  Comparable<Method1BudgetCalculationLeaseItemLine> {

    public Method1BudgetCalculationLeaseItemLine() {}

    public Method1BudgetCalculationLeaseItemLine(
            final String budgetToString,
            final String leaseToString,
            final String leaseItemToString,
            final String leaseTermToString,
            final BigDecimal budgetedValue
            ) {
        this.budgetToString = budgetToString;
        this.leaseToString = leaseToString;
        this.leaseItemToString = leaseItemToString;
        this.leaseTermToString = leaseTermToString;
        this.budgetedValue = budgetedValue;
    }

    //region > test (property)
    private String budgetToString;

    @MemberOrder(sequence = "1")
    public String getBudgetToString() {
        return budgetToString;
    }

    public void setBudgetToString(final String budgetToString) {
        this.budgetToString = budgetToString;
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
    private String leaseItemToString;

    @MemberOrder(sequence = "3")
    public String getLeaseItemToString() {
        return leaseItemToString;
    }

    public void setLeaseItemToString(final String leaseItemToString) {
        this.leaseItemToString = leaseItemToString;
    }
    //endregion

    //region > leaseTermToString (property)
    private String leaseTermToString;

    @MemberOrder(sequence = "4")
    public String getLeaseTermToString() {
        return leaseTermToString;
    }

    public void setLeaseTermToString(final String leaseTermToString) {
        this.leaseTermToString = leaseTermToString;
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




    @Override public int compareTo(final Method1BudgetCalculationLeaseItemLine o) {
        return ObjectContracts.compare(this, o, "budgetToString, leaseToString, leaseItemToString");
    }
}
