import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"
import {encode} from "js-base64"

    export const authApi = createApi({
    reducerPath: "api/auth",
    baseQuery: fetchBaseQuery({baseUrl: "http://localhost:8080/api/auth"}),
    endpoints: (build) => ({
        register: build.mutation({
            query: ({username, password}) => ({
                url: "/register",
                method: "POST",
                body: {username, password}
            }),
            transformResponse: (response) => response.data,
        }),
        login: build.mutation({
            query: ({username, password}) => ({
                url: "/login",
                method: "POST",
                headers: {
                    Authorization: "Basic " + encode(username + ":" + password),
                },
            }),
            transformResponse: (response) => response.data,
        })
    })
})

export const {useRegisterMutation, useLoginMutation} = authApi
