package org.estatio.dom.agreement;

import org.junit.Test;

import org.estatio.dom.AbstractBeanPropertiesTest;
import org.estatio.dom.Lockable;
import org.estatio.dom.PojoTester.FixtureDatumFactory;
import org.estatio.dom.party.Party;
import org.estatio.dom.party.PartyForTesting;

public class AgreementRoleTest_beanProperties extends AbstractBeanPropertiesTest {

    @Test
    public void test() {
        final AgreementRole agreement = new AgreementRole();
        newPojoTester()
            .withFixture(pojos(AgreementRoleType.class))
            .withFixture(pojos(Agreement.class, AgreementForTesting.class))
            .withFixture(pojos(Party.class, PartyForTesting.class))
            .withFixture(statii())
            .exercise(agreement);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static FixtureDatumFactory<Lockable> statii() {
        return new FixtureDatumFactory(Lockable.class, (Object[])org.estatio.dom.Status.values());
    }

    
}
