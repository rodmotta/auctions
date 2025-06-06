import api from './api';

export async function getAuctions() {
    try {
        const config = {
            skipAuth: true
        }
        const response = await api.get('/auctions', config);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}

export async function getAuctionsById(auctionId) {
    try {
        const config = {
            skipAuth: true
        }
        const response = await api.get(`/auctions/${auctionId}`, config);
        return response.data;
    } catch (error) {
        console.error('Erro:', error);
    }
}
