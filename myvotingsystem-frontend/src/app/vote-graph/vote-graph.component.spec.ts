import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteGraphComponent } from './vote-graph.component';

describe('VoteGraphComponent', () => {
  let component: VoteGraphComponent;
  let fixture: ComponentFixture<VoteGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
