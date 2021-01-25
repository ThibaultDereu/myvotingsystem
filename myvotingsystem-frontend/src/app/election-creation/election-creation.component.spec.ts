import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElectionCreationComponent } from './election-creation.component';

describe('ElectionCreationComponent', () => {
  let component: ElectionCreationComponent;
  let fixture: ComponentFixture<ElectionCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElectionCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ElectionCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
