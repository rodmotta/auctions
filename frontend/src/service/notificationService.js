import api from './api';

export function getUserNotifications() {
    try {
        return api.get("/notifications");
    } catch (error) {
        console.error('Erro:', error);
    }
}