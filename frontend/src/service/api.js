import axios from 'axios';
import authStore from '../stores/authStore';

const api = axios.create({
    baseURL: 'http://localhost:8080',
});

api.interceptors.request.use((config) => {
    if (!config.skipAuth) {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
    }
    return config;
});

let isRefreshing = false;
let failedQueue = [];

const processQueue = (error, token = null) => {
    failedQueue.forEach(prom => {
        if (error) {
            prom.reject(error);
        } else {
            prom.resolve(token);
        }
    });
    failedQueue = [];
};

api.interceptors.response.use(
    res => res,
    async error => {

        const originalRequest = error.config;

        if (error.response.status !== 401 || originalRequest._retry) {
            return Promise.reject(error);
        }

        if (isRefreshing) {
            return new Promise(function (resolve, reject) {
                failedQueue.push({ resolve, reject });
            }).then(token => {
                originalRequest.headers.Authorization = `Bearer ${token}`;
                return api(originalRequest);
            }).catch(err => {
                return Promise.reject(err);
            });
        }

        isRefreshing = true;

        return new Promise(async (resolve, reject) => {
            try {
                const newAccessToken = await authStore.getState().refreshToken();
                originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                originalRequest._retry = true;

                processQueue(null, newAccessToken);

                resolve(api(originalRequest));
            } catch (refreshError) {
                processQueue(refreshError, null);
                console.error("Failed to refresh token:", refreshError);
                reject(refreshError);
            }
            finally {
                isRefreshing = false;
            }
        })
    }
)

export default api;