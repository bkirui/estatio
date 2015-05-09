/*
 * Copyright 2015 Yodo Int. Projects and Consultancy
 *
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.estatio.dom.budget;

import java.math.BigDecimal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by jodo on 01/05/15.
 */
public class BudgetKeyValueMethodTest {

    @Test
    public void testCalculate(){

        BudgetKeyValueMethod method = BudgetKeyValueMethod.MILLESIMI;
        BudgetKeyValueMethod method2 = BudgetKeyValueMethod.ITA;

        assertFalse(method.equals(method2));
        assertTrue(method.calculate(new BigDecimal(1), new BigDecimal(1)).equals(new BigDecimal(1000)));
        assertTrue(method2.calculate(new BigDecimal(1), new BigDecimal(1)).equals(new BigDecimal(1)));

        assertEquals(method.calculate(new BigDecimal(1), new BigDecimal(10)), new BigDecimal(100));
        assertEquals(method.calculate(new BigDecimal(1), new BigDecimal(100)),new BigDecimal(10));
        assertEquals(method.calculate(new BigDecimal(1), new BigDecimal(1000)),new BigDecimal(1));
        assertEquals(method.calculate(new BigDecimal(1), new BigDecimal(10000)),new BigDecimal(0.1).setScale(1, BigDecimal.ROUND_HALF_DOWN));
        assertEquals(method.calculate(new BigDecimal(1), new BigDecimal(100000)),new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_DOWN));

        assertEquals(method2.calculate(new BigDecimal(1), new BigDecimal(10)), new BigDecimal(0.1).setScale(1, BigDecimal.ROUND_HALF_DOWN));
        assertEquals(method2.calculate(new BigDecimal(1), new BigDecimal(100)),new BigDecimal(0.01).setScale(2, BigDecimal.ROUND_HALF_DOWN));
    }

}
