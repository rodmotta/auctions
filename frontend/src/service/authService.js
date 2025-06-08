import api from './api';

export function exchangeCode(code, redirectUri = "http://localhost:5173/callback") {
    try {
        const payload = {
            code: code,
            redirectUri: redirectUri
        };
        const config = {
            skipAuth: true
        }
        return api.post('/auth/token', payload, config);
    } catch (error) {
        console.error('Erro:', error);
        throw error;
    }
}

export function refreshAccessToken(refreshToken) {
    try {
        const payload = {
            refreshToken: refreshToken
        };
        const config = {
            skipAuth: true
        }
        return api.post('/auth/refresh-token', payload, config);
    } catch (error) {
        console.error('Erro:', error);
        throw error;
    }
}

export function invalidateRefreshToken(refreshToken) {
    try {
        const payload = {
            refreshToken: refreshToken
        };
        const config = {
            skipAuth: true
        }
        return api.post('/auth/logout', payload, config);
    } catch (error) {
        console.error('Erro:', error);
        throw error;
    }
}
