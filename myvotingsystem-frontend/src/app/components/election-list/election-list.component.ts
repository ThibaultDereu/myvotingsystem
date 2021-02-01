import { Component, OnInit } from '@angular/core';
import { ElectionService } from '../../services/election.service';
import { Election } from '../../models/election';

@Component({
  selector: 'app-election-list',
  templateUrl: './election-list.component.html',
  styleUrls: ['./election-list.component.css'],
})
export class ElectionListComponent implements OnInit {
  elections: Election[];
  loading = false;

  constructor(private electionService: ElectionService) {}

  ngOnInit(): void {
    this.getElections();
  }

  getElections(): void {
    this.loading = true;
    this.electionService.getElections().subscribe({
      next: (elections) => (this.elections = elections),
      complete: () => (this.loading = false),
    });
  }
}
