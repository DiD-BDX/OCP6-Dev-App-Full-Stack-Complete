import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreationArticleComponent } from './creation-article.component';

describe('CreationArticleComponent', () => {
  let component: CreationArticleComponent;
  let fixture: ComponentFixture<CreationArticleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreationArticleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreationArticleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
