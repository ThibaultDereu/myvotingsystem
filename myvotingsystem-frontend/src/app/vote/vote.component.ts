import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { CdkDragDrop, moveItemInArray } from '@angular/cdk/drag-drop';

import { ElectionFull } from '../election-full';
import { ElectionService } from '../election.service';
import { VoteService } from '../vote.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-vote',
  templateUrl: './vote.component.html',
  styleUrls: ['./vote.component.css'],
})
export class VoteComponent implements OnInit {
  election: ElectionFull = {} as ElectionFull;

  constructor(
    private electionService: ElectionService,
    private messageService: MessageService,
    private voteService: VoteService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    const electionId: number = Number(
      this.route.snapshot.paramMap.get('electionId')
    );
    this.electionService
      .getElection(electionId)
      .subscribe((election) => (this.election = election));
  }

  drop(event: CdkDragDrop<string[]>): void {
    moveItemInArray(
      this.election.candidates,
      event.previousIndex,
      event.currentIndex
    );
  }

  goBack(): void {
    this.location.back();
  }

  submit(): void {
    this.voteService
      .submitVote(this.election.id, this.election.candidates)
      .subscribe(
        (_) => {
          this.messageService.printMessage(
            `Your vote was successfully submitted.`,
            'Success'
          );
          this.goBack();
        },
        (error) =>
          this.messageService.printMessage(
            `An Error occurred : ${error.message}`,
            'error'
          )
      );
  }
}
