package org.estatio.dom.index;

import java.math.BigDecimal;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;

import org.estatio.dom.EstatioRefDataObject;
import org.estatio.dom.Status;
import org.estatio.dom.WithStartDate;
import org.estatio.dom.WithStatus;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Query(name = "findByIndexAndStartDate", language = "JDOQL", value = "SELECT FROM org.estatio.dom.index.IndexValue WHERE indexBase.index == :index && startDate >= :startDate")
public class IndexValue extends EstatioRefDataObject<IndexValue> implements WithStartDate {

    public IndexValue() {
        super("indexBase, startDate desc");
    }
    
    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    private LocalDate startDate;

    @MemberOrder(sequence = "1")
    @Title(sequence = "2", prepend = ":")
    @Disabled
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name="INDEXBASE_ID")
    private IndexBase indexBase;

    @Hidden(where = Where.PARENTED_TABLES)
    @Title(sequence = "2")
    @MemberOrder(sequence = "2")
    @Disabled
    public IndexBase getIndexBase() {
        return indexBase;
    }

    public void setIndexBase(final IndexBase indexBase) {
        this.indexBase = indexBase;
    }

    public void modifyIndexBase(final IndexBase indexBase) {
        IndexBase currentIndexBase = getIndexBase();
        if (indexBase == null || indexBase.equals(currentIndexBase)) {
            return;
        }
        indexBase.addToValues(this);
    }

    public void clearIndexBase() {
        IndexBase currentIndexBase = getIndexBase();
        if (currentIndexBase == null) {
            return;
        }
        currentIndexBase.removeFromValues(this);
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(scale = 4)
    private BigDecimal value;

    @MemberOrder(sequence = "4")
    @Disabled
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(final BigDecimal value) {
        this.value = value;
    }

    // //////////////////////////////////////

    @Prototype
    public void remove() {
        getContainer().remove(this);
    }


}