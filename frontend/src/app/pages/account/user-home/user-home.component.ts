import { ChangeDetectionStrategy, Component } from '@angular/core';
import { IProductPrize } from 'src/app/core/models/user/user-stat.model';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class UserHomeComponent {
  constructor(private userService: UserService){}
  stats$ = this.userService.getUserStatistics();
  
  productPrize(position: number, value: string): IProductPrize{
    return {position, value};
  }
}
