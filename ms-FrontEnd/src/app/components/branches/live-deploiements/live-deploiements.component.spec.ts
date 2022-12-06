import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LiveDeploiementsComponent } from './live-deploiements.component';

describe('LiveDeploiementsComponent', () => {
  let component: LiveDeploiementsComponent;
  let fixture: ComponentFixture<LiveDeploiementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LiveDeploiementsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LiveDeploiementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
