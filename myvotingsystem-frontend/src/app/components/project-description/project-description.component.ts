import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.css'],
})
export class ProjectDescriptionComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<ProjectDescriptionComponent>) {}

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit(): void {}

  onCloseButton(): void {
    this.dialogRef.close();
  }
}
