import api from './api';

export async function getAuctions() {
    try {
        const response = await api.get('/auctions');
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}

export async function getAuctionsById(auctionId) {
    try {
        const response = await api.get(`/auctions/${auctionId}`);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}
