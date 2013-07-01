package org.estatio.dom.lease;

import java.util.List;

import javax.jdo.annotations.VersionStrategy;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.Bulk;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberGroups;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Title;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.annotation.ActionSemantics.Of;

import org.estatio.dom.EstatioTransactionalObject;
import org.estatio.dom.Status;
import org.estatio.dom.WithInterval;
import org.estatio.dom.WithIntervalMutable;
import org.estatio.dom.WithStatus;
import org.estatio.dom.agreement.AgreementRole;
import org.estatio.dom.agreement.AgreementRoleCommunicationChannel;
import org.estatio.dom.tag.Tag;
import org.estatio.dom.tag.Tags;
import org.estatio.dom.valuetypes.LocalDateInterval;

@javax.jdo.annotations.PersistenceCapable
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "VERSION")
@javax.jdo.annotations.Index(
        name = "LEASE_UNIT_IDX",
        members = { "lease", "unit", "startDate" })
@javax.jdo.annotations.Unique(
        name = "LEASE_UNIT_IDX2",
        members = { "lease", "unit", "startDate" })
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByLeaseAndUnitAndStartDate", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.estatio.dom.lease.LeaseUnit "
                        + "WHERE lease == :lease "
                        + "&& unit == :unit "
                        + "&& startDate == :startDate"),
        @javax.jdo.annotations.Query(
                name = "findByLeaseAndUnitAndEndDate", language = "JDOQL",
                value = "SELECT "
                        + "FROM org.estatio.dom.lease.LeaseUnit "
                        + "WHERE lease == :lease "
                        + "&& unit == :unit "
                        + "&& endDate == :endDate"),
})
@MemberGroups({ "General", "Dates", "Tags", "Related" })
public class LeaseUnit extends EstatioTransactionalObject<LeaseUnit, Status> implements WithIntervalMutable<LeaseUnit> {

    public LeaseUnit() {
        super("lease, unit, startDate desc", Status.LOCKED, Status.UNLOCKED);
    }

    // //////////////////////////////////////

    private Status status;

    @MemberOrder(sequence = "4.5")
    @Disabled
    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(final Status status) {
        this.status = status;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name = "LEASE_ID")
    private Lease lease;

    @Title(sequence = "1", append = ":")
    @MemberOrder(sequence = "1")
    @Hidden(where = Where.REFERENCES_PARENT)
    public Lease getLease() {
        return lease;
    }

    public void setLease(final Lease lease) {
        this.lease = lease;
    }

    public void modifyLease(final Lease lease) {
        Lease currentLease = getLease();
        if (lease == null || lease.equals(currentLease)) {
            return;
        }
        lease.addToUnits(this);
    }

    public void clearLease() {
        Lease currentLease = getLease();
        if (currentLease == null) {
            return;
        }
        currentLease.removeFromUnits(this);
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name = "UNIT_ID")
    private UnitForLease unit;

    @Title(sequence = "2", append = ":")
    @MemberOrder(sequence = "2")
    @Hidden(where = Where.REFERENCES_PARENT)
    public UnitForLease getUnit() {
        return unit;
    }

    public void setUnit(final UnitForLease unit) {
        this.unit = unit;
    }

    public void modifyUnit(final UnitForLease unit) {
        UnitForLease currentUnit = getUnit();
        if (unit == null || unit.equals(currentUnit)) {
            return;
        }
        unit.addToLeases(this);
    }

