import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { LoginRequest } from "../models/user/requests/login-request.model";
import { AuthResponse } from "../models/user/responses/auth-response.model";
import { filter, map, of, tap, throwIfEmpty } from "rxjs";
import { clearAuthToken, getAuthToken, setAuthToken } from "../utils/user.util";
import { IApiResponse } from "../models/api-response.model";
import { CHECKAUTH_API_URL, LOGIN_API_URL, REGISTER_API_URL } from "../constants/api-urls.constant";
import { RegisterRequest } from "../models/user/requests/register-request.model";

@Injectable({
    providedIn: 'root'
})
export class AuthService{
    constructor(private httpClient: HttpClient){}

    login(loginRequest: LoginRequest){
        return this.httpClient.post<IApiResponse<AuthResponse>>(
            LOGIN_API_URL,
            loginRequest
        ).pipe(
            tap(res => setAuthToken(res.data.token)),
            map(res => res.data)
        );
    }

    register(registerRequest: RegisterRequest){
        return this.httpClient.post(
            REGISTER_API_URL,
            registerRequest
        );
    }

    checkAuth(){
        return this.httpClient.get<IApiResponse<AuthResponse>>(
            CHECKAUTH_API_URL
        ).pipe(
            filter(res => res.isSuccess),
            throwIfEmpty(() => new Error("Invalid Token!")),
            tap(res => res.data.token = getAuthToken()!),
            map(res => res.data)
        );
    }

    logout(){
        clearAuthToken();
        return of(true);
    }
}