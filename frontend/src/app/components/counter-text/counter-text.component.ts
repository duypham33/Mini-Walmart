import { ChangeDetectionStrategy, Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-counter-text',
  templateUrl: './counter-text.component.html',
  styleUrls: ['./counter-text.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CounterTextComponent implements OnInit {
  @Input() text!: string;
  tokens?: {value: string, isNum: boolean}[];

  ngOnInit(): void {
    this.tokens = this.text.split(" ").map(token => ({
      value: token, 
      isNum: this.isNumber(token)
    }));
  }

  isNumber(value: string): boolean {
    const v = value.replace("$", "");
    return !isNaN(parseFloat(v)) && isFinite(Number(v));
  }
}
