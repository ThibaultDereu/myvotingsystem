import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatInputModule } from '@angular/material/input';
import { MatChipsModule } from '@angular/material/chips';
import { MatDatepickerModule } from '@angular/material/datepicker';
import {
  MatMomentDateModule,
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
} from '@angular/material-moment-adapter';
import { MAT_DATE_FORMATS } from '@angular/material/core';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { MatBadgeModule } from '@angular/material/badge';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatDialogModule } from '@angular/material/dialog';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatMenuModule } from '@angular/material/menu';

import { NgxEchartsModule } from 'ngx-echarts';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ElectionListComponent } from './components/election-list/election-list.component';
import { ElectionCreationComponent } from './components/election-creation/election-creation.component';
import { VoteComponent } from './components/vote/vote.component';
import { VoteResultsComponent } from './components/vote-results/vote-results.component';
import { VoteGraphComponent } from './components/vote-graph/vote-graph.component';
import { ProjectDescriptionComponent } from './components/project-description/project-description.component';

import * as echarts from 'echarts';
import { LocalizedDatePipe } from './pipes/localized-date.pipe';

@NgModule({
  declarations: [
    AppComponent,
    ElectionListComponent,
    ElectionCreationComponent,
    VoteComponent,
    VoteResultsComponent,
    VoteGraphComponent,
    ProjectDescriptionComponent,
    LocalizedDatePipe,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatTooltipModule,
    MatInputModule,
    MatChipsModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatSnackBarModule,
    DragDropModule,
    MatBadgeModule,
    MatDividerModule,
    MatButtonToggleModule,
    MatDialogModule,
    MatProgressSpinnerModule,
    MatMenuModule,
    NgxEchartsModule.forRoot({ echarts }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient],
      },
    }),
  ],
  bootstrap: [AppComponent],
  providers: [
    { provide: MAT_MOMENT_DATE_ADAPTER_OPTIONS, useValue: { useUtc: true } },

    {
      provide: MAT_DATE_FORMATS,
      useValue: {
        parse: { dateInput: 'YYYY-MM-DD' },
        display: {
          dateInput: 'YYYY-MM-DD',
          monthYearLabel: 'MMM YYYY',
          dateA11yLabel: 'LL',
          monthYearA11yLabel: 'MMMM YYYY',
        },
      },
    },
  ],
})
export class AppModule {}

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
