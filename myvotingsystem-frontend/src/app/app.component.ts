import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DateAdapter } from '@angular/material/core';
import { TranslateService } from '@ngx-translate/core';
import { registerLocaleData } from '@angular/common';
import localeFrench from '@angular/common/locales/fr';

import { ProjectDescriptionComponent } from './components/project-description/project-description.component';

interface LanguageOption {
  code: string;
  caption: string;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'voting system';
  languages: LanguageOption[] = [
    { code: 'en', caption: 'English' },
    { code: 'fr', caption: 'Français' },
  ];

  constructor(
    public dialog: MatDialog,
    private translateService: TranslateService,
    private dateAdapter: DateAdapter<Date>
  ) {
    // TODO : register user preference in local storage
    translateService.setDefaultLang('en');
    translateService.use('en');

    registerLocaleData(localeFrench);
  }

  openPresentationDialog(): void {
    this.dialog.open(ProjectDescriptionComponent, {
      width: '600px',
    });
  }

  isCurrentLanguage(code: string): boolean {
    return this.translateService.currentLang === code;
  }

  setLanguage(code: string): void {
    this.translateService.use(code);
    this.dateAdapter.setLocale(code);
  }
}
