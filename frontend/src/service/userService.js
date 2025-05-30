import api from './api';

export async function register(payload) {
    try {
        await api.post('/users/register', payload);
    } catch (error) {
        console.error('Erro:', error);
    }
}

export async function login(payload) {
    try {
        const response = await api.post('/users/login', payload);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}
