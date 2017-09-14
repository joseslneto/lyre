import {NgModule} from '@angular/core';

import {FlexLayoutModule} from "@angular/flex-layout";

import {
    MdButtonModule,
    MdCheckboxModule,
    MdToolbarModule,
    MdMenuModule,
    MdIconModule,
    MdCardModule,
    MdSidenavModule,
    MdListModule,
    MdInputModule,
    MdFormFieldModule,
    MdTableModule,
    MdPaginatorModule,
    MdGridListModule,
    MdDialogModule,
    MdTooltipModule
} from '@angular/material';

@NgModule({
    imports: [
        FlexLayoutModule,
        MdButtonModule,
        MdCheckboxModule,
        MdToolbarModule,
        MdMenuModule,
        MdIconModule,
        MdCardModule,
        MdSidenavModule,
        MdListModule,
        MdInputModule,
        MdFormFieldModule,
        MdTableModule,
        MdPaginatorModule,
        MdGridListModule,
        MdDialogModule,
        MdTooltipModule
    ],
    exports: [
        FlexLayoutModule,
        MdButtonModule,
        MdCheckboxModule,
        MdToolbarModule,
        MdMenuModule,
        MdIconModule,
        MdCardModule,
        MdSidenavModule,
        MdListModule,
        MdInputModule,
        MdFormFieldModule,
        MdTableModule,
        MdPaginatorModule,
        MdGridListModule,
        MdDialogModule,
        MdTooltipModule
    ],
})
export class Modules {
}
