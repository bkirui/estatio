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
package org.estatio.dom.party;

import java.util.List;
import javax.inject.Inject;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Prototype;
import org.apache.isis.applib.annotation.RegEx;
import org.estatio.dom.RegexValidation;
import org.estatio.dom.UdoDomainRepositoryAndFactory;
import org.estatio.dom.Dflt;
import org.estatio.dom.apptenancy.EstatioApplicationTenancies;

@DomainService(repositoryFor = Organisation.class)
@DomainServiceLayout(
        named = "Parties",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "20.2")
public class Organisations extends UdoDomainRepositoryAndFactory<Organisation> {

    public Organisations() {
        super(Organisations.class, Organisation.class);
    }

    // //////////////////////////////////////

    @Action(semantics = SemanticsOf.NON_IDEMPOTENT)
    @MemberOrder(sequence = "1")
    public Organisation newOrganisation(
            final @ParameterLayout(named = "Reference") @Parameter(regexPattern = RegexValidation.REFERENCE) String reference,
            final @Named("Name") String name,
            final ApplicationTenancy applicationTenancy) {
        final Organisation organisation = newTransientInstance(Organisation.class);
        organisation.setApplicationTenancyPath(applicationTenancy.getPath());
        organisation.setReference(reference);
        organisation.setName(name);
        persist(organisation);
        return organisation;
    }

    public List<ApplicationTenancy> choices2NewOrganisation() {
        return estatioApplicationTenancies.countryTenanciesForCurrentUser();
    }

    public ApplicationTenancy default2NewOrganisation() {
        return Dflt.of(choices2NewOrganisation());
    }

    // //////////////////////////////////////

    @Action(semantics = SemanticsOf.SAFE, restrictTo = RestrictTo.PROTOTYPING)
    @MemberOrder(sequence = "99")
    public List<Organisation> allOrganisations() {
        return allInstances();
    }

    // //////////////////////////////////////

    @Inject
    private EstatioApplicationTenancies estatioApplicationTenancies;


}
