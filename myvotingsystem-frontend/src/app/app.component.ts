import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';

import { ProjectDescriptionComponent } from './project-description/project-description.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'voting system';

  constructor(public dialog: MatDialog) {}

  openPresentationDialog(): void {
    this.dialog.open(ProjectDescriptionComponent, {
      width: '600px',
    });
  }
}
