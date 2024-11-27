import { ChangeDetectionStrategy, Component } from '@angular/core';
import { IProductPrize } from 'src/app/core/models/user/user-stat.model';
import { UserService } from 'src/app/core/services/user.service';

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AdminHomeComponent {
  constructor(private userService: UserService){}
  stats$ = this.userService.getAdminStatistics();
  
  productPrize(position: number, value: string): IProductPrize{
    return {position, value};
  }
}
