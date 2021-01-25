import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Validators, FormBuilder, FormArray } from '@angular/forms';
import { MatChipInputEvent } from '@angular/material/chips';

import { ElectionService } from '../election.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-election-creation',
  templateUrl: './election-creation.component.html',
  styleUrls: ['./election-creation.component.css'],
})
export class ElectionCreationComponent implements OnInit {
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  minCandidates = 2;

  electionForm = this.formBuilder.group({
    name: ['', Validators.required],
    closingDate: ['', Validators.required],
    candidateNames: this.formBuilder.array(
      [],
      [Validators.minLength(this.minCandidates), Validators.required]
    ),
  });

  filterBeforeToday = (d: Date | null): boolean => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    return d >= today;
  };

  get candidateControls(): FormArray {
    return this.electionForm.get('candidateNames') as FormArray;
  }

  constructor(
    private formBuilder: FormBuilder,
    private electionService: ElectionService,
    private location: Location,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {}

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add candidate
    if ((value || '').trim()) {
      this.candidateControls.push(this.formBuilder.control(value));
    }
    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(candidate: string): void {
    const index = this.candidateControls.value.indexOf(candidate);
    if (index >= 0) {
      this.candidateControls.removeAt(index);
    }
  }

  submit(): void {
    this.electionService
      .createElection(this.electionForm.value)
      .subscribe((election) => {
        this.messageService.printMessage(
          `Election "${election.name}" was successfully created.`
        );
        this.goBack();
      });
  }

  goBack(): void {
    this.location.back();
  }
}
