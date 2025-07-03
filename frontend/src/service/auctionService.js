import api from './api';

export function getAuctions(status) {
    try {
        const config = {
            params: { status },
            skipAuth: true
        }
        return api.get('/auctions', config);
    } catch (error) {
        console.error('Erro:', error);
    }
}

export function getAuctionsById(auctionId) {
    try {
        const config = {
            skipAuth: true
        }
        return api.get(`/auctions/${auctionId}`, config);
    } catch (error) {
        console.error('Erro:', error);
    }
}
