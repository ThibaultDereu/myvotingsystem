import { Candidate } from './candidate';

export interface ElectionFull {
  id: number;
  name: string;
  closingDate: Date;
  creatingTime: Date;
  candidates: Candidate[];
}
