import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"

    export const authApi = createApi({
    reducerPath: "api/auth",
    baseQuery: fetchBaseQuery({baseUrl: "http://localhost:8080/api/auth"}),
    endpoints: (build) => ({
        register: build.mutation({
            query: (credentials) => ({
                url: "/register",
                method: "POST",
                body: credentials
            }),
            transformResponse: (response) => response.data,
        }),
        login: build.mutation({
            query: (credentials) => ({
                url: "/login",
                method: "POST",
                body: credentials
            }),
            transformResponse: (response) => response.data,
        })
    })
})

export const {useRegisterMutation, useLoginMutation} = authApi
