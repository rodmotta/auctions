import api from './api';

export function getBidsByAuctionId(auctionId) {
    try {
        const config = {
            skipAuth: true
        }
        return api.get(`/bids/auction/${auctionId}`, config);
    } catch (error) {
        console.error('Erro:', error);
    }
}

export function placeBid(payload) {
    try {
        api.post('/bids', payload);
    } catch (error) {
        console.error('Erro:', error);
    }
}
