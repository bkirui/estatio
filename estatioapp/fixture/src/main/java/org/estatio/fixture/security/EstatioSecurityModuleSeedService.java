/*
 *  Copyright 2014 Eurocommercial Properties NV
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
package org.estatio.fixture.security;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

/**
 * Installs security seed data on application startup.
 */
@DomainService
@Hidden
public class EstatioSecurityModuleSeedService {

    @PostConstruct
    @Programmatic
    public void init() {
        fixtureScripts.runFixtureScript(new EstatioSecurityModuleSeedFixture(), null);
    }

    @Inject
    FixtureScripts fixtureScripts;

}