    public void clearUnit() {
        UnitForLease currentUnit = getUnit();
        if (currentUnit == null) {
            return;
        }
        currentUnit.removeFromLeases(this);
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Persistent
    private LocalDate startDate;

    @MemberOrder(name = "Dates", sequence = "3")
    @Optional
    @Disabled
    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    @javax.jdo.annotations.Persistent
    private LocalDate endDate;

    @MemberOrder(name = "Dates", sequence = "4")
    @Disabled
    @Optional
    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    // //////////////////////////////////////

    @MemberOrder(name="endDate", sequence="1")
    @ActionSemantics(Of.IDEMPOTENT)
    @Override
    public LeaseUnit changeDates(
            final @Named("Start Date") LocalDate startDate, 
            final @Named("End Date") LocalDate endDate) {
        setStartDate(startDate);
        setEndDate(endDate);
        return this;
    }

    public String disableChangeDates(
            final LocalDate startDate, 
            final LocalDate endDate) {
        return getStatus().isLocked()? "Cannot modify when locked": null;
    }
    
    @Override
    public LocalDate default0ChangeDates() {
        return getStartDate();
    }
    @Override
    public LocalDate default1ChangeDates() {
        return getEndDate();
    }
    
    @Override
    public String validateChangeDates(
            final LocalDate startDate, 
            final LocalDate endDate) {
        return startDate.isBefore(endDate)?null:"Start date must be before end date";
    }

    // //////////////////////////////////////

    @Override
    @Programmatic
    public LocalDateInterval getInterval() {
        return LocalDateInterval.including(getStartDate(), getEndDate());
    }

    @Hidden
    // TODO (where=Where.ALL_TABLES)
    @MemberOrder(name = "Related", sequence = "9.1")
    @Named("Previous Occupany")
    @Disabled
    @Optional
    @Override
    public LeaseUnit getPrevious() {
        return null;
    }

    @Hidden
    // TODO (where=Where.ALL_TABLES)
    @MemberOrder(name = "Related", sequence = "9.2")
    @Named("Next Occupancy")
    @Disabled
    @Optional
    @Override
    public LeaseUnit getNext() {
        return null;
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name = "BRANDTAG_ID")
    private Tag brandTag;

    @Hidden
    public Tag getBrandTag() {
        return brandTag;
    }

    public void setBrandTag(final Tag brandTag) {
        this.brandTag = brandTag;
    }

    @MemberOrder(name = "Tags", sequence = "6")
    @Optional
    public String getBrand() {
        final Tag existingTag = getBrandTag();
        return existingTag != null ? existingTag.getValue() : null;
    }

    public void setBrand(final String brand) {
        final Tag existingTag = getBrandTag();
        Tag tag = tags.tagFor(existingTag, this, "brand", brand);
        setBrandTag(tag);
    }

    public List<String> choicesBrand() {
        return tags.choices(this, "brand");
    }

    @MemberOrder(name = "Brand", sequence = "6.1")
    public LeaseUnit newBrand(@Named("Tag") @Optional final String brand) {
        setBrand(brand);
        return this;
    }

    public String default0NewBrand() {
        return getBrand();
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name = "SECTORTAG_ID")
    private Tag sectorTag;

    @Hidden
    public Tag getSectorTag() {
        return sectorTag;
    }

    public void setSectorTag(final Tag sectorTag) {
        this.sectorTag = sectorTag;
    }

    @MemberOrder(name = "Tags", sequence = "7")
    @Optional
    public String getSector() {
        final Tag existingTag = getSectorTag();
        return existingTag != null ? existingTag.getValue() : null;
    }

    public void setSector(final String sector) {
        final Tag existingTag = getSectorTag();
        Tag tag = tags.tagFor(existingTag, this, "sector", sector);
        setSectorTag(tag);
    }

    public List<String> choicesSector() {
        return tags.choices(this, "sector");
    }

    @MemberOrder(name = "Sector", sequence = "7.1")
    public LeaseUnit newSector(@Named("Tag") @Optional final String sector) {
        setSector(sector);
        return this;
    }

    public String default0NewSector() {
        return getSector();
    }

    // //////////////////////////////////////

    @javax.jdo.annotations.Column(name = "ACTIVITYTAG_ID")
    private Tag activityTag;

    @Hidden
    public Tag getActivityTag() {
        return activityTag;
    }

    public void setActivityTag(final Tag activityTag) {
        this.activityTag = activityTag;
    }

    @MemberOrder(name = "Tags", sequence = "8")
    @Optional
    public String getActivity() {
        final Tag existingTag = getActivityTag();
        return existingTag != null ? existingTag.getValue() : null;
    }

    public void setActivity(final String activity) {
        final Tag existingTag = getActivityTag();
        Tag tag = tags.tagFor(existingTag, this, "activity", activity);
        setActivityTag(tag);
    }

    public List<String> choicesActivity() {
        return tags.choices(this, "activity");
    }

    @MemberOrder(name = "Activity", sequence = "8.1")
    public LeaseUnit newActivity(@Named("Tag") @Optional final String activity) {
        setActivity(activity);
        return this;
    }

    public String default0NewActivity() {
        return getActivity();
    }

    // //////////////////////////////////////

    private Tags tags;

    public void injectTags(final Tags tags) {
        this.tags = tags;
    }

}
