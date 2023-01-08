import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"
import {selectToken} from "../redux/login/loginSelectors";
import {createLogoutThunk} from "../redux/thunks/logoutThunk";

const baseQuery = fetchBaseQuery({
    baseUrl: "http://localhost:8080/api/point",
    prepareHeaders: (headers, {getState}) => {
        const token = selectToken(getState())
        if (token) {
            headers.set("authorization", `Bearer ${token}`)
        }
        return headers
    },
})
const baseQueryWithRedirect = async (args, api, extraOptions) => {
    let result = await baseQuery(args, api, extraOptions)
    if (result.error) {
        if (result.error.status == 401) {
            api.dispatch(createLogoutThunk())
        }
        console.log(result.error)
    }
    return result
}

export const pointsApi = createApi({
    reducerPath: "api/points",
    baseQuery: baseQueryWithRedirect,
    tagTypes: ['points'],
    endpoints: (build) => ({
        fetchAll: build.query({
            query: ({page, size}) => ({
                url: `?page=${page}&size=${size}`,
                method: "GET"
            }),
            providesTags: ['points'],
            transformResponse: (response) => response.data
        }),
        countAll: build.query({
            query: () => ({
                url: "/count",
                method: "GET"
            }),
            providesTags: ['points'],
            transformResponse: (response) => response.data
        }),
        postHit: build.mutation({
            query: ({x, y, r}) => ({
                url: "",
                method: "POST",
                body: {x, y, r}
            }),
            invalidatesTags: ['points'],
            transformResponse: (response) => response.data
        }),
        clearPoints: build.mutation({
            query: () => ({
                url: "",
                method: "DELETE",
            }),
            invalidatesTags: ['points'],
            transformResponse: (response) => response.data
        })
    })
})

export const {useFetchAllQuery, useCountAllQuery, usePostHitMutation, useClearPointsMutation} = pointsApi
