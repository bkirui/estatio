/*
 *
 *  Copyright 2012-2015 Eurocommercial Properties NV
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
package org.estatio.dom.budget;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;

public enum BudgetKeyValueMethod {
    MILLESIMI{
        @Override
        public BigDecimal calculate(final BigDecimal numerator, final BigDecimal denominator) {
            return numerator.multiply(new BigDecimal(1000)).divide(denominator, MathContext.DECIMAL32);
        }
        @Override
        public boolean isValid(BudgetKeyTable budgetKeyTable) {
                BigDecimal sum = new BigDecimal(0);
                for (Iterator<BudgetKeyItem> it = budgetKeyTable.getBudgetKeyItems().iterator(); it.hasNext();) {
                    sum = sum.add(it.next().getKeyValue());
                }
                if (!sum.equals(new BigDecimal(1000))) {
                    return false;
                }
            return true;
        }
    },
    ITA{
        @Override
        public BigDecimal calculate(final BigDecimal numerator, final BigDecimal denominator) {
            return numerator.divide(denominator, MathContext.DECIMAL32);
        }
        @Override
        public boolean isValid(BudgetKeyTable budgetKeyTable) {
            return true;
        }
    };

    public abstract BigDecimal calculate(final BigDecimal numerator, final BigDecimal denominator);

    public abstract boolean isValid(BudgetKeyTable budgetKeyTable);
}
