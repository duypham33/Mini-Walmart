const authKey = "authToken";

export function getAuthToken(){
    return localStorage.getItem(authKey);
}

export function setAuthToken(token: string){
    localStorage.setItem(authKey, token);
}

export function clearAuthToken(){
    localStorage.removeItem(authKey);
}