/*
 * Copyright 2012-2015 Eurocommercial Properties NV
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

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.memento.MementoService;
import org.apache.isis.applib.services.memento.MementoService.Memento;

import org.isisaddons.module.excel.dom.ExcelService;

import org.estatio.dom.asset.Unit;

@DomainService
public class BudgetKeyItemImportExportService {

    @PostConstruct
    public void init() {
        if(excelService == null) {
            throw new IllegalStateException("Require ExcelService to be configured");
        }
    }

    // //////////////////////////////////////
    // bulk update manager (action)
    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @MemberOrder(name="Budgets", sequence="90.1")
    public BudgetKeyItemImportExportManager bulkUpdateManager() {
        BudgetKeyItemImportExportManager template = new BudgetKeyItemImportExportManager();
        template.setFileName("budgetKeyItems.xlsx");
        return newBulkUpdateManager(template);
    }


    // //////////////////////////////////////
    // memento for manager
    // //////////////////////////////////////
    
    String mementoFor(final BudgetKeyItemImportExportManager tdieim) {
        final Memento memento = mementoService.create();
        memento.set("fileName", tdieim.getFileName());
        return memento.asString();
    }
    
    void initOf(final String mementoStr, final BudgetKeyItemImportExportManager manager) {
        final Memento memento = mementoService.parse(mementoStr);
        manager.setFileName(memento.get("fileName", String.class));
    }

    BudgetKeyItemImportExportManager newBulkUpdateManager(BudgetKeyItemImportExportManager manager) {
        return container.newViewModelInstance(BudgetKeyItemImportExportManager.class, mementoFor(manager));
    }
    
    // //////////////////////////////////////
    // memento for line item
    // //////////////////////////////////////
    
    String mementoFor(BudgetKeyItemImportExportLineItem lineItem) {
        final Memento memento = mementoService.create();
        memento.set("budgetKeyItem", bookmarkService.bookmarkFor(lineItem.getBudgetKeyItem()));
        memento.set("budgetKeyTable", bookmarkService.bookmarkFor(lineItem.getBudgetKeyTable()));
        memento.set("unit", bookmarkService.bookmarkFor(lineItem.getUnit()));
        memento.set("sourcevalue", lineItem.getSourceValue());
        memento.set("foundationvalue", lineItem.getFoundationValue());
        memento.set("keyvalue", lineItem.getKeyValue());
        return memento.asString();
    }

    void init(String mementoStr, BudgetKeyItemImportExportLineItem lineItem) {
        final Memento memento = mementoService.parse(mementoStr);
        lineItem.setBudgetKeyItem(bookmarkService.lookup(memento.get("budgetKeyItem", Bookmark.class), BudgetKeyItem.class));
        lineItem.setBudgetKeyTable(bookmarkService.lookup(memento.get("budgetKeyTable", Bookmark.class), BudgetKeyTable.class));
        lineItem.setUnit(bookmarkService.lookup(memento.get("unit", Bookmark.class), Unit.class));
        lineItem.setSourceValue(memento.get("sourcevalue", BigDecimal.class));
        lineItem.setFoundationValue(memento.get("foundationvalue", BigDecimal.class));
        lineItem.setKeyValue(memento.get("keyvalue", BigDecimal.class));
    }
    
    BudgetKeyItemImportExportLineItem newLineItem(BudgetKeyItemImportExportLineItem lineItem) {
        return container.newViewModelInstance(BudgetKeyItemImportExportLineItem.class, mementoFor(lineItem));
    }


    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private BookmarkService bookmarkService;
    
    @javax.inject.Inject
    private MementoService mementoService;

}
