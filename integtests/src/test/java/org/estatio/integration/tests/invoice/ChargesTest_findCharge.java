/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.estatio.integration.tests.invoice;

import org.estatio.dom.charge.Charge;
import org.estatio.dom.charge.Charges;
import org.estatio.fixture.EstatioBaseLineFixture;
import org.estatio.integration.tests.EstatioIntegrationTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ChargesTest_findCharge extends EstatioIntegrationTest {

    @Before
    public void setupData() {
        scenarioExecution().install(new EstatioBaseLineFixture());
    }

    private Charges charges;

    @Before
    public void setUp() throws Exception {
        charges = service(Charges.class);
    }
    
    @Test
    public void whenExists() throws Exception {
        Charge charge = charges.findCharge("RENT");
        Assert.assertEquals(charge.getReference(), "RENT");
    }


}