import { Candidate } from './candidate';

export interface VoteResults {
  electionName: string;
  nbVotes: number;
  candidates: Candidate[];
  winners: Candidate[];
  adjacencyMap: {
    [candId: number]: {
      [otherCandId: number]: number;
    };
  };
}
