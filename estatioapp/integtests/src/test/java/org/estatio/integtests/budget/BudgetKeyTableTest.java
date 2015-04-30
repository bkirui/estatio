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
package org.estatio.integtests.budget;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import org.estatio.dom.budget.BudgetFoundationValueType;
import org.estatio.dom.budget.BudgetKeyTable;
import org.estatio.dom.budget.BudgetKeyTables;
import org.estatio.dom.budget.BudgetKeyValueMethod;
import org.estatio.fixture.EstatioOperationalTeardownFixture;
import org.estatio.fixture.budget.BudgetKeyTableForOxf;
import org.estatio.integtests.EstatioIntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class BudgetKeyTableTest extends EstatioIntegrationTest {


    @Before
    public void setupData() {
        runFixtureScript(new EstatioOperationalTeardownFixture());
        runFixtureScript(new BudgetKeyTableForOxf());
    }

    @Inject
    BudgetKeyTables tables;

    protected BudgetKeyTable budgetKeyTable =  new BudgetKeyTable();

    public static class AllBudgetKeyTables extends BudgetKeyTableTest {

        @Test
        public void whenExists() throws Exception {
            assertThat(tables.allBudgetKeyTables().size()).isGreaterThan(0);
        }
    }

    public static class changeBudgetKeyTable extends BudgetKeyTableTest {

        @Test
        public void whenSetUp() throws Exception {

            //given
            budgetKeyTable = tables.allBudgetKeyTables().get(0);
            assertThat(budgetKeyTable.getName().equals(BudgetKeyTableForOxf.NAME));
            assertThat(budgetKeyTable.getStartDate().equals(BudgetKeyTableForOxf.STARTDATE));
            assertThat(budgetKeyTable.getEndDate().equals(BudgetKeyTableForOxf.ENDDATE));
            assertThat(budgetKeyTable.getFoundationValueType().equals(BudgetKeyTableForOxf.BUDGET_FOUNDATION_VALUE_TYPE));
            assertThat(budgetKeyTable.getKeyValueMethod().equals(BudgetKeyTableForOxf.BUDGET_KEY_VALUE_METHOD));


            //when
            budgetKeyTable.changeName("something else");
            budgetKeyTable.changeDates(new LocalDate(2015, 07, 01), new LocalDate(2015, 12, 31));
            budgetKeyTable.changeFoundationValueType(BudgetFoundationValueType.COUNT);
            budgetKeyTable.changeKeyValueMethod(BudgetKeyValueMethod.ITA);

            //then
            assertThat(budgetKeyTable.getName().equals("something else"));
            assertThat(budgetKeyTable.getStartDate().equals(new LocalDate(2015, 07, 01)));
            assertThat(budgetKeyTable.getEndDate().equals(new LocalDate(2015,12,31)));
            assertThat(budgetKeyTable.getFoundationValueType().equals(BudgetFoundationValueType.COUNT));
            assertThat(budgetKeyTable.getKeyValueMethod().equals(BudgetKeyValueMethod.ITA));
        }



    }

}