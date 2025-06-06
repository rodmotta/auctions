import api from './api';

export async function getBidsByAuctionId(auctionId) {
    try {
        const config = {
            skipAuth: true
        }
        const response = await api.get(`/bids/auction/${auctionId}`, config);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}

export async function placeBid(payload) {
    try {
        api.post('/bids', payload);
    } catch (error) {
        console.error('Erro:', error);
    }
}
