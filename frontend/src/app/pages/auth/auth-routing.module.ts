import { RouterModule, Routes } from "@angular/router";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";
import { NgModule } from "@angular/core";
import { AUTH_LOGIN_PATH, AUTH_PAGES_ROOT, AUTH_REGISTER_PATH } from "src/app/core/constants/app-urls.constant";

const routes: Routes = [
    {path: '', component: LoginComponent},
    {path: AUTH_LOGIN_PATH, pathMatch: 'full', redirectTo: `/${AUTH_PAGES_ROOT}`},
    {path: AUTH_REGISTER_PATH, component: RegisterComponent},
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [RouterModule]
})
export class AuthRoutingModule{}