import { Component, OnInit, Input } from '@angular/core';

import { VoteResults } from '../vote-results';
import { Candidate } from '../candidate';

@Component({
  selector: 'app-vote-graph',
  templateUrl: './vote-graph.component.html',
  styleUrls: ['./vote-graph.component.css'],
})
export class VoteGraphComponent implements OnInit {
  @Input() voteResults: VoteResults;

  nodes: any = [];
  links: any = [];

  // documention here : https://echarts.apache.org/v4/en/option.html#series-graph
  options = {
    tooltip: {},
    animationDurationUpdate: 1500,
    animationEasingUpdate: 'quinticInOut',
    series: [
      {
        type: 'graph',
        layout: 'circular',
        symbolSize: 40,
        roam: true,
        focusNodeAdjacency: true,
        label: {
          show: true,
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [4, 10],
        edgeLabel: {
          fontSize: 20,
        },
        lineStyle: {
          opacity: 0.9,
          width: 2,
          curveness: 0,
        },
        data: this.nodes,
        links: this.links,
      },
    ],
  };

  constructor() {}

  isWinner(candidate): boolean {
    return this.voteResults.winners.some((cand) => cand.id === candidate.id);
  }

  ngOnInit(): void {
    const candidates: Candidate[] = this.voteResults.candidates;
    const mapNodeNames: Map<number, string> = new Map();

    // create nodes
    for (let i = 0; i < candidates.length; i++) {
      const candidate: Candidate = candidates[i];
      const nodeName: string = (i + 1).toString();
      const isWinner: boolean = this.isWinner(candidate);
      this.nodes.push({
        name: nodeName,
        itemStyle: {
          color: isWinner ? '#cc3333' : '#5470c6',
        },
        tooltip: {
          formatter: (_) => candidate.name,
          extraCssText: 'background-color: rgba(0,0,0,0.4);',
          textStyle: { color: '#fff' },
        },
      });
      mapNodeNames.set(candidate.id, nodeName);
    }

    // create links
    for (const cand1 of candidates) {
      for (const cand2 of candidates) {
        const score1vs2: number =
          this.voteResults.adjacencyMap[cand1.id][cand2.id] ?? 0;
        const score2vs1: number =
          this.voteResults.adjacencyMap[cand2.id][cand1.id] ?? 0;
        if (score1vs2 > score2vs1) {
          const link = {
            source: mapNodeNames.get(cand1.id),
            target: mapNodeNames.get(cand2.id),
            emphasis: {
              label: {
                show: true,
                formatter: (_) => (score1vs2 - score2vs1).toString(),
              },
            },
          };
          this.links.push(link);
        }
      }
    }
  }
}
