import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { VoteResults } from '../../models/vote-results';
import { VoteService } from '../../services/vote.service';
import { Candidate } from '../../models/candidate';

@Component({
  selector: 'app-vote-results',
  templateUrl: './vote-results.component.html',
  styleUrls: ['./vote-results.component.css'],
})
export class VoteResultsComponent implements OnInit {
  voteResults: VoteResults;
  valueType = 'wins';
  selectedCandidates = [];

  constructor(
    private voteService: VoteService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    const electionId: number = Number(
      this.route.snapshot.paramMap.get('electionId')
    );

    this.voteService.getVoteResults(electionId).subscribe((voteResults) => {
      this.voteResults = voteResults;
      this.voteResults.candidates.sort((candA, candB) =>
        candA.name.toUpperCase() < candB.name.toUpperCase() ? -1 : 1
      );
    });
  }

  isWinner(candidate: Candidate): boolean {
    return this.voteResults.winners.some((cand) => cand.id === candidate.id);
  }

  getCellColor(cell1: Candidate, cell2: Candidate): string {
    if (cell1 === cell2) {
      return '';
    }
    const score1: number =
      this.voteResults.adjacencyMap[cell1.id][cell2.id] ?? 0;
    const score2: number =
      this.voteResults.adjacencyMap[cell2.id][cell1.id] ?? 0;

    if (score1 === score2) {
      return 'yellow-cell';
    } else if (score1 > score2) {
      return 'green-cell';
    } else {
      return 'red-cell';
    }
  }

  getCellValue(cell1: Candidate, cell2: Candidate): string {
    if (cell1 === cell2) {
      return '';
    }
    const score1: number =
      this.voteResults.adjacencyMap[cell1.id][cell2.id] ?? 0;
    const score2: number =
      this.voteResults.adjacencyMap[cell2.id][cell1.id] ?? 0;

    if (this.valueType === 'margins') {
      return (score1 - score2).toString();
    } else if (this.valueType === 'percentages') {
      return Math.round((score1 * 100) / (score1 + score2)) + '%';
    } else {
      return score1.toString();
    }
  }

  select(cell1: Candidate, cell2: Candidate): void {
    this.selectedCandidates = [cell1, cell2];
  }

  unselect(): void {
    this.selectedCandidates = [];
  }

  isSelected(cell1: Candidate, cell2: Candidate): boolean {
    return (
      this.selectedCandidates.includes(cell1) &&
      this.selectedCandidates.includes(cell2) &&
      cell1 !== cell2
    );
  }

  goBack(): void {
    this.location.back();
  }
}
