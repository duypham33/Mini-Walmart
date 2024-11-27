import { HttpErrorResponse } from "@angular/common/http"

export const parseResponseErr = (error: HttpErrorResponse): string => {
    if(typeof error.error === "string") return error.error;
    return error.error.message;
}