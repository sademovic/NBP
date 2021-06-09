import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { CategoryService } from '../common/category.service';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

  single: any[];
  multi: any[];

  view: any[] = [700, 400];

  // options
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Category';
  showYAxisLabel = true;
  yAxisLabel = 'Items sold';

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  reqTime = 0;
  @ViewChild('dateInput', {static: false}) dateInput: ElementRef;

  constructor( private categoryService: CategoryService ) {
    
  }

  ngOnInit(): void {
    this.categoryService.getCategoryStats( this.reqTime );
    this.categoryService.statSuccess.subscribe((res) => {
      console.log(res);
      this.single = res;
    });
  }

  onSelect(event) {
    console.log(event);
  }

  onDate() {
    if (this.dateInput.nativeElement.value) {
      this.reqTime = Date.parse(this.dateInput.nativeElement.value);
      this.categoryService.getCategoryStats( this.reqTime );
    }
  }

  onClear() {
    this.reqTime = 0;
    
    this.categoryService.getCategoryStats( this.reqTime );
  }
}
