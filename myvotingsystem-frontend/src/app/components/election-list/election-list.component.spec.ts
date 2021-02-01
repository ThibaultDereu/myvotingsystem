import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionListComponent } from './election-list.component';

describe('ElectionListComponent', () => {
  let component: ElectionListComponent;
  let fixture: ComponentFixture<ElectionListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElectionListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ElectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
