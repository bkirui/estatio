/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.estatio.webapp.services.admin;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.isisaddons.module.publishing.dom.PublishingServiceRepository;
import org.joda.time.LocalDate;
import org.apache.isis.applib.ViewModel;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.services.HasTransactionId;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.clock.ClockService;
import org.apache.isis.objectstore.jdo.applib.service.DomainChangeJdoAbstract;
import org.estatio.dom.UdoDomainService;

@DomainService(menuOrder = "95")
public class EstatioAuditingContributions extends UdoDomainService<EstatioAuditingContributions> {

    public EstatioAuditingContributions() {
        super(EstatioAuditingContributions.class);
    }

    /**
     * Depending on which services are available, returns either a list of {@link CommandJdo command}s that have 
     * caused a change in the domain object or a list of {@link AuditEntryJdo audit entries} capturing the 'effect' 
     * of that change.
     * 
     * <p>
     * If {@link CommandJdo command}s are returned, then the corresponding {@link AuditEntryJdo audit entries} are
     * available from each command.
     */
    @NotInServiceMenu
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence="30")
    public List<? extends DomainChangeJdoAbstract> recentChanges (
            final Object targetDomainObject,
            final @Optional @Named("From") LocalDate from,
            final @Optional @Named("To") LocalDate to) {
        final Bookmark targetBookmark = bookmarkService.bookmarkFor(targetDomainObject);
        final List<DomainChangeJdoAbstract> changes = Lists.newArrayList();
        if(commandServiceRepository != null) {
            changes.addAll(commandServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        } 
        if(publishingServiceRepository != null) {
            changes.addAll(publishingServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        }
        changes.addAll(auditingServiceRepository.findByTargetAndFromAndTo(targetBookmark, from, to));
        Collections.sort(changes, DomainChangeJdoAbstract.compareByTimestampDescThenType());
        return changes;
    }
    /**
     * Hide for implementations of {@link HasTransactionId} (in other words for {@link CommandJdo command}s, {@link AuditEntryJdo audit entries}
     * and {@link org.isisaddons.module.publishing.dom.PublishedEvent published event}s) and for {@link ViewModel}s.
     */
    public boolean hideRecentChanges(final Object targetDomainObject, final LocalDate from, final LocalDate to) {
        return targetDomainObject instanceof HasTransactionId || targetDomainObject instanceof ViewModel || auditingServiceRepository == null || bookmarkService == null;
    }
    public LocalDate default1RecentChanges() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default2RecentChanges() {
        return clockService.now();
    }

    
    // //////////////////////////////////////

    
    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;
    
    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;
    
    @javax.inject.Inject
    private PublishingServiceRepository publishingServiceRepository;
    
    @javax.inject.Inject
    private BookmarkService bookmarkService;

    @javax.inject.Inject
    private ClockService clockService;
    
}

