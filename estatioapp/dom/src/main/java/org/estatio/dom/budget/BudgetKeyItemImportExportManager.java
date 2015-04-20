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

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.MemberGroupLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

import org.isisaddons.module.excel.dom.ExcelService;

import org.estatio.app.EstatioViewModel;

@DomainObject(
        nature = Nature.VIEW_MODEL
)
@DomainObjectLayout(
        named ="Import/export manager",
        bookmarking = BookmarkPolicy.AS_ROOT
)
@MemberGroupLayout(left={"File", "Criteria"})
public class BudgetKeyItemImportExportManager extends EstatioViewModel {

    // //////////////////////////////////////
    
    public String title() {
        return "Import/export manager";
    }
    
    // //////////////////////////////////////
    // ViewModel implementation
    // //////////////////////////////////////
    

    public String viewModelMemento() {
        return budgetKeyItemImportExportService.mementoFor(this);
    }

    public void viewModelInit(final String mementoStr) {
        budgetKeyItemImportExportService.initOf(mementoStr, this);
    }

    
    // //////////////////////////////////////
    // fileName (property)
    // changeFileName
    // //////////////////////////////////////

    private String fileName;
    
    @MemberOrder(name="File", sequence="1")
    public String getFileName() {
        return fileName;
    }
    public void setFileName(final String fileName) {
        this.fileName = fileName;
    }

    // //////////////////////////////////////

    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            named = "Change"
    )
    @MemberOrder(name="fileName", sequence="1")
    public BudgetKeyItemImportExportManager changeFileName(final String fileName) {
        setFileName(fileName);
        return budgetKeyItemImportExportService.newBulkUpdateManager(this);
    }
    public String default0ChangeFileName() {
        return getFileName();
    }

    // //////////////////////////////////////
    // allToDos
    // //////////////////////////////////////

    @SuppressWarnings("unchecked")
    @Collection
    @CollectionLayout(
            render = RenderType.EAGERLY
    )
    public List<BudgetKeyItem> getBudgetKeyItems() {
        return container.allInstances(BudgetKeyItem.class);
    }


    // //////////////////////////////////////
    // export (action)
    // //////////////////////////////////////
    
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(name="budgetKeyItems", sequence="1")
    public Blob export() {
        final String fileName = withExtension(getFileName(), ".xlsx");
        final List<BudgetKeyItem> items = getBudgetKeyItems();
        return toExcel(fileName, items);
    }

    public String disableExport() {
        return getFileName() == null? "file name is required": null;
    }

    private static String withExtension(final String fileName, final String fileExtension) {
        return fileName.endsWith(fileExtension) ? fileName : fileName + fileExtension;
    }

    private Blob toExcel(final String fileName, final List<BudgetKeyItem> items) {
        final List<BudgetKeyItemImportExportLineItem> budgetKeyItemViewModels = Lists.transform(items, toLineItem());
        return excelService.toExcel(budgetKeyItemViewModels, BudgetKeyItemImportExportLineItem.class, fileName);
    }

    private Function<BudgetKeyItem, BudgetKeyItemImportExportLineItem> toLineItem() {
        return new Function<BudgetKeyItem, BudgetKeyItemImportExportLineItem>(){
            @Override
            public BudgetKeyItemImportExportLineItem apply(final BudgetKeyItem budgetKeyItem) {
                final BudgetKeyItemImportExportLineItem template = new BudgetKeyItemImportExportLineItem();
                template.modifyBudgetKeyItem(budgetKeyItem);
                return budgetKeyItemImportExportService.newLineItem(template);
            }
        };
    }


    // //////////////////////////////////////
    // import (action)
    // //////////////////////////////////////

    @Action
    @ActionLayout(
            named = "Import"
    )
    @MemberOrder(name="budgetKeyItems", sequence="2")
    public List<BudgetKeyItemImportExportLineItem> importBlob(
            @ParameterLayout(named="Excel spreadsheet") final Blob spreadsheet) {
        final List<BudgetKeyItemImportExportLineItem> lineItems =
                excelService.fromExcel(spreadsheet, BudgetKeyItemImportExportLineItem.class);
        container.informUser(lineItems.size() + " items imported");
        return lineItems;
    }
    

    // //////////////////////////////////////
    // Injected Services
    // //////////////////////////////////////

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private ExcelService excelService;

    @javax.inject.Inject
    private BudgetKeyItemImportExportService budgetKeyItemImportExportService;

}
