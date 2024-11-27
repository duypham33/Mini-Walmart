import { ChangeDetectionStrategy, ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { trigger, transition, style, animate } from '@angular/animations';

@Component({
  selector: 'app-counter',
  templateUrl: './counter.component.html',
  styleUrls: ['./counter.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    trigger('countAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.5)' }),
        animate('500ms ease-out', style({ opacity: 1, transform: 'scale(1)' }))
      ])
    ])
  ]
})
export class CounterComponent implements OnInit {
  currentNumber: number = 0;
  @Input() text!: string;
  targetNumber: number = 0;
  duration: number = 1500;
  intervalTime: number = 15;
  direction: 1 | -1 = -1;  // 1 for counting up, -1 for counting down
  countTimes: number = 2; // Number of times to count (forward and back)
  countComplete: number = 0; // To track how many full counts have completed

  constructor(private cdRef: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.animateNumber();
  }
  
  get check(){
    return this.text?.includes("$");
  }

  get checkDot(){
    return this.text?.includes(".");
  }

  animateNumber(): void {
    this.targetNumber = parseFloat(this.text.replace("$", ""));

    // Step size based on target and duration
    const step = this.targetNumber / (this.duration / this.intervalTime);
    
    // Set an interval to update the currentNumber
    const interval = setInterval(() => {
      // Increment or decrement the currentNumber based on direction
      this.currentNumber += this.direction * step;

      // Check if we need to reverse direction
      if (this.direction === 1 && this.currentNumber >= this.targetNumber) {
        this.currentNumber = this.targetNumber; // Stop exactly at 100
        this.direction = -1; // Change direction to count down
        this.countComplete++; // Full cycle completed (up + down)
      } else if (this.direction === -1 && this.currentNumber <= 0) {
        this.currentNumber = 0; // Stop exactly at 0
        this.direction = 1; // Change direction to count up again
        this.countComplete++; // Full cycle completed (down + up)
      }

      // Stop the animation after the specified number of full cycles (countTimes)
      if (this.countComplete >= this.countTimes * 2) {
        clearInterval(interval); // Stop the interval
      }

      this.cdRef.detectChanges(); // Manually trigger change detection to update the view
    }, this.intervalTime);
  }
}
