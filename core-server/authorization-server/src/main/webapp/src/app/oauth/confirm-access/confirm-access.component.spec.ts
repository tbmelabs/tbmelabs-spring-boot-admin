import {} from 'jasmine';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmAccessComponent } from './confirm-access.component';

describe('ConfirmAccessComponent', () => {
  let component: ConfirmAccessComponent;
  let fixture: ComponentFixture<ConfirmAccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmAccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmAccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
