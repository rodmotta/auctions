import api from './api';

export async function getBidsByAuctionId(auctionId) {
    try {
        const response = await api.get(`/bids/auction/${auctionId}`);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}

export async function placeBid(payload) {
    try {
        await api.post('/bids', payload);
    } catch (error) {
        console.error('Erro:', error);
    }
}
